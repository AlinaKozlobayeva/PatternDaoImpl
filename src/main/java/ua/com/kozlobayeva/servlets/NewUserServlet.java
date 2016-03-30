package ua.com.kozlobayeva.servlets;

import ua.com.kozlobayeva.dao.impl.UserDaoImpl;
import ua.com.kozlobayeva.entity.User;
import ua.com.kozlobayeva.entity.UserFields;

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
        String name = req.getParameter(UserFields.NAME);
        String login = req.getParameter(UserFields.LOGIN);
        String password = req.getParameter(UserFields.PASSWORD);

        UserDaoImpl userDao = new UserDaoImpl();
        userDao.create(new User(name, login, password));
        req.getRequestDispatcher("../pages/welcomePage.jsp").forward(req, resp);
    }
}
