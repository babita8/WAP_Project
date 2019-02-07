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


public class DeleteTaskServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println(request.getParameter("taskid"));

        System.out.println("POST DeleteTaskServlet");
        int taskId = Integer.parseInt(request.getParameter("taskid"));

        Util.deleteTask(taskId);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String JSONtasks = new Gson().toJson(Util.getTaskListJson(0));
        System.out.println("Out Delete Task: " + JSONtasks);
        out.write(JSONtasks);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
