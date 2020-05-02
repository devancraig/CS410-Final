import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class commands {


    public static void main(String[] args) {
        Connection c = null;


        try {
            // The newInstance() call is a work around for some broken Java implementations
            Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-06.cleardb.net:3306/heroku_ffcbf3ee2ade5c2?sslmode=require&user=b6956053778dcf&password=1d34da0b");
            Statement stmt = c.createStatement();
            ResultSet rs=stmt.executeQuery("select * from class");
            while(rs.next()){
                System.out.println(rs.getInt(1)+" "+rs.getString(2));
            } 

        } catch (Exception ex) {
            // handle the error
            System.err.println(ex);
        }
    }
}