import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {

	private int port;
	private String repertoire;

	public Serveur(int port, String rep) {
		if (port > 1023)
			this.port = port;
		else {
			System.out.println("N° de port invalide, port initialisé à 8080.");
			this.port = 8080;
		}
		this.repertoire = rep;
	}

	public static void main(String args[]) {
		if (args.length != 1) {
			System.out.println("argument invalide !");
			return;
		}
		Serveur serv = new Serveur(8080, args[0]);
		serv.run();

	}

	private void run() {

		try {
			ServerSocket servSock = new ServerSocket(port);
			while (true) {
				OutputStreamWriter outputwriter;

				System.out.println("ready");
				FtpRequest ftpR= new FtpRequest(servSock.accept());
				ftpR.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
