package br.gov.mg.testeutil.vo;

/**
 * @author sandra.rodrigues
 */
/**
 * @author sandra.rodrigues
 *
 */
public class QuantitativoRunVO {

	private int quantidadeRun;
	private int quantidadeFalha;
	private int quantidadeErro;
	private int quantidadeSucesso;
	private int quantidadeSkiped;

	public int getQuantidadeRun() {
		return quantidadeRun;
	}

	public void setQuantidadeRun(int quantidadeRun) {
		this.quantidadeRun = quantidadeRun;
	}

	public int getQuantidadeFalha() {
		return quantidadeFalha;
	}

	public void setQuantidadeFalha(int quantidadeFalha) {
		this.quantidadeFalha = quantidadeFalha;
	}

	public int getQuantidadeErro() {
		return quantidadeErro;
	}

	public void setQuantidadeErro(int quantidadeErro) {
		this.quantidadeErro = quantidadeErro;
	}

	public int getQuantidadeSucesso() {
		return quantidadeSucesso;
	}

	public void setQuantidadeSucesso(int quantidadeSucesso) {
		this.quantidadeSucesso = quantidadeSucesso;
	}

	public int getQuantidadeSkiped() {
		return quantidadeSkiped;
	}

	public void setQuantidadeSkiped(int quantidadeSkiped) {
		this.quantidadeSkiped = quantidadeSkiped;
	}

	public void sumaRun() {
		setQuantidadeRun(getQuantidadeRun() + 1);
	}

	public void sumaFalha() {
		setQuantidadeFalha(getQuantidadeFalha() + 1);
	}

	public void sumaSucesso() {
		setQuantidadeSucesso(getQuantidadeSucesso() + 1);
	}

	public void sumaErro() {
		setQuantidadeErro(getQuantidadeErro() + 1);
	}

	public void sumaSkiped() {
		setQuantidadeSkiped(getQuantidadeSkiped() + 1);
	}

	public void resetAll() {
		setQuantidadeRun(0);
		setQuantidadeErro(0);
		setQuantidadeFalha(0);
		setQuantidadeSucesso(0);
		setQuantidadeSkiped(0);
	}
}
