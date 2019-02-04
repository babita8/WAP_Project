package controller;

import model.User;
import utility.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LoginServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private static final long serialVersionUID = 1L;

    // This method is called by the servlet container to process a 'post' request
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    public void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        // Reading post parameters from the request
        String param1 = req.getParameter("login_id"),
                param2 = req.getParameter("login_pwd");

        // Checking for null and empty values
        if(param1 == null || param2 == null || "".equals(param1) || "".equals(param2)) {
            req.setAttribute("error_message", "Please enter login id and password");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        } else {
            User userFound = Util.searchUserInDb(param1, param2);
            if(userFound!=null) {
                req.getSession().setAttribute("user",userFound);
                req.getRequestDispatcher("/tasks.html").forward(req, resp);
            } else {
                req.setAttribute("error_message", "You are not an authorised user. Please check with administrator.");
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
            }
        }
    }

}
