
package com.DreamzTechnology;

import java.util.*;
import java.sql.*;

class Student {
    private int student_No;
    private String student_Name;
    private String dob;
    private String doj;

    Student(int student_No, String student_Name, String dob, String doj) {
        this.student_No = student_No;
        this.student_Name = student_Name;
        this.dob = dob;
        this.doj = doj;

    }

    public int getStudent_No() {
        return student_No;
    }

    public String getDob() {
        return dob;
    }

    public String getDoj() {
        return doj;
    }

    public String getStudent_Name() {
        return student_Name;
    }

    public String toString() {
        return student_No + " " + student_Name + " " + dob + " " + doj;
    }
}

class CRUD {
    public static void main(String[] args) throws SQLException {


        String url = "jdbc:mysql://localhost:3306/mydb";
        String user = "root";
        String password = "avishkar2001";
        System.out.println("Connecting To Database");
        try {
            Connection mycom = DriverManager.getConnection(url, user, password);
            Statement mystmt = mycom.createStatement();
            String sql = "select *  from mydb.student";
            ResultSet rs = mystmt.executeQuery(sql);
            while (rs.next())
                System.out.println(rs.getInt("STUDENT_NO"));
            System.out.println("Connection sucessful!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error!");
        }


        List<Student> c = new ArrayList<Student>();
        Scanner s = new Scanner(System.in);
        Scanner s1 = new Scanner(System.in);
        int ch;
        do {
            System.out.println("1.INSERT");
            System.out.println("2.UPDATE");
            System.out.println("3.DELETE");
            System.out.println("4.DISPLAY");
            System.out.print("Enter Your Choice : ");
            ch = s.nextInt();

            switch (ch) {

                //  Add data to student table
                case 1:
                    System.out.print("Enter Student Number: ");
                    int std_no = s.nextInt();
                    System.out.print("Enter Student Name : ");
                    String std_name = s1.nextLine();
                    System.out.print("Enter Dob : ");
                    String do_birth = s1.nextLine();
                    System.out.print("Enter Doj : ");
                    String do_join = s1.nextLine();


                    c.add(new Student(std_no, std_name, do_birth, do_join));

                    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "avishkar2001")) {
                        String sql = "INSERT INTO Student (STUDENT_NO, STUDENT_NAME, STUDENT_DOB, STUDENT_DOJ) VALUES (?, ?, ?, ?)";
                        PreparedStatement statement = conn.prepareStatement(sql);
                        statement.setInt(1, std_no);
                        statement.setString(2, std_name);
                        statement.setString(3, do_birth);
                        statement.setString(4, do_join);
                        int rowsInserted = statement.executeUpdate();
                        if (rowsInserted > 0) {
                            System.out.println("Record inserted successfully!");
                        }

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    break;

                // Update data in student table.
                case 2:
                    boolean found = false;
                    System.out.print("Enter Student No to Update :");
                    int Student_NO = s.nextInt();

                    ListIterator<Student> li = c.listIterator();
                    while (li.hasNext()) {
                        Student e = li.next();
                        if (e.getStudent_No() == Student_NO) {
                            System.out.print("Enter new Student NO : ");
                            std_no = s.nextInt();
                            System.out.print("Enter new Name : ");
                            std_name = s1.nextLine();
                            System.out.print("Enter new dob : ");
                            do_birth = s1.nextLine();
                            System.out.print("Enter new doj : ");
                            do_join = s1.nextLine();
                            li.set(new Student(std_no, std_name, do_birth, do_join));


                            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "avishkar2001")) {
                                String sql = "UPDATE Student SET STUDENT_NAME=?, STUDENT_DOB=?, STUDENT_DOJ=? WHERE STUDENT_NO=?";

                                PreparedStatement statement = conn.prepareStatement(sql);
                                statement.setString(1, std_name);
                                statement.setString(2, do_birth);
                                statement.setString(3, do_join);
                                statement.setInt(4, std_no);

                                int rowsUpdated = statement.executeUpdate();
                                if (rowsUpdated > 0) {
                                    System.out.println("An existing record was updated successfully!");
                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }

                            found = true;
                        }
                    }
                    if (!found) {
                        System.out.println("Record Not Found");
                    } else {
                        System.out.println("Record Updated Successfully...!");
                    }
                    break;

                //Delete data from the student table.
                case 3:
                    found = false;
                    System.out.print("Enter Student NO to Delete :");
                    Student_NO = s.nextInt();
                    System.out.println("----------------------------");
                    Iterator<Student> i;
                    i = c.iterator();
                    while (i.hasNext()) {
                        Student e = i.next();
                        if (e.getStudent_No() == Student_NO) {
                            i.remove();
                            found = true;
                        }
                    }

                    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "avishkar2001")) {

                        String sql = "DELETE FROM STUDENT WHERE STUDENT_NO=?";

                        PreparedStatement statement = conn.prepareStatement(sql);
                        statement.setInt(1, Student_NO);

                        int rowsDeleted = statement.executeUpdate();
                        if (rowsDeleted > 0) {
                            System.out.println("A record was deleted successfully!");
                        }

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                    if (!found) {
                        System.out.println("Record Not Found");
                    } else {
                        System.out.println("Record Deleted Successfully...!");
                    }

                    System.out.println("----------------------------");
                    break;

                //Get list of student.
                case 4:
                    System.out.println("----------------------------");
                    i = c.iterator();
                    while (i.hasNext()) {
                        Student r = i.next();
                        System.out.println(r);
                    }
                    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "avishkar2001")) {

                        String sql = "SELECT * FROM STUDENT";

                        Statement statement = conn.createStatement();
                        ResultSet result = statement.executeQuery(sql);

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                    System.out.println("----------------------------");
                    break;
            }
        } while (ch != 0);
    }
}




