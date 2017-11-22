package br.gov.mg.testeutil.report;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.junit.AfterClass;
import org.junit.ClassRule;

import br.gov.mg.testeutil.report.rules.RuleReport;
import br.gov.mg.testeutil.report.rules.RuleReportSuiteProjeto;
import br.gov.mg.testeutil.report.rules.SuiteSiare;

public class ST0000_ReportSuiteProjeto {
	private static String nomeSuite;
	public static boolean quitAmbiente = true;

	@ClassRule
	public static RuleReport getResource() {
		SuiteSiare.idSuite += 1;
		RuleReportSuiteProjeto.isSuiteTotal = false;
		return RuleReportSuiteProjeto.startTestesSuiteFilha();
	}

	public static void setNomeProjeto(String nomeProjeto_, String nomeSuite_) {
		if (StringUtils.isBlank(SuiteSiare.nomePrimeiraSuite)) {
			RuleReportSuiteProjeto.isSuiteTotal = true;
			SuiteSiare.nomePrimeiraSuite = nomeSuite_;
			SuiteSiare.suitePrincipalVO.setNomeSuite(nomeSuite_);
			nomeSuite = nomeSuite_;
		} else {
			RuleReportSuiteProjeto.isSuiteTotal = false;
		}
		SuiteSiare.quitAmbiente = quitAmbiente;
		RuleReportSuiteProjeto.beforeClassSuite(nomeProjeto_, nomeSuite_);
	}

	@AfterClass
	public static void afterClass() throws Exception {
		SuiteSiare.idSuite -= 1;
		if (Objects.equals(nomeSuite, SuiteSiare.suitePrincipalVO.getNomeSuite())) {
			RuleReportSuiteProjeto.isSuiteTotal = true;
		}
	}
}
