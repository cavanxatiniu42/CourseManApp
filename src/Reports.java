import java.sql.SQLException;

/**
 * Created by Thu Thuy Nguyen on 02/11/2016.
 * overview: a class to show all reports
 * attribute: myDBApp MyDBApp
 */
public class Reports {
    private MyDBApp myDBApp;

    /**
     * effect: create Reports object
     * @param myDBApp MyDBApp
     */
    public Reports (MyDBApp myDBApp){
        this.myDBApp = myDBApp;
    }

    /**
     * effect if exist student
     *              using selectToHtmlFile method in MyDBApp to write all courses of this student in an HTML file
     * @param studentid int
     * @return String
     * throws SQLException
     */
    public String courseOfAStudent (int studentid) throws SQLException {
        String sql = "SELECT enrolment.course,course.name, enrolment.semester, enrolment.finalgrade, student.firstname, student.lastname\n" +
                "FROM enrolment " +
                "INNER JOIN student ON enrolment.student = student.studentid " +
                "INNER JOIN course ON enrolment.course = course.courseid\n" +
                "WHERE student = 1";
        if (isExistStudent(studentid)){
            return myDBApp.selectToHtmlFile(sql, "my_enrols.html");
        }
        return null;
    }

    /**
     * effect: if course is exist
     *              using selectToHtmlFile method in MyDBApp to write all students in a course in an HTML file
     * @param courseid String
     * @return String
     * throws SQLException
     */
    public String studentsOfACourse (String courseid) throws SQLException{
        String sql = "SELECT enrolment.student, enrolment.course, enrolment.finalgrade, student.firstname, student.lastname " +
                "FROM enrolment " +
                "INNER JOIN student ON enrolment.student = student.studentid " +
                "WHERE course = '"+courseid+"';";
        if (isExistCourse(courseid)){
            return myDBApp.selectToHtmlFile(sql, "course_enrols.html");
        }
        return null;
    }

    /**
     * effect: using selectToHtmlFile method in MyDBApp to write all students failed at least one course in an HTML file
     * @return String
     */
    public String failedStudent (){
        String sql = "SELECT student.studentid, student.firstname, student.lastname, enrolment.course, course.name\n" +
                "                FROM enrolment \n" +
                "                INNER JOIN student ON enrolment.student = student.studentid\n" +
                "                INNER JOIN course ON enrolment.course = course.courseid\n" +
                "                 WHERE finalgrade = 'F';";
        return myDBApp.selectToHtmlFile(sql, "fails.html");
    }

    /**
     * effect: using selectAll method in MyDBApp to select course where courseId = courseId
     *          if result equals " "
     *              return false
     *          else
     *              return true
     * @param courseId String
     * @return boolean
     * throws SQLException
     */
    private boolean isExistCourse(String courseId) throws SQLException {
        String sql = "SELECT courseid FROM course WHERE courseid = '" + courseId+"'";
        if (!myDBApp.selectAll(sql).equals("")){
            return true;
        }
        System.err.println("Course does not exist.");
        return false;
    }

    /**
     * effect: using selectAll method in MyDBApp to select student where studentId = studentId
     *          if result equals " "
     *              return false
     *          else
     *              return true
     * @param studentId int
     * @return boolean
     * throws SQLException
     */
    private boolean isExistStudent(int studentId) throws SQLException {
        String sql = "SELECT studentid FROM student WHERE studentid =  " + studentId;
        if (!myDBApp.selectAll(sql).equals("")){
            return true;
        }
        System.err.println("Student does not exist.");
        return false;
    }
}
