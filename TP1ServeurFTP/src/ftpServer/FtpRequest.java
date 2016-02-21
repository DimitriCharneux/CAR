package ftpServer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * This class manage one client connected to the FTP server.
 * 
 * @author Dimitri Charneux
 * 
 */
public class FtpRequest extends Thread {
	private Socket soc, socData;
	private int timeOut;
	private OutputStreamWriter outputwriter, outputWriterData;
	private BufferedReader inputreader;
	private boolean estAuthentifer, veutQuitter;
	private String utilisateurConnecte, tmpUser, repPrinc;
	private File repertoire;

	/**
	 * Constructor of this class.
	 * 
	 * @param soc
	 */
	public FtpRequest(Socket soc) {
		super("Thread ftp request");
		this.soc = soc;
		this.timeOut = 5;
		this.estAuthentifer = false;
		veutQuitter = false;
	}

	/**
	 * The heart of this class. This method receive and process all request of
	 * the client until the client stay connected.
	 */
	public void run() {
		try {
			outputwriter = new OutputStreamWriter(soc.getOutputStream());
			inputreader = new BufferedReader(new InputStreamReader(
					soc.getInputStream()));
			send(ReturnCode.servicePret());
			while (!veutQuitter) {
				processRequest();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("fin du thread");
	}

	/**
	 * This method sort the request and send the related function.
	 * @throws IOException
	 */
	private void processRequest() throws IOException {
		String[] cmd = inputreader.readLine().split(" ", 2);
		System.out.println("commande recu :");
		for (String tmp : cmd)
			System.out.println("\t" + tmp);

		switch (cmd[0]) {
		case "USER":
			if (cmd.length < 2) {
				send(ReturnCode.authentificationFail());
				return;
			}
			if (estAuthentifer) {
				estAuthentifer = false;
				utilisateurConnecte = "";
			}
			tmpUser = cmd[1];
			send(ReturnCode.attenteMdp());
			break;
		case "PASS":
			if (tmpUser != null && cmd.length == 2 && tmpUser.equals(cmd[1])) {
				estAuthentifer = true;
				utilisateurConnecte = tmpUser;
				send(ReturnCode.authentificationOk());
				openRepository();
			} else {
				send(ReturnCode.authentificationFail());
			}
			break;
		case "LIST":
			if (estAuthentifer)
				sendData(processLIST());
			else
				send(ReturnCode.nonConnecte());
			break;
		case "RETR":
			if (estAuthentifer && cmd.length == 2)
				sendData(processRETR(cmd[1]));
			else
				send(ReturnCode.nonConnecte());
			break;
		case "PORT":
			if (estAuthentifer && cmd.length == 2) {
				processPort(cmd[1]);
			} else
				send(ReturnCode.nonConnecte());
			break;
		case "STOR":
			if (estAuthentifer && cmd.length == 2) {
				processSTOR(cmd[1]);
			} else
				send(ReturnCode.nonConnecte());
			break;
		case "PWD":
			if (estAuthentifer) {
				send(processPWD());
			} else
				send(ReturnCode.nonConnecte());
			break;
		case "CWD":
			if (estAuthentifer && cmd.length == 2) {
				send(processCWD(cmd[1]));
			} else
				send(ReturnCode.nonConnecte());
			break;
		case "MKD":
			if (estAuthentifer && cmd.length == 2) {
				send(processMKD(cmd[1]));
			} else
				send(ReturnCode.nonConnecte());
			break;
		case "CDUP":
			if (estAuthentifer) {
				send(processCDUP());
			} else
				send(ReturnCode.nonConnecte());
			break;
		case "QUIT":
			disconnect();
			return;
		default:
			send("Commande \'" + cmd[0] + "\' Inconnue.\n");
			break;
		}
	}

	/**
	 * This method receive the port to send and receive the data by the client.
	 * @param cmd 
	 */
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
			send(ReturnCode.serviceOk());
		} catch (Exception e) {
			send(ReturnCode.erreurArgs());
		}

	}

	/**
	 * This method return the file hope by the client.
	 * @param pathname file hope by the client.
	 */
	private String processRETR(String pathname) {
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
			return tmp;
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * This method record a file send by the client in the current directory.
	 * @param fileName
	 */
	private void processSTOR(String fileName) {
		File fichier = new File(repertoire.getPath() + "/" + fileName);
		String data = receiveData();
		if (data == null)
			return;
		try {
			fichier.createNewFile();
			FileOutputStream output = new FileOutputStream(fichier);
			output.write(data.getBytes());
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method return the path of the current working directory.
	 */
	private String processPWD() {
		return repertoire.getPath().replace("repPrincipale", "") + "\n";
	}

	/**
	 * This method change the current working directory.
	 * @param nameRep new working directory.
	 */
	private String processCWD(String nameRep) {
		File tmp = null;
		if(nameRep.startsWith("/" + utilisateurConnecte)){
			tmp  = new File(repPrinc);
			if(nameRep.equals("/" + utilisateurConnecte)){
				repertoire = tmp;
				return ReturnCode.serviceOk();
			}
			nameRep = nameRep.replace("/" + utilisateurConnecte+"/", "");
		} else {
			tmp = repertoire;
		}
		String[] strs = nameRep.split("/");
		System.out.println(nameRep);
		for(String str : strs){
			System.out.println("str : " + str);
			tmp = changeInDirectory(str, tmp);
			if(tmp == null){
				return ReturnCode.erreur();
			}
		}
		if(tmp.getPath().startsWith(repPrinc)){
			repertoire = tmp;
			return ReturnCode.serviceOk();
		} else {
			return ReturnCode.erreur();
		}
		
	}
	
	private File changeInDirectory(String dir, File begin){
		if(dir.equals("..")) return begin.getPath().contains(utilisateurConnecte)?begin.getParentFile():null;
		File[] tmp = begin.listFiles();
		for (File rep : tmp) {
			System.out.println("sv");
			if (rep.getName().equals(dir) && rep.isDirectory()) {
				return rep;
			}
		}
		return null;
	}
	

	/**
	 * This method create a new directory in the current working directory.
	 * @param string the name of the new directory.
	 */
	private String processMKD(String string) {
		string = string.replaceAll("\\W", "_");
		repertoire = new File(repertoire.getPath() + "/" + string);
		repertoire.mkdir();
		repertoire = repertoire.getParentFile();
		return ReturnCode.serviceOk();
	}
	
	/**
	 * This method go in the parent of the current working directory.
	 */
	private String processCDUP() {
		if (!repertoire.getPath().equals(repPrinc)) {
			repertoire = repertoire.getParentFile();
			return ReturnCode.serviceOk();
		} else {
			return ReturnCode.erreur();
		}
	}

	/**
	 * This method return the list of files in the current working directory.
	 */
	private String processLIST() {
		String listFile = "";
		File[] files = repertoire.listFiles();
		for (File f : files) {
			listFile += f.getName() + "\n";
		}
		return listFile;
	}
	
	/**
	 * Open the main working directory of the client.
	 */
	private void openRepository() {
		repertoire = new File(Server.repertoire);
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

	/**
	 * Disconnect the client to the server.
	 * @throws IOException
	 */
	private void disconnect() throws IOException {
		send(ReturnCode.deconnexion());
		estAuthentifer = false;
		veutQuitter = true;
		soc.close();
	}

	/**
	 * Send a message to the main port of the client.
	 * @param msg Message send to the client.
	 */
	private void send(String msg) {
		try {
			outputwriter.write(msg);
			outputwriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send a message to the data port of the client.
	 * @param msg message send to the client.
	 */
	private void sendData(String msg) {
		send(ReturnCode.beginTransfert());
		try {
			outputWriterData = new OutputStreamWriter(socData.getOutputStream());
			outputWriterData.write(msg);
			outputWriterData.flush();
			socData.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		send(ReturnCode.endTransfert());
	}

	/**
	 * Method to receive data on the data port of the client.
	 * @return data send by the client.
	 */
	private String receiveData() {
		send(ReturnCode.beginTransfert());
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					socData.getInputStream()));
			String res = "", tmp;
			while ((tmp = br.readLine()) != null) {
				res += tmp + "\n";
			}
			send(ReturnCode.endTransfert());
			return res;
		} catch (IOException e) {
			e.printStackTrace();
			send(ReturnCode.endTransfert());
			return null;
		}
	}

}
