import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class InitialiseBibliothequeServlet extends HttpServlet{
	@EJB(name="maBiblio")
	private BibliothequeItf biblio;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		biblio.initBiblio();
		out.println("<html><body><h2>Bibliothèque initialisée.</h2></body></html>"); 
	}
}
