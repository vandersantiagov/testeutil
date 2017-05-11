package br.gov.mg.testeutil.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.Properties;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 * A program demonstrates how to dowload files from local computer to a remote
 * FTP server using Apache Commons Net API.
 * @author rafael.carmo
 */
public class FTPDownloadDriveBrowser {

	protected static String CAMINHO_PROPERTIES_DRIVER_POROS = "/stor/home/siareear/ambiente/testeFuncional/driverBrowser.properties"; 

	public static void obterDriver(BrowserEnum browser, String version) {


		FTPClient ftpClient = new FTPClient();
		String caminhoLocal = null;
		Process p = null;
		try {
			p = Runtime.getRuntime().exec("netsh advfirewall set global StatefulFTP disable");
			p.waitFor();

			String caminhoDriver = obtemCaminhoDrive(browser, version);
			caminhoLocal = "C:/Ambiente/DriversBrowser/" + caminhoDriver.split("/")[caminhoDriver.split("/").length - 1];
			File downloadDrive = new File("C:/Ambiente/DriversBrowser/" + caminhoDriver.split("/")[caminhoDriver.split("/").length - 1]);
			if (!downloadDrive.exists()) {
				OutputStream os = new BufferedOutputStream(new FileOutputStream(downloadDrive));
				ftpClient = connectFtp();
				InputStream is = ftpClient.retrieveFileStream(caminhoDriver);
				byte[] bytesArray = new byte[4096];
				int bytesRead = -1;
				while ((bytesRead = is.read(bytesArray)) != -1) {
					os.write(bytesArray, 0, bytesRead);
				}

				os.close();
				is.close();
			}

			System.setProperty(browser.getChaveSystemProperty(), caminhoLocal);


		} catch (IOException | InterruptedException ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
				if (p != null && p.isAlive()){
					p.destroyForcibly();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	protected static FTPClient connectFtp() throws SocketException, IOException{
		String server = "poros.fazenda.mg.gov.br";
		int port = 21;
		String user = "siareear";
		String pass = "sef2006";

		FTPClient ftpClient = new FTPClient();
		ftpClient.connect(server, port);
		ftpClient.login(user, pass);
		ftpClient.enterLocalPassiveMode();
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

		return ftpClient;
	}

	protected static String obtemCaminhoDrive(BrowserEnum browser, String version) throws IOException{
		FTPClient ftpClient = connectFtp();	
		try {
			Properties prop = new Properties();
			InputStream inputStream = ftpClient.retrieveFileStream(CAMINHO_PROPERTIES_DRIVER_POROS);        
			prop.load(inputStream);
			return prop.getProperty(browser + "_" + version);
		} finally {
			if (ftpClient.isConnected()) {
				ftpClient.disconnect();
			}
		}
	}

	public static void main(String[] args) {
		FTPDownloadDriveBrowser.obterDriver(BrowserEnum.IE, "11");
	}

}