import java.lang.annotation.Inherited;
import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

/**
 * Cette classe représente un acteur de notre architecture akka. Cette classee est obsolète car elle ne fonctionne que pour les arbres.
 * 
 * @author Dimitri Charneux
 * 
 */
public class ActeurArbre extends UntypedActor {
	private List<ActorRef> fils;
	private ActorRef pere;

	/**
	 * Constructeur de la classe.
	 */
	public ActeurArbre() {
		fils = new ArrayList<ActorRef>();
	}

	/**
	 * Méthode qui gère le comportement de l'acteur quand il reçoit un message.
	 */
	public void onReceive(Object arg0) throws Exception {
		if (arg0 instanceof String) {
			System.out.println((String) arg0);
		} else if (arg0 instanceof Message) {
			System.out.println(this.getSelf().path().name() + " recoit : "
					+ ((Message) arg0).getMsg() + " de "
					+ getSender().path().name());

			for (ActorRef a : fils) {
				if (!a.equals(this.getSender()))
					a.tell((Message) (arg0), this.getSelf());
			}
			if (pere != null && !pere.equals(this.getSender())) {
				pere.tell(arg0, this.getSelf());
			}

		} else if (arg0 instanceof MessageEnfant) {
			MessageEnfant msg = (MessageEnfant) arg0;
			System.out.println(this.getSelf().path().name() + msg);
			switch (msg.getMethode()) {
			case MessageEnfant.AJOUTEFILS:
				this.ajouteFils(msg.getActeur());
				break;
			case MessageEnfant.ENLEVEFILS:
				this.enleveFils(msg.getActeur());
				break;
			case MessageEnfant.AJOUTEPERE:
				pere = msg.getActeur();
				break;
			case MessageEnfant.ENLEVEPERE:
				pere = null;
				break;
			}
		}
	}

	/**
	 * Cette méthode ajoute un fils à l'acteur. Elle envoit également un message
	 * au fils indiquant que l'acteur est son père.
	 * 
	 * @param a
	 *            Acteur à ajouté comme fils.
	 */
	public void ajouteFils(ActorRef a) {
		fils.add(a);
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
		fils.remove(a);
		a.tell(new MessageEnfant(MessageEnfant.ENLEVEPERE, this.getSelf()),
				this.getSelf());
	}
}
