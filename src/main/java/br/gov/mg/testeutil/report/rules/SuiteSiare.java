package br.gov.mg.testeutil.report.rules;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.runner.Description;

import br.gov.mg.testeutil.metodos.MetodosSiare;
import br.gov.mg.testeutil.report.html.ReportHTML;
import br.gov.mg.testeutil.vo.ExceptionVO;
import br.gov.mg.testeutil.vo.QuantitativoRunVO;
import br.gov.mg.testeutil.vo.SuitePrincipalVO;
import br.gov.mg.testeutil.vo.SuiteVO;

/**
 * @author sandra.rodrigues
 */
public class SuiteSiare {

	public static SuitePrincipalVO suitePrincipalVO;
	public static List<SuiteVO> suites;
	public static String nomePrimeiraSuite;
	public static Integer idSuite = 0;
	public static boolean quitAmbiente;

	public static void startReport() {
		try {
			if (suitePrincipalVO == null) {
				suitePrincipalVO = new SuitePrincipalVO();
				suitePrincipalVO.setQuantitativoRun(new QuantitativoRunVO());
				suitePrincipalVO.setDataInicioExecucao(new Date());
				suitePrincipalVO.setSuitePrincipal(true);
				suitePrincipalVO.setNomeSuite(nomePrimeiraSuite);
			}
		} catch (Throwable e) {
			addExceptionVO(e, suitePrincipalVO.getExceptions());
		}
	}

	public static void finalizeReport() throws Exception {
		boolean suiteQueIniciouOsTestes = isSuiteQueIniciouOsTestes();
		try {
			if (suiteQueIniciouOsTestes && quitAmbiente) {
				quitAmbiente();
			}
		} catch (Throwable e) {
			addExceptionVO(e, suitePrincipalVO.getExceptions());
		} finally {
			try {
				RuleReportSuiteProjeto.suiteFilhaVO.setDataFimExecucao(new Date());
				if (suiteQueIniciouOsTestes) {
					Date dataFimExecucao = new Date();
					suitePrincipalVO.setDataFimExecucao(dataFimExecucao);
					ReportHTML.createHTML(suitePrincipalVO);
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean isSuiteQueIniciouOsTestes() {
		return RuleReportSuiteProjeto.isSuiteTotal && idSuite == 0;
	}

	private static void quitAmbiente() throws Exception {
		// Focar e fechar janela do Ambiente SICAF
		MetodosSiare.setAmbienteSicaf();
		MetodosSiare.quitAmbiente();
		// Focar e fechar janela do Ambiente SOL
		MetodosSiare.setAmbienteSol();
		MetodosSiare.quitAmbiente();
	}

	public static void addExceptionVO(Throwable e, List<ExceptionVO> exceptions) {
		ExceptionVO exceptionVO = new ExceptionVO();
		exceptionVO.setFalha(isFailed(e));
		exceptionVO.setException(e);
		exceptionVO.setMessage(e.getMessage());
		exceptions.add(exceptionVO);
		e.printStackTrace();
	}

	public static String getNameByPosition(Description description) {
		String[] caminhoClasse = converteStringEmArray(description);
		String nomeProjeto = caminhoClasse[caminhoClasse.length - 1];
		return nomeProjeto;
	}

	/**
	 * Convert String em array, e retorna o Array. <br/>
	 * obs.: a string deve ser separada por ponto final. <br/>
	 * Ex.: String contem o texto: "package.classe.metodo" <br/>
	 * Transforma em um array de 3 possições, primeira posição package,
	 * a segunda classe e a terceira metodo.
	 * 
	 * @param description
	 * @return
	 */
	public static String[] converteStringEmArray(Description description) {
		String newTexto = description.getClassName().replace(".", ",");
		String caminhoClasse[] = newTexto.split(Pattern.quote(","));
		return caminhoClasse;
	}

	public static boolean isFailed(Throwable e) {
		return e instanceof AssertionError;
	}

	public static void setQuantitativoRunVO(QuantitativoRunVO quantitativoOrigem,
			QuantitativoRunVO quantitativoDestino) {

		int countRun = quantitativoOrigem.getQuantidadeRun() + quantitativoDestino.getQuantidadeRun();
		quantitativoDestino.setQuantidadeRun(countRun);

		int countErro = quantitativoOrigem.getQuantidadeErro() + quantitativoDestino.getQuantidadeErro();
		quantitativoDestino.setQuantidadeErro(countErro);

		int countFailed = quantitativoOrigem.getQuantidadeFalha() + quantitativoDestino.getQuantidadeFalha();
		quantitativoDestino.setQuantidadeFalha(countFailed);

		int countSuccess = quantitativoOrigem.getQuantidadeSucesso() + quantitativoDestino.getQuantidadeSucesso();
		quantitativoDestino.setQuantidadeSucesso(countSuccess);

		int countSkiped = quantitativoOrigem.getQuantidadeSkiped() + quantitativoDestino.getQuantidadeSkiped();
		quantitativoDestino.setQuantidadeSkiped(countSkiped);
	}
}
