package car.tp2;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * La classe principale de l'application.
 * Demarre un serveur sur le port 8080.
 * Utilise le prefixe /rest/ pour les URLs.
 * 
 * @author Lionel Seinturier <Lionel.Seinturier@univ-lille1.fr>
 */
public class Starter {
	
	public static void main( final String[] args ) throws Exception {
		
        ftpServer.Server serv = new ftpServer.Server(8081, "repPrincipal");
		serv.start();
		
		
		/*FTPClient ftpClient= new FTPClient();
		System.out.println("1");
		ftpClient.enterLocalPassiveMode();
		ftpClient.connect("localhost", 8080);
		System.out.println("2");
		ftpClient.login("loul", "loul");
		
		
		String[] listFile =  ftpClient.listNames();
		String tmp = "";
		for(String file : listFile){
			tmp += file + "\n";
		}
		System.out.println(tmp);
		System.out.println(ftpClient.printWorkingDirectory());
		System.out.println("fin");*/
		
		
		Server server = new Server( 8080 );
		        
 		final ServletHolder servletHolder = new ServletHolder( new CXFServlet() );
 		final ServletContextHandler context = new ServletContextHandler(); 		
 		context.setContextPath( "/" );
 		context.addServlet( servletHolder, "/rest/*" ); 	
 		context.addEventListener( new ContextLoaderListener() );
 		
 		context.setInitParameter( "contextClass", AnnotationConfigWebApplicationContext.class.getName() );
 		context.setInitParameter( "contextConfigLocation", Config.class.getName() );
 		 		
        server.setHandler( context );
        server.start();
        server.join();
	}
}
