package main.java;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AfficheAuteursServlet extends HttpServlet{
	@EJB(name="maBiblio")
	private BibliothequeItf biblio;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<html><body><h2>Voici la liste des auteurs de la bibliotheque</h2><ul>"); 
		List<String> listAuteurs = biblio.retourneAuteurs(); 
		for(String auteur : listAuteurs){
			out.println("<li>" + auteur + "</li>");
		}
		out.println("</ul></body></html>"); 
	}
}
