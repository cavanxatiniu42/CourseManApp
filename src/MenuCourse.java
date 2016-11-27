import java.sql.SQLException;

/**
 * Created by Thu Thuy Nguyen on 17/10/2016.
 * overview: a class to manage course in database
 * attribute: myDBApp MyDBApp
 */
public class MenuCourse {
    private MyDBApp myDBApp;

    /**
     * overview: create a MenuCourse object
     * @param myDBApp MyDBApp
     */
    public MenuCourse(MyDBApp myDBApp){
        this.myDBApp = myDBApp;
    }

    /**
     * overview: a method to add a new course into database
     * effect: if courseId, courseName, prerequisite are valid
     *              if course is not exist
     *                  using insert method in MyDBApp to insert into database
     *                  return true
     *           else
     *              return false
     * @param courseId String
     * @param courseName String
     * @param prerequisite String
     * @return boolean
     * throws SQLException
     */
    public boolean addCourse (String courseId, String courseName, String prerequisite) throws SQLException {
        if (validateCourseID(courseId) &&validateCourseName(courseName) && validatePrerequisite(prerequisite)){
            if (!isExistCourse(courseId)){
                String sql = String.format("INSERT INTO course VALUES ('%s','%s','%s');", courseId, courseName, prerequisite);
                if (myDBApp.insert(sql)){
                    return true;
             }
            }
        }
        return false;
    }

    /**
     * overview: a method to update a course's information
     * effect:  if courseName and prerequisite are valid
     *              if course is exist
     *                  using method update in MyDBApp to update course
     *                  return true
     *            else return false
     * @param courseId String
     * @param courseName String
     * @param prerequisite String
     * @return boolean
     * throws SQLException
     */
    public boolean updateCourse (String courseId, String courseName, String prerequisite) throws SQLException {
        if (validateCourseName(courseName) && validatePrerequisite(prerequisite)){
            if (isExistCourse(courseId)){
            String sql = String.format("UPDATE course \n" +
                    "SET name = '%s', prerequisites = '%s' \n" +
                    "WHERE courseid = '%s';", courseName, prerequisite, courseId);
            if (myDBApp.update(sql)){
                return true;
            }

            }
        }
        return false;
    }

    /**
     * overview: delete a course in database
     * effect: if using delete method in MyDBApp to delete successfully
     *              return true
     *          else
     *              return false
     * @param courseId String
     * @return boolean
     */
    public boolean deleteCourse (String courseId){
        String sql = "DELETE FROM course WHERE courseid = '"+courseId+"' ";
        return myDBApp.delete(sql);
    }

    /**
     * effect: using method selectToHtmlFile in MyDBApp to write all students in an HTML file
     * @return String
     */
    public String showAllCourseInHtmlFile(){
        String sql = "SELECT * FROM course";
        return myDBApp.selectToHtmlFile(sql, "courses.html");
    }

    /**
     * effect: using method selectAll in MyDBApp to show all students
     * @return String
     * throws SQLException
     */
    public String allCourse () throws SQLException {
        String sql = "SELECT * FROM course";
        return myDBApp.selectAll(sql);
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
        String sql = "SELECT courseid FROM course WHERE courseid = '"+ courseId+ "' ";
        return !myDBApp.selectAll(sql).equals("");
    }

    /**
     * effect if courseId != null /\ courseId.length <= 5
     *              return true
     *         else
     *              return false
     * @param courseId String
     * @return boolean
     */
    private boolean validateCourseID (String courseId){
        return courseId != null && courseId.length() <= 5;
    }

    /**
     * effect if courseName != null /\ courseId.length <= 50
     *              return true
     *         else
     *              return false
     * @param courseName String
     * @return boolean
     */
    private boolean validateCourseName (String courseName){
        return courseName != null && courseName.length() <= 50;
    }

    /**
     *effect if prerequisite != null /\ prerequisite.length <= 250
     *              return true
     *         else
     *              return false
     * @param prerequisite String
     * @return boolean
     */
    private boolean validatePrerequisite (String prerequisite){
        return prerequisite != null && prerequisite.length() <= 250;
    }

}
