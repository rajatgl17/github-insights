package in.rajatgoel.github_insights.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.rajatgoel.github_insights.model.DBUpdater;

@WebServlet(urlPatterns={"/UpdateDB"})
public class UpdateDB extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBUpdater dbu;

	public UpdateDB() {
		super();
	}
	
	public void init(){
		dbu = new DBUpdater();
		Thread t = new Thread(dbu);
		t.start();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("Updation is started");		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void destroy(){
		dbu.stop();
	}

}
