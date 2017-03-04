import analysis.Analysis;
import analysis.ContentSource;
import schema.FlightData;
import sql.SQLLiteModel;
import sql.SQLModel;
import storage.CSVImport;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lloyd on 04/03/2017.
 */
public class Main {

    FlightData flightData;
    List<Analysis> analysisList = new ArrayList<>();


    public static void main(String[] args) {
        Main main = new Main();
        main.importCSV();
        main.runAnalysis();
    }

    public void runAnalysis(){
        SQLModel sqlModel = new SQLLiteModel(flightData.getName());

        try {
            PreparedStatement preparedStatement = sqlModel.getPreparedStatement("SELECT MAX(id) FROM " + flightData.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            int numberResults = resultSet.getInt("MAX(id)");
            resultSet.close();

            analysisList.add(new ContentSource(flightData, sqlModel));

            for (int row = 1; row < numberResults; row ++){
                for (Analysis analysis: analysisList) {
                    analysis.performAnalysisOnNextRow();
                }
            }

            for (Analysis analysis: analysisList ) {
                HashMap<Integer, List<Integer>> results= analysis.getResults();
                for (Map.Entry<Integer, List<Integer>> entry : results.entrySet()) {
                    System.out.print(entry.getKey());
                    System.out.print(": ");
                    for (int id : entry.getValue()) {
                        System.out.print(id + ",");
                    }
                    System.out.println();
                }
            }
            sqlModel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void importCSV(){
        flightData = new FlightData();
        flightData.setName("baw");

        CSVImport csvImport = new CSVImport();
        File file = new File("_data/clean/" + flightData.getName() + ".csv");

        try {
            csvImport.loadFlightData(flightData, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
