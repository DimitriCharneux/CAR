import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class FtpRequest extends Thread {
	private Socket soc, socData;
	private int timeOut;
	private OutputStreamWriter outputwriter, outputWriterData;
	private BufferedReader inputreader;
	private boolean estAuthentifer, veutQuitter;
	private String utilisateurConnecte, tmpUser, repPrinc;
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
		System.out.println("commande recu :");
		for (String tmp : cmd)
			System.out.println("\t" + tmp);

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
		case "PORT":
			if (estAuthentifer && cmd.length == 2) {
				processPort(cmd[1]);
			} else
				send(CodeDeRetour.nonConnecte());
			break;
		case "STOR":
			if (estAuthentifer && cmd.length == 2) {
				processSTOR(cmd[1]);
			} else
				send(CodeDeRetour.nonConnecte());
			break;
		case "PWD":
			if (estAuthentifer) {
				processPWD();
			} else
				send(CodeDeRetour.nonConnecte());
			break;
		case "CWD":
			if (estAuthentifer && cmd.length == 2) {
				processCWD(cmd[1]);
			} else
				send(CodeDeRetour.nonConnecte());
			break;
		case "MKD":
			if (estAuthentifer && cmd.length == 2) {
				processMKD(cmd[1]);
			} else
				send(CodeDeRetour.nonConnecte());
			break;
		case "CDUP":
			if (estAuthentifer) {
				processCDUP();
			} else
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

	private void processPort(String cmd) {
		String[] tmpPort = cmd.split(",");
		String tmp = "";
		int port = Integer.parseInt(tmpPort[4]) * 256
				+ Integer.parseInt(tmpPort[5]);
		for (int i = 0; i < 4; i++)
			tmp += tmpPort[i] + ".";
		tmp = tmp.substring(0, tmp.length() - 1);
		System.out.println("addr : \'" + tmp + "\', port : \'" + port + "\'");
		try {
			socData = new Socket(tmp, port);
			send(CodeDeRetour.serviceOk());
		} catch (Exception e) {
			send(CodeDeRetour.erreurArgs());
		}

	}

	private void processRETR(String pathname) {
		try {
			InputStream flux = new FileInputStream(repertoire.getPath() + "/"
					+ pathname);
			InputStreamReader lecture = new InputStreamReader(flux);
			BufferedReader buff = new BufferedReader(lecture);
			String tmp = "", line;
			while ((line = buff.readLine()) != null) {
				tmp += line + "\n";
			}
			buff.close();
			sendData(tmp);
		} catch (Exception e) {

		}

	}

	private void processSTOR(String fileName) {
		File fichier = new File(repertoire.getPath() +"/" +fileName);
		String data = receiveData();
		if(data == null)return ;
		try {
			fichier.createNewFile();
			FileOutputStream output = new FileOutputStream(fichier);
			output.write(data.getBytes());
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void processPWD() {
		System.out.println("path : " + repertoire.getPath());
		send(repertoire.getPath() + "\n");
	}
	

	private void processCWD(String nameRep) {
		File[] tmp = repertoire.listFiles();
		for (File rep : tmp) {
			if (rep.getName().equals(nameRep) && rep.isDirectory()) {
				repertoire = rep;
				send(CodeDeRetour.serviceOk());
				return;
			}
		}
		send(CodeDeRetour.erreur());
	}
	
	private void processMKD(String string) {
		repertoire = new File(repertoire.getPath() + "/" + string);
		repertoire.mkdir();
		repertoire = repertoire.getParentFile();
		send(CodeDeRetour.serviceOk());
	}

	private void processCDUP() {
		System.out.println(repPrinc);
		System.out.println(repertoire.getPath());
		
		if(!repertoire.getPath().equals(repPrinc)){
			repertoire = repertoire.getParentFile();
			send(CodeDeRetour.serviceOk());
		} else {
			send(CodeDeRetour.erreur());
		}
	}
	

	private void ouvreRepertoire() {
		repertoire = new File("repPrincipale");
		repertoire.mkdir();
		File[] tmp = repertoire.listFiles();
		for (File rep : tmp) {
			if (rep.getName().equals(utilisateurConnecte) && rep.isDirectory()) {
				repertoire = rep;
				repPrinc = repertoire.getPath();
				return;
			}
		}
		repertoire = new File("repPrincipale/" + utilisateurConnecte);
		repertoire.mkdir();
		repPrinc = repertoire.getPath();
	}

	private void processLIST() {
		String listFile = "";
		File[] files = repertoire.listFiles();
		for (File f : files) {
			listFile += f.getName() + "\n";
		}
		sendData(listFile);
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

	private void sendData(String msg) {
		send(CodeDeRetour.beginTransfert());
		try {
			outputWriterData = new OutputStreamWriter(socData.getOutputStream());
			outputWriterData.write(msg);
			outputWriterData.flush();
			socData.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		send(CodeDeRetour.endTransfert());
	}

	private String receiveData() {
		send(CodeDeRetour.beginTransfert());
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					socData.getInputStream()));
			String res = "", tmp;
			while((tmp = br.readLine())!=null){
				res += tmp + "\n";
			}
			send(CodeDeRetour.endTransfert());
			return res;
		} catch (IOException e) {
			e.printStackTrace();
			send(CodeDeRetour.endTransfert());
			return null;
		}
	}

}
