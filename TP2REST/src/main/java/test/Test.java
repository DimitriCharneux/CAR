package test;


import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;

import org.junit.After;

import car.tp2.FtpResource;

public class Test {
	private FtpResource res;
	private static ftpServer.Server serv;
	
	@org.junit.BeforeClass
	public static void initClass(){
		serv = new ftpServer.Server(8081, "repPrincipal");
		serv.start();
	}
	
	@org.junit.Before
	public void init() {
		res = new FtpResource();
	}

	@After
	public void end() {
		File f = new File("repPrincipal/userTest");
		File[] tmp = f.listFiles();
		if(tmp == null) return;
		for (File ff : tmp)
			ff.delete();
	}

	@org.junit.Test
	public void testAuth() throws IOException {
		assertEquals("<h1>Vous etes authentifie.</h1>", res.authentification("userTest", "userTest"));
		assertEquals("<h1>Authentification echoue.</h1>", res.authentification("userTest", "userTeslknt"));
	}

	@org.junit.Test
	public void testPWD() throws SocketException, IOException {
		assertEquals("<h1>Vous devez vous connecter.</h1>", res.getPwd());
		res.authentification("userTest", "userTest");
		assertEquals("<a>/userTest</a>", res.getPwd());
	}
	
	@org.junit.Test
	public void deconnexion() throws SocketException, IOException {
		assertEquals("<h1>Vous etes authentifie.</h1>", res.authentification("userTest", "userTest"));
		res.deconnexion();
		assertEquals("<h1>Vous devez vous connecter.</h1>", res.getPwd());
	}

	@org.junit.Test
	public void testCWD() throws IOException {
		File f = new File("repPrincipal/userTest/repTest");
		f.mkdir();
		assertEquals("<h1>Vous devez vous connecter.</h1>", res.getPwd());
		res.authentification("userTest", "userTest");
		assertEquals("<a>/userTest</a>", res.getPwd());
		/*assertEquals("<h1>operation impossible.</h1>", res.getCdup());
		assertEquals("<a>Ce dossier est vide.</a>", res.getCwd("repTest"));
		assertEquals("<a>/userTest/repTest</a>", res.getPwd());
		assertEquals("<a>Ce dossier est vide.</a>", res.getCdup());
		assertEquals("<a>/userTest</a>", res.getPwd());
		assertEquals("<h1>operation impossible.</h1>", res.getCwd("wbbxb"));
		f.delete();*/
	}

	@org.junit.Test
	public void testMKD() throws SocketException, IOException {
		res.authentification("userTest", "userTest");
		File f = new File("repPrincipal/userTest/repTest");
		assertFalse(f.exists());
		res.getMkd("repTest");
		assertTrue(f.exists());
		f.delete();
	}

	@org.junit.Test
	public void testList() throws IOException {
		res.authentification("userTest", "userTest");
		File f = new File("repPrincipal/userTest/repTest");
		f.mkdir();
		f = new File("repPrincipal/userTest/fileTest");
		f.createNewFile();
		FileOutputStream output = new FileOutputStream(f);
		output.write("test".getBytes());
		output.close();
		String tmp = "<table><tr><td><a href=\"http://localhost:8080/rest/tp2/ftp/cdup\">.." +
				"</a></td></tr><tr><td><a href=\"http://localhost:8080/rest/tp2/ftp/cwd/repTest\">" +
				"repTest</a></td></tr><tr><td><a href=\"http://localhost:8080/rest/tp2/ftp/get/fileTest\">" +
				"fileTest</a></td></tr></table>";
		assertEquals(tmp,res.getList());
		res.deleteFile("fileTest");
		res.deleteFile("repTest");
		assertEquals("<a>Ce dossier est vide.</a></br><a href=\"http://localhost:8080/rest/tp2/ftp/cdup\">..</a>", res.getList());
	}
}
