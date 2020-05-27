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

import beans.Post;
import beans.PostComment;
import beans.User;
import beans.UserPost;
import service.CommentService;
import service.PostService;

@WebServlet(urlPatterns = { "/post" })
public class PostServlet extends HttpServlet {

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

	    request.setAttribute("isShowPostForm", isShowPostForm);
	    request.getRequestDispatcher("/post.jsp").forward(request, response);
	  }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        List<String> errorMessages = new ArrayList<String>();

        Post post = getPost(request);
        if (!isValid(post, errorMessages)) {
            request.setAttribute("errorMessages", errorMessages);
            request.setAttribute("post", post);
            request.getRequestDispatcher("post.jsp").forward(request, response);
            return;
        }

        User user = (User) session.getAttribute("loginUser");
        post.setUserId(user.getId());

        new PostService().insert(post);
        List<UserPost> posts = new PostService().select();
        List<PostComment> comments = new CommentService().select();
        request.setAttribute("posts", posts);
        request.setAttribute("comments", comments);
        request.setAttribute("PostMessage", "投稿が完了しました");
        response.sendRedirect("./");
    }

    private Post getPost(HttpServletRequest request) throws IOException, ServletException {
    	Post post = new Post();
    	post.setTitle(request.getParameter("title"));
    	post.setText(request.getParameter("text"));
    	post.setCategory(request.getParameter("category"));
		return post;
	}


	private boolean isValid(Post post, List<String> errorMessages) {

    	String title = post.getTitle();
    	String text = post.getText();
    	String category = post.getCategory();

        if (StringUtils.isEmpty(text)) {
            errorMessages.add("本文を入力してください");
        } else if(text.matches("[ 　]+")){
        	errorMessages.add("本文を入力してください");
        } else if (1000 < text.length()) {
            errorMessages.add("本文は1000文字以下で入力してください");
        }

        if (StringUtils.isEmpty(title)) {
            errorMessages.add("件名を入力してください");
        } else if(title.matches("[ 　]+")){
        	errorMessages.add("件名を入力してください");
        } else if (30 < title.length()) {
            errorMessages.add("件名は30文字以下で入力してください");
        }

        if (StringUtils.isEmpty(category)) {
            errorMessages.add("カテゴリーを入力してください");
        } else if(category.matches("[ 　]+")){
        	errorMessages.add("カテゴリーを入力してください");
        } else if (10 < category.length()) {
            errorMessages.add("カテゴリーは10文字以下で入力してください");
        }

        if (errorMessages.size() != 0) {
            return false;
        }
        return true;
    }
}