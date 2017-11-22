package br.gov.mg.testeutil.report.rules;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import org.junit.internal.AssumptionViolatedException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import br.gov.mg.testeutil.enums.TipoArquivoEnum;
import br.gov.mg.testeutil.metodos.MetodosSiare;
import br.gov.mg.testeutil.vo.ClasseDeTesteVO;
import br.gov.mg.testeutil.vo.ExceptionVO;
import br.gov.mg.testeutil.vo.MetodoClasseTesteVO;
import br.gov.mg.testeutil.vo.QuantitativoRunVO;

/**
 * @author sandra.rodrigues
 */
public class RuleReport extends TestWatcher {

	public RuleReport() {
	}

	public static ClasseDeTesteVO classeDeTeste;
	private MetodoClasseTesteVO metodo = new MetodoClasseTesteVO();
	private Date dataInicioExecucaoMetodo;
	private boolean isCallSuite;
	public static Date dataInicioExecucaoClasseTeste;

	public RuleReport(Date date) {
		dataInicioExecucaoClasseTeste = new Date();
		dataInicioExecucaoMetodo = date;
		isCallSuite = false;
		countRun();
	}

	private void setNomeSuite() {
		classeDeTeste.setNomeSuite(RuleReportSuiteProjeto.nomeSuite);
	}

	public RuleReport(boolean isChamadaSuite) {
		dataInicioExecucaoMetodo = new Date();
		isCallSuite = isChamadaSuite;
		if (!isChamadaSuite) {
			countRun();
		}
	}

	@Override
	protected void starting(Description description) {
		try {
			if (classeDeTeste == null) {
				classeDeTeste = new ClasseDeTesteVO();
				classeDeTeste.setDataInicioExecucao(dataInicioExecucaoClasseTeste);
			} else if (!Objects.equals(SuiteSiare.getNameByPosition(description), classeDeTeste.getNomeClasse())) {
				classeDeTeste = new ClasseDeTesteVO();
				classeDeTeste.setDataInicioExecucao(dataInicioExecucaoClasseTeste);
			}
			classeDeTeste.setQuantitativoRunVO(new QuantitativoRunVO());
			setNomeClasse(description);
			setNomeProjeto();
			setNomeSuite();
			setInicioExecucao();

			super.starting(description);
			metodo.setDataInicioExecucao(dataInicioExecucaoMetodo);
			if (!isSuiteGeral()) {
				countRun();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void finished(Description description) {
		try {
			Date dataFimExecucao = new Date();
			setNomeProjeto();
			setNomeSuite();
			classeDeTeste.getMetodos().add(metodo);
			classeDeTeste.setDataFimExecucao(dataFimExecucao);
			if (RuleReportSuiteProjeto.suiteFilhaVO.getClassesDeTesteByName() == null) {
				RuleReportSuiteProjeto.suiteFilhaVO
						.setClassesDeTesteByName(new LinkedHashMap<String, ClasseDeTesteVO>());
			}
			RuleReportSuiteProjeto.suiteFilhaVO.getClassesDeTesteByName().put(classeDeTeste.getNomeClasse(),
					classeDeTeste);
			super.finished(description);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public Statement apply(Statement base, Description description) {
		return super.apply(base, description);
	}

	@Override
	protected void succeeded(Description description) {
		try {
			if (isCallSuite) {
				return;
			}
			metodo.setSucess(true);
			setNomeClasse(description);
			setNomeMetodo(description);
			setDescripcion(description);
			setNomeProjeto();
			setDataFimExecucaoMetodo();
			countSuccess();
		} catch (Throwable e1) {
			countErro();
			addException(e1, metodo.getExceptions());
		}
	}

	@Override
	protected void skipped(AssumptionViolatedException e, Description description) {
		try {
			if (isCallSuite) {
				return;
			}
			metodo.setSkiped(true);
			setNomeClasse(description);
			setNomeMetodo(description);
			setDescripcion(description);
			setNomeProjeto();
			setDataFimExecucaoMetodo();
			countSkips();
		} catch (Throwable e1) {
			countErro();
			addException(e1, metodo.getExceptions());
		}
	}

	@Override
	protected void failed(Throwable e, Description description) {
		boolean isFailed = SuiteSiare.isFailed(e);
		try {
			if (isCallSuite) {
				return;
			}
			metodo.setFalha(isFailed);
			metodo.setErro(!isFailed);

			String identificacaoProblema = isFailed ? "FALHA " : "ERRO ";

			MetodosSiare.capturaScreenDaTela(RuleReportSuiteProjeto.nomeProjeto,
					identificacaoProblema + description.getTestClass().getSimpleName());
			// private static final String PATH_DIRETORIO_REPORT =
			// FileUtil.getDiretorio(MetodosSiare.diretorioPrincipal, "Report");

			// String pathProjeto = FileUtil.getDiretorio(PATH_DIRETORIO_REPORT
			// + "\\", "Prints");
			//
			// MetodosSiare.capturaScreenDaTela(pathProjeto,
			// identificacaoProblema +
			// description.getTestClass().getSimpleName());

			String caminhoPrint = MetodosSiare.diretorioPrincipal + RuleReportSuiteProjeto.nomeSuite + "\\"
					+ identificacaoProblema + description.getTestClass().getSimpleName() + "." + TipoArquivoEnum.JPEG;

			setNomeMetodo(description);
			setDescripcion(description);
			setNomeProjeto();
			setDataFimExecucaoMetodo();
			addException(e, metodo.getExceptions());
			metodo.setCaminhoPrintErro(caminhoPrint);

			if (isFailed) {
				countFailed();
			} else {
				countErro();
			}

		} catch (Throwable e1) {
			countErro();
			addException(e1, metodo.getExceptions());
		}
	}

	private void setDataFimExecucaoMetodo() {
		metodo.setDataFimExecucao(new Date());
		String chaveMapa = classeDeTeste.getNomeClasse() + "." + classeDeTeste.getNomeSuite();
		BigDecimal tempo = classeDeTeste.getTempoPorClasse().get(chaveMapa);
		if (tempo != null) {
			tempo.add(metodo.getDuracaoSeconds());
		} else {
			classeDeTeste.getTempoPorClasse().put(chaveMapa, metodo.getDuracaoSeconds());
		}

	}

	private void setNomeProjeto() {
		String nomeProjeto = RuleReportSuiteProjeto.nomeProjeto;
		metodo.setNomeProjeto(nomeProjeto);
		classeDeTeste.setNomeProjeto(nomeProjeto);
	}

	private void setDescripcion(Description description) {
		metodo.setDescription(description);
	}

	private boolean isSuiteGeral() {
		return SuiteSiare.suitePrincipalVO.isSuitePrincipal();
	}

	private void setInicioExecucao() {
		classeDeTeste.setDataInicioExecucao(new Date());
	}

	private void countSuccess() {
		if (classeDeTeste != null) {
			classeDeTeste.getQuantitativoRunVO().sumaSucesso();
		}
		RuleReportSuiteProjeto.suiteFilhaVO.getQuantitativoRunVO().sumaSucesso();
	}

	private void countSkips() {
		if (classeDeTeste != null) {
			classeDeTeste.getQuantitativoRunVO().sumaSkiped();
		}
		RuleReportSuiteProjeto.suiteFilhaVO.getQuantitativoRunVO().sumaSkiped();
	}

	private void countFailed() {
		if (classeDeTeste != null) {
			classeDeTeste.getQuantitativoRunVO().sumaFalha();
		}
		RuleReportSuiteProjeto.suiteFilhaVO.getQuantitativoRunVO().sumaFalha();
	}

	private void countErro() {
		if (classeDeTeste != null) {
			classeDeTeste.getQuantitativoRunVO().sumaErro();
		}
		RuleReportSuiteProjeto.suiteFilhaVO.getQuantitativoRunVO().sumaErro();
	}

	private void countRun() {
		if (classeDeTeste != null) {
			classeDeTeste.getQuantitativoRunVO().sumaRun();
		}
		RuleReportSuiteProjeto.suiteFilhaVO.getQuantitativoRunVO().sumaRun();
	}

	private void addException(Throwable e1, List<ExceptionVO> exceptions) {
		SuiteSiare.addExceptionVO(e1, exceptions);
	}

	private void setNomeClasse(Description description) throws IOException {
		String nomeClasse = description.getTestClass().getSimpleName();
		classeDeTeste.setNomeClasse(nomeClasse);
	}

	private void setNomeMetodo(Description description) {
		metodo.setNome(description.getMethodName());
	}
}
