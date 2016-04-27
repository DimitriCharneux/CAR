package car.tp4;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface BibliothequeItfRemote {
	public void initBiblio();
	public List<String> retourneAuteurs();
}
