package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Extraction;
import beans.PostComment;
import beans.User;
import service.CommentService;
import service.ExtractionService;

@WebServlet(urlPatterns = { "/extraction" })
public class ExtractionServlet extends HttpServlet {

	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws IOException, ServletException {

	        boolean isShowExtractionForm = false;
	        User user = (User) request.getSession().getAttribute("loginUser");
	        if (user != null) {
	            isShowExtractionForm = true;
	       	}else{
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

	    	Extraction posts = getExtraction(request);
	    	List<Extraction> postsB = new ExtractionService().select(posts);
         	request.setAttribute("posts", postsB);

	        List<PostComment> comments = new CommentService().select();
	        request.setAttribute("start", request.getParameter("start"));
	        request.setAttribute("end", request.getParameter("end"));
	        request.setAttribute("category", request.getParameter("category"));
	        request.setAttribute("comments", comments);
	        request.setAttribute("isShowExtractionForm", isShowExtractionForm);
	        request.getRequestDispatcher("/home.jsp").forward(request, response);


	    }

	    private Extraction getExtraction(HttpServletRequest request) throws IOException, ServletException {
	    	Extraction posts = new Extraction();
	    	posts.setStart(request.getParameter("start"));
	    	posts.setEnd(request.getParameter("end"));
	    	posts.setCategory(request.getParameter("category"));

			return posts;
	    }
	}
