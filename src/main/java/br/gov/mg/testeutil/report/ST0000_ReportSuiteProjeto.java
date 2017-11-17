package br.gov.mg.testeutil.report;

import org.junit.ClassRule;

import br.gov.mg.testeutil.report.rules.RuleReport;
import br.gov.mg.testeutil.report.rules.RuleReportSuiteProjeto;

public class ST0000_ReportSuiteProjeto {

	@ClassRule
	public static RuleReport getResource() {
		return RuleReportSuiteProjeto.startTestesSuiteFilha();
	}

	public static void setNomeProjeto(String nomeProjeto_, String nomeSuite_) {
		RuleReportSuiteProjeto.beforeClassSuite(nomeProjeto_, nomeSuite_);
	}
}
