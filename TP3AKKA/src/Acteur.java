import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class Acteur extends UntypedActor {
	private List<ActorRef> fils;

	public Acteur() {
		fils = new ArrayList<ActorRef>();
	}

	public void onReceive(Object arg0) throws Exception {
		if (arg0 instanceof String) {
			System.out.println((String) arg0);
		} else if (arg0 instanceof Message) {
			System.out.println(this.getSelf().path().name() + " recoit : "
					+ ((Message) arg0).getMsg() + " de " + getSender().path().name() );

			for (ActorRef a : fils) {
				a.tell((Message) (arg0), this.getSelf());
			}
		} else if (arg0 instanceof MessageEnfant) {
			MessageEnfant msg = (MessageEnfant) arg0;
			System.out.println(this.getSelf().path().name()  + msg);
			if (msg.getMethode() == MessageEnfant.AJOUTEFILS)
				this.ajouteFils(msg.getFils());
			else
				fils.remove(msg.getFils());
		}
	}

	public void ajouteFils(ActorRef a) {
		fils.add(a);
	}

	public void ajouteFils(List<ActorRef> a) {
		fils.addAll(a);
	}

	public void setFils(List<ActorRef> a) {
		fils = a;
	}

}
