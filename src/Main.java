import javax.xml.bind.SchemaOutputResolver;
import java.sql.SQLException;
import java.time.Year;

/**
 * Created by Thu Thuy Nguyen on 20/10/2016.
 */
public class Main {
    public static void main(String[] args) throws SQLException {
        MyDBApp myDBApp = new MyDBApp("jdbc:postgresql:%s");
        myDBApp.connect("courseman", "postgres", "123456");

        EnrolmentManagement enrolmentManagement = new EnrolmentManagement(myDBApp);
        Reports reports = new Reports(myDBApp);

        menuStudent(myDBApp);
//        MenuStudent menuStudent = new MenuStudent(myDBApp);
//        System.out.println(menuStudent.validateID(12));

        myDBApp.close();
    }
    public static void menuStudent (MyDBApp myDBApp) throws SQLException {
        MenuStudent menuStudent = new MenuStudent(myDBApp);
        int choice;
        do {
            TextIO.putln("==================Menu Student===================");
            TextIO.putln("1. Add a student");
            TextIO.putln("2. Edit student");
            TextIO.putln("3. Delete a student");
            TextIO.putln("4. Show all students");
            TextIO.putln("5. Show all students in an HTML file");
            TextIO.putln("0. Exit");
            switch (choice = TextIO.getInt()){
                case 1:
                    TextIO.putln("Input studentID: ");
                    int studentId = TextIO.getInt();
                    TextIO.putln("Input first name");
                    TextIO.getln();
                    String firstName =  TextIO.getln();
                    TextIO.putln("Input last name");
                    String lastName = TextIO.getln();
                    TextIO.putln("Input address: ");
                    String address = TextIO.getln();
                    TextIO.putln("Input birthday (dd/mm/yyyy): ");
                    String dob = TextIO.getln();
                    if (menuStudent.addStudent(studentId,firstName,lastName,address,dob)){
                        TextIO.putln("Add successfully!");
                    } else {
                        TextIO.putln("Failed!");
                    }
                    break;
                case 2:
                    TextIO.putln("Input studentID you want to edit: ");
                    int studentID = TextIO.getInt();
                    TextIO.putln(myDBApp.selectAll("SELECT * FROM student WHERE studentid = '" + studentID+ "';"));
                    TextIO.putln("Is this student you want to edit?" +
                                 "If yes, press 1; else press 0?");
                    int c = TextIO.getInt();
                    if (c == 0){
                        break;
                    }
                    TextIO.putln("Input new first name: ");
                    TextIO.getln();
                    String fName = TextIO.getln();
                    TextIO.putln("Input new last name: ");
                    String lName = TextIO.getln();
                    TextIO.putln("Input new address: ");
                    String add = TextIO.getln();
                    TextIO.putln("Input new date of birth: ");
                    String dateOfBirth = TextIO.getln();
                    if (menuStudent.updateStudent(studentID, fName, lName, add, dateOfBirth)){
                        TextIO.putln("Edit successfully");
                    } else {
                        TextIO.putln("Failed");
                    }
                    break;
                case 3:
                    TextIO.putln("Input student id you want to delete? ");
                    int id = TextIO.getInt();
                    TextIO.putln(myDBApp.selectAll("SELECT * FROM student WHERE studentid = '" + id+ "';"));
                    TextIO.putln("Is this student you want to delete?" +
                            "If yes, press 1; else press 0?");
                    int c2 = TextIO.getInt();
                    if (c2 == 0){
                        break;
                    }
                    if (menuStudent.deleteStudent(id)){
                        TextIO.putln("Delete successfully");
                    } else {
                        TextIO.putln("Failed");
                    }
                    break;
                case 4:
                    TextIO.putln(menuStudent.allStudent());
                    break;
                case 5:
                    TextIO.putln(menuStudent.showAllStudentInHtmlFile());
                    break;


            }

        } while (choice != 0);
    }

}
