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
import beans.UserEditAccount;
import beans.UserPost;
import service.CommentService;
import service.EditService;
import service.PostService;
import service.UserService;

@WebServlet(urlPatterns = { "/edit" })
public class EditServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

    	boolean isShowEditForm = false;
        User user1 = (User) request.getSession().getAttribute("loginUser");
        if (user1 != null) {
            isShowEditForm = true;
       	}else{
       		List<String> errorMessages = new ArrayList<String>();
            errorMessages.add("ログインしてください");
            request.setAttribute("errorMessages", errorMessages);
       		request.getRequestDispatcher("/login.jsp").forward(request, response);
       	}
        if (!user1.getBranch_office().equals("1")) {
        	List<String> errorMessages = new ArrayList<String>();
            List<UserPost> posts = new PostService().select();
            List<PostComment> comments = new CommentService().select();
            errorMessages.add("権限がありません");
            request.setAttribute("errorMessages", errorMessages);
            request.setAttribute("posts", posts);
            request.setAttribute("comments", comments);
       		request.getRequestDispatcher("/home.jsp").forward(request, response);
        }

        System.out.println(request.getParameter("id"));
        User user3 = new EditService().select3(request.getParameter("id"));
        if (user3 == null) {
        	List<Management> users = new UserService().select();
        	List<String> errorMessages = new ArrayList<String>();
        	errorMessages.add("不正なパラメータが入力されました");
            request.setAttribute("errorMessages", errorMessages);
            request.setAttribute("users", users);
            request.getRequestDispatcher("/management.jsp").forward(request, response);
        }


    	User user = new EditService().select(request.getParameter("id"));

    	request.setAttribute("loginUser", user1);
    	request.setAttribute("user", user);
        request.setAttribute("isShowEditForm", isShowEditForm);
    	request.getRequestDispatcher("edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        List<String> errorMessages = new ArrayList<String>();
        UserEdit user = getUser(request);

        String account = request.getParameter("account");
        String oldAccount = request.getParameter("oldAccount");
        UserEditAccount A = new UserService().select4(account, oldAccount);
	        if (A != null) {
	        	request.setAttribute("errorMessages", "アカウント名が重複しています");
	        	request.setAttribute("user", user);
	            request.getRequestDispatcher("edit.jsp").forward(request, response);
	            return;
	        }

        if (!isValid(user, errorMessages)) {
            request.setAttribute("errorMessages", errorMessages);
        	request.setAttribute("user", user);
            request.getRequestDispatcher("edit.jsp").forward(request, response);
            return;
        }

        new EditService().update(user);

        response.sendRedirect("./management");
    }

    private UserEdit getUser(HttpServletRequest request) throws IOException, ServletException {

        UserEdit user = new UserEdit();
        user.setId(Integer.parseInt(request.getParameter("id")));
        user.setAccount(request.getParameter("account"));
        user.setPassword(request.getParameter("password"));
        user.setPasswordB(request.getParameter("passwordB"));
        user.setName(request.getParameter("name"));
        user.setBranch_office(Integer.parseInt(request.getParameter("branch_office")));
        user.setDepartment(Integer.parseInt(request.getParameter("department")));
        user.setStatus(Integer.parseInt(request.getParameter("status")));
        return user;
    }

    private boolean isValid(UserEdit user, List<String> errorMessages) {

        String account = user.getAccount();
        String password = user.getPassword();
        String name = user.getName();
        String passwordB = user.getPasswordB();
        int branch_office = user.getBranch_office();
        int department = user.getDepartment();

        if (branch_office == 1 && department == 3
        	|| branch_office == 1 && department == 4
        	|| branch_office == 2 && department == 1
        	|| branch_office == 2 && department == 2
        	|| branch_office == 3 && department == 1
        	|| branch_office == 3 && department == 2
        	|| branch_office == 4 && department == 1
        	|| branch_office == 4 && department == 2
        	){errorMessages.add("支社と部署の組み合わせが誤っています");
            }

        if (0 < account.length() && account.length() < 6) {
            errorMessages.add("アカウント名は6文字以上を入力してください");
        } else if (20 < account.length()) {
            errorMessages.add("アカウント名は20文字以下で入力してください");
        } else if (account.matches("[azAZ0*9]")) {
            errorMessages.add("アカウント名は半角英数字で入力してください");
        }

        if (20 < password.length()) {
            errorMessages.add("パスワードは20文字以下で入力してください");
        } else if (password.matches("[azAZ0*9]")) {
            errorMessages.add("パスワードは半角文字で入力してください");
        }else if(!password.equals(passwordB)) {
        	errorMessages.add("パスワードが一致しません");
        }

        if (10 < name.length()) {
            errorMessages.add("名称は10文字以下を入力してください");
        }

        if (errorMessages.size() != 0) {
            return false;
        }
        return true;
    }
}