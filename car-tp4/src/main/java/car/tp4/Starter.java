package car.tp4;

import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;


@WebServlet(name = "Starter", urlPatterns = "/starter", loadOnStartup = 1)
public class Starter extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	@EJB
	protected Bibliotheque l;
	
	public void init(ServletConfig config) {
		l.init();
		config.getServletContext().setAttribute("library", l);
	}	
	
}