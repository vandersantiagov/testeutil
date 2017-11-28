package br.gov.mg.testeutil.report.rules;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.junit.internal.AssumptionViolatedException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import br.gov.mg.testeutil.metodos.MetodosSiare;
import br.gov.mg.testeutil.report.html.FileHTML;
import br.gov.mg.testeutil.report.vo.ClasseDeTesteVO;
import br.gov.mg.testeutil.report.vo.ExceptionVO;
import br.gov.mg.testeutil.report.vo.MetodoClasseTesteVO;
import br.gov.mg.testeutil.report.vo.QuantitativoRunVO;
import br.gov.mg.testeutil.util.FileUtil;

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
		} finally {
			String fileName = description.getTestClass().getSimpleName();
			treathPilhaErro(fileName);
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
		} finally {
			String fileName = "SUCESSO_" + description.getTestClass().getSimpleName();
			treathPilhaErro(fileName);
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
		} finally {
			String fileName = "SKIPED_" + description.getTestClass().getSimpleName();
			treathPilhaErro(fileName);
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
				treathPilhaErro(fileName);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	private void treathPilhaErro(String fileName) {
		By comandoDetalharPilhaDeErro = ObjetosReportDetalhesErro.comandoDetalharPilhaDeErro;
		if (isTelaErro(comandoDetalharPilhaDeErro)) {
			MetodosSiare.umClique(comandoDetalharPilhaDeErro);
			String nameArquivo = fileName + "_Pilha_Erro";
			File capturaScreenDaTela = MetodosSiare.capturaScreenDaTela(RuleReportSuiteProjeto.nomeProjeto,
					nameArquivo);

			StringBuilder textoToHTML = createHTML();
			StringBuilder textoToTxt = createTxt();

			String path = MetodosSiare.diretorioPrincipal + RuleReportSuiteProjeto.nomeProjeto + "\\" + nameArquivo;
			String pathFileHTMLPilhaErro = FileHTML.generateHTMLByText(path, textoToHTML.toString());
			String pathFileTxtPilhaErro = FileUtil.generateTxtByText(path, textoToTxt.toString());

			metodo.setCaminhoArquivoHTMLPilhaErro(pathFileHTMLPilhaErro);
			metodo.setCaminhoArquivoTXTPilhaErro(pathFileTxtPilhaErro);
			metodo.setCaminhoPrintPilhaErro(capturaScreenDaTela.getPath());
		}
	}

	private boolean isTelaErro(By comandoDetalharPilhaDeErro) {
		boolean isTelaErro = false;
		boolean isVisibleFormErro = true;
		try {
			By nameFormErro = ObjetosReportDetalhesErro.nameFormErro;
			try {
				MetodosSiare.driver.findElement(nameFormErro);
			} catch (Exception e) {
				isVisibleFormErro = false;
			}
			boolean formErroContainsTextoErro = false;
			if (isVisibleFormErro) {
				WebElement findElement = MetodosSiare.driver.findElement(nameFormErro);
				String textErro = findElement.getText();
				formErroContainsTextoErro = StringUtils.containsIgnoreCase(textErro, "Erro");

			boolean isBtnDetalheVisible = MetodosSiare.verificaSeOElementoEstaVisivel(comandoDetalharPilhaDeErro);

			isTelaErro = isVisibleFormErro && isBtnDetalheVisible && formErroContainsTextoErro;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isTelaErro;
	}

	private StringBuilder createHTML() {
		StringBuilder sb = new StringBuilder();
		sb.append(FileHTML.HTML_OPEN_HTML);
		// Erro
		fundoETextoCinza("Erro", sb);
		String textoDoElemento = MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.mensagemErro);
		sb.append(textoDoElemento);

		// Dados do Erro
		fundoETextoCinza("Dados do Erro", sb);
		sb.append(FileHTML.HTML_OPEN_TABLE_HTML);
		sb.append(FileHTML.HTML_OPEN_TR_HTML);
		sb.append(FileHTML.HTML_OPEN_TD_HTML);
		fundoCinzaTextoVermelho(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.lblAplicacaoErro), sb);
		sb.append(FileHTML.HTML_CLOSE_TD_HTML);
		sb.append(FileHTML.HTML_OPEN_TD_HTML);
		fundoCinzaTextoSemFormatacao(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.aplicacaoErro), sb);
		sb.append(FileHTML.HTML_CLOSE_TD_HTML);
		sb.append(FileHTML.HTML_CLOSE_TR_HTML);

		sb.append(FileHTML.HTML_OPEN_TR_HTML);
		sb.append(FileHTML.HTML_OPEN_TD_HTML);
		fundoCinzaTextoVermelho(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.lblModuloErro), sb);
		sb.append(FileHTML.HTML_CLOSE_TD_HTML);
		sb.append(FileHTML.HTML_OPEN_TD_HTML);
		fundoCinzaTextoSemFormatacao(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.moduloErro), sb);
		sb.append(FileHTML.HTML_CLOSE_TD_HTML);
		sb.append(FileHTML.HTML_CLOSE_TR_HTML);

		sb.append(FileHTML.HTML_OPEN_TR_HTML);
		sb.append(FileHTML.HTML_OPEN_TD_HTML);
		fundoCinzaTextoVermelho(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.lblTelaErro), sb);
		sb.append(FileHTML.HTML_CLOSE_TD_HTML);
		sb.append(FileHTML.HTML_OPEN_TD_HTML);
		fundoCinzaTextoSemFormatacao(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.telaErro), sb);
		sb.append(FileHTML.HTML_CLOSE_TD_HTML);
		sb.append(FileHTML.HTML_CLOSE_TR_HTML);

		sb.append(FileHTML.HTML_OPEN_TR_HTML);
		sb.append(FileHTML.HTML_OPEN_TD_HTML);
		fundoCinzaTextoVermelho(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.lblAcaoErro), sb);
		sb.append(FileHTML.HTML_CLOSE_TD_HTML);
		sb.append(FileHTML.HTML_OPEN_TD_HTML);
		fundoCinzaTextoSemFormatacao(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.acaoErro), sb);
		sb.append(FileHTML.HTML_CLOSE_TD_HTML);
		sb.append(FileHTML.HTML_CLOSE_TR_HTML);

		sb.append(FileHTML.HTML_CLOSE_TABLE_HTML);

		// Pilha de Erros
		fundoCinzaEscuroTextoVermelho(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.lblUFWStackTrace), sb);
		sb.append(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.UFWStackTrace));

		// Java Stack Trace
		fundoCinzaEscuroTextoVermelho(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.lblJavaStackTrace), sb);
		String javaStackTrace = MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.javaStackTrace);
		String javaStackTraceComQuebra = javaStackTrace.replace(" at ", " at <br/>");
		sb.append(javaStackTraceComQuebra);

		sb.append(FileHTML.HTML_CLOSE_HTML);

		return sb;
	}

	private StringBuilder createTxt() {
		StringBuilder sb = new StringBuilder();
		// Erro
		sb.append("Erro").append("\n");
		String textoDoElemento = MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.mensagemErro);
		sb.append(textoDoElemento).append("\n\n");

		// Dados do Erro
		sb.append("Dados do Erro").append("\n");
		sb.append(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.lblAplicacaoErro)).append("			 ");
		sb.append(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.aplicacaoErro)).append("\n");

		sb.append(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.lblModuloErro)).append("				 ");
		sb.append(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.moduloErro)).append("\n");

		sb.append(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.lblTelaErro)).append("				 ");
		sb.append(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.telaErro)).append("\n");

		sb.append(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.lblAcaoErro)).append("				 ");
		sb.append(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.acaoErro)).append("\n\n");

		// Pilha de Erros
		sb.append(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.lblUFWStackTrace)).append("\n");
		sb.append(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.UFWStackTrace)).append("\n\n");

		// Java Stack Trace
		sb.append(MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.lblJavaStackTrace)).append("\n");
		String javaStackTrace = MetodosSiare.textoDoElemento(ObjetosReportDetalhesErro.javaStackTrace);
		String javaStackTraceComQuebra = javaStackTrace.replace(" at ", " at \n");
		sb.append(javaStackTraceComQuebra);
		return sb;
	}

	private void fundoETextoCinza(String textoCabecalho, StringBuilder sb) {
		String colorCinzaEscuroBackground = "#C0C0C0";
		String colorCinzaEscuroFonte = "#5F5F5F";

		String corDeFonte = "color:";
		String corDeFundo = "; background-color:";
		String negrito = "; font-weight: bold";
		String margin0 = "; margin:0px";
		sb.append("<p style='").append(corDeFonte).append(colorCinzaEscuroFonte).append(corDeFundo)
				.append(colorCinzaEscuroBackground).append(negrito).append(margin0).append("'>").append(textoCabecalho)
				.append("</p>");
	}

	private void fundoCinzaTextoVermelho(String textoCabecalho, StringBuilder sb) {
		String colorCinzaBackground = "#EFEFEF";
		String colorRedFonte = "#CC0000";

		String corDeFonte = "color:";
		String corDeFundo = "; background-color:";

		sb.append("<p style='").append(corDeFonte).append(colorRedFonte).append(corDeFundo).append(colorCinzaBackground)
				.append("'>").append(textoCabecalho).append("</p>");
	}

	private void fundoCinzaTextoSemFormatacao(String textoCabecalho, StringBuilder sb) {
		String corDeFundo = "background-color:";
		String colorCinzaBackground = "#EFEFEF";

		sb.append("<p style='").append(corDeFundo).append(colorCinzaBackground).append("'>").append(textoCabecalho)
				.append("</p>");
	}

	private void fundoCinzaEscuroTextoVermelho(String textoCabecalho, StringBuilder sb) {

		String colorCinzaEscuroBackground = "#C0C0C0";
		String colorRedFonte = "#CC0000";

		String corDeFundo = "; background-color:";
		String corDeFonte = "color:";
		String negrito = "; font-weight: bold";
		String margin0 = "; margin:0px";

		sb.append("<p style='").append(corDeFonte).append(colorRedFonte).append(corDeFundo)
				.append(colorCinzaEscuroBackground).append(negrito).append(margin0).append("'>").append(textoCabecalho)
				.append("</p>");
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
