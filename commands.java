import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.*;

public class commands {

    public static String currentClass = "";
    public static String classTextFile = "/home/TANNERHALCUMB/cs410/final/currentClass.txt";


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
                } catch (SQLException sqlEx) {} // ignore
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {} // ignore
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
            while (rs1.next()) {
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
                } catch (SQLException sqlEx) {} // ignore
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {} // ignore
                stmt = null;
            }
        }
    }

    public static void selectClass3(Connection conn, String courseNum, String term, int sectionNum) {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        int class_id = 0;

        try {
            String query = String.format("SELECT * FROM class " +
                "WHERE course_number like '%s'" +
                "AND term LIKE '%s'" +
                "AND section_number LIKE '%d' ;", courseNum, term, sectionNum);

            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet s = st.executeQuery(query);

            String courseNumber = "";
            int size = 0;

            // iterate through the java resultset
            while (s.next()) {
                courseNumber = s.getString("course_number");
                size++;
            }

            if (size == 1) {
                currentClass = courseNumber;
                System.out.println("Current class: " + currentClass);
            } else {
                System.out.println("Class unable to be located.");
            }


            conn.close();

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
                } catch (SQLException sqlEx) {} // ignore
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {} // ignore
                stmt = null;
            }
        }
    }
    
    
    public static void selectClass2(Connection conn, String courseNum, String term) {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        int class_id = 0;

        try {
            String query = String.format("SELECT * FROM class " +
                "WHERE course_number like '%s'" +
                "AND term LIKE '%s'; ", courseNum, term);

            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet s = st.executeQuery(query);

            String courseNumber = "";
            int size = 0;

            // iterate through the java resultset
            while (s.next()) {
                courseNumber = s.getString("course_number");
                size++;
            }

            if (size == 1) {
                currentClass = courseNumber;
                System.out.println("Current class: " + currentClass);
            } else {
                System.out.println("Not distinct class.");
            }


            conn.close();

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
                } catch (SQLException sqlEx) {} // ignore
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {} // ignore
                stmt = null;
            }
        }
    }
    
    
    public static void selectClass1(Connection conn, String courseNum) {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        int class_id = 0;

        try {
            String query = String.format("SELECT * FROM class " +
                "WHERE course_number like '%s'; ", courseNum);

            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet s = st.executeQuery(query);

            String courseNumber = "";
            int size = 0;

            // iterate through the java resultset
            while (s.next()) {
                courseNumber = s.getString("course_number");
                size++;
            }

            if (size == 1) {
                currentClass = courseNumber;
                System.out.println("Current class: " + currentClass);
                


                try{
                  File file = new File(classTextFile);

                  FileWriter fw = new FileWriter(file.getAbsoluteFile());
                  BufferedWriter bw = new BufferedWriter(fw);
                  bw.write(currentClass);
                  bw.close();

                  }catch(IOException e){
                    e.printStackTrace();
                  }

                
            } else {
                System.out.println("Not distinct class.");
            }


            conn.close();

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
                } catch (SQLException sqlEx) {} // ignore
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {} // ignore
                stmt = null;
            }
        }
    }
    
    public static void showClass() {
    
    try {
      File file = new File(classTextFile); 
  
      BufferedReader br = new BufferedReader(new FileReader(file));
    
      String currClass; 
      while ((currClass = br.readLine()) != null) 
          System.out.println("Current class: " + currClass); 
          currentClass = currClass;
        }
        catch(Exception e){
          System.out.println("No current class available.");
        }
      
    }
    
    

    public static void main(String[] args) {
        Connection conn = null;

        try {
            // The newInstance() call is a work around for some broken Java implementations
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-06.cleardb.net:3306/heroku_ffcbf3ee2ade5c2?sslmode=require&user=b6956053778dcf&password=1d34da0b");
            if (args.length == 0) {
                System.out.println("Usage :   new-class course_number term section_number description");
                System.out.println("Usage :   ");
                System.out.println("Usage :   ");
                return;
            }

            if (args[0].equals("new-class")) {
                System.out.println("Creating a new class");
                newClass(conn, args[1], args[2], Integer.parseInt(args[3]), args[4]);
            } else if (args[0].equals("add-student")) {
                System.out.println("Adding a student");
                addStudent(conn, args[1], Integer.parseInt(args[2]), args[3], args[4]);
            } else if (args[0].equals("select-class")) {
                System.out.println("Selecting a class");
                if (args.length == 4) {
                    selectClass3(conn, args[1], args[2], Integer.parseInt(args[3]));
                } else if (args.length == 3) {
                    selectClass2(conn, args[1], args[2]);
                } else if (args.length == 2) {
                    selectClass1(conn, args[1]);
                }

            } else if (args[0].equals("show-class")) {
                System.out.println("Showing current class");
                showClass();
            }
              

        } catch (Exception ex) {
            // handle the error
            System.err.println(ex);
        }
    }
}