package car.tp4;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Livre implements Serializable{
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name="AUTEUR")
	private String auteur;
	
	@Column(name="TITRE")
	private String titre;
	
	@Column(name="ANNEE")
	private String annee;
	
	public Livre(String auteur, String titre, String annee){
		this.auteur = auteur;
		this.titre = titre;
		this.annee = annee;
	}

	public long getId() {
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
