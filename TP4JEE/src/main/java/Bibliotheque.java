package main.java;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;


@Stateless(name="maBiblio")
public class Bibliotheque implements BibliothequeItf{
	private List<Livre> listLivres;
	
	public Bibliotheque(){
		listLivres = new ArrayList<Livre>();
	}
	
	@Override
	public void initBiblio() {
		Livre tmp = new Livre("Victor Hugo", "Les miserables", "1862");
		listLivres.add(tmp);
		tmp = new Livre("Charles Baudelaire", "Les fleurs du mal", "1867");
		listLivres.add(tmp);
		tmp = new Livre("Andrzej Sapkowski", "Le sorceleur : Tome 1", "1992");
		listLivres.add(tmp);
		
		
	}

	@Override
	public List<String> retourneAuteurs() {
		List<String> listAuteurs = new ArrayList<String>();
		for(Livre l : listLivres){
			listAuteurs.add(l.getAuteur());
		}
		return listAuteurs;
	}
	
}
