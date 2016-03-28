import java.io.Serializable;

import akka.actor.ActorRef;

/**
 * Classe contenant les messages permettant la gestion des parents.
 * @author Dimitri Charneux
 *
 */
public class MessageEnfant implements Serializable{
	private static final long serialVersionUID = 4104056810899927705L;
	public final static int AJOUTEFILS = 42, ENLEVEFILS = 1337, AJOUTEPERE = 666, ENLEVEPERE = 2016, AJOUTEVOISIN = 1234, ENLEVEVOISIN = 4321; 
	private ActorRef acteur;
	private int methode;
	
	/**
	 * Constructeur de la classe.
	 * @param methode Indique si l'acteur est ajouter ou retirer, en tant que père ou fils.
	 * @param acteur Acteur auquel on applique la méthode.
	 */
	public MessageEnfant(int methode, ActorRef acteur){
		this.methode = methode;
		this.acteur = acteur;
	}

	/**
	 * Méthode retournant l'acteur.
	 * @return Acteur de la classe.
	 */
	public ActorRef getActeur() {
		return acteur;
	}

	/**
	 * Méthode permettant de changer l'acteur.
	 * @param acteur Nouveau acteur de la classe.
	 */
	public void setActeur(ActorRef acteur) {
		this.acteur = acteur;
	}

	/**
	 * Méthode retournant la méthode de la classe.
	 * @return Méthode de la classe.
	 */
	public int getMethode() {
		return methode;
	}

	/**
	 * Méthode modifiant la méthode de la classe.
	 * @param methode Nouvelle méthode de la classe.
	 */
	public void setMethode(int methode) {
		this.methode = methode;
	}

	public String toString() {
		switch(methode){
		case AJOUTEFILS:
			return " ajoute le fils " + acteur.path().name() ;
		case ENLEVEFILS:
			return " retire le fils " + acteur.path().name() ;
		case AJOUTEPERE:
			return " ajoute le pere " + acteur.path().name() ;
		case ENLEVEPERE:
			return " enleve le pere " + acteur.path().name() ;
		case AJOUTEVOISIN:
			return " ajoute le voisin " + acteur.path().name() ;
		case ENLEVEVOISIN:
			return " enleve le voisin " + acteur.path().name() ;
		default:
			return "methode introuvable";
		}
	}

	
}
