import java.sql.SQLException;

/**
 * Created by Thu Thuy Nguyen on 17/10/2016.
 */
public class MenuCourse {
    private MyDBApp myDBApp;

    public MenuCourse(MyDBApp myDBApp){
        this.myDBApp = myDBApp;
    }

    public boolean addCourse (String courseId, String courseName, String prerequisite) throws SQLException {
        if (validateCourseId(courseId) &&validateCourseName(courseName) && validatePrerequisite(prerequisite)){

            String sql = String.format("INSERT INTO course VALUES ('%s','%s','%s');", courseId, courseName, prerequisite);
            if (myDBApp.insert(sql)){
                return true;
            }
        }
        return false;
    }

    public boolean updateCourse (String courseId, String courseName, String prerequisite){
        if (validateCourseName(courseName) && validatePrerequisite(prerequisite)){

            String sql = String.format("UPDATE course \n" +
                    "SET name = '%s', prerequisites = '%s' \n" +
                    "WHERE courseid = '%s';", courseName, prerequisite, courseId);
            if (myDBApp.update(sql)){
                return true;
            }
        }
        return false;
    }

    public boolean deleteCourse (String courseId){
        String sql = "DELETE FROM course WHERE courseid = '"+courseId+"' ";
        if (myDBApp.delete(sql)){
            return true;
        }
        return false;
    }

    public String showAllCourseInHtmlFile(){
        String sql = "SELECT * FROM course";
        return myDBApp.selectToHtmlFile(sql, "course.html");
    }

    public String allCourse () throws SQLException {
        String sql = "SELECT * FROM course";
        return myDBApp.selectAll(sql);
    }

    private boolean validateCourseId(String courseId) throws SQLException {
        String sql = "SELECT courseid FROM course WHERE courseid = '"+ courseId+ "' ";
        if (!myDBApp.selectAll(sql).equals("")){
            System.err.println("courseid is existed!");
            return false;
        }
        return true;
    }

    private boolean validateCourseName (String courseName){
        if (courseName != null && courseName.length() <= 50 ){
            return true;
        }
        return false;
    }

    private boolean validatePrerequisite (String prerequisite){
        if (prerequisite!= null && prerequisite.length()<= 50){
            return true;
        }
        return false;
    }

}
