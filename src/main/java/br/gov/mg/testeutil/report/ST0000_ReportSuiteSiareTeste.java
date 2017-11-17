package br.gov.mg.testeutil.report;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import br.gov.mg.testeutil.report.rules.SuiteSiare;

public class ST0000_ReportSuiteSiareTeste {

	@BeforeClass
	public static void beforeClass() {
		SuiteSiare.startReport();
	}

	@AfterClass
	public static void afterClass() throws Exception {
		SuiteSiare.finalizeReport();
	}
}
