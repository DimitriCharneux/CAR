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

@WebServlet(name = "Auteurs", urlPatterns = "/auteurs")
public class AfficheAuteursServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	@EJB
	private Bibliotheque biblio;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		req.setAttribute("auteurs", biblio.retourneAuteurs());
		this.getServletContext().getRequestDispatcher("/auteurs.jsp").forward(req, resp);
	}
}
