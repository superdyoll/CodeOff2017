import schema.FlightData;
import sql.SQLLiteModel;
import sql.SQLModel;
import storage.CSVImport;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Lloyd on 04/03/2017.
 */
public class Main {
    public static void main(String[] args) {
        CSVImport csvImport = new CSVImport();
        File file = new File("_data/clean/bee.csv");

        FlightData flightData = new FlightData();
        flightData.setName("bee");

        try {
            csvImport.loadFlightData(flightData, file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SQLModel sqlModel = new SQLLiteModel("bee");

        try {
            PreparedStatement preparedStatement = sqlModel.getPreparedStatement("SELECT COUNT(timestamp) FROM bee");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                System.out.println(resultSet.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
