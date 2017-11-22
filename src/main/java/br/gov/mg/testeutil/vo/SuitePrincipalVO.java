package br.gov.mg.testeutil.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.gov.mg.testeutil.util.DateUtil;

public class SuitePrincipalVO {
	private boolean isSuitePrincipal;
	private String nomeSuite;
	private Map<KeyMapVO<String, String>, SuiteVO> suitesFilhasByNome = new LinkedHashMap<KeyMapVO<String, String>, SuiteVO>();
	private Date dataInicioExecucao;
	private Date dataFimExecucao;
	private List<ExceptionVO> exceptions;
	private QuantitativoRunVO quantitativoRun;

	public boolean isSuitePrincipal() {
		return isSuitePrincipal;
	}

	public void setSuitePrincipal(boolean isSuitePrincipal) {
		this.isSuitePrincipal = isSuitePrincipal;
	}

	public String getNomeSuite() {
		return nomeSuite;
	}

	public void setNomeSuite(String nomeSuite) {
		this.nomeSuite = nomeSuite;
	}

	public Map<KeyMapVO<String, String>, SuiteVO> getSuitesFilhasByNome() {
		return suitesFilhasByNome;
	}

	public void setSuitesFilhasByNome(Map<KeyMapVO<String, String>, SuiteVO> suitesFilhasByNome) {
		this.suitesFilhasByNome = suitesFilhasByNome;
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

	public QuantitativoRunVO getQuantitativoRun() {
		return quantitativoRun;
	}

	public void setQuantitativoRun(QuantitativoRunVO quantitativoRun) {
		this.quantitativoRun = quantitativoRun;
	}

	/**
	 * Tempo total execução no formato x horas, x minutos, x segundos.
	 * 
	 * @return
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 12:01:30
	 *
	 */
	public String tempoTotalExecucao() {
		long diferenca = DateUtil.getDiferencaEmMilliSegundosEntreDatas(dataFimExecucao, dataInicioExecucao);
		return DateUtil.getMinutos(diferenca) + " horas " + DateUtil.getHoras(diferenca) + " minutos "
				+ DateUtil.getSegundos(diferenca) + " segundos";
	}
}
