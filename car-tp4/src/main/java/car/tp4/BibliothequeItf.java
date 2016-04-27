package car.tp4;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;

@Local
public interface BibliothequeItf {
	public void initBiblio();
	public List<String> retourneAuteurs();
}
