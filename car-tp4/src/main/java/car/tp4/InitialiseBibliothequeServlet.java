package car.tp4;
import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Init", urlPatterns = "/init")
public class InitialiseBibliothequeServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@EJB
	protected Bibliotheque biblio;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		biblio.initBiblio();
		req.setAttribute("result", "donnees initialisees");
		this.getServletContext().getRequestDispatcher("/init.jsp").forward(req, resp);
		
	}
}
