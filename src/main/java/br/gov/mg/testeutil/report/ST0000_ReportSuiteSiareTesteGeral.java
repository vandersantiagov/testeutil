package br.gov.mg.testeutil.report;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import br.gov.mg.testeutil.report.rules.RuleReportSuiteProjeto;
import br.gov.mg.testeutil.report.rules.SuiteSiare;

public class ST0000_ReportSuiteSiareTesteGeral {
	public static boolean quitAmbiente = true;

	@BeforeClass
	public static void beforeClass() {
		RuleReportSuiteProjeto.isSuiteTotal = true;
		SuiteSiare.startReport();
	}

	public static void setNomeSuite(String nomeSuite_) {
		SuiteSiare.quitAmbiente = quitAmbiente;
		SuiteSiare.nomePrimeiraSuite = nomeSuite_;
	}

	@AfterClass
	public static void afterClass() throws Exception {
		RuleReportSuiteProjeto.isSuiteTotal = true;
		SuiteSiare.finalizeReport();
	}

}
