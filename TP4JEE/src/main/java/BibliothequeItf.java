package main.java;
import java.util.List;

import javax.ejb.Local;


@Local
public interface BibliothequeItf {
	public void initBiblio();
	public List<String> retourneAuteurs();
}
