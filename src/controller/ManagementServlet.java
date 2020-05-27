
package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Management;
import beans.PostComment;
import beans.User;
import beans.UserEdit;
import beans.UserPost;
import service.CommentService;
import service.EditService;
import service.PostService;
import service.UserService;

@WebServlet(urlPatterns = { "/management" })

public class ManagementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        boolean isShowMessageForm = false;
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user != null) {
            isShowMessageForm = true;
        }else{
       		List<String> errorMessages = new ArrayList<String>();
            errorMessages.add("ログインしてください");
            request.setAttribute("errorMessages", errorMessages);
       		request.getRequestDispatcher("/login.jsp").forward(request, response);
       	}
        request.setAttribute("loginUser", user);


        if (!user.getBranch_office().equals("1")) {
        	List<String> errorMessages = new ArrayList<String>();
            List<UserPost> posts = new PostService().select();
            List<PostComment> comments = new CommentService().select();
            errorMessages.add("権限がありません");
            request.setAttribute("errorMessages", errorMessages);
            request.setAttribute("posts", posts);
            request.setAttribute("comments", comments);
       		request.getRequestDispatcher("/home.jsp").forward(request, response);
        }

        List<Management> users = new UserService().select();


        request.setAttribute("users", users);
        request.setAttribute("isShowMessageForm", isShowMessageForm);
        request.getRequestDispatcher("/management.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        UserEdit user = getUser(request);

        new EditService().statusUpdate(user);

        response.sendRedirect("./management");
    }

    private UserEdit getUser(HttpServletRequest request) throws IOException, ServletException {

        UserEdit user = new UserEdit();
        user.setId(Integer.parseInt(request.getParameter("id")));
        user.setStatus(Integer.parseInt(request.getParameter("status")));
        return user;
    }

}