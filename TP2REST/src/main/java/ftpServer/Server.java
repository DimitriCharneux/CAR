package ftpServer;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;

/**
 * Class represent a server FTP.
 * 
 * @author Dimitri Charneux
 * 
 */
public class Server extends Thread{

	private int port;
	public static String repertoire;

	/**
	 * Constructor of this class. She take two parameters. The port to connect
	 * the server and the name of the current repository. By default, the server
	 * run on the port 8081
	 * 
	 * @param port
	 * @param rep
	 */
	public Server(int port, String rep) {
		if (port > 1023)
			this.port = port;
		else {
			System.out.println("N° de port invalide, port initialisé à 8080.");
			this.port = 8081;
		}
		this.repertoire = rep;
	}

	/**
	 * Method to run the server. She open a thread for each client connected.
	 */
	public void run() {

		try {
			ServerSocket servSock = new ServerSocket(port);
			while (true) {
				OutputStreamWriter outputwriter;

				System.out.println("ready");
				FtpRequest ftpR = new FtpRequest(servSock.accept());
				ftpR.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
