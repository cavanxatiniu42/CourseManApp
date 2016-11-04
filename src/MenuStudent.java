import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Thu Thuy Nguyen on 17/10/2016.
 */
public class MenuStudent {
    private MyDBApp myDBApp;

    public MenuStudent(MyDBApp myDBApp) {
        this.myDBApp = myDBApp;
    }

    public boolean addStudent(int studentId, String firstName, String lastName, String address, String dateOfBirth){
        String sql = String.format("INSERT INTO Student VALUES (%d,'%s','%s','%s','%s');", studentId, firstName, lastName, address, dateOfBirth);
        if (validateID(studentId) && validateName(firstName) && validateName(lastName) && validateAddress(address) && validateDOB(dateOfBirth)){
            if (myDBApp.insert(sql)){
                return true;
            }
        }
        return false;
    }

    public boolean updateStudent (int studentId, String firstName, String lastName, String address, String dateOfBirth){
        if ( validateName(firstName) && validateName(lastName) && validateAddress(address) && validateDOB(dateOfBirth)){
            String sql = String.format("UPDATE Student \n" +
                    "SET firstname = '%s',lastname = '%s',address = '%s',dateofbirth = '%s' \n" +
                    "WHERE studentid = %d;", firstName, lastName, address, dateOfBirth, studentId);
           return myDBApp.update(sql);
        }
        return false;
    }

    public boolean deleteStudent (int studentId){
        String sql = "DELETE FROM student WHERE studentid =" +studentId+ " ";
        if (myDBApp.delete(sql)){
            return true;
        }
        return false;
    }

    public String showAllStudentInHtmlFile(){
        String sql = "SELECT * FROM student";
        return myDBApp.selectToHtmlFile(sql, "student.html");
    }

    public String allStudent() throws SQLException {
        String sql = "SELECT * FROM student";
        return myDBApp.selectAll(sql);
    }

    private boolean validateID (int studentId){
        String sql = "SELECT studentid FROM student WHERE studentid = "+ studentId+ " ";
        if (studentId <= Integer.parseInt(myDBApp.maxID(sql))){
            return false;
        }
       return true;
    }

    private boolean validateName(String name){
        if (name!= null && name.length()<=50){
            return true;
        }
        return false;
    }

    private boolean validateAddress (String address){
        if (address != null && address.length() <= 250){
            return true;
        }
        return false;
    }

    private boolean validateDOB (String dateOfBirth){
        if (dateOfBirth != null && dateOfBirth.length()<=30){
            return true;
        }
        return false;
    }
}
