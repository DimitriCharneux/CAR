package car.tp4;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AfficheAuteursServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	@EJB(name="Bibliotheque")
	private BibliothequeItf biblio;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		List<String> listAuteurs = biblio.retourneAuteurs(); 
		
		if(listAuteurs.isEmpty())
			out.println("<html><body><h2>La bibliotheque est vide.</h2><ul>");
		else 
			out.println("<html><body><h2>Voici la liste des auteurs de la bibliotheque : </h2><ul>"); 
		
		for(String auteur : listAuteurs){
			out.println("<li>" + auteur + "</li>");
		}
		out.println("</ul></body></html>"); 
	}
}
