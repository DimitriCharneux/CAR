import java.io.Serializable;


public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4104056810899927705L;
	private String msg;
	
	public Message(String msg){
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
}
