public class CodeDeRetour {
	public static final int SERVICE_PRET = 220, ATTENTE_MDP = 331,
			AUTHENTIFICATION_FAIL = 332, AUTHENTIFICATION_OK = 230,
			BEGIN_TRANSFERT = 125, END_TRANSFERT = 226, DECONNEXION = 221, NONCONNECTE = 532;

	public static String servicePret(){
		return SERVICE_PRET + " : service pret.\n";
	}
	
	public static String attenteMdp(){
		return ATTENTE_MDP + " : Entrez votre mot de passe.\n";
	}
	
	public static String authentificationFail(){
		return AUTHENTIFICATION_FAIL + " : Erreur d'authentification.\n";
	}
	
	public static String authentificationOk(){
		return AUTHENTIFICATION_OK + " : Authentification réussi.\n";
	}
	
	public static String beginTranfert(){
		return BEGIN_TRANSFERT + " : Le tranfert à commencé.\n";
	}
	
	public static String endTransfert(){
		return END_TRANSFERT + " : Le transfert est terminé.\n";
	}
	
	public static String deconnexion(){
		return DECONNEXION + " : Déconnexion.\n";
	}
	
	
	public static String nonConnecte(){
		return NONCONNECTE + " : Vous devez-être connecté pour effectuer cette action.\n";
	}
	
	
	
}
