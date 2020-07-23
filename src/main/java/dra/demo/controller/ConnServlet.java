package dra.demo.controller;

import dra.demo.model.dao.*;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author George
 */
public class ConnServlet extends HttpServlet {
     
    @Override 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String adminemail = request.getParameter("adminemail");
        String adminpass = request.getParameter("adminpassword");
        String role = request.getParameter("role");
        String db = request.getParameter("database");
        String collection = request.getParameter("collection");
        String[] credentials = {adminemail, adminpass,role,db,collection};
        MongoDBManager manager = new MongoDBManager(adminemail, adminpass,role,db,collection); 
        HttpSession session = request.getSession();              
        String status = (manager != null) ? "Connected to mLab" : "Disconnected from mLab";        
        
        session.setAttribute("status", status); 
        session.setAttribute("credentials", credentials);
           
        RequestDispatcher rs = request.getRequestDispatcher("index.jsp");
        rs.forward(request, response);
    }     
}
