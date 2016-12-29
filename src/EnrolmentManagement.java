import java.sql.SQLException;

/**
 * Created by Thu Thuy Nguyen on 25/10/2016.
 * overview: a class to manage
 * attribute: myDBApp MyDBApp
 */
public class EnrolmentManagement {
    private MyDBApp myDBApp;

    /**
     * effect: to create EnrolmentManagement object
     * @param myDBApp MyDBApp
     */
    public EnrolmentManagement (MyDBApp myDBApp){
        this.myDBApp = myDBApp;
    }

    /**
     * effect: if courseId, studentId, semester are valid
     *              if course and student are exist
     *                  if enrolment is not exist
     *                      if student is eligible to enrol
     *                          using method insert in MyDBApp to add a new enrolment
     *                          return true
     *           else
     *                  return false
     * @param studentId int
     * @param courseId String
     * @param semester int
     * @return String
     * throws SQLException
     */
    public  boolean addEnrolment (int studentId, String courseId, int semester) throws SQLException {
        final char defaultFinalGrade = 'F';
        if (validateCourseId(courseId)&&validateStudentId(studentId)&&validateSemester(semester)){
            if (isExistCourse(courseId)&&isExistStudent(studentId)){
                if (isExistEnrolment(studentId,courseId)){
                    if (isEligibleToEnroll(studentId,courseId)){
                        String sql = String.format("INSERT INTO Enrolment VALUES (%d,'%s',%d, '%s');", studentId, courseId, semester, defaultFinalGrade );
                        return myDBApp.insert(sql);
                    } else {
                        System.err.println("Student has not enroled to previous course!");
                    }
                }
            }
        }
        return false;
    }

    /**
     * effect: if studentId, courseId, finalgrade are valid
     *              if enrolment is exist
     *                  using method update in MyDBApp to update final grade
     *                  return true
     *          else
     *              return false
     * @param studentId int
     * @param courseId String
     * @param finalGrade char
     * @return String
     * throws SQLException
     */
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

    /**
     * effect: using selectAll method in MyDBApp to show all enrolments in database
     * @return String
     * throws SQLException
     */
    String allEnrolment() throws SQLException {
        String sql =  "SELECT student.studentid, student.firstname, student.lastname, enrolment.course, enrolment.semester, enrolment.finalgrade" +
                " FROM enrolment INNER JOIN student ON enrolment.student = student.studentid";
        return myDBApp.selectAll(sql);
    }

    /**
     * effect: using selectToHtmlFile in MyDBApp to write all enrolments in an HTML file
     * @return String
     */
    public String allEnrolmentToHtmlFile(){
         String sql = "SELECT student.studentid, student.firstname, student.lastname, enrolment.course, course.name, enrolment.semester, enrolment.finalgrade" +
                 " FROM enrolment " +
                 "INNER JOIN student ON enrolment.student = student.studentid" +
                 "INNER JOIN course On enrolment.course = course.courseid";
         return myDBApp.selectToHtmlFile(sql, "enrols.html");
     }

    /**
     * effect: using selectToHtmlFile in MyDBApp to write all enrolments sorted in an HTML file
     * @return String
     */
    public String allEnrolmentInSortedOrder(){
         String sql = "SELECT student.firstname, student.lastname, enrolment.course, course.name, enrolment.semester, enrolment.finalgrade " +
                 "FROM enrolment " +
                 "INNER JOIN student ON enrolment.student = student.studentid " +
                 "INNER JOIN course ON enrolment.course = course.courseid"+
                 "ORDER BY CASE finalgrade " +
                 "WHEN  'E' THEN 1 " +
                 "WHEN  'G' THEN 2 " +
                 "WHEN  'P' THEN 3 " +
                 "WHEN  'F' THEN 4 " +
                 "ELSE 5 END";
        return myDBApp.selectToHtmlFile(sql, "enrols_sorted.html");
     }

    /**
     * effect: select prerequisite of the course
     *          select final grade of this student in previous course base on prerequisite
     *          if finalgrade is empty
     *              return false
     *          else
     *              return true
     * @param studentId int
     * @param courseId String
     * @return boolean
     * throws SQLException
     */
    private boolean isEligibleToEnroll(int studentId, String courseId) throws SQLException {
        String sql = String.format("SELECT prerequisites FROM course WHERE courseid = '%s';", courseId);
        String prerequisites = myDBApp.selectAll(sql).substring(0,3);
        if (prerequisites.equals("-")){
            return true;
        }
        if (isExistEnrolment(studentId, prerequisites)){
            return false;
        } else {
            return true;
        }
    }

    /**
     * effect: using selectAll method in MyDBApp to select enrolment where studentId = studentId and courseId = courseId
     *          if result equals " "
     *              return false
     *          else
     *              return true
     * @param studentId int
     * @param courseId String
     * @return boolean
     * throws SQLException
     */
    public boolean isExistEnrolment(int studentId, String courseId) throws SQLException {
         String sql ="SELECT * FROM enrolment WHERE student ="+ studentId+" AND course = '"+ courseId+"';";
         if (myDBApp.selectAll(sql).equals("")){
             return true;
         }
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
    public boolean isExistStudent(int studentId) throws SQLException {
        String sql = "SELECT studentid FROM student WHERE studentid =  " + studentId;
        if (!myDBApp.selectAll(sql).equals("")){
            return true;
        }
        return false;
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
    public boolean isExistCourse(String courseId) throws SQLException {
        String sql = "SELECT courseid FROM course WHERE courseid = '" + courseId+"'";
        if (!myDBApp.selectAll(sql).equals("")){
            return true;
        }
        return false;
    }

    /**
     * effect if studentId >0
     *            return true
     *         else
     *            return false
     *            print error message
     * @param studentId int
     * @return boolean
     */
    private boolean validateStudentId (int studentId){
        if (studentId > 0){
            return true;
        }
        System.err.println("Student ID has to be greater than 0");
        return false;
    }

    /**
     * effect if courseId != null /\ courseId.length <= 5
     *              return true
     *         else
     *              return false
     * @param courseId String
     * @return boolean
     */
    private boolean validateCourseId (String courseId){
        if (courseId!= null && courseId.length()<5){
            return true;
        }
        System.err.println("Course Id is invalid");
        return false;
    }

    /**
     * effect: if semester >0 /\ semester <= 8
     *              return true
     *          else
     *              return false
     * @param semester int
     * @return boolean
     */
    private boolean validateSemester(int semester){
        if (semester>0 && semester<=8){
            return true;
        }
        System.err.println("Invalid Semester");
        return false;
    }

    /**
     * effect: if finalGrade == E \/ finalGrade == G \/ finalGrade == P \/ finalGrade == F
     *              return true
     *          else
     *              return false
     * @param finalGrade char
     * @return boolean
     */
    private boolean validateFinalGrade (char finalGrade){
        if (finalGrade == 'E'|| finalGrade == 'G' || finalGrade == 'P' || finalGrade == 'F'){
            return true;
        }
        System.err.println("Invalid final grade");
        return false;
    }


}
