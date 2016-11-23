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
        int choice;
        do {
            TextIO.putln("===========Course Man App============");
            TextIO.putln("1. Menu Student");
            TextIO.putln("2. Menu Course");
            TextIO.putln("3. Menu Enrolment");
            TextIO.putln("4. Reports");
            TextIO.putln("0. Exit");
            switch (choice= TextIO.getInt()){
                case 1:
                    menuStudent(myDBApp);
                    break;
                case 2:
                    menuCourse(myDBApp);
                    break;
                case 3:
                    menuEnrolment(myDBApp);
                    break;
                case 4:
                    report(myDBApp);
                    break;
            }
        }while (choice != 0);
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
                    if (menuStudent.isExistStudent(studentId)){
                        TextIO.putln("Student existed");
                        break;
                    }
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
                        TextIO.putln("Failed! \n" +
                                     "Check input!");
                    }
                    break;
                case 2:
                    TextIO.putln("Input studentID you want to edit: ");
                    int studentID = TextIO.getInt();
                    if (!menuStudent.isExistStudent(studentID)){
                        TextIO.putln("Student does not exist");
                        break;
                    }
                    TextIO.putln(myDBApp.selectAll("SELECT * FROM student WHERE studentid = '" + studentID+ "';"));
                    TextIO.putln("Is this student you want to edit?\r\n" +
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
                        TextIO.putln("Failed \n" +
                                "Check input");
                    }
                    break;
                case 3:
                    TextIO.putln("Input student id you want to delete? ");
                    int id = TextIO.getInt();
                    if (!menuStudent.isExistStudent(id)){
                        TextIO.putln("Student does not exist");
                        break;
                    }
                    TextIO.putln(myDBApp.selectAll("SELECT * FROM student WHERE studentid = '" + id+ "';"));
                    TextIO.putln("Is this student you want to delete? \r\n" +
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

    public static void menuCourse (MyDBApp myDBApp) throws SQLException {
        MenuCourse menuCourse = new MenuCourse(myDBApp);
        int choice;
        do {
            TextIO.putln("==================Menu Course===================");
            TextIO.putln("1. Add a course");
            TextIO.putln("2. Edit course");
            TextIO.putln("3. Delete a course");
            TextIO.putln("4. Show all courses");
            TextIO.putln("5. Show all courses in an HTML file");
            TextIO.putln("0. Exit");
            switch (choice = TextIO.getInt()){
                case 1:
                    TextIO.getln();
                    TextIO.putln("Input course id: ");
                    String courseId = TextIO.getln();
                    if (menuCourse.isExistCourse(courseId)){
                        TextIO.putln("Course existed");
                        break;
                    }
                    TextIO.putln("Input course name: ");
                    String courseName = TextIO.getln();
                    TextIO.putln("Input course's prerequisite: ");
                    String prerequisite = TextIO.getln();
                    if (menuCourse.addCourse(courseId, courseName, prerequisite)){
                        TextIO.putln("Add successfully");
                    } else {
                        TextIO.putln("Failed");
                    }
                    break;
                case 2:
                    TextIO.getln();
                    TextIO.putln("Input course id you want to edit: ");
                    String courseID = TextIO.getln();
                    if (!menuCourse.isExistCourse(courseID)){
                        TextIO.putln("Course does not exist");
                        break;
                    }
                    TextIO.putln(myDBApp.selectAll("SELECT * FROM course WHERE courseid = '" + courseID+ "'"));
                    TextIO.putln("Is this student you want to edit? \r\n" +
                            "If yes, press 1; else press 0?");
                    int c = TextIO.getInt();
                    if (c == 0){
                        break;
                    }
                    TextIO.getln();
                    TextIO.putln("Input new course name: ");
                    String newCourseName = TextIO.getln();
                    TextIO.putln("Input new prerequisite: ");
                    String newPrerequisite = TextIO.getln();
                    if (menuCourse.updateCourse(courseID, newCourseName, newPrerequisite)){
                        TextIO.putln("Edit successfully");
                    } else {
                        TextIO.putln("Failed");
                    }
                    break;
                case 3:
                    TextIO.getln();
                    TextIO.putln("Input course id you want to delete? ");
                    String ID = TextIO.getln();
                    if (!menuCourse.isExistCourse(ID)){
                        TextIO.putln("Course does not exist");
                        break;
                    }
                    TextIO.putln(myDBApp.selectAll("SELECT * FROM course WHERE courseid = '" + ID+"'"));
                    TextIO.putln("Is this the course you want to delete? \r\n" +
                                 "If yes, press 1, else press 0?");
                    int c2 = TextIO.getInt();
                    if (c2 == 0){
                        break;
                    }
                    if (menuCourse.deleteCourse(ID)){
                        TextIO.putln("Delete successfully");
                    } else {
                        TextIO.putln("Failed");
                    }
                    break;
                case 4:
                    TextIO.putln(menuCourse.allCourse());
                    break;
                case 5:
                    TextIO.putln(menuCourse.showAllCourseInHtmlFile());


            }
        } while (choice != 0);
    }

    public static void menuEnrolment (MyDBApp myDBApp) throws SQLException {
        EnrolmentManagement enrolmentManagement = new EnrolmentManagement(myDBApp);
        int choice;
        do {
            TextIO.putln("==================Menu Enrolment===================");
            TextIO.putln("1. Add a enrolment");
            TextIO.putln("2. Update final grade");
            TextIO.putln("3. Show all enrolments");
            TextIO.putln("4. Show all enrolments in HTML file");
            TextIO.putln("5. Show all courses in an HTML file sorted");
            TextIO.putln("0. Exit");
            switch (choice = TextIO.getInt()){
                case 1:
                    TextIO.putln("Input student id");
                    int studentID = TextIO.getInt();
                    TextIO.getln();
                    TextIO.putln("Input course id ");
                    String courseId = TextIO.getln();
                    TextIO.putln("Input semester");
                    int semester = TextIO.getInt();
                    if (enrolmentManagement.addEnrolment(studentID, courseId, semester)){
                        TextIO.putln("Add successfully");
                    } else {
                        TextIO.putln("failed");
                    }
                    break;
                case 2:
                    TextIO.putln("Input student id: ");
                    int studentId = TextIO.getInt();
                    TextIO.getln();
                    TextIO.putln("Input course id: ");
                    String courseID = TextIO.getln();
                    if (enrolmentManagement.isExistEnrolment(studentId,courseID)){
                        TextIO.putln("Enrolment does not exist");
                        break;
                    }
                    TextIO.putln(myDBApp.selectAll("SELECT enrolment.course, student.firstname, student.lastname, enrolment.finalgrade " +
                            "FROM enrolment INNER JOIN student ON enrolment.student = student.studentid " +
                            "WHERE course = '"+courseID+"' AND student = "+studentId+";"));
                    TextIO.putln("Is this enrolment you want to update? \r\n" +
                                 "If yes, press 1, else press 0?");
                    int c = TextIO.getInt();
                    if (c == 0){
                        break;
                    }
                    TextIO.putln("Input new final grade: ");
                    char finalGrade = TextIO.getChar();
                    if (enrolmentManagement.updateFinalGrade(studentId,courseID,finalGrade)){
                        TextIO.putln("Update successfully");
                    } else {
                        TextIO.putln("fail");
                    }
                    break;
                case 3:
                    TextIO.putln(enrolmentManagement.allEnrolment());
                    break;
                case 4:
                    TextIO.putln(enrolmentManagement.allEnrolmentToHtmlFile());
                    break;
                case 5:
                    TextIO.putln(enrolmentManagement.allEnrolmentInSortedOrder());
            }
        }while (choice != 0);

    }

    public static void report(MyDBApp myDBApp) throws SQLException {
        Reports reports = new Reports(myDBApp);
        int choice;
        do {
            TextIO.putln("==================Reports==================");
            TextIO.putln("1. Display all courses of a student");
            TextIO.putln("2. Display all students in a course");
            TextIO.putln("3. Display all students failing at least one course");
            switch (choice = TextIO.getInt()){
                case 1:
                    TextIO.putln("Input student id: ");
                    int studentId = TextIO.getInt();
                    TextIO.putln(reports.courseOfAStudent(studentId));
                    break;
                case 2:
                    TextIO.getln();
                    TextIO.putln("Input course id: ");
                    String courseId = TextIO.getln();
                    TextIO.putln(reports.studentsOfACourse(courseId));
                    break;
                case 3:
                    TextIO.putln(reports.failedStudent());
                    break;
            }
        } while (choice != 0);
    }

}
