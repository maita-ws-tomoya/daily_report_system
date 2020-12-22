package controllers.follow;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsCreateServlet
 */
@WebServlet("/follow/destroy")
public class FollowsDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowsDestroyServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {

            EntityManager em = DBUtil.createEntityManager();

            Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");
            Employee employee = (Employee)request.getSession().getAttribute("employee");

            Follow f = (Follow)em.createNamedQuery("getFollowRelation", Follow.class)
                                           .setParameter("login_employee",login_employee.getId())
                                           .setParameter("employee", employee.getId())
                                           .getSingleResult();

            em.getTransaction().begin();
            em.remove(f);       // データ削除
            em.getTransaction().commit();
            em.close();

            request.getSession().setAttribute("flush", "フォローを解除しました。");

            response.sendRedirect(request.getContextPath() + "/employees/index");
        }
    }

}