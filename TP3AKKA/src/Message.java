import java.io.Serializable;

/**
 * Classe représentant un message contenant une chaîne de caractère.
 * @author dimitri
 *
 */
public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4104056810899927705L;
	private String msg;
	
	/**
	 * Constructeur de la classe.
	 * @param msg Chaîne du message.
	 */
	public Message(String msg){
		this.msg = msg;
	}

	/**
	 * Méthode retournant le message.
	 * @return Message envoyé.
	 */
	public String getMsg() {
		return msg;
	}
}
