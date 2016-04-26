package main.java;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Livre {
	public static int cptId = 0;
	
	@Id
	private int id;
	private String auteur, titre, annee;
	
	public Livre(String auteur, String titre, String annee){
		this.auteur = auteur;
		this.titre = titre;
		this.annee = annee;
		this.id = cptId;
		Livre.cptId++;
	}

	public int getId() {
		return id;
	}

	public String getAuteur() {
		return auteur;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getAnnee() {
		return annee;
	}

	public void setAnnee(String annee) {
		this.annee = annee;
	}
}
