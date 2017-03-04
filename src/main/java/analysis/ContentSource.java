package analysis;

import schema.FlightData;
import sql.SQLModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.*;

/**
 * Created by Lloyd on 04/03/2017.
 */
public class ContentSource implements Analysis {

    FlightData flightData;
    SQLModel model;
    HashMap<String, Integer> contentMappings;
    HashMap<Integer, List<Integer>> resultMappings;
    int highestId = 0;
    int row = 1;

    ResultSet resultSet;

    public ContentSource(FlightData flightData, SQLModel model) {
        this.flightData = flightData;
        this.model = model;

        contentMappings = new HashMap<>();
        resultMappings = new HashMap<>();

        String sql = "SELECT content_source FROM " + flightData.getName();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = model.getPreparedStatement(sql);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void performAnalysisOnNextRow() throws Exception {
        resultSet.next();

        String contentSource = resultSet.getString("content_source");
        String identifier = null;

        if (contentSource != null) {
            Pattern pattern = Pattern.compile("identifier=(\\S+),");
            Matcher matcher = pattern.matcher(contentSource);

            if (matcher.find()) {
                identifier = matcher.group(1);
            }
        }

        if (contentMappings.containsKey(identifier)) {
            List<Integer> resultsList = resultMappings.get(contentMappings.get(identifier));
            resultsList.add(row);
        } else {
            contentMappings.put(identifier, highestId);
            List<Integer> resultsList = new ArrayList<>();
            resultsList.add(row);
            resultMappings.put(highestId, resultsList);
            highestId++;
        }

        row++;
    }

    @Override
    public HashMap<Integer, List<Integer>> getResults() {
        return resultMappings;
    }

    @Override
    public void close() throws Exception {
        model.close();
    }


}
