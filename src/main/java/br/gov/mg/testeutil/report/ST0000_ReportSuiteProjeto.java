package br.gov.mg.testeutil.report;

import java.util.Objects;

import org.junit.ClassRule;

import br.gov.mg.testeutil.report.rules.RuleReport;
import br.gov.mg.testeutil.report.rules.RuleReportSuiteProjeto;

public class ST0000_ReportSuiteProjeto {

	@ClassRule
	public static RuleReport getResource() {
		Integer idAnterior = RuleReportSuiteProjeto.idSuiteFilha;
		Integer idSuite = Integer.valueOf(2);
		if (idAnterior != null && Objects.equals(idAnterior, idSuite)) {
			idSuite = idSuite + 1;
		}
		return RuleReportSuiteProjeto.startTestesSuiteFilha(idSuite);
	}

	public static void setNomeProjeto(String nomeProjeto_, String nomeSuite_) {
		RuleReportSuiteProjeto.beforeClassSuite(nomeProjeto_, nomeSuite_);
	}
}
