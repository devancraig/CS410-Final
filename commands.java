import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.*;
import java.util.*; 

public class commands {

    public static String currentClass = "";
    public static String classTextFile = "currentClass.txt";

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

            class_id = showClassId();
            stmt = conn.prepareStatement("call addstudent(?,?,?,?,?)");
            stmt.setString(1, uname);
            stmt.setInt(2, sID);
            stmt.setString(3, last);
            stmt.setString(4, first);
            stmt.setInt(5, class_id);
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

    public static void addAssignment(Connection conn, String name, String category, String desc, int points) {

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            Statement stmt1 = conn.createStatement();
            ResultSet rs1 = stmt1.executeQuery(
                    String.format("SELECT * FROM categories " + "WHERE class_id =  %d " + " AND NAME LIKE '%%%s%%'; ",
                            showClassId(), category));
            while (rs1.next()) {
                int cat_id = rs1.getInt("cat_id");
                System.out.println(cat_id);
                stmt = conn.prepareStatement("call addassignment(?,?,?,?)");
                stmt.setInt(1, cat_id);
                stmt.setString(2, name);
                stmt.setString(3, desc);
                stmt.setInt(4, points);
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

    public static void addCategory(Connection conn, String name, Double weight) {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        int class_id = showClassId();

        try {

            System.out.println(class_id);
            stmt = conn.prepareStatement("call addcategories(?,?,?)");
            stmt.setInt(1, class_id);
            stmt.setString(2, name);
            stmt.setDouble(3, weight);
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

    public static void addGrade(Connection conn, String assignName, String username, Double grade) {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        int class_id = showClassId();
        int studentId = 0;
        int assignId = 0;
        String letter = null;

        try {

            String query = String.format("SELECT * FROM students " +
                                          " WHERE username = '%s' AND class_id = %s; ", username, showClassId());

            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet s = st.executeQuery(query);

            String courseNumber = "";
            // iterate through the java resultset
            while (s.next()) {
                studentId = Integer.parseInt(s.getString("s_id"));
            }
            
            stmt = conn.prepareStatement("call getassignid(?,?)");
            stmt.setString(1, assignName);
            stmt.setInt(2, class_id);
            rs = stmt.executeQuery();

            while(rs.next()){
                assignId = Integer.parseInt(rs.getString("assign_id"));
            }
    
            if(grade >= 90){
                letter = "A";
            }else if (grade >= 80){
                letter = "B";
            } else if (grade >= 70){
                letter = "C";
            } else if (grade >= 60){
                letter = "D";
            } else {
                letter = "F";
            }

            stmt = conn.prepareStatement("call addgrade(?,?,?,?)");
            stmt.setInt(1, assignId);
            stmt.setInt(2, studentId);
            stmt.setString(3, letter);
            stmt.setDouble(4, grade);
            ResultSet rst = stmt.executeQuery();

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

    public static void studentGrades(Connection conn, String name) {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        ResultSet s = null;
        int class_id = showClassId();
        List<String> catName = new ArrayList<String>();
        List<String> assignName = new ArrayList<String>();
        List<Integer> weight = new ArrayList<Integer>();
        List<Integer> pv = new ArrayList<Integer>();
        List<Double> grade = new ArrayList<Double>();
        double fgrade = 0;

        try {

            stmt = conn.prepareStatement("call studentgrade(?,?)");
            stmt.setString(1, name);
            stmt.setInt(2, class_id);
            rs = stmt.executeQuery();

            while(rs.next()){
                catName.add(rs.getString("catName"));
                assignName.add(rs.getString("assignName"));
                weight.add(rs.getInt("weight"));
                pv.add(rs.getInt("point_value"));
                grade.add(rs.getDouble("grade"));
            }

            for(int i = 0; i < catName.size(); i++){
                System.out.println("Category: " + catName.get(i) + " | Assignment: " + assignName.get(i) + " | Grade: " + grade.get(i) + "/" + pv.get(0));
            }

            stmt = conn.prepareStatement("call studentfinalgrade(?)");
            stmt.setInt(1, class_id);
            s = stmt.executeQuery();

            while(s.next()){
                fgrade += s.getDouble("total");
            }

            System.out.println("Current Grade: " + fgrade);
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

    

    public static void selectClass3(Connection conn, String courseNum, String term, int sectionNum) {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        int class_id = 0;

        try {
            String query = String.format("SELECT * FROM class " + "WHERE course_number like '%s'" + "AND term LIKE '%s'"
                    + "AND section_number LIKE '%d' ;", courseNum, term, sectionNum);

            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet s = st.executeQuery(query);

            String courseNumber = "";
            int size = 0;

            // iterate through the java resultset
            while (s.next()) {
                courseNumber = s.getString("course_number");
                class_id = s.getInt("class_id");
                size++;
            }

            if (size == 1) {
                currentClass = courseNumber;
                System.out.println("Current class: " + currentClass);
                System.out.println("Class ID: " + class_id);

                try {
                    File file = new File(classTextFile);

                    FileWriter fw = new FileWriter(file.getAbsoluteFile());
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(currentClass + "\n");
                    bw.write(Integer.toString(class_id));
                    bw.close();

                } catch (IOException e) {
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

    public static void selectClass2(Connection conn, String courseNum, String term) {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        int class_id = 0;

        try {
            String query = String.format(
                    "SELECT * FROM class " + "WHERE course_number like '%s'" + "AND term LIKE '%s'; ", courseNum, term);

            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet s = st.executeQuery(query);

            String courseNumber = "";
            int size = 0;

            // iterate through the java resultset
            while (s.next()) {
                courseNumber = s.getString("course_number");
                class_id = s.getInt("class_id");
                size++;
            }

            if (size == 1) {
                currentClass = courseNumber;
                System.out.println("Current class: " + currentClass);
                System.out.println("Class ID: " + class_id);

                try {
                    File file = new File(classTextFile);

                    FileWriter fw = new FileWriter(file.getAbsoluteFile());
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(currentClass + "\n");
                    bw.write(Integer.toString(class_id));
                    bw.close();

                } catch (IOException e) {
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

    public static void selectClass1(Connection conn, String courseNum) {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        int class_id = 0;

        try {
            String query = String.format("SELECT * FROM class " + "WHERE course_number like '%s'; ", courseNum);

            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet s = st.executeQuery(query);

            String courseNumber = "";
            int size = 0;

            // iterate through the java resultset
            while (s.next()) {
                courseNumber = s.getString("course_number");
                class_id = s.getInt("class_id");
                size++;
            }

            if (size == 1) {
                currentClass = courseNumber;
                System.out.println("Current class: " + currentClass);
                System.out.println("Class ID: " + class_id);

                try {
                    File file = new File(classTextFile);

                    FileWriter fw = new FileWriter(file.getAbsoluteFile());
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(currentClass + "\n");
                    bw.write(Integer.toString(class_id));
                    bw.close();

                } catch (IOException e) {
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

    public static String showClass() {

        try {
            File file = new File(classTextFile);

            BufferedReader br = new BufferedReader(new FileReader(file));

            String currClass;
            if ((currClass = br.readLine()) != null)
                // System.out.println("Current class: " + currClass);
                currentClass = currClass;
        } catch (Exception e) {
            System.out.println("No current class available.");
        }

        return currentClass;

    }

    public static int showClassId() {
        String c_id = "0";
        try {
            File file = new File(classTextFile);

            BufferedReader br = new BufferedReader(new FileReader(file));

            String classID;

            while ((classID = br.readLine()) != null)
                // System.out.println("Current class: " + currClass);
                c_id = classID;
        } catch (Exception e) {
            System.out.println("No current class available.");
        }

        return Integer.parseInt(c_id);

    }

    public static void showCategories(Connection conn) {
        String query = String.format(
                "SELECT categories.name, categories.weight FROM categories " + "left join class "
                        + "on categories.class_id = class.class_id " + "where class.course_number = '%s';",
                showClass());
        try {
            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet s = st.executeQuery(query);

            String courseNumber = "";
            int size = 0;

            // iterate through the java resultset
            while (s.next()) {
                String catName = s.getString("name");
                String weight = s.getString("weight");

                System.out.println("Category: " + catName + " , Weight: " + weight);
                size++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void gradebook(Connection conn) {
        String query = String.format(
                "SELECT SUM(total) AS Grades FROM finalgradebook");
        try {
            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet s = st.executeQuery(query);

            String courseNumber = "";
            int size = 0;

            // iterate through the java resultset
            while (s.next()) {
                Double grade = s.getDouble("Grades");

                System.out.println("Grade: " + grade);
                size++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showAssignments(Connection conn) {

        PreparedStatement stmt = null;
        ResultSet s = null;

        try {
            stmt = conn.prepareStatement("call showassignments(?)");
            stmt.setString(1, showClass());
            s = stmt.executeQuery();

            String courseNumber = "";

            // iterate through the java resultset
            while (s.next()) {
                String assignName = s.getString("name");
                String description = s.getString("description");
                String pv = s.getString("point_value");
                String cat = s.getString("category");

                System.out.println("Assignment: " + assignName + ", Categories: " + cat +", Description: " + description + ", Point Value: " + pv);
            }

        } catch (SQLException ex) {
            // handle any errors
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
        } finally {
            // it is a good idea to release resources in a finally{} block
            // in reverse-order of their creation if they are no-longer needed
            if (s != null) {
                try {
                    s.close();
                } catch (SQLException sqlEx) {
                } // ignore
                s = null;
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

    public static void showStudents(Connection conn) {
        String query = String.format("SELECT * from students " + "left join class "
                + "on students.class_id = class.class_id " + "where class.course_number = '%s';", showClass());
        try {
            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet s = st.executeQuery(query);

            String courseNumber = "";
            int size = 0;

            // iterate through the java resultset
            while (s.next()) {
                String courseNum = s.getString("course_number");
                String username = s.getString("username");
                String firstName = s.getString("firstname");
                String lastName = s.getString("lastname");
                String studentID = s.getString("StudentID");

                System.out.println(courseNum + "\t" + username + "\t" + firstName + "\t" + lastName + "\t" + studentID);
                size++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showStudents(Connection conn, String search) {

        String query = String.format(
                "SELECT * from students " + "left join class " + "on students.class_id = class.class_id "
                        + "where class.course_number = '%s' " + "AND students.username like '%%%s%%'",
                showClass(), search);
        try {
            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet s = st.executeQuery(query);

            String courseNumber = "";
            int size = 0;

            // iterate through the java resultset
            while (s.next()) {
                String courseNum = s.getString("course_number");
                String username = s.getString("username");
                String firstName = s.getString("firstname");
                String lastName = s.getString("lastname");
                String studentID = s.getString("StudentID");

                System.out.println(courseNum + "\t" + username + "\t" + firstName + "\t" + lastName + "\t" + studentID);
                size++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void listClasses(Connection conn) {

        String query = "SELECT course_number, count(*) FROM class " + "left join students "
                + "on class.class_id = students.class_id " + "group by course_number; ";
        try {
            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet s = st.executeQuery(query);

            String courseNumber = "";
            int size = 0;
            System.out.println("Class:" + "\t\t" + "# Students:");

            // iterate through the java resultset
            while (s.next()) {
                String courseNum = s.getString("course_number");
                int stuCount = s.getInt("count(*)");

                System.out.println(courseNum + "\t\t\t" + stuCount);
                size++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Connection conn = null;

        try {
            // The newInstance() call is a work around for some broken Java implementations
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://us-cdbr-east-06.cleardb.net:3306/heroku_ffcbf3ee2ade5c2?sslmode=require&user=b6956053778dcf&password=1d34da0b");
            if (args.length == 0) {
                System.out.println("Usage :   new-class course_number term section_number description");
                System.out.println("Usage :   show-class");
                System.out.println("Usage :   show-categories");
                return;
            }

            if (args[0].equals("new-class")) {
                System.out.println("Creating a new class");
                newClass(conn, args[1], args[2], Integer.parseInt(args[3]), args[4]);
            } else if (args[0].equals("add-student")) {
                System.out.println("Adding a student");
                addStudent(conn, args[1], Integer.parseInt(args[2]), args[3], args[4]);
            } else if (args[0].equals("add-assignment")) {
                System.out.println("Adding an assignment");
                addAssignment(conn, args[1], args[2], args[3], Integer.parseInt(args[4]));
            } else if (args[0].equals("add-category")) {
                System.out.println("Adding a category");
                addCategory(conn, args[1], Double.parseDouble(args[2]));
            } else if (args[0].equals("grade")) {
                System.out.println("Adding a grade");
                addGrade(conn, args[1], args[2],Double.parseDouble(args[3]));   
            }  else if (args[0].equals("select-class")) {
                System.out.println("Selecting a class");
                if (args.length == 4) {
                    selectClass3(conn, args[1], args[2], Integer.parseInt(args[3]));
                } else if (args.length == 3) {
                    selectClass2(conn, args[1], args[2]);
                } else if (args.length == 2) {
                    selectClass1(conn, args[1]);
                }

            } else if (args[0].equals("show-class")) {
                System.out.println("Showing Current Class: " + showClass());
            } else if (args[0].equals("show-classid")) {
                System.out.println("Showing Class id: " + showClassId());
            } else if (args[0].equals("show-categories")) {
                System.out.println("Class: " + showClass());
                System.out.println("Showing Class Categories: ");
                showCategories(conn);
            } else if (args[0].equals("show-assignment")){
                System.out.println("Class: " + showClass());
                System.out.println("Showing Assignments: ");
                showAssignments(conn);
            } 
            else if (args[0].equals("show-students")) {
                System.out.println("Current Class Students: ");
                if (args.length == 1) {
                    showStudents(conn);
                } else if (args.length == 2) {
                    showStudents(conn, args[1]);
                }
            } else if (args[0].equals("list-classes")) {
                System.out.println("Listing Classes: \n");
                listClasses(conn);
            } else if (args[0].equals("student-grade")) {
                System.out.println("Class: " + showClass());
                System.out.println("Displaying Students grades");
                studentGrades(conn, args[1]);
            } else if (args[0].equals("gradebook")) {
                System.out.println("Class: " + showClass());
                System.out.println("Displaying gradebook");
                gradebook(conn);
            } 

        } catch (Exception ex) {
            // handle the error
            System.err.println(ex);
        }
    }
}