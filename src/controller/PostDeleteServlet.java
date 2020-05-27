package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Post;
import service.PostService;

@WebServlet(urlPatterns = { "/postDelete" })
public class PostDeleteServlet extends HttpServlet {

	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException  {

	        int id = Integer.parseInt(request.getParameter("id"));

	        Post post = new Post();
	        post.setId(id);


	        new PostService().delete(post);
	        response.sendRedirect("./");
	    }
	}

