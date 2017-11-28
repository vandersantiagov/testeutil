package br.gov.mg.testeutil.report.vo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.runner.Description;

import br.gov.mg.testeutil.util.DateUtil;

/**
 * @author sandra.rodrigues
 */
public class MetodoClasseTesteVO {

	private String nome;
	private String nomeProjeto;
	private Date dataInicioExecucao;
	private Date dataFimExecucao;
	private List<ExceptionVO> exceptions;
	private boolean isSucess;
	private boolean isErro;
	private boolean isFalha;
	private boolean isSkiped;
	private String caminhoPrintErro;
	private String caminhoArquivoHTMLPilhaErro;
	private String caminhoArquivoTXTPilhaErro;
	private String caminhoPrintPilhaErro;
	private Description description;
	private String nomeSuite;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeProjeto() {
		return nomeProjeto;
	}

	public void setNomeProjeto(String nomeProjeto) {
		this.nomeProjeto = nomeProjeto;
	}

	public Date getDataInicioExecucao() {
		return dataInicioExecucao;
	}

	public void setDataInicioExecucao(Date dataInicioExecucao) {
		this.dataInicioExecucao = dataInicioExecucao;
	}

	public Date getDataFimExecucao() {
		return dataFimExecucao;
	}

	public void setDataFimExecucao(Date dataFimExecucao) {
		this.dataFimExecucao = dataFimExecucao;
	}

	public List<ExceptionVO> getExceptions() {
		if (exceptions == null) {
			exceptions = new ArrayList<ExceptionVO>();
		}
		return exceptions;
	}

	public void setExceptions(List<ExceptionVO> exceptions) {
		this.exceptions = exceptions;
	}

	public boolean isSucess() {
		return isSucess;
	}

	public void setSucess(boolean isSucess) {
		this.isSucess = isSucess;
	}

	public boolean isErro() {
		return isErro;
	}

	public void setErro(boolean isErro) {
		this.isErro = isErro;
	}

	public boolean isFalha() {
		return isFalha;
	}

	public void setFalha(boolean isFalha) {
		this.isFalha = isFalha;
	}

	public boolean isSkiped() {
		return isSkiped;
	}

	public void setSkiped(boolean isSkiped) {
		this.isSkiped = isSkiped;
	}

	public String getCaminhoPrintErro() {
		return caminhoPrintErro;
	}

	public void setCaminhoPrintErro(String caminhoPrintErro) {
		this.caminhoPrintErro = caminhoPrintErro;
	}

	public String getCaminhoArquivoHTMLPilhaErro() {
		return caminhoArquivoHTMLPilhaErro;
	}

	public void setCaminhoArquivoHTMLPilhaErro(String caminhoArquivoHTMLPilhaErro) {
		this.caminhoArquivoHTMLPilhaErro = caminhoArquivoHTMLPilhaErro;
	}

	public String getCaminhoArquivoTXTPilhaErro() {
		return caminhoArquivoTXTPilhaErro;
	}

	public void setCaminhoArquivoTXTPilhaErro(String caminhoArquivoTXTPilhaErro) {
		this.caminhoArquivoTXTPilhaErro = caminhoArquivoTXTPilhaErro;
	}

	public String getCaminhoPrintPilhaErro() {
		return caminhoPrintPilhaErro;
	}

	public void setCaminhoPrintPilhaErro(String caminhoPrintPilhaErro) {
		this.caminhoPrintPilhaErro = caminhoPrintPilhaErro;
	}

	public Description getDescription() {
		return description;
	}

	public void setDescription(Description description) {
		this.description = description;
	}

	public String getNomeSuite() {
		return nomeSuite;
	}

	public void setNomeSuite(String nomeSuite) {
		this.nomeSuite = nomeSuite;
	}

	/**
	 * Tempo total em segundos com 3 casas decimais. Exemplo: 523 segundos irá
	 * apresentar assim: 0,523.
	 * 
	 * @author sandra.rodrigues
	 * @return
	 */
	public BigDecimal getDuracaoSeconds() {
		long diferenca = DateUtil.getDiferencaEmMilliSegundosEntreDatas(dataFimExecucao, dataInicioExecucao);
		double diferencaSegundos = diferenca * 0.001;
		return arredondaValor(diferencaSegundos, 3);
	}

	/**
	 * Tempo total em segundos com 3 casas decimais entre parênteses acompanhado
	 * da letra s.
	 * <br/>
	 * Exemplo: 523 segundos irá apresentar assim: (0,523s).
	 * 
	 * @author sandra.rodrigues
	 * @return
	 */
	public String getTextoTempoTotalExecucaoEmSegundos() {
		long diferenca = DateUtil.getDiferencaEmMilliSegundosEntreDatas(dataFimExecucao, dataInicioExecucao);
		double diferencaSegundos = diferenca * 0.001;
		return "(" + arredondaValor(diferencaSegundos, 3) + " s)";
	}

	/**
	 * Arredonda valor para 3 casas decimais, sendo que 2,5 se torna 3.
	 * 
	 * @author sandra.rodrigues
	 * @return
	 */
	public static BigDecimal arredondaValor(double valor, int casasDecimais) {
		return new BigDecimal(valor).setScale(casasDecimais, RoundingMode.HALF_UP);
	}
}
