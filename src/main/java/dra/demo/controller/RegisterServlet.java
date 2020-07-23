package dra.demo.controller;

import dra.demo.model.User;
import dra.demo.model.dao.MongoDBManager;
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
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Validator validator = new Validator();
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");

        User user = new User(name, email, password, phone);
        String[] access = (String[]) session.getAttribute("credentials");

        MongoDBManager manager = new MongoDBManager(access[0], access[1], access[2], access[3], access[4]);

        if (!validator.validateEmail(email)) {
            session.setAttribute("emailErr", "Incorrect email format");
            request.getRequestDispatcher("index.jsp").include(request, response);
        } else if (!validator.validatePassword(password)) {
            session.setAttribute("passErr", "Incorrect password format");
            request.getRequestDispatcher("index.jsp").include(request, response);
        } else {
            try {
                manager.create(name, email, password, phone);
                session.setAttribute("user", user);
                request.getRequestDispatcher("main.jsp").include(request, response);
            } catch (NullPointerException ex) {
                session.setAttribute("existErr", "Guest user!");
                request.getRequestDispatcher("main.jsp").include(request, response);
            }
        }
    }
}
