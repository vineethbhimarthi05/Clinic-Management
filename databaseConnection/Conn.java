package databaseConnection;
import java.sql.*;
public class Conn {


    public  Connection conn;
    public Statement s;
    public Conn(){
        //PreparedStatement s;
        try{
            //Class.forName(com.mysql.cj.jdbc.Driver);
             conn=DriverManager.getConnection("jdbc:mysql:///clinic_management","root","abhinay2247");
             s=conn.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
