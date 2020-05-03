import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class commands {

    public static void newClass(Connection conn, String cnum, String term, int snum, String desc) {

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement("call newclass(?,?,?,?)");
            stmt.setString(1, cnum);
            stmt.setString(2, term);
            stmt.setInt(3, snum);
            stmt.setString(4, desc);
            rs = stmt.executeQuery();
            // Now do something with the ResultSet ....
            
        } catch (SQLException ex) {
            // handle any errors
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
        } finally {
            // it is a good idea to release resources in a finally{} block
            // in reverse-order of their creation if they are no-longer needed
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore
                stmt = null;
            }
        }
    }

    public static void addStudent(Connection conn, String uname, int sID, String last, String first) {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        int class_id = 0;

        try {
            Statement stmt1 = conn.createStatement();
            ResultSet rs1 = stmt1.executeQuery("SELECT class_id FROM class WHERE term LIKE '%Sp%20%';");
            while(rs1.next()){
                class_id = rs1.getInt("class_id");
                System.out.println(class_id);
                stmt = conn.prepareStatement("call addstudent(?,?,?,?,?)");
                stmt.setString(1, uname);
                stmt.setInt(2, sID);
                stmt.setString(3, last);
                stmt.setString(4, first);
                stmt.setInt(5, class_id);
                rs = stmt.executeQuery();
                // Now do something with the ResultSet ....
            }
            
        } catch (SQLException ex) {
            // handle any errors
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
        } finally {
            // it is a good idea to release resources in a finally{} block
            // in reverse-order of their creation if they are no-longer needed
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore
                stmt = null;
            }
        }
    }

    public static void main(String[] args) {
        Connection conn = null;

        try {
            // The newInstance() call is a work around for some broken Java implementations
            Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-06.cleardb.net:3306/heroku_ffcbf3ee2ade5c2?sslmode=require&user=b6956053778dcf&password=1d34da0b");
            if (args.length == 0){
                System.out.println ("Usage :   new-class course_number term section_number description");
                System.out.println ("Usage :   ");
                System.out.println ("Usage :   ");
                return;
            }

            if (args[0].equals("new-class")){
                System.out.println("Creating a new class");
                newClass(conn, args[1], args[2],Integer.parseInt(args[3]), args[4]);
            } else if (args[0].equals("add-student")){
                System.out.println("Adding a student");
                addStudent(conn, args[1], Integer.parseInt(args[2]), args[3], args[4]);
            }

        } catch (Exception ex) {
            // handle the error
            System.err.println(ex);
        }
    }
}