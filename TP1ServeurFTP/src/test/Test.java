package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.After;

import ftpServer.FtpRequest;
import ftpServer.ReturnCode;
import ftpServer.Server;

public class Test {
	private FtpRequest req;
	private Server server;
	
	@org.junit.Before
	public void init(){
		req = new FtpRequest(null);
		req.setUtilisateurConnecte("userTest");
		server = new Server(8080, "repertoireTest");
	}
	
	@After
	public void end(){
		File f = new File("repertoireTest/userTest");
		File[] tmp = f.listFiles();
		for(File ff : tmp)
			ff.delete();
		f.delete();
		f = new File("repertoireTest");
		f.delete();
	}
	
	@org.junit.Test
	public void testOpenRepository() {
		req.openRepository();
		assertEquals("repertoireTest/userTest", req.getRepertoire().getPath());
	}
	
	@org.junit.Test
	public void testPWD() {
		req.openRepository();
		assertEquals("/userTest\n", req.processPWD());
	}
	
	@org.junit.Test
	public void testCWD() {
		req.openRepository();
		File f = new File("repertoireTest/userTest/repTest");
		f.mkdir();
		assertEquals("/userTest\n", req.processPWD());
		assertEquals(ReturnCode.serviceOk(),req.processCWD("repTest"));
		assertEquals("/userTest/repTest\n", req.processPWD());
		assertEquals(ReturnCode.serviceOk(),req.processCWD(".."));
		assertEquals("/userTest\n", req.processPWD());
		assertEquals(ReturnCode.erreur(),req.processCWD("jqfhla"));
		f.delete();
	}
	
	@org.junit.Test
	public void testMKD() {
		req.openRepository();
		File f = new File("repertoireTest/userTest/repTest");
		assertFalse(f.exists());
		assertEquals(ReturnCode.serviceOk(),req.processMKD("repTest"));
		assertTrue(f.exists());
		f.delete();
	}
	
	@org.junit.Test
	public void testCDUP() {
		req.openRepository();
		File f = new File("repertoireTest/userTest/repTest");
		f.mkdir();
		assertEquals("/userTest\n", req.processPWD());
		req.processCWD("repTest");
		assertEquals("/userTest/repTest\n", req.processPWD());
		assertEquals(ReturnCode.serviceOk(),req.processCDUP());
		assertEquals("/userTest\n", req.processPWD());
		assertEquals(ReturnCode.erreur(),req.processCDUP());
		f.delete();
	}
	
	@org.junit.Test
	public void testList() throws IOException {
		req.openRepository();
		File f = new File("repertoireTest/userTest/repTest");
		f.mkdir();
		f = new File("repertoireTest/userTest/fileTest");
		f.createNewFile();
		FileOutputStream output = new FileOutputStream(f);
		output.write("test".getBytes());
		output.close();
		String tmp = "repTest\nfileTest\n";
		assertEquals(tmp, req.processLIST());
		f.delete();
		f = new File("repertoireTest/userTest/repTest");
		f.delete();
		assertEquals("", req.processLIST());
	}
	
	@org.junit.Test
	public void testRetr() throws IOException {
		req.openRepository();
		File f = new File("repertoireTest/userTest/fileTest");
		f.createNewFile();
		FileOutputStream output = new FileOutputStream(f);
		output.write("test".getBytes());
		output.close();
		assertEquals("test\n", req.processRETR("fileTest"));
		f.delete();
		assertNull(req.processRETR("fileTest"));
	}
	
}
