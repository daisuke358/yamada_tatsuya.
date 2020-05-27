package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import beans.PostComment;
import beans.User;
import beans.UserAccount;
import beans.UserPost;
import service.CommentService;
import service.PostService;
import service.UserService;

@WebServlet(urlPatterns = { "/signup" })
public class SignUpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        User user = (User) request.getSession().getAttribute("loginUser");
        if (user != null) {
       	}else{
       		List<String> errorMessages = new ArrayList<String>();
            errorMessages.add("ログインしてください");
            request.setAttribute("errorMessages", errorMessages);
       		request.getRequestDispatcher("/login.jsp").forward(request, response);
       	}

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

        request.getRequestDispatcher("signup.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        List<String> errorMessages = new ArrayList<String>();
        User user = getUser(request);
        UserAccount A = new UserService().select2(request.getParameter("account"));
	        if (A != null) {
	        	request.setAttribute("errorMessages", "アカウント名が重複しています");
	        	request.setAttribute("user", user);
	            request.getRequestDispatcher("signup.jsp").forward(request, response);
	            return;
	        }

        if (!isValid(user, errorMessages)) {
            request.setAttribute("errorMessages", errorMessages);
            request.setAttribute("user", user);
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }
        new UserService().insert(user);

        response.sendRedirect("./management");
    }

	private User getUser(HttpServletRequest request) throws IOException, ServletException {

        User user = new User();
        user.setAccount(request.getParameter("account"));
        user.setPassword(request.getParameter("password"));
        user.setPasswordB(request.getParameter("passwordB"));
        user.setName(request.getParameter("name"));
        user.setBranch_office(request.getParameter("branch_office"));
        user.setDepartment(request.getParameter("department"));
        user.setStatus(request.getParameter("status"));
        return user;
    }

    private boolean isValid(User user, List<String> errorMessages) {


        String account = user.getAccount();
        String password = user.getPassword();
        String passwordB = user.getPasswordB();
        String branch_office = user.getBranch_office();
        String department = user.getDepartment();
        String name = user.getName();


        if (branch_office.equals("1") && department.equals("3")
        	|| branch_office.equals("1") && department.equals("4")
        	|| branch_office.equals("2") && department.equals("1")
        	|| branch_office.equals("2") && department.equals("2")
        	|| branch_office.equals("3") && department.equals("1")
        	|| branch_office.equals("3") && department.equals("2")
        	|| branch_office.equals("4") && department.equals("1")
        	|| branch_office.equals("4") && department.equals("2")
        	) {errorMessages.add("支社と部署の組み合わせが誤っています");
        }


        if (StringUtils.isEmpty(account)) {
            errorMessages.add("アカウント名を入力してください");
        } else if (account.length() < 6) {
            errorMessages.add("アカウント名は6文字以上を入力してください");
        } else if (20 < account.length()) {
            errorMessages.add("アカウント名は20文字以下で入力してください");
        } else if (account.matches("[azAZ0*9]")) {
            errorMessages.add("アカウント名は半角英数字で入力してください");
        }

        if (StringUtils.isEmpty(password)) {
            errorMessages.add("パスワードを入力してください");
        } else if (password.length() < 6) {
            errorMessages.add("パスワードは6文字以上を入力してください");
        } else if (20 < password.length()) {
            errorMessages.add("パスワードは20文字以下で入力してください");
        } else if (password.matches("[azAZ0*9]")) {
            errorMessages.add("パスワードは半角文字で入力してください");
        }else if(!password.equals(passwordB)) {
        	errorMessages.add("パスワードが一致しません");
        }

        if (StringUtils.isEmpty(name)) {
            errorMessages.add("名称を入力してください");
        } else if (10 < name.length()) {
            errorMessages.add("名称は10文字以下を入力してください");
        }

        if (errorMessages.size() != 0) {
            return false;
        }
        return true;
    }
}