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

        System.out.println("POST!!");
        User loginUser = (User) request.getSession().getAttribute("user");
        ArrayList<User> userInGroup = new ArrayList<User>(1);
        userInGroup.add(loginUser);

        // Load task list of all members of group which logged in user belongs to
        Document group = Util.getGroupofUser(loginUser.getId());
        List<Document> taskList = Util.getTaskListByGroup((int)group.get("groupId"));

        // Load all members of group which logged in user belongs to
        List<Document> groupMembers = Util.getUserList((int)group.get("groupId"));

        request.getSession().setAttribute("groupMembers", groupMembers);
        request.getSession().setAttribute("group", group);
        request.getSession().setAttribute("taskList", taskList);

        request.getSession().setAttribute("userInGroup", userInGroup);

        //request.getRequestDispatcher("tasks.jsp").forward(request, response);
        response.sendRedirect("tasks.jsp");


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("GET!! FOR AJAX CALL");
        PrintWriter out = response.getWriter();

        int selUserId = Integer.parseInt(request.getParameter("selUserId"));
        String JSONtasks;
        if(selUserId > 0)
            JSONtasks = new Gson().toJson(Util.getTaskListJson(selUserId));
        else {
            Document group = (Document)request.getSession().getAttribute("group");
            JSONtasks = new Gson().toJson(Util.getTaskListByGroup((int)group.get("groupId")));
        }
        //List<Task> taskList = new MockData().retrieveTaskList();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        //System.out.println("Out: "+JSONtasks);
        //out.write(JSONtasks);
        System.out.println("Out: " + JSONtasks);
        out.write(JSONtasks);

    }
}
