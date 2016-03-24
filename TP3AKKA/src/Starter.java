import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Starter {
	public static void main(String args[]) {
		ActorSystem system = ActorSystem.create("MySystem");
		ActorRef acteur1, acteur2, acteur3, acteur4, acteur5, acteur6;
		acteur1 = system.actorOf(Props.create(Acteur.class), "acteur1");
		acteur2 = system.actorOf(Props.create(Acteur.class), "acteur2");
		acteur3 = system.actorOf(Props.create(Acteur.class), "acteur3");
		acteur4 = system.actorOf(Props.create(Acteur.class), "acteur4");
		acteur5 = system.actorOf(Props.create(Acteur.class), "acteur5");
		acteur6 = system.actorOf(Props.create(Acteur.class), "acteur6");
		acteur1.tell(new MessageEnfant(MessageEnfant.AJOUTEFILS, acteur2), ActorRef.noSender());
		acteur1.tell(new MessageEnfant(MessageEnfant.AJOUTEFILS, acteur5), ActorRef.noSender());
		acteur2.tell(new MessageEnfant(MessageEnfant.AJOUTEFILS, acteur3), ActorRef.noSender());
		acteur2.tell(new MessageEnfant(MessageEnfant.AJOUTEFILS, acteur4), ActorRef.noSender());
		acteur5.tell(new MessageEnfant(MessageEnfant.AJOUTEFILS, acteur6), ActorRef.noSender());
		
		acteur1.tell(new Message("azertyuiop"), ActorRef.noSender());
		system.shutdown();
	}
}
