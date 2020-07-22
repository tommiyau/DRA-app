package dra.demo.controller;

import dra.demo.model.dao.*;
import java.io.IOException;
import java.io.Serializable;
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
public class ConnServlet extends HttpServlet implements Serializable{
    private MongoDBManager manager;  
     
    @Override 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String adminemail = request.getParameter("adminemail");
        String adminpass = request.getParameter("adminpassword");
        String role = request.getParameter("role");
        String db = request.getParameter("database");
        String collection = request.getParameter("collection");
        manager = new MongoDBManager(adminemail, adminpass,role,db,collection);        
        response.setContentType("text/html;charset=UTF-8");  
        HttpSession session = request.getSession();              
        String status = (manager != null) ? "Connected to mLab" : "Disconnected from mLab";        
        
        session.setAttribute("status", status); 
        session.setAttribute("manager", manager);
           
        RequestDispatcher rs = request.getRequestDispatcher("index.jsp");
        rs.forward(request, response);
    }    
  
}
