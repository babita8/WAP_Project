package controller;

import model.User;
import utility.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
                taskDate = req.getParameter("taskDate"),
                taskCategory = req.getParameter("taskCategory"),
                taskPriority = req.getParameter("taskPriority"),
                taskAssigned = req.getParameter("taskAssigned"),
                edit = req.getParameter("edit"),
                taskId = req.getParameter("taskId");


        // Checking for null and empty values

        if (edit.equals("0")) {
            //todo insert
            System.out.println("Insert " + taskName + " " + taskDate + " " + taskCategory + " "
                    + taskPriority + " " + taskAssigned + " " + taskId);
        } else {
            //todo edit
            System.out.println("Edit " + taskName + " " + taskDate + " " + taskCategory + " "
                    + taskPriority + " " + taskAssigned + " " + taskId);

        }

    }
}
