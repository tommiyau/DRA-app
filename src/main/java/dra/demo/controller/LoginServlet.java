package dra.demo.controller;

import dra.demo.model.User;
import dra.demo.model.dao.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author George
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Validator validator = new Validator();
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        MongoDBManager manager = (MongoDBManager) session.getAttribute("manager");

        if (!validator.validateEmail(email)) {
            session.setAttribute("emailErr", "Incorrect email format");            
            request.getRequestDispatcher("index.jsp").include(request, response);
        } else if (!validator.validatePassword(password)) {
            session.setAttribute("passErr", "Incorrect password format");
            request.getRequestDispatcher("index.jsp").include(request, response);
        } else {
            try {
                User user = manager.user(email, password);
                session.setAttribute("user", user);
                request.getRequestDispatcher("main.jsp").include(request, response);
            } catch (NullPointerException ex) {
                session.setAttribute("existErr", "User does not exist!");
                request.getRequestDispatcher("main.jsp").include(request, response);
            }
        }
    }
}
