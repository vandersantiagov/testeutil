package br.gov.mg.testeutil.util.naoSiare;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Classe com m�todos de apoio, que otimizam a codifica��o das classes de p�gina.
 * @author Antonio Bernardo
 *
 */
	public abstract class UtilsNaoSiare {	
		
		private static final WebDriver driver;
		private static final WebDriverWait wait;
		
		static{
			driver = SeleniumNaoSiare.getDriver();
			wait = new WebDriverWait(driver, 10);
		}

		/**
		 * M�todo para verificar a visibilidade de um elemento utilizando o locator
		 * @param locator
		 */
		public static void isVisible(By locator) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		}
		
		/**
		 * M�todo para verificar a visibilidade de um elemento utilizando o ID
		 * @param id
		 */
		public static void isVisible(String id) {
			isVisible(By.id(id));
		}
		
		/**
		 * M�todo para verificar a presen�a de um elemento utilizando o locator
		 * @param locator
		 */
		public static void isLocated(By locator) {
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		}
		
		/**
		 * M�todo para verificar a presen�a de um elemento utilizando o ID
		 * @param id
		 */
		public static void isLocated(String id) {
			isLocated(By.id(id));
		}
		
		/**
		 * M�todo para verificar se um elemento � clic�vel utilizando o locator
		 * @param locator
		 */
		public static void isClickable(By locator) {
			wait.until(ExpectedConditions.elementToBeClickable(locator));
		}
		
		/**
		 * M�todo para verificar se um elemento � clic�vel utilizando o ID
		 * @param id
		 */
		public static void isClickable(String id) {
			isClickable(By.id(id));
		}
}
