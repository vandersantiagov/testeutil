package br.gov.mg.testeutil.report;

import java.util.Date;

import org.junit.Rule;

import br.gov.mg.testeutil.report.rules.RuleReport;

public class A0000_ReportTeste {
	Date dataTeste = new Date();
	@Rule
	public RuleReport log = new RuleReport(dataTeste);

}
