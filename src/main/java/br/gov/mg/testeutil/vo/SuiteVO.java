package br.gov.mg.testeutil.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.gov.mg.testeutil.util.DateUtil;

/**
 * @author sandra.rodrigues
 */
public class SuiteVO {

	private boolean isSuitePrincipal;
	private String nomeSuite;
	private String nomeProjeto;
	private Date dataInicioExecucao;
	private Date dataFimExecucao;
	private Map<String, ClasseDeTesteVO> classesDeTesteByName = new HashMap<String, ClasseDeTesteVO>();
	private List<ExceptionVO> exceptions;
	private QuantitativoRunVO quantitativoRunVO = new QuantitativoRunVO();

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

	public Map<String, ClasseDeTesteVO> getClassesDeTesteByName() {
		return classesDeTesteByName;
	}

	public void setClassesDeTesteByName(Map<String, ClasseDeTesteVO> classesDeTesteByName) {
		this.classesDeTesteByName = classesDeTesteByName;
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

	public QuantitativoRunVO getQuantitativoRunVO() {
		return quantitativoRunVO;
	}

	public void setQuantitativoRunVO(QuantitativoRunVO quantitativoRunVO) {
		this.quantitativoRunVO = quantitativoRunVO;
	}

	/**
	 * Tempo total execução no formato x horas, x minutos, x segundos.
	 * 
	 * @author sandra.rodrigues
	 * @return
	 */
	public String tempoTotalExecucao() {
		long diferenca = DateUtil.getDiferencaEmMilliSegundosEntreDatas(dataFimExecucao, dataInicioExecucao);
		return DateUtil.getMinutos(diferenca) + " horas " + DateUtil.getHoras(diferenca) + " minutos "
				+ DateUtil.getSegundos(diferenca) + " segundos";
	}
}
