package controllers.reports;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsShowServlet
 */
@WebServlet("/reports/show")
public class ReportsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsShowServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));
        Employee login_e = (Employee)request.getSession().getAttribute("login_employee");

        Integer good_flag = 1;
        Integer follow_flag = 1;

        long good_count = (long)em.createNamedQuery("getGoodsCount", Long.class)
                .setParameter("login_employee",login_e.getId())
                .setParameter("report_id", r.getId())
                .getSingleResult();

        long good_all_count = (long)em.createNamedQuery("getAllGoodsCount", Long.class)
                .setParameter("report_id", r.getId())
                .getSingleResult();

        if(good_count==0){
        good_flag =0;
        }

        long follow_count = (long)em.createNamedQuery("getFollowsCount", Long.class)
                .setParameter("login_employee",login_e.getId())
                .setParameter("employee", r.getEmployee().getId())
                .getSingleResult();

        if(follow_count==0){
            follow_flag =0;
        }

        em.close();

        request.getSession().setAttribute("employee", r.getEmployee());
        request.getSession().setAttribute("login_employee", login_e);
        request.getSession().setAttribute("follow_flag", follow_flag);
        request.getSession().setAttribute("good_all_count", good_all_count);
        request.getSession().setAttribute("good_flag", good_flag);
        request.getSession().setAttribute("report", r);
        request.setAttribute("_token", request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
        rd.forward(request, response);
    }

}