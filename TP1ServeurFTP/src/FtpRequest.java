import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class FtpRequest extends Thread {
	private Socket soc;
	private int timeOut;
	private OutputStreamWriter outputwriter;
	private BufferedReader inputreader;
	private boolean estAuthentifer;

	public FtpRequest(Socket soc) {
		super("Thread ftp request");
		this.soc = soc;
		this.timeOut = 5;
		this.estAuthentifer = false;
	}

	public void run() {
		try {
			outputwriter = new OutputStreamWriter(soc.getOutputStream());
			inputreader = new BufferedReader(new InputStreamReader(
					soc.getInputStream()));
			send(CodeDeRetour.servicePret());
			identification();
			while (estAuthentifer) {
				interprete();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void identification() throws IOException {
		String user = null;
		while (!estAuthentifer) {
			String[] cmd = inputreader.readLine().split(" ", 2);
			switch (cmd[0]) {
			case "USER":
				user = cmd[1];
				send(CodeDeRetour.attenteMdp());
				break;
			case "PASS":
				if (user != null && user.equals(cmd[1])) {
					estAuthentifer = true;
					send(CodeDeRetour.authentificationOk());
				} else {
					send(CodeDeRetour.authentificationFail());
				}
				break;
			case "QUIT":
				deconnexion();
				return;
			default:
				send("Commande Inconnue.\n");
				break;
			}
		}

	}

	private void interprete() throws IOException {
		String user = null;
		String[] cmd = inputreader.readLine().split(" ", 2);
		switch (cmd[0]) {
		case "ECHO":
			send(cmd[1] + "\n");
			break;
		case "QUIT":
			deconnexion();
			return;
		default:
			send("Commande Inconnue.\n");
			break;
		}
	}
	
	private void deconnexion() throws IOException{
		send(CodeDeRetour.deconnexion());
		estAuthentifer = false;
		soc.close();
	}

	private void send(String msg) {
		try {
			outputwriter.write(msg);
			outputwriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
