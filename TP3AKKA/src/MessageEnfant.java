import java.io.Serializable;

import akka.actor.ActorRef;


public class MessageEnfant implements Serializable{
	private static final long serialVersionUID = 4104056810899927705L;
	public final static int AJOUTEFILS = 42, ENLEVEFILS = 1337; 
	private ActorRef fils;
	private int methode;
	
	public MessageEnfant(int methode, ActorRef fils){
		this.methode = methode;
		this.fils = fils;
	}

	public ActorRef getFils() {
		return fils;
	}

	public void setFils(ActorRef fils) {
		this.fils = fils;
	}

	public int getMethode() {
		return methode;
	}

	public void setMethode(int methode) {
		this.methode = methode;
	}

	public String toString() {
		if(methode == AJOUTEFILS)
			return " ajoute " + fils.path().name() ;
		else
			return " retire " + fils.path().name() ;
	}

	
}
