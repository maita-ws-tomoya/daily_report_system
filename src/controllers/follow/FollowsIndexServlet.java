package controllers.follow;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import utils.DBUtil;

/**
 * Servlet implementation class EmployeesIndexServlet
 */
@WebServlet("/follows/index")
public class FollowsIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowsIndexServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        int page = 1;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(NumberFormatException e) { }


        long follows_count = (long)em.createNamedQuery("getAllFollowsCount", Long.class)
                                       .setParameter("login_employee",login_employee.getId())
                                       .getSingleResult();

        List<Employee> follows = null;

        if(follows_count != 0){
            follows = em.createNamedQuery("getAllFollowsEmployees",Employee.class)
                    .setParameter("login_employee",login_employee.getId())
                    .setFirstResult(15 * (page - 1))
                    .setMaxResults(15)
                    .getResultList();
        }

        em.close();

        request.getSession().setAttribute("follows_count", follows_count);
        request.getSession().setAttribute("follows", follows);
        request.setAttribute("page", page);
        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/follows/index.jsp");
        rd.forward(request, response);
    }
}