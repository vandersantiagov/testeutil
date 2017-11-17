package br.gov.mg.testeutil.enums;

public enum TipoArquivoEnum {
	HTML(".html"), JPEG(".jpeg"), JPG(".jpg"), PDF(".pdf");

	private String tipoArquivo;

	TipoArquivoEnum(String tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}

	public String getTipoArquivo() {
		return tipoArquivo;
	}
	
}
