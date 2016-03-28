import java.io.Serializable;
import java.util.Date;

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
	private Date date;
	
	/**
	 * Constructeur de la classe.
	 * @param msg Chaîne du message.
	 */
	public Message(String msg){
		this.msg = msg;
		this.date = new Date();
	}

	/**
	 * Méthode retournant le message.
	 * @return Message envoyé.
	 */
	public String getMsg() {
		return msg;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((msg == null) ? 0 : msg.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (msg == null) {
			if (other.msg != null)
				return false;
		} else if (!msg.equals(other.msg))
			return false;
		return true;
	}

	
}
