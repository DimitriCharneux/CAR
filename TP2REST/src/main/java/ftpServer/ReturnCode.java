package ftpServer;

/**
 * Class contains the return code of the server FTP.
 * @author Dimitri Charneux
 *
 */
public class ReturnCode {
	public static final int SERVICE_PRET = 220, ATTENTE_MDP = 331,
			AUTHENTIFICATION_FAIL = 332, AUTHENTIFICATION_OK = 230,
			BEGIN_TRANSFERT = 125, END_TRANSFERT = 226, DECONNEXION = 221,
			NONCONNECTE = 532, SERVICE_OK = 200, ERREUR_ARG = 501,
			ERREUR = 550, SYST = 215;

	public static String servicePret() {
		return SERVICE_PRET + " : service pret.  ";
	}

	public static String attenteMdp() {
		return ATTENTE_MDP + " : Entrez votre mot de passe.  ";
	}

	public static String authentificationFail() {
		return AUTHENTIFICATION_FAIL + " : Erreur d'authentification.  ";
	}

	public static String authentificationOk() {
		return AUTHENTIFICATION_OK + " : Authentification réussi.  ";
	}

	public static String beginTransfert() {
		return BEGIN_TRANSFERT + " : Le tranfert à commencé.  ";
	}

	public static String endTransfert() {
		return END_TRANSFERT + " : Le transfert est terminé.  ";
	}

	public static String deconnexion() {
		return DECONNEXION + " : Déconnexion.  ";
	}

	public static String nonConnecte() {
		return NONCONNECTE
				+ " : Vous devez-être connecté pour effectuer cette action.  ";
	}

	public static String serviceOk() {
		return SERVICE_OK + " : Service ok.  ";
	}

	public static String erreurArgs() {
		return ERREUR_ARG + " : Erreur dans les arguments.  ";
	}

	public static String erreur() {
		return ERREUR + " : Erreur.  ";
	}
	
	public static String syst() {
		return SYST + " UNIX Type: L8";
	}

}
