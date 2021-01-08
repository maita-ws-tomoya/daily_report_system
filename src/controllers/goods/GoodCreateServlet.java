package controllers.goods;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Good;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class GoodCreateServlet
 */
@WebServlet("/good/create")
public class GoodCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoodCreateServlet() {
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

            Good g = new Good();

            g.setReport((Report)request.getSession().getAttribute("report"));

            Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");
            g.setDoGood(login_employee.getId());

            em.getTransaction().begin();
            em.persist(g);
            em.getTransaction().commit();
            em.close();
            request.getSession().setAttribute("flush", "いいね！しました。");
            request.getSession().setAttribute("report", g.getReport());

            response.sendRedirect(request.getContextPath() + "/reports/show");

        }
    }

}
