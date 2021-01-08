package controllers.attendances;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Attendance;
import models.Employee;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsIndexServlet
 */
@WebServlet("/attendance/index")
public class AttendancesIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AttendancesIndexServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();
        request.setAttribute("_token", request.getSession().getId());
        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }

        List<Attendance> attendances;

        attendances = em.createNamedQuery("getLoginEmployeeAttendances", Attendance.class)
                                  .setParameter("login_employee",login_employee.getId() )
                                  .setFirstResult(15 * (page - 1))
                                  .setMaxResults(15)
                                  .getResultList();

        long attendances_count = (long)em.createNamedQuery("getLoginEmployeeAttendancesCount", Long.class)
                                  .setParameter("login_employee",login_employee.getId() )
                                  .getSingleResult();

        long Null_out_time_count = (long)em.createNamedQuery("getLoginEmployeeAttendancesOutTimeNullCount", Long.class)
                                  .setParameter("login_employee",login_employee.getId() )
                                  .getSingleResult();

        em.close();

        request.getSession().setAttribute("login_employee", login_employee);
        request.setAttribute("attendances", attendances);
        request.setAttribute("attendances_count", attendances_count);
        request.setAttribute("Null_out_time_count", Null_out_time_count);
        request.setAttribute("page", page);

        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/attendances/index.jsp");
        rd.forward(request, response);
    }

}