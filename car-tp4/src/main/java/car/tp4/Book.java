package car.tp4;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Book{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String auteur;
	
	private String titre;
	
	private String annee;
	
	public Book(){
		this.auteur = "default";
		this.titre = "default";
		this.annee = "default";
	}
	
	public Book(String auteur, String titre, String annee){
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
