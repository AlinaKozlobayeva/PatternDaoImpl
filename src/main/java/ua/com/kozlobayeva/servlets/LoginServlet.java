package ua.com.kozlobayeva.servlets;

import ua.com.kozlobayeva.dao.impl.UserDaoImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Alina on 29.03.2016.
 */
public class LoginServlet extends HttpServlet {
    /**
     *
     */
    public static final String PARAM_LOGIN = "login";
    /**
     *
     */
    public static final String PARAM_PASSWORD = "password";
    /**
     *
     */
    public static final String PAGE_OK = "../pages/userPage.jsp";
    /**
     *
     */
    public static final String PAGE_ERROR = "../pages/error.jsp";




    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("../pages/welcomePage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(PARAM_LOGIN);
        String password = req.getParameter(PARAM_PASSWORD);
        log("Login=" + login + ":: password=" + password);

        UserDaoImpl userDao = new UserDaoImpl();
        if (userDao.findUserByLogin(login).getPassword().equals(password)) {
            req.getRequestDispatcher(PAGE_OK).forward(req, resp);
        } else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher(PAGE_ERROR);
            rd.include(req, resp);

        }
    }
}
