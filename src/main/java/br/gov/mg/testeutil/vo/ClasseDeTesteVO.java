package br.gov.mg.testeutil.vo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.gov.mg.testeutil.util.DateUtil;

/**
 * @author sandra.rodrigues
 */
public class ClasseDeTesteVO {

	private String nomeClasse;
	private String nomeProjeto;
	private String nomeSuite;
	private Date dataInicioExecucao;
	private Date dataFimExecucao;
	private List<MetodoClasseTesteVO> metodos;
	private List<ExceptionVO> exceptions;
	private Map<String, BigDecimal> tempoPorClasse;
	private QuantitativoRunVO quantitativoRunVO;

	public String getNomeClasse() {
		return nomeClasse;
	}

	public void setNomeClasse(String nomeClasse) {
		this.nomeClasse = nomeClasse;
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

	public List<MetodoClasseTesteVO> getMetodos() {
		if (metodos == null) {
			metodos = new ArrayList<MetodoClasseTesteVO>();
		}
		return metodos;
	}

	public void setMetodos(List<MetodoClasseTesteVO> metodos) {
		this.metodos = metodos;
	}

	public List<ExceptionVO> getExceptions() {
		return exceptions;
	}

	public void setExceptions(List<ExceptionVO> exceptions) {
		this.exceptions = exceptions;
	}

	public String getNomeSuite() {
		return nomeSuite;
	}

	public void setNomeSuite(String nomeSuite) {
		this.nomeSuite = nomeSuite;
	}

	public Map<String, BigDecimal> getTempoPorClasse() {
		if (tempoPorClasse == null) {
			tempoPorClasse = new HashMap<String, BigDecimal>();
		}
		return tempoPorClasse;
	}

	public void setTempoPorClasse(Map<String, BigDecimal> tempoPorClasse) {
		this.tempoPorClasse = tempoPorClasse;
	}

	public QuantitativoRunVO getQuantitativoRunVO() {
		return quantitativoRunVO;
	}

	public void setQuantitativoRunVO(QuantitativoRunVO quantitativoRunVO) {
		this.quantitativoRunVO = quantitativoRunVO;
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
		return "(" + getDuracaoSeconds() + " s)";
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
