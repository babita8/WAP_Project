package controller;

import com.google.gson.Gson;
import model.Task;
import model.User;
import utility.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//@WebServlet(name = "/EditTaskServlet")
public class EditTaskServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("POST!! EditTaskServlet");
        handleRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Get!! EditTaskServlet");

    }

    public void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        // let task = "taskName="+ taskName + "&taskDate="+taskDate + "&taskCategory=" + taskCategory +"&taskPriority=" +taskPriority+"&taskAssigned="+taskAssigned+ "&edit=" + edit;

        // Reading post parameters from the request
        String taskName = req.getParameter("taskName"),
                taskDate = req.getParameter("dueDate"),
                taskCategory = req.getParameter("taskCategory"),
                taskPriority = req.getParameter("taskPriority"),
                taskAssigned = req.getParameter("taskAssigned"),
                edit = req.getParameter("editOrInsert"),
                taskId = req.getParameter("_id");
        System.out.println("task ID" + taskId);


        // Checking for null and empty values

        if (taskId.equals("")) {
            //todo insert

            System.out.println("POST from EditTaskServlet With AJAX Insert");
            int assignUser = Integer.parseInt(req.getParameter("assigned"));
            String category = req.getParameter("category");
            int createUser = Integer.parseInt(req.getParameter("myhidCreate"));
            String dueDate = req.getParameter("dueDate");
            int priority = Integer.parseInt(req.getParameter("star"));

            String task = req.getParameter("task");
            Task insertTask = new Task(task, dueDate, category, priority, assignUser, createUser);

            Util.insertTask(insertTask);

            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            String JSONtasks = new Gson().toJson(Util.getTaskListJson(0));
            System.out.println("Out Insert Task: " + JSONtasks);
            out.write(JSONtasks);

        } else {
            //todo edit
//            System.out.println("Edit " + taskName + " " + taskDate + " " + taskCategory + " "
//                    + taskPriority + " " + taskAssigned + " " + taskId);

            System.out.println("POST from EditTaskServlet With AJAX Edit");
            int assignUser = Integer.parseInt(req.getParameter("assigned"));
            String category = req.getParameter("category");
            int createUser = Integer.parseInt(req.getParameter("myhidCreate"));
            String dueDate = req.getParameter("dueDate");
            int priority = Integer.parseInt(req.getParameter("priority"));
            if(priority==0)
                priority = 1;
            String task = req.getParameter("task");
            int id = Integer.parseInt(req.getParameter("_id"));
            Task insertTask = new Task(id, task, dueDate, category, priority, assignUser, createUser);

            Util.updateTask(insertTask);

            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            String JSONtasks = new Gson().toJson(Util.getTaskListJson(0));
            System.out.println("Out Insert Task: " + JSONtasks);
            out.write(JSONtasks);


        }

    }
}
