package car.tp2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import ftpServer.FtpRequest;
import ftpServer.ReturnCode;

/**
 * Cette classe va gérer une interface web en REST d'un serveur FTP.
 * @author Dimitri Charneux
 *
 */
@Path("/ftp")
public class FtpResource {

	private FTPClient ftpClient = new FTPClient();
	private boolean co = false;

	/**
	 * Methode qui sert à ce déconnecter du serveur FTP.
	 * @return une page web indiquant que l'on est déconnecté.
	 * @throws SocketException
	 * @throws IOException
	 */
	@GET
	@Produces("text/html")
	@Path("/deco/")
	public String deconnexion() throws SocketException, IOException {
		ftpClient.quit();
		co = false;
		return "<h1>Deconnecte.</h1>";
	}

	/**
	 * Méthode utilisée pour afficher le repertoire courant de la personne connectée.
	 * @return le répertoire courant.
	 * @throws SocketException
	 * @throws IOException
	 */
	@GET
	@Produces("text/html")
	@Path("/pwd")
	public String getPwd() throws SocketException, IOException {
		if (!co)
			return "<h1>Vous devez vous connecter.</h1>";
		String dir = ftpClient.printWorkingDirectory();
		return "<a>" + dir + "</a>";
	}

	/**
	 * Méthode utilisée pour changer de répertoire courant.
	 * @param dir dossier dans lequel ont souhaite aller.
	 * @return La liste des fichiers dans le dossier donné en paramètre.
	 * @throws SocketException
	 * @throws IOException
	 */
	@GET
	@Produces("text/html")
	@Path("/cwd/{dir}")
	public String getCwd(@PathParam("dir") String dir) throws SocketException,
			IOException {
		if (!co)
			return "<h1>Vous devez vous connecter.</h1>";
		ftpClient.cwd(dir);
		int codeDeRetour = ftpClient.getReplyCode();
		if (codeDeRetour == ReturnCode.SERVICE_OK) {
			return getList();
		} else {
			return "<h1>operation impossible.</h1>";
		}
	}

	/**
	 * Méthode servant à retourner dans le répertoire parent.
	 * @return La liste des fichiers dans le répertoire parent.
	 * @throws SocketException
	 * @throws IOException
	 */
	@GET
	@Produces("text/html")
	@Path("/cdup")
	public String getCdup() throws SocketException, IOException {
		if (!co)
			return "<h1>Vous devez vous connecter.</h1>";
		ftpClient.cdup();
		int codeDeRetour = ftpClient.getReplyCode();
		if (codeDeRetour == ReturnCode.SERVICE_OK) {
			return getList();
		} else {
			return "<h1>operation impossible.</h1>";
		}
	}
 
	/**
	 * Méthode servant à créer un répertoire dans le répertoire courant.
	 * @param dir Répertoire que l'on souhaite créer.
	 * @return La liste des éléments présents dans le répertoire courant.
	 * @throws SocketException
	 * @throws IOException
	 */
	@GET
	@Produces("text/html")
	@Path("/mkd/{dir}")
	public String getMkd(@PathParam("dir") String dir) throws SocketException,
			IOException {
		if (!co)
			return "<h1>Vous devez vous connecter.</h1>";
		ftpClient.mkd(dir);
		int codeDeRetour = ftpClient.getReplyCode();
		if (codeDeRetour == ReturnCode.SERVICE_OK) {
			return "<h1> dossier " + dir + " cree.</h1>" + getList();
		} else {
			return "<h1>operation impossible.</h1>";
		}
	}

	/**
	 * Méthode affichant les éléments présents dans le répertoire courant.
	 * @return La liste des éléments présents dans le répertoire courant.
	 * @throws SocketException
	 * @throws IOException
	 */
	@GET
	@Produces("text/html")
	@Path("/ls")
	public String getList() throws SocketException, IOException {
		if (!co)
			return "<h1>Vous devez vous connecter.</h1>";
		return generateHTML(ftpClient.listFiles(""));
	}

	/**
	 * Méthode utilisée pour générer une page html qui affiche les éléments envoyés en paramètres.
	 * @param ftpFiles élements à afficher dans la page html.
	 * @return Une page html contenant les éléments de la liste.
	 */
	private String generateHTML(FTPFile[] ftpFiles) {
		if (ftpFiles.length == 0)
			return "<a>Ce dossier est vide.</a></br><a href=\"http://localhost:8080/rest/tp2/ftp/cdup\">..</a>";
		String tmp = "<table><tr><td><a href=\"http://localhost:8080/rest/tp2/ftp/cdup\">..</a></td></tr>";
		for (FTPFile file : ftpFiles) {
			String tmp2 = file.toString();
			String tmp3 = tmp2.substring(1, tmp2.length());
			if (tmp2.charAt(0) == 'd')
				tmp += "<tr><td><a href=\"http://localhost:8080/rest/tp2/ftp/cwd/"
						+ tmp3 + "\">" + tmp3 + "</a></td></tr>";
			else
				tmp += "<tr><td><a href=\"http://localhost:8080/rest/tp2/ftp/get/"
						+ tmp3 + "\">" + tmp3 + "</a></td></tr>";
		}
		tmp += "</table>";
		return tmp;
	}

	/**
	 * Méthode retournant le fichier dont le nom a été passer en paramètre.
	 * @param fileName nom du fichier à retourner.
	 * @return Le fichier dont le nom a été envoyé.
	 * @throws SocketException
	 * @throws IOException
	 */
	@GET
	@Produces("application/octet-stream")
	@Path("/get/{file: .*}")
	public Object getFile(@PathParam("file") String fileName)
			throws SocketException, IOException {
		File file = new File(fileName);
		FileOutputStream fileo = new FileOutputStream(file);
		ftpClient.retrieveFile(fileName, fileo);
		fileo.close();
		return file;
	}
	
	/**
	 * Méthode renvoyant le code d'une page html d'authentification.
	 * @return Code d'une page html d'authentification.
	 * @throws IOException
	 */
	@GET
	@Produces("text/html")
	@Path("/auth")
	public String initAuth() throws IOException {
		String tmp = "<div>"
				+ "<h1 style='font-size:1.2em;"
				+ " font-family: sans'>Authentification"
				+ "</h1>"
				+ "<form method='POST' action='http://localhost:8080/rest/tp2/ftp/authentification'"
				+ " enctype='multipart/form-data'>"
				+ "User name:<br>"
				+ "<input type=\"text\" name=\"login\"><br>"
				+ "User password:<br>"
				+ "<input type=\"password\" name=\"psw\"><br>"
				+ "<input type='submit' value='Valider'>"
				+ "</form> </div>";

		return tmp;
	}
	
	/**
	 * Méthode pour connecter un utilisateur au serveur ftp.
	 * @param login login de l'utilisateur.
	 * @param psw mot de passe de l'utilisateur.
	 * @return Une page html indiquant si l'utilisateur a été connecté ou non.
	 * @throws IOException
	 */
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("text/html; charset=UTF-8")
	@Path("/authentification")
	public String authentification(@Multipart("login") String login,
			@Multipart("psw") String psw) throws IOException {
		ftpClient = new FTPClient();
		ftpClient.enterLocalPassiveMode();
		FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_L8);
		conf.setUnparseableEntries(true);
		ftpClient.configure(conf);
		ftpClient.connect("localhost", 8081);
		ftpClient.login(login, psw);
		int codeDeRetour = ftpClient.getReplyCode();
		if (codeDeRetour == ReturnCode.AUTHENTIFICATION_OK) {
			co = true;
			return "<h1>Vous etes authentifie.</h1>";
		} else {
			co = false;
			return "<h1>Authentification echoue.</h1>";
		}
	}
	
	/**
	 * Méthode renvoyant une page html servant à envoyer un fichier au serveur.
	 * @return Une page html servant à envoyer un fichier au serveur.
	 * @throws IOException
	 */
	@GET
	@Produces("text/html")
	@Path("/initUpload")
	public String initUpload() throws IOException {
		if (!co)
			return "<h1>Vous devez vous connecter.</h1>";
		String tmp = "<div>"
				+ "<h1 style='font-size:1.2em;"
				+ " font-family: sans'>Telecharger un fichier"
				+ "</h1>"
				+ "<form method='POST' action='http://localhost:8080/rest/tp2/ftp/upload'"
				+ " enctype='multipart/form-data'>\n" + "Choisir le fichier"
				+ "<input type='file' name='file'>"
				+ "<br> nom de la destination : "
				+ "<input type='text' name='name' /><br />\n"
				+ "<input type='submit' value='Telecharger'>\n"
				+ "</form> </div>";

		return tmp;
	}

	/**
	 * Méthode utilisée pour envoyer un fichier au serveur FTP. 
	 * @param fichier Fichier envoyé au serveur.
	 * @param name Nom du fichier envoyé au serveur.
	 * @return Une page html contenant la liste des fichiers du répertoire courant.
	 * @throws IOException
	 */
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("text/html; charset=UTF-8")
	@Path("/upload")
	public String upload(@Multipart("file") InputStream fichier,
			@Multipart("name") String name) throws IOException {
		if (!co)
			return "<h1>Vous devez vous connecter.</h1>";
		ftpClient.storeFile(name, fichier);
		int codeDeRetour = ftpClient.getReplyCode();
		if (codeDeRetour == ReturnCode.END_TRANSFERT) {
			return "<h1>operation reussie.</h1>" + getList();
		} else {
			return "<h1>operation echoue.</h1>";
		}
	}
	
	/**
	 * Méthode utiliser pour supprimer un fichier ou un dossier.
	 * @param file Nom de l'élément à supprimer du serveur.
	 * @return Une page html contenant la liste des fichiers du répertoire courant.
	 * @throws SocketException
	 * @throws IOException
	 */
	@GET
	@Produces("text/html")
	@Path("/rm/{file}")
	public String deleteFile(@PathParam("file") String file) throws SocketException,
			IOException {
		if (!co)
			return "<h1>Vous devez vous connecter.</h1>";
		ftpClient.deleteFile(file);
		int codeDeRetour = ftpClient.getReplyCode();
		if (codeDeRetour == ReturnCode.SERVICE_OK) {
			return "<h1> fichier " + file + " supprime.</h1>" + getList();
		} else {
			return "<h1>operation impossible.</h1>";
		}
	}

}
