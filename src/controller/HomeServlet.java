package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.PostComment;
import beans.User;
import beans.UserPost;
import service.CommentService;
import service.PostService;

@WebServlet(urlPatterns = { "/index.jsp" })
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        boolean isShowPostForm = false;
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user != null) {
            isShowPostForm = true;
       	}else{
       		List<String> errorMessages = new ArrayList<String>();
            errorMessages.add("ログインしてください");
            request.setAttribute("errorMessages", errorMessages);
       		request.getRequestDispatcher("/login.jsp").forward(request, response);
       	}
        SimpleDateFormat sdformat1
		= new SimpleDateFormat("yyyy/MM/dd");

        Calendar cal = Calendar.getInstance();
        request.setAttribute("today", sdformat1.format(cal.getTime()));
        Calendar calB = Calendar.getInstance();
        calB.add(Calendar.DATE, +1); // 当日+1日
        request.setAttribute("todayB", sdformat1.format(calB.getTime()));

        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.DATE, -3); // 当日-3日
        request.setAttribute("threeDaysAgo", sdformat1.format(cal2.getTime()));

        Calendar cal3 = Calendar.getInstance();
        cal3.add(Calendar.DATE, -7); // 一週間前
        request.setAttribute("oneWeekAgo", sdformat1.format(cal3.getTime()));

        Calendar cal4 = Calendar.getInstance();
        cal4.add(Calendar.DATE, -30); // 1ヶ月前
        request.setAttribute("oneMonthAgo", sdformat1.format(cal4.getTime()));

        List<UserPost> posts = new PostService().select();
        List<PostComment> comments = new CommentService().select();

        request.setAttribute("loginUserId", user.getId());
        request.setAttribute("loginUserBranchOffice", user.getBranch_office());
        request.setAttribute("posts", posts);
        request.setAttribute("comments", comments);
        request.setAttribute("isShowPostForm", isShowPostForm);
        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }
}