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
 * Servlet implementation class GoodDestroyServlet
 */
@WebServlet("/good/destroy")
public class GoodDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoodDestroyServlet() {
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
            Report report = ((Report)request.getSession().getAttribute("report"));

            Good g = (Good)em.createNamedQuery("getGoods", Good.class)
                                           .setParameter("login_employee",login_employee.getId())
                                           .setParameter("report_id", report.getId())
                                           .getSingleResult();

            em.getTransaction().begin();
            em.remove(g);       // データ削除
            em.getTransaction().commit();
            em.close();

            request.getSession().setAttribute("flush", "いいね！を解除しました。");

//            response.sendRedirect(request.getContextPath() + "/reports/index");
            response.sendRedirect(request.getContextPath() + "/reports/show");
        }
    }

}
