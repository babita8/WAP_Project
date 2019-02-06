package controller;

import com.google.gson.Gson;
import model.Task;
import model.User;
import org.bson.Document;
import utility.MockData;
import utility.Util;

import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class TaskServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("POST TaskServlet");
        User loginUser = (User) request.getSession().getAttribute("user");
        ArrayList<User> userInGroup = new ArrayList<User>(1);
        userInGroup.add(loginUser);


        //List<Task> taskList = Util.getTaskList(loginUser.getId());

        List<Document> taskList = Util.getTaskListJson(0);


        request.getSession().setAttribute("taskList", taskList);

        request.getSession().setAttribute("userInGroup", userInGroup);

        //request.getRequestDispatcher("tasks.jsp").forward(request, response);
        response.sendRedirect("tasks.jsp");


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("GET!! FOR AJAX CALL");
        PrintWriter out = response.getWriter();

        String JSONtasks = new Gson().toJson(Util.getTaskListJson(0));
        //List<Task> taskList = new MockData().retrieveTaskList();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        //System.out.println("Out: "+JSONtasks);
        //out.write(JSONtasks);
        System.out.println("Out: " + JSONtasks);
        out.write(JSONtasks);

    }
}
