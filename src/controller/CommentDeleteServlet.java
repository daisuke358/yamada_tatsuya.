package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Comment;
import service.CommentService;

@WebServlet(urlPatterns = { "/commentDelete" })
public class CommentDeleteServlet extends HttpServlet {

	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException  {

	        String id = request.getParameter("id");

	        Comment comment = new Comment();
	        comment.setId(id);

	        new CommentService().delete(comment);
	        response.sendRedirect("./");
	    }
	}

