package br.gov.mg.testeutil.util;

public enum BrowserEnum {
	FIREFOX("webdriver.gecko.driver"), IE("webdriver.ie.driver"), CHROME("webdriver.chrome.driver"), PHANTOMJS("phantomjs.binary.path");

	private String chaveSystemProperty;

	private BrowserEnum(String chaveSystemProperty) {
		this.chaveSystemProperty = chaveSystemProperty;
	}

	public String getChaveSystemProperty() {
		return chaveSystemProperty;
	}

	public void setChaveSystemProperty(String chaveSystemProperty) {
		this.chaveSystemProperty = chaveSystemProperty;
	}

}
