package car.tp4;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Init", urlPatterns = "/init")
public class InitialiseBibliothequeServlet extends HttpServlet{
	@EJB
	private Bibliotheque biblio;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		//biblio = new Bibliotheque();
		biblio.initBiblio();
		//out.println("<html><body><h2>Bibliothèque "); 
		//out.println("initialisée.</h2></body></html>");
		req.setAttribute("init", "données initialisées");
		this.getServletContext().getRequestDispatcher("init.jsp").forward(req, resp);
		
	}
}
