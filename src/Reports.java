import java.sql.SQLException;

/**
 * Created by Thu Thuy Nguyen on 02/11/2016.
 */
public class Reports {
    private MyDBApp myDBApp;

    public Reports (MyDBApp myDBApp){
        this.myDBApp = myDBApp;
    }

    public String courseOfAStudent (int studentid) throws SQLException {
        String sql = "SELECT course FROM enrolment WHERE student = " + studentid + ";";
        if (isExistStudent(studentid)){
            return myDBApp.selectToHtmlFile(sql, "my_enrols.html");
        }
        return null;
    }

    public String studentsOfACourse (String courseid) throws SQLException{
        String sql = "SELECT enrolment.student, enrolment.course, enrolment.finalgrade, student.firstname, student.lastname FROM enrolment INNER JOIN student ON enrolment.student = student.studentid WHERE course = '"+courseid+"';";
        if (isExistCourse(courseid)){
            return myDBApp.selectToHtmlFile(sql, "course_enrols.html");
        }
        return null;
    }

    public String failedStudent (){
        String sql = "SELECT * FROM enrolment WHERE finalgrade = 'F';";
        return myDBApp.selectToHtmlFile(sql, "fails.html");
    }
    private boolean isExistCourse(String courseId) throws SQLException {
        String sql = "SELECT courseid FROM course WHERE courseid = '" + courseId+"'";
        if (!myDBApp.selectAll(sql).equals("")){
            return true;
        }
        System.err.println("Course does not exist.");
        return false;
    }
    private boolean isExistStudent(int studentId) throws SQLException {
        String sql = "SELECT studentid FROM student WHERE studentid =  " + studentId;
        if (!myDBApp.selectAll(sql).equals("")){
            return true;
        }
        System.err.println("Student does not exist.");
        return false;
    }
}
