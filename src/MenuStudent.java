import java.sql.SQLException;

/**
 * Created by Thu Thuy Nguyen on 17/10/2016.
 * @Overview: a class to manage student in database
 * @attribute: myDBApp: MyDBApp
 */
public class MenuStudent {
    private MyDBApp myDBApp;

    /**
     * @effect: create object MenuStudent
     * @param myDBApp
     */
    public MenuStudent(MyDBApp myDBApp) {
        this.myDBApp = myDBApp;
    }

    /**
     * @overview: add a student in database
     * @effect: if studentId, firstName, lastName, address, dateOfBirth are valid
     *              using insert method in MyDBApp to add student
     *              return true
     *          else
     *              return false
     * @param studentId
     * @param firstName
     * @param lastName
     * @param address
     * @param dateOfBirth
     * @return
     * @throws SQLException
     */
    public boolean addStudent(int studentId, String firstName, String lastName, String address, String dateOfBirth) throws SQLException {
        String sql = String.format("INSERT INTO Student VALUES (%d,'%s','%s','%s','%s');", studentId, firstName, lastName, address, dateOfBirth);
        if (validateID(studentId) && validateName(firstName) && validateName(lastName) && validateAddress(address) && validateDOB(dateOfBirth)){
            if (!isExistStudent(studentId)){
                return myDBApp.insert(sql);
            }
        }
        return false;
    }

    /**
     * @overview: update a student's information
     * @effect: if new firstName, lastName, address, dateOfBirth are valid
     *              using update method in MyDBApp to update student
     *              return true
     *          else
     *              return false
     *
     * @param studentId
     * @param firstName
     * @param lastName
     * @param address
     * @param dateOfBirth
     * @return
     */
    public boolean updateStudent (int studentId, String firstName, String lastName, String address, String dateOfBirth) throws SQLException {
        if ( validateName(firstName) && validateName(lastName) && validateAddress(address) && validateDOB(dateOfBirth)){
            if (isExistStudent(studentId)) {
            String sql = String.format("UPDATE Student \n" +
                    "SET firstname = '%s',lastname = '%s',address = '%s',dateofbirth = '%s' \n" +
                    "WHERE studentid = %d;", firstName, lastName, address, dateOfBirth, studentId);
           return myDBApp.update(sql);

            }
        }
        return false;
    }

    /**
     * @overview: delete a student in database
     * @effect: if using delete method in MyDBApp to delete successfully
     *              return true
     *          else
     *              return false
     * @param studentId
     * @return
     */
    public boolean deleteStudent (int studentId){
        String sql = "DELETE FROM student WHERE studentid =" +studentId+ " ";
        return myDBApp.delete(sql);
    }

    /**
     * @effect: using method selectToHtmlFile in MyDBApp to write all students in an HTML file
     * @return
     */
    public String showAllStudentInHtmlFile(){
        String sql = "SELECT * FROM student";
        return myDBApp.selectToHtmlFile(sql, "student.html");
    }

    /**
     * @effect: using method selectAll in MyDBApp to show all students
     * @return
     * @throws SQLException
     */
    public String allStudent() throws SQLException {
        String sql = "SELECT * FROM student";
        return myDBApp.selectAll(sql);
    }

    /**
     * @effect: using selectAll method in MyDBApp to select student where studentId = studentId
     *          if result equals " "
     *              return false
     *          else
     *              return true
     * @param studentId
     * @return
     * @throws SQLException
     */
    public boolean isExistStudent(int studentId) throws SQLException {
        String sql = "SELECT studentid FROM student WHERE studentid = "+ studentId+ " ";
        if (myDBApp.selectAll(sql).equals("")){
            return false;
        }
       return true;
    }

    /**
     * @effect if studentId >0
     *            return true
     *         else
     *            return false
     *            print error message
     * @param studentId
     * @return
     */
    private boolean validateID (int studentId){
        if ( studentId > 0 ){
            return true;
        }
        System.err.println("studentId cannot be smaller than 0");
        return false;
    }

    /**
     * @effect: if name != null /\ name.length <=50
     *              return true
     *          else
     *              return false
     * @param name
     * @return
     */
    private boolean validateName(String name){
        if (name!= null && name.length()<=50){
            return true;
        }
        return false;
    }

    /**
     * @effect: if address != null /\ address.length <=50
     *              return true
     *          else
     *              return false
     * @param address
     * @return
     */
    private boolean validateAddress (String address){
        if (address != null && address.length() <= 250){
            return true;
        }
        return false;
    }

    /**
     * @effect: if dateOfBirth != null /\ dateOfBirth.length <=50
     *              return true
     *          else
     *              return false
     * @param dateOfBirth
     * @return
     */
    private boolean validateDOB (String dateOfBirth){
        if (dateOfBirth != null && dateOfBirth.length()<=30){
            return true;
        }
        return false;
    }
}
