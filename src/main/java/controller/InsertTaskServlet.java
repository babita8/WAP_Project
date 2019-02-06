package controller;

import com.google.gson.Gson;
import model.Task;
import utility.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class InsertTaskServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("POST from InsertTaskServlet");
        int assignUser = Integer.parseInt(request.getParameter("assigned"));
        String category = request.getParameter("category");
        int createUser = Integer.parseInt(request.getParameter("myhidCreate"));
        String dueDate = request.getParameter("requiredBy");
        int priority = Integer.parseInt(request.getParameter("star"));
        String task = request.getParameter("task");
        Task insertTask = new Task(task, dueDate, category, priority, assignUser, createUser);

        Util.insertTask(insertTask);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String JSONtasks = new Gson().toJson(Util.getTaskListJson(0));
        System.out.println("Out Insert Task: " + JSONtasks);
        out.write(JSONtasks);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
