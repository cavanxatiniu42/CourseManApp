import javax.xml.bind.SchemaOutputResolver;
import java.sql.SQLException;

/**
 * Created by Thu Thuy Nguyen on 20/10/2016.
 */
public class Main {
    public static void main(String[] args) throws SQLException {
        MyDBApp myDBApp = new MyDBApp("jdbc:postgresql:%s");
        myDBApp.connect("courseman", "postgres", "123456");
        MenuStudent menuStudent = new MenuStudent(myDBApp);
    //    System.out.println(menuStudent.addStudent(12,"thuy","thu","hanoi","18/11"));
//        System.out.println(menuStudent.addStudent(11,"thuy","thu","hanoi","18/11"));
      //  System.out.println(menuStudent.allStudent());
//        System.out.println(menuStudent.showAllStuden"INSERT INTO Student VALUES (%d,'%s','%s','%s','%s');"t());
//    System.out.println(menuStudent.allStudent());
    //    System.out.println(menuStudent.updateStudent(11, "Thu", "Thuy", "hanoi", "19/11"));
//        MenuCourse menuCourse = new MenuCourse(myDBApp);
//        System.out.println(menuCourse.allCourse());
       EnrolmentManagement enrolmentManagement = new EnrolmentManagement(myDBApp);
       // System.out.println(enrolmentManagement.addEnrolment(4,"JAV",4));
       // System.out.println(enrolmentManagement.allEnrolment());
     //   System.out.println(enrolmentManagement.isEligibleToEnroll(4,"JAV"));
     //   enrolmentManagement.allEnrolmentInSortedOrder();
    //   System.out.println(enrolmentManagement.isExistEnrolment(4,"JAV"));
        Reports reports = new Reports(myDBApp);
      //  reports.courseOfAStudent(1);
        reports.studentsOfACourse("PPL");
    }
}
