package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import beans.Comment;
import beans.PostComment;
import beans.UserPost;
import service.CommentService;
import service.PostService;

@WebServlet(urlPatterns = { "/comment" })
public class CommentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        List<String> errorMessages = new ArrayList<String>();

        String text = request.getParameter("text");
        String loginUserId = request.getParameter("loginUserId");
        String postId = request.getParameter("id");

        if (!isValid(text, errorMessages)) {
            session.setAttribute("errorMessages", errorMessages);
            List<UserPost> posts = new PostService().select();
            List<PostComment> comments = new CommentService().select();
            request.setAttribute("posts", posts);
            request.setAttribute("comments", comments);
            response.sendRedirect("./");
            return;
        }

        Comment comment = new Comment();
        comment.setText(text);
        comment.setPostId(postId);
        comment.setUserId(loginUserId);

        new CommentService().insert(comment);
        response.sendRedirect("./");
    }

    private boolean isValid(String text, List<String> errorMessages) {

        if (StringUtils.isEmpty(text)) {
            errorMessages.add("コメントを入力してください");
        } else if(text.matches("[ 　]+")){
        	errorMessages.add("コメントを入力してください");
        }
        else if (500 < text.length()) {
            errorMessages.add("コメントは500文字以下で入力してください");
        }

        if (errorMessages.size() != 0) {
            return false;
        }
        return true;
    }
}