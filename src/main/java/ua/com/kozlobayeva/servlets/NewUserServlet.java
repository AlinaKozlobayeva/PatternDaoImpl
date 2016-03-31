package ua.com.kozlobayeva.servlets;

import ua.com.kozlobayeva.dao.impl.UserDaoImpl;
import ua.com.kozlobayeva.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Alina on 30.03.2016.
 */
public class NewUserServlet extends HttpServlet {

    /**
     *
     */
    public static final String ATTRIBUTE_MODEL_TO_VIEW = "user";
    /**
     *
     */
    public static final String PAGE_OK = "../pages/welcomePage.jsp";



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("../pages/welcomePage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        UserDaoImpl userDao = new UserDaoImpl();
        userDao.create(new User(login, password, name));
        req.getRequestDispatcher("../pages/welcomePage.jsp").forward(req, resp);
    }
}
