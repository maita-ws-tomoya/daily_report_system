package controllers.attendances;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Attendance;
import models.Employee;
import utils.DBUtil;

/**
 * Servlet implementation class AttendancesCreateServlet
 */
@WebServlet("/attendance/create")
public class AttendancesCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AttendancesCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {

            EntityManager em = DBUtil.createEntityManager();

            Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");


            long Null_out_time_count = (long)em.createNamedQuery("getLoginEmployeeAttendancesOutTimeNullCount", Long.class)
                                            .setParameter("login_employee",login_employee.getId() )
                                            .getSingleResult();

            if(Null_out_time_count == 0){

                Attendance a = new Attendance();

                a.setEmployee(login_employee);

                Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                a.setIn_time(currentTime);

                em.getTransaction().begin();
                em.persist(a);

            }else{
                Attendance a = em.createNamedQuery("getLoginEmployeeAttendancesOutTimeNull",Attendance.class)
                                            .setParameter("login_employee",login_employee.getId() )
                                            .getSingleResult();

                Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                a.setOut_time(currentTime);
                em.getTransaction().begin();
            }

            em.getTransaction().commit();
            em.close();
            request.getSession().setAttribute("flush", "勤怠情報を更新しました。");
            response.sendRedirect(request.getContextPath() + "/attendance/index");
        }
    }
}
