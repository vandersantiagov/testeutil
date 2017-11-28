package br.gov.mg.testeutil.report.rules;

import java.util.Date;
import java.util.LinkedHashMap;

import org.junit.runner.Description;

import br.gov.mg.testeutil.report.vo.KeyMapVO;
import br.gov.mg.testeutil.report.vo.QuantitativoRunVO;
import br.gov.mg.testeutil.report.vo.SuiteVO;

/**
 * @author sandra.rodrigues
 */
public class RuleReportSuiteProjeto {

	public static SuiteVO suiteFilhaVO;
	public static QuantitativoRunVO quantitativoRunSuiteVO;
	public static String nomeSuite;
	public static String nomeProjeto;
	public static Date dateInicioSuiteTestes = new Date();

	public static boolean isSuiteTotal;
	public static Integer idSuiteTotal;

	public static RuleReport startTestesSuiteFilha() {
		SuiteSiare.startReport();
		return new RuleReport(true) {

			@Override
			protected void starting(Description description) {
				try {
					suiteFilhaVO = new SuiteVO();
					suiteFilhaVO.setQuantitativoRunVO(new QuantitativoRunVO());
					dateInicioSuiteTestes = new Date();
					suiteFilhaVO.setDataInicioExecucao(dateInicioSuiteTestes);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}

			@Override
			protected void finished(Description description) {
				try {
					if (SuiteSiare.suitePrincipalVO.getSuitesFilhasByNome() == null) {
						SuiteSiare.suitePrincipalVO
								.setSuitesFilhasByNome(new LinkedHashMap<KeyMapVO<String, String>, SuiteVO>());
					}
					KeyMapVO<String, String> keyMap = new KeyMapVO<String, String>(suiteFilhaVO.getNomeProjeto(),
							suiteFilhaVO.getNomeSuite());

					SuiteSiare.suitePrincipalVO.getSuitesFilhasByNome().put(keyMap, suiteFilhaVO);
					SuiteSiare.finalizeReport();
				} catch (Throwable e) {
					SuiteSiare.addExceptionVO(e, suiteFilhaVO.getExceptions());
					e.printStackTrace();
				}
			}
		};
	}

	public static void beforeClassSuite(String nomeProjeto_, String nomeSuite_) {
		nomeProjeto = nomeProjeto_;
		nomeSuite = nomeSuite_;
		suiteFilhaVO.setNomeSuite(nomeSuite);
		suiteFilhaVO.setNomeProjeto(nomeProjeto);
	}
}
