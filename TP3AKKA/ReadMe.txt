Dimitri Charneux.

Deux classes d'acteurs sont présentes dans l'archive, ActeurVoisin et ActeurArbre. ActeurArbre est une version obsolete ne fonctionnant qu'avec les arbres. 
Pour ajouter un fils ou un voisin, il faut appeler :
	acteur1.tell(new MessageEnfant(MessageEnfant.AJOUTEFILS, acteur5), ActorRef.noSender());
L'ajout d'un parent se fait automatiquement quand on ajoute un fils.


Pour tester si un message a déjà été reçu, je garde une liste des précédents messages dans les acteurs.
Pour autoriser les messages ayant le même contenu, j'ai ajouté une variable date à la création du message.
Grâce à cela, on peut différencier les messages ayant le même contenu mais étant deux messages indépendants.

Je n'ai pas réalisé de tests unitaires car je ne sais pas comment tester des méthodes qui sont appelées par le systeme.
Il aurait fallu tester les traces et vérifier que chaque noeud a recu le message une seule fois.