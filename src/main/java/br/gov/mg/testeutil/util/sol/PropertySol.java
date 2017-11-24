package br.gov.mg.testeutil.util.sol;

import java.io.IOException;
import java.util.Properties;

/**
* @author Antonio Bernardo
* Define o caminho do driver dos diferentes browsers
* Acessa as configura��es definidas no config.properties e retorna a informa��o 
*/
public abstract class PropertySol {
	public static final String BROWSER_NAME;
	public static final String BROWSER_VERSION;
	public static final String SITE_ADDRESS;
	public static Boolean ambienteSOL = false;
	
	private static final String PROPERTIES_FILE = "br/gov/siare/configSol.properties";
	
	static{
		BROWSER_NAME = get("browser.name");
		SITE_ADDRESS = get("site.address");
		BROWSER_VERSION = get("browser.version"); 
	}
	
	/**
	 * Metodo para pegar o valor de alguma propriedade no arquivo de configuracao do Selenium
	 * O caminho e o nome do arquivo pode ser trocados
	 */
	private static String get(String name) {
		Properties properties = new Properties();
		String value = null;
		try {
			properties.load(PropertySol.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
		    value = properties.getProperty(name);
		} catch (IOException e) {
			System.out.println("Arquivo não encontrado test/resources/" + PROPERTIES_FILE);
			e.printStackTrace();
		}
		return value;
	}


}
