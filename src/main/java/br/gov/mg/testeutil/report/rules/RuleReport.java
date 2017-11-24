package br.gov.mg.testeutil.report.rules;

import java.io.File;
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
import org.openqa.selenium.By;

import br.gov.mg.testeutil.metodos.MetodosSiare;
import br.gov.mg.testeutil.report.html.FileHTML;
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
		String identificacaoProblema = isFailed ? "FALHA_" : "ERRO_";

		String fileName = identificacaoProblema + description.getTestClass().getSimpleName();
		try {
			if (isCallSuite) {
				return;
			}
			metodo.setFalha(isFailed);
			metodo.setErro(!isFailed);

			File fileCriado = MetodosSiare.capturaScreenDaTela(RuleReportSuiteProjeto.nomeProjeto, fileName);
			setNomeMetodo(description);
			setDescripcion(description);
			setNomeProjeto();
			setDataFimExecucaoMetodo();
			addException(e, metodo.getExceptions());
			metodo.setCaminhoPrintErro(fileCriado.getPath());

			if (isFailed) {
				countFailed();
			} else {
				countErro();
			}

		} catch (Throwable e1) {
			countErro();
			addException(e1, metodo.getExceptions());
		} finally {
			try {
				By comandoDetalharPilhaDeErro = ObjetosReportDetalhesErro.comandoDetalharPilhaDeErro;
				boolean isBtnDetalheVisible = MetodosSiare.verificaSeOElementoEstaVisivel(comandoDetalharPilhaDeErro);
				if (isBtnDetalheVisible) {
					treathPilhaErro(fileName, comandoDetalharPilhaDeErro);
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	private void treathPilhaErro(String fileName, By comandoDetalharPilhaDeErro) {
		MetodosSiare.umClique(comandoDetalharPilhaDeErro);
		String nameArquivo = fileName + "_Pilha_Erro";
		File capturaScreenDaTela = MetodosSiare.capturaScreenDaTela(RuleReportSuiteProjeto.nomeProjeto, nameArquivo);

		StringBuilder sb = new StringBuilder();
		sb.append(FileHTML.HTML_OPEN_HTML);
		// Erro
		fundoETextoCinza("Erro", sb);
		String textoDoElemento = MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.mensagemErro);
		sb.append(textoDoElemento);

		// Dados do Erro
		fundoETextoCinza("Dados do Erro", sb);
		fundoCinzaTextoVermelho(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.lblAplicacaoErro), sb);
		fundoCinzaTextoSemFormatacao(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.aplicacaoErro), sb);
		fundoCinzaTextoVermelho(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.lblModuloErro), sb);
		fundoCinzaTextoSemFormatacao(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.moduloErro), sb);
		fundoCinzaTextoVermelho(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.lblTelaErro), sb);
		fundoCinzaTextoSemFormatacao(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.telaErro), sb);
		fundoCinzaTextoVermelho(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.lblAcaoErro), sb);
		fundoCinzaTextoSemFormatacao(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.acaoErro), sb);

		// Pilha de Erros
		fundoCinzaEscuroTextoVermelho(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.lblUFWStackTrace), sb);
		sb.append(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.UFWStackTrace));

		// Java Stack Trace
		fundoCinzaEscuroTextoVermelho(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.lblJavaStackTrace), sb);
		sb.append(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.javaStackTrace));

		sb.append(FileHTML.HTML_CLOSE_HTML);

		String path = MetodosSiare.diretorioPrincipal + RuleReportSuiteProjeto.nomeProjeto + "\\" + nameArquivo;
		String pathFilePilhaErro = FileHTML.generateHTMLByText(path, sb.toString());

		metodo.setCaminhoArquivoPilhaErro(pathFilePilhaErro);
		metodo.setCaminhoPrintPilhaErro(capturaScreenDaTela.getPath());
	}

	private void fundoETextoCinza(String textoCabecalho, StringBuilder sb) {

		String colorCinzaBackground = "#EFEFEF";
		String colorCinzaEscuroFonte = "#5F5F5F";

		String corDeFundo = "background-color:";
		String corDeFonte = "color:";

		sb.append("<p style='").append(corDeFonte).append(colorCinzaEscuroFonte).append(" ; ").append(corDeFundo)
				.append(colorCinzaBackground).append("'>").append(textoCabecalho).append("</p>");
	}

	private void fundoCinzaTextoVermelho(String textoCabecalho, StringBuilder sb) {

		String colorCinzaBackground = "#EFEFEF";
		String colorRedFonte = "#CC0000";

		String corDeFundo = "background-color:";
		String corDeFonte = "color:";

		sb.append("<p style='").append(corDeFonte).append(colorRedFonte).append(" ; ").append(corDeFundo)
				.append(colorCinzaBackground).append("'>").append(textoCabecalho).append("</p>");
	}

	private void fundoCinzaTextoSemFormatacao(String textoCabecalho, StringBuilder sb) {
		String colorCinzaBackground = "#EFEFEF";
		String corDeFundo = "background-color:";

		sb.append("<p style='").append(corDeFundo).append(colorCinzaBackground).append("'>").append(textoCabecalho)
				.append("</p>");
	}

	private void fundoCinzaEscuroTextoVermelho(String textoCabecalho, StringBuilder sb) {

		String colorCinzaEscuroBackground = "#C0C0C0";
		String colorRedFonte = "#CC0000";

		String corDeFundo = "background-color:";
		String corDeFonte = "color:";

		sb.append("<p style='").append(corDeFonte).append(colorRedFonte).append(" ; ").append(corDeFundo)
				.append(colorCinzaEscuroBackground).append("'>").append(textoCabecalho).append("</p>");
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
