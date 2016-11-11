import java.io.*;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.Properties;

public class MyDBApp {
    private Connection connection;
    private String dbDriver ;

    public MyDBApp(String driver) throws InternalError {
        this.dbDriver = driver;

        try {
            if(this.dbDriver == "jdbc:postgresql:%s") {
                Class.forName("org.postgresql.Driver");
            }

        } catch (ClassNotFoundException var3) {
            System.err.println("Failed to load database driver");
            var3.printStackTrace();
            throw new InternalError();
        }
    }

    /**
     * This method is used to connect to the database
     * @require
     * @param dbName String cannot be null
     * @param userName String cannot be null
     * @param password String cannot be null
     *
     * @effect
     *  Connect to database and return true if succeed and false if failed
     *
     * @modify
     *  this.connection
     *
     * @return boolean
     */
    public boolean connect (String dbName, String userName, String password) throws SQLException{
        try {
           // Class.forName(dbDriver);
            //jdbc:postgresql:courseman
            String connectString = String.format("jdbc:postgresql:%s", dbName);
            connection = DriverManager.getConnection(connectString, userName, password);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method is used to get rows and columns of the database according to sql statement
     * @require
     * @param sql String cannot be null
     *
     * @effect
     *  if sql is invalid
     *      throws exception
     *  else
     *      get data from database and transmit to ResultSet and display on html page
     *
     * @modify resultSet
     *
     * @return String
     */
    public String selectToHtmlFile(String sql, String path){
        String html ="";
        try(Statement statement = this.connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int count = resultSetMetaData.getColumnCount();
            html +="<meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\" />";
            html += "<table border=1> ";
            html += "<tr>";
            for (int i = 1; i <= count ; i++) {
                html += "<th>";
                html += resultSetMetaData.getColumnLabel(i);
            }
            html += "</tr>";
            while (resultSet.next()){
                html += "<tr>";
                for (int i = 1; i <= count ; i++) {
                    html += "<td>";
                    html += resultSet.getString(i);
                    html += "</td>";
                }
                html += "</tr>";
                html += "\n";
            }
                html += "</table>";
            File file = new File(path);
            BufferedWriter bufferedWriter = new BufferedWriter( new OutputStreamWriter( new FileOutputStream( file )));
            bufferedWriter.write( html );
            bufferedWriter.close();
            return "result in " + path;
        } catch (SQLException |IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String selectAll (String sql) throws SQLException {
        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int count = resultSetMetaData.getColumnCount();
            int size =0;
            StringBuilder result = new StringBuilder();
                while (resultSet.next()) {
                    size++;
                    for (int i = 1; i <=count ; i++) {
                       result.append(resultSet.getString(i));
                        if (i < count){
                            result.append(",");
                        }
                    }
                        result.append("\r\n");
                    if (size==0){
                        return "";
                    }
                }
                return result.toString();
            }
    }

    public String maxID (String sql){
        try(Statement statement = this.connection.createStatement()){
            ResultSet resultSet;
            resultSet = statement.executeQuery(sql);
            String Id = null;
            while (resultSet.next()){
                Id = resultSet.getString(1);
            }
            return Id;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method is used to update data in the database
     * @require
     * @param sql String cannot be null
     *
     * @effect
     *  if sql is invalid
     *      throws exception
     *      return false
     *  else
     *      update data in the database
     *      return true
     *
     * @modify data
     *
     * @return boolean
     */
    public boolean update (String sql){
        try(Statement statement = this.connection.createStatement()) {
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            System.out.println("Failed to update");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method is used to delete data in the database
     * @param sql String cannot be null
     *
     * @effect
     *  if sql is invalid
     *      throws exception
     *      return false
     *  else
     *      delete data in the database
     *      return true
     *
     * @modify data
     * @return boolean
     */
    public boolean delete (String sql){
        try(Statement statement = this.connection.createStatement()) {
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            System.out.println("Failed to delete");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method is used to insert data into the database
     * @require
     * @param sql String cannot be null
     *
     * @effect
     *  if sql is invalid
     *      throws exception
     *      return false
     *  else
     *      insert data into the database
     *      return true
     *
     * @modify data
     * @return boolean
     */
    public boolean insert (String sql){

        try(Statement statement = this.connection.createStatement()){
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            System.out.println("Failed to insert into database");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method is used to close the database
     * @modify this.connection
     *
     */
    public void close(){
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
