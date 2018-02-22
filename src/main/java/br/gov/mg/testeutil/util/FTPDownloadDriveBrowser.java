package br.gov.mg.testeutil.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 * A program demonstrates how to dowload files from local computer to a remote
 * FTP server using Apache Commons Net API.
 * 
 * @author rafael.carmo
 */
public class FTPDownloadDriveBrowser {

	protected static String CAMINHO_PROPERTIES_DRIVER_POROS = "/stor/home/siareear/ambiente/testeFuncional/driverBrowser.properties";
	// Caminho dos drivers
	private static final String PATH_DRIVER_CHROME = "C:/Ambiente/DriversBrowser/chromedriver_2_35.EXE";
	private static final String PATH_DRIVER_FIREFOX = "C:/Ambiente/DriversBrowser/geckodriver_0_15_0.EXE";
	private static final String PATH_DRIVER_IE = "C:/Ambiente/DriversBrowser/IEDriverServer_3.4.0.EXE";
	private static final String PATH_DRIVER_PHANTOMJS = "C:/Ambiente/DriversBrowser/phantomjs_2_1_1.EXE";

	public static void obterDriver(BrowserEnum browser, String version) {

		FTPClient ftpClient = new FTPClient();
		String caminhoLocal = null;
		Process p = null;
		try {
			p = Runtime.getRuntime().exec("netsh advfirewall set global StatefulFTP disable");
			p.waitFor();

			String caminhoDriver = obtemCaminhoDrive(browser, version);
			if (StringUtils.isNotBlank(caminhoDriver)) {
				caminhoLocal = "C:/Ambiente/DriversBrowser/"
						+ caminhoDriver.split("/")[caminhoDriver.split("/").length - 1];
				File downloadDrive = new File(
						"C:/Ambiente/DriversBrowser/" + caminhoDriver.split("/")[caminhoDriver.split("/").length - 1]);
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
			} else {
				switch (browser) {
				case CHROME:
					caminhoLocal = PATH_DRIVER_CHROME;
					break;
				case FIREFOX:
					caminhoLocal = PATH_DRIVER_FIREFOX;
					break;
				case IE:
					caminhoLocal = PATH_DRIVER_IE;
					break;
				default:
					caminhoLocal = PATH_DRIVER_PHANTOMJS;
					break;
				}
				System.out.println("Driver carregado do diretório local!");
			}

			File downloadDrive = new File(caminhoLocal);
			if(downloadDrive.exists()){
				System.setProperty(browser.getChaveSystemProperty(), caminhoLocal);
			}else{
				throw new IllegalArgumentException("Tentativa de download do ftp falhou e não existe driver local.");
			}

		} catch (IOException | InterruptedException ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
				if (p != null && p.isAlive()) {
					p.destroyForcibly();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	protected static FTPClient connectFtp() throws SocketException, IOException {
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

	protected static String obtemCaminhoDrive(BrowserEnum browser, String version) throws IOException, Exception {
		FTPClient ftpClient = connectFtp();
		try {
			Properties prop = new Properties();
			InputStream inputStream = ftpClient.retrieveFileStream(CAMINHO_PROPERTIES_DRIVER_POROS);
			if (inputStream == null) {
				return "";
			}
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