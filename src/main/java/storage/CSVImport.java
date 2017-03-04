package storage;

import org.apache.commons.lang3.SystemUtils;
import org.sqlite.SQLiteConfig;
import schema.FlightData;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.*;

/**
 * Created by Lloyd on 04/03/2017.
 */
public class CSVImport {

    public boolean isDebug = true;

    public SQLiteConfig getDatabaseConfig() {

        SQLiteConfig databaseConfig = new SQLiteConfig();

        databaseConfig.setPragma(SQLiteConfig.Pragma.JOURNAL_MODE, "WAL");
        databaseConfig.setPragma(SQLiteConfig.Pragma.SYNCHRONOUS, "NORMAL");
        databaseConfig.setPragma(SQLiteConfig.Pragma.CACHE_SIZE, "10000");

        return databaseConfig;

    }

    /**
     * Returns the commands for importing a CSV file
     * using the compiled sqlite binary
     *
     * @param file
     * @param tableName
     * @return
     */
    public String loadCSVtoDB(File file, String tableName) {
        if (isDebug) {
            System.out.println("Loading file" + file.getPath());
        }

        return "begin;\n.mode csv \n.separator \",\"\n.import "
                + file.getPath().replace("\\", "\\\\")
                + " "
                + tableName
                + "\nend transaction;";
    }

    public ProcessBuilder returnProcessBuilder(FlightData flightData) throws Exception {

        if (SystemUtils.IS_OS_MAC_OSX) {

            return new ProcessBuilder("tools/osx/sqlite3", "_data/" + flightData.getName() + ".db");
        } else {
            if (SystemUtils.IS_OS_WINDOWS) {

                return new ProcessBuilder("tools/win/sqlite3", "_data/" + flightData.getName() + ".db");
            } else if (SystemUtils.IS_OS_LINUX) {

                return new ProcessBuilder("tools/linux/sqlite3", "_data/" + flightData.getName() + ".db");

            }

        }
        throw new Exception("Host is not a supported operating system (osx, windows or linux)");


    }

    //creates a new db for the specified flightData and loads unzipped log files into the flightData
    public void loadFlightData(FlightData flightData, File loadedFiles) throws Exception {
        ProcessBuilder processBuilder = returnProcessBuilder(flightData);

        processBuilder.redirectErrorStream(true);

        Process proc = processBuilder.start();

        System.out.println("Connected");

        //refer to the method in storageModel
        executeOnSqlite(proc, loadedFiles, flightData.getName());

        printSqliteOutput(proc);

        proc.destroy();

    }

    public void executeOnSqlite(Process proc, File file, String title) throws IOException {
        OutputStream os = proc.getOutputStream();

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));

            writer.write(loadCSVtoDB(file, title));
            writer.flush();

        writer.write("DELETE FROM "+ title +" WHERE timestamp == \'timestamp\';");
        writer.flush();
        writer.close();
    }



    public void printSqliteOutput(Process proc) throws IOException {
        InputStreamReader reader = new InputStreamReader(proc.getInputStream());

        int charCount;

        char[] readBuffer = new char[1000];

        /**
         *  Outputs the console output of the sqlite commands. This is silent.
         */
        while (true) {
            charCount = reader.read(readBuffer);
            if (charCount > 0) {
                System.out.print(new String(readBuffer, 0, charCount));
            } else {
                break;
            }
        }
    }

}
