package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.User;
import utility.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/profile_controller")
public class UserProfileController extends HttpServlet {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        User user = gson.fromJson(req.getParameter("user"), User.class);
//        System.out.println(user.getUserName());
//        Util.upsertUser(user);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      //  System.out.println("doPost UserProfileController");
        User user = gson.fromJson(req.getParameter("user"), User.class);
        //System.out.println(user.getUserName());
        Util.upsertUser(user);
        resp.getWriter().print("{\"msg\": \"Done!\"}");
    }
}
