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

            Attendance a = new Attendance();

            a.setEmployee((Employee)request.getSession().getAttribute("login_employee"));

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            a.setIn_time(currentTime);

            em.getTransaction().begin();
            em.persist(a);
            em.getTransaction().commit();
            em.close();
            request.getSession().setAttribute("flush", "勤怠情報を更新しました。");
            response.sendRedirect(request.getContextPath() + "/attendance/index");
        }
    }
}
