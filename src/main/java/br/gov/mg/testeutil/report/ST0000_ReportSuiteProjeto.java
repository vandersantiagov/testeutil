package br.gov.mg.testeutil.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.junit.AfterClass;
import org.junit.ClassRule;

import br.gov.mg.testeutil.report.rules.RuleReport;
import br.gov.mg.testeutil.report.rules.RuleReportSuiteProjeto;
import br.gov.mg.testeutil.report.rules.SuiteSiare;

public class ST0000_ReportSuiteProjeto {
	private static String nomeSuite;
	public static boolean quitAmbiente = true;
	public static boolean printErrosJunit = true;

	@ClassRule
	public static RuleReport getResource() {
		SuiteSiare.idSuite += 1;
		RuleReportSuiteProjeto.isSuiteTotal = false;
		return RuleReportSuiteProjeto.startTestesSuiteFilha();
	}

	public static void setNomeProjeto(String nomeProjeto, String nomeSuite) {
		SuiteSiare.printErrosJunit = printErrosJunit;
		if (StringUtils.isBlank(SuiteSiare.nomePrimeiraSuite)) {
			RuleReportSuiteProjeto.isSuiteTotal = true;
			SuiteSiare.nomePrimeiraSuite = nomeSuite;
			SuiteSiare.suitePrincipalVO.setNomeSuite(nomeSuite);
			SuiteSiare.nomeProjetoSuitePrincipal = nomeProjeto;
			ST0000_ReportSuiteProjeto.nomeSuite = nomeSuite;
		} else {
			RuleReportSuiteProjeto.isSuiteTotal = false;
		}
		SuiteSiare.quitAmbiente = quitAmbiente;
		RuleReportSuiteProjeto.beforeClassSuite(nomeProjeto, nomeSuite);
	}

	public static void relatorioPerformace(String caminhoRelatorio) throws IOException {
		Properties p = new Properties();
		p.load(new FileInputStream(new File(caminhoRelatorio)));
		for (String k : p.stringPropertyNames()) {
			System.setProperty(k, p.getProperty(k));
		}
	}

	@AfterClass
	public static void afterClass() throws Exception {
		SuiteSiare.idSuite -= 1;
		if (Objects.equals(nomeSuite, SuiteSiare.suitePrincipalVO.getNomeSuite())) {
			RuleReportSuiteProjeto.isSuiteTotal = true;
		}
	}
}
