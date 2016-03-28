import java.lang.annotation.Inherited;
import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

/**
 * Cette classe représente un acteur de notre architecture akka. Cette classe
 * fonctionne pour les arbres et les graphes.
 * 
 * @author Dimitri Charneux
 * 
 */
public class ActeurVoisin extends UntypedActor {
	private List<ActorRef> voisins;
	private List<Message> precedents;

	/**
	 * Constructeur de la classe.
	 */
	public ActeurVoisin() {
		voisins = new ArrayList<ActorRef>();
		precedents = new ArrayList<Message>();
	}

	/**
	 * Méthode qui gère le comportement de l'acteur quand il reçoit un message.
	 */
	public void onReceive(Object arg0) throws Exception {
		if (arg0 instanceof String) {
			System.out.println((String) arg0);
		} else if (arg0 instanceof Message) {

			if (precedents.contains(arg0)) {
				System.out.println(this.getSelf().path().name()
						+ " a deja recu le message "
						+ ((Message) arg0).getMsg());
				return;
			}
			System.out.println(this.getSelf().path().name() + " recoit : "
					+ ((Message) arg0).getMsg() + " de "
					+ getSender().path().name());

			for (ActorRef a : voisins) {
				if (!a.equals(this.getSender()))
					a.tell((Message) (arg0), this.getSelf());
			}
			precedents.add((Message) arg0);

		} else if (arg0 instanceof MessageEnfant) {
			MessageEnfant msg = (MessageEnfant) arg0;
			System.out.println(this.getSelf().path().name() + msg);
			switch (msg.getMethode()) {
			case MessageEnfant.AJOUTEVOISIN:
			case MessageEnfant.AJOUTEPERE:
				this.ajouteVoisin(msg.getActeur());
				break;
			case MessageEnfant.AJOUTEFILS:
				this.ajouteFils(msg.getActeur());
				break;
			case MessageEnfant.ENLEVEVOISIN:
			case MessageEnfant.ENLEVEPERE:
				this.enleveVoisin(msg.getActeur());
				break;
			case MessageEnfant.ENLEVEFILS:
				this.enleveFils(msg.getActeur());
				break;
			}
		}
	}

	/**
	 * Cette méthode ajoute un voisin à l'acteur.
	 * 
	 * @param a
	 *            Acteur à ajouté comme fils.
	 */
	public void ajouteVoisin(ActorRef a) {
		voisins.add(a);
	}

	/**
	 * Cette méthode enlève un voisin à l'acteur.
	 * 
	 * @param a
	 *            Acteur à retiré comme fils.
	 */
	public void enleveVoisin(ActorRef a) {
		voisins.remove(a);
	}

	/**
	 * Cette méthode ajoute un fils à l'acteur. Elle envoit également un message
	 * au fils indiquant que l'acteur est son père.
	 * 
	 * @param a
	 *            Acteur à ajouté comme fils.
	 */
	public void ajouteFils(ActorRef a) {
		voisins.add(a);
		a.tell(new MessageEnfant(MessageEnfant.AJOUTEPERE, this.getSelf()),
				this.getSelf());
	}

	/**
	 * Cette méthode enlève un fils à l'acteur. Elle envoit également un message
	 * au fils indiquant que l'acteur n'est plus son père.
	 * 
	 * @param a
	 *            Acteur à retiré comme fils.
	 */
	public void enleveFils(ActorRef a) {
		voisins.remove(a);
		a.tell(new MessageEnfant(MessageEnfant.ENLEVEPERE, this.getSelf()),
				this.getSelf());
	}
}
