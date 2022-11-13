package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OutilsManipulationDonnee extends Logging{

    public Connection connection(String urlDatabase, String userDatabase, String passwordDatabase) throws SQLException {
        return DriverManager.getConnection(urlDatabase, userDatabase, passwordDatabase);
    }

    public String formatageQuery(String query, String... JDDs){
        for(String JDD : JDDs){
            query = query.replaceFirst("(?:\\?\\?)+", JDD);
        }
        return query;
    }


    public ResultSet select(Connection connection, String query) throws SQLException {
        LOGGER.info("Execution de la query : " + query);
        PreparedStatement ps = connection.prepareStatement(query);
        return ps.executeQuery();
    }

    public List<String> resultQueryToString(Connection connection, String query) throws SQLException {
        ResultSet resultSet = select(connection, query);
        List<String> listResultSet = new ArrayList<>();
        while (resultSet.next()){
            listResultSet.add(resultSet.getString(1));
        }
        return listResultSet;
    }


}
