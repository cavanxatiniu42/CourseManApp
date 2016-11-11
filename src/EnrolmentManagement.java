import java.sql.SQLException;

/**
 * Created by Thu Thuy Nguyen on 25/10/2016.
 */
public class EnrolmentManagement {
    private MyDBApp myDBApp;

    public EnrolmentManagement (MyDBApp myDBApp){
        this.myDBApp = myDBApp;
    }

    public  boolean addEnrolment (int studentId, String courseId, int semester) throws SQLException {
        final char defaultFinalGrade = 'F';
        if (validateCourseId(courseId)&&validateStudentId(studentId)&&validateSemester(semester)){
            if (isExistCourse(courseId)&&isExistStudent(studentId)){
                if (isExistEnrolment(studentId,courseId)){
                    if (isEligibleToEnroll(studentId,courseId)){
                        String sql = String.format("INSERT INTO Enrolment VALUES (%d,'%s',%d, '%s');", studentId, courseId, semester, defaultFinalGrade );
                        return myDBApp.insert(sql);
                    }
                }
            }
        }
        return false;
    }

    public boolean updateFinalGrade (int studentId, String courseId, char finalGrade) throws SQLException {
        if (validateStudentId(studentId) && validateCourseId(courseId) && validateFinalGrade(finalGrade)){
            if (!isExistEnrolment(studentId,courseId)){
                String sql = String.format("UPDATE enrolment \n" +
                        "SET finalgrade = '%s' \n" +
                        "WHERE student = %d AND course = '%s';", finalGrade, studentId, courseId);
                return myDBApp.update(sql);
            }
        }
        return false;
    }

    public String allEnrolment() throws SQLException {
        String sql =  "SELECT student.studentid, student.firstname, student.lastname, enrolment.course, enrolment.semester, enrolment.finalgrade" +
                " FROM enrolment INNER JOIN student ON enrolment.student = student.studentid";
        return myDBApp.selectAll(sql);
    }

    public String allEnrolmentToHtmlFile(){
         String sql = "SELECT student.studentid, student.firstname, student.lastname, enrolment.course, enrolment.semester, enrolment.finalgrade" +
                 " FROM enrolment INNER JOIN student ON enrolment.student = student.studentid";
         return myDBApp.selectToHtmlFile(sql, "enrols.html");
     }

    public String allEnrolmentInSortedOrder(){
         String sql = "SELECT student.firstname, student.lastname, enrolment.course, enrolment.semester, enrolment.finalgrade " +
                 "FROM enrolment INNER JOIN student ON enrolment.student = student.studentid " +
                 "ORDER BY CASE finalgrade " +
                 "WHEN  'E' THEN 1 " +
                 "WHEN  'G' THEN 2 " +
                 "WHEN  'P' THEN 3 " +
                 "WHEN  'F' THEN 4 " +
                 "ELSE 5 END";
        return myDBApp.selectToHtmlFile(sql, "enrols_sorted.html");
     }

    private boolean isEligibleToEnroll(int studentId, String courseId) throws SQLException {
        String sql = String.format("SELECT prerequisites FROM course WHERE courseid = '%s';", courseId);
        String prerequisites = myDBApp.selectAll(sql).substring(0,3);
        String sql2 = "SELECT finalgrade FROM enrolment WHERE student = " +studentId+" AND course = '"+prerequisites+"'";
        if (myDBApp.selectAll(sql2).equals(" ")){
            return false;
        }
        return true;
    }

    private boolean isExistEnrolment(int studentId, String courseId) throws SQLException {
         String sql ="SELECT * FROM enrolment WHERE student ="+ studentId+" AND course = '"+ courseId+"';";
         if (myDBApp.selectAll(sql).equals("")){
             return true;
         }
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

    private boolean isExistCourse(String courseId) throws SQLException {
        String sql = "SELECT courseid FROM course WHERE courseid = '" + courseId+"'";
        if (!myDBApp.selectAll(sql).equals("")){
            return true;
        }
        System.err.println("Course does not exist.");
        return false;
    }

    private boolean validateStudentId (int studentId){
        if (studentId > 0){
            return true;
        }
        System.err.println("Student ID has to be greater than 0");
        return false;
    }

    private boolean validateCourseId (String courseId){
        if (courseId!= null && courseId.length()<5){
            return true;
        }
        System.err.println("Course Id is invalid");
        return false;
    }

    private boolean validateSemester(int semester){
        if (semester>0 && semester<=8){
            return true;
        }
        System.err.println("Invalid Semester");
        return false;
    }

    private boolean validateFinalGrade (char finalGrade){
        if (finalGrade == 'E'|| finalGrade == 'G' || finalGrade == 'P' || finalGrade == 'F'){
            return true;
        }
        System.err.println("Invalid final grade");
        return false;
    }


}
