package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.User;
import org.bson.Document;
import utility.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


public class LoginServlet extends HttpServlet {


    private static final long serialVersionUID = 1L;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // User group is faked because no API written get user groups without passing user id
        Map<Integer, String> groups = new HashMap<>();
        groups.put(1, "IT");
        groups.put(2, "Sales");
        groups.put(3, "Finance");
        groups.put(4, "HR");

        resp.getWriter().print(gson.toJson(groups));
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String param1 = req.getParameter("login_id");
        String param2 = req.getParameter("login_pwd");

        if (param1 == null || param2 == null || "".equals(param1) || "".equals(param2)) {
            resp.getWriter().print("{\"msg\":\"User id and password are both mandatory fields.\"}");
        } else {
            User userFound = Util.searchUserInDb(param1, param2);
            if (userFound != null) {
                req.getSession().setAttribute("user", userFound);
                loadTasks(req, resp);
                resp.getWriter().print("{\"redirect\":\"tasks.jsp\"}");

            } else {
                resp.getWriter().print("{\"msg\":\"User id and/or password incorrect.\"}");
            }
        }
    }

    private void loadTasks(HttpServletRequest request, HttpServletResponse response) {
        User loginUser = (User) request.getSession().getAttribute("user");
        ArrayList<User> userInGroup = new ArrayList<User>(1);
        userInGroup.add(loginUser);

        // Load task list of all members of group which logged in user belongs to
        Document group = Util.getGroupofUser(loginUser.getId());
        List<Document> taskList = Util.getTaskListByGroup((int) group.get("groupId"));

        // Load all members of group which logged in user belongs to
        List<Document> groupMembers = Util.getUserList((int) group.get("groupId"));

        request.getSession().setAttribute("groupMembers", groupMembers);
        request.getSession().setAttribute("group", group);
        request.getSession().setAttribute("taskList", taskList);
        request.getSession().setAttribute("userInGroup", userInGroup);

        request.getSession().setAttribute("taskListJSon", new Gson().toJson(taskList));
        System.out.println(new Gson().toJson(taskList));
    }

}
