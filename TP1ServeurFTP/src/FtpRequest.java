import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class FtpRequest extends Thread {
	private Socket soc;
	private int timeOut;
	private OutputStreamWriter outputwriter;
	private BufferedReader inputreader;
	private boolean estAuthentifer, veutQuitter;
	private String utilisateurConnecte, tmpUser;
	private File repertoire;

	public FtpRequest(Socket soc) {
		super("Thread ftp request");
		this.soc = soc;
		this.timeOut = 5;
		this.estAuthentifer = false;
		veutQuitter = false;
	}

	public void run() {
		try {
			outputwriter = new OutputStreamWriter(soc.getOutputStream());
			inputreader = new BufferedReader(new InputStreamReader(
					soc.getInputStream()));
			send(CodeDeRetour.servicePret());
			while (!veutQuitter) {
				processRequest();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("fin du thread");
	}

	private void processRequest() throws IOException {
		String[] cmd = inputreader.readLine().split(" ", 2);
		switch (cmd[0]) {
		case "USER":
			if (cmd.length < 2) {
				send(CodeDeRetour.authentificationFail());
				return;
			}
			if (estAuthentifer) {
				estAuthentifer = false;
				utilisateurConnecte = "";
			}
			tmpUser = cmd[1];
			send(CodeDeRetour.attenteMdp());
			break;
		case "PASS":
			if (tmpUser != null && cmd.length == 2 && tmpUser.equals(cmd[1])) {
				estAuthentifer = true;
				utilisateurConnecte = tmpUser;
				send(CodeDeRetour.authentificationOk());
				ouvreRepertoire();
			} else {
				send(CodeDeRetour.authentificationFail());
			}
			break;
		case "ECHO":
			send(cmd[1] + "\n");
			break;
		case "LIST":
			if (estAuthentifer)
				processLIST();
			else
				send(CodeDeRetour.nonConnecte());
			break;
		case "RETR":
			if (estAuthentifer && cmd.length == 2)
				processRETR(cmd[1]);
			else
				send(CodeDeRetour.nonConnecte());
			break;
		case "QUIT":
			deconnexion();
			return;
		default:
			send("Commande \'" + cmd[0] + "\' Inconnue.\n");
			break;
		}
	}

	private void processRETR(String pathname) {
		try {
			InputStream flux=new FileInputStream(repertoire.getPath() + "/" + pathname); 
			InputStreamReader lecture=new InputStreamReader(flux);
			BufferedReader buff=new BufferedReader(lecture);
			String tmp = "", line;
			while((line = buff.readLine())!=null){
				tmp += line + "\n";
			}
			buff.close();
			send(tmp);
		} catch (Exception e) {

		}

	}

	private void ouvreRepertoire() {
		repertoire = new File("repPrincipale");
		repertoire.mkdir();
		File[] tmp = repertoire.listFiles();
		for (File rep : tmp) {
			if (rep.getName().equals(utilisateurConnecte) && rep.isDirectory()) {
				repertoire = rep;
				return;
			}
		}
		repertoire = new File("repPrincipale/" + utilisateurConnecte);
		repertoire.mkdir();
	}

	private void processLIST() {
		String listFile = "";
		File[] files = repertoire.listFiles();
		for (File f : files) {
			listFile += f.getName() + "\n";
		}
		send(listFile);
	}

	private void deconnexion() throws IOException {
		send(CodeDeRetour.deconnexion());
		estAuthentifer = false;
		veutQuitter = true;
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
