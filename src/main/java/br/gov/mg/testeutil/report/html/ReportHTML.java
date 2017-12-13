package br.gov.mg.testeutil.report.html;

import static br.gov.mg.testeutil.report.html.FileHTML.HTML_CLOSE_DETAILS;
import static br.gov.mg.testeutil.report.html.FileHTML.HTML_CLOSE_FONT;
import static br.gov.mg.testeutil.report.html.FileHTML.HTML_CLOSE_HTML;
import static br.gov.mg.testeutil.report.html.FileHTML.HTML_CLOSE_NEGRITO;
import static br.gov.mg.testeutil.report.html.FileHTML.HTML_CLOSE_P;
import static br.gov.mg.testeutil.report.html.FileHTML.HTML_CLOSE_PRE;
import static br.gov.mg.testeutil.report.html.FileHTML.HTML_CLOSE_SPAN;
import static br.gov.mg.testeutil.report.html.FileHTML.HTML_CLOSE_SUMMARY;
import static br.gov.mg.testeutil.report.html.FileHTML.HTML_OPEN_DETAILS;
import static br.gov.mg.testeutil.report.html.FileHTML.HTML_OPEN_HTML;
import static br.gov.mg.testeutil.report.html.FileHTML.HTML_OPEN_NEGRITO;
import static br.gov.mg.testeutil.report.html.FileHTML.HTML_OPEN_P;
import static br.gov.mg.testeutil.report.html.FileHTML.HTML_OPEN_PRE;
import static br.gov.mg.testeutil.report.html.FileHTML.HTML_OPEN_SPAN;
import static br.gov.mg.testeutil.report.html.FileHTML.HTML_OPEN_SUMMARY;
import static br.gov.mg.testeutil.report.html.FileHTML.HTML_QUEBRA_LINHA;
import static br.gov.mg.testeutil.report.html.FileHTML.closeFalha;
import static br.gov.mg.testeutil.report.html.FileHTML.closeFalhaProjeto;
import static br.gov.mg.testeutil.report.html.FileHTML.closeGeralProjeto;
import static br.gov.mg.testeutil.report.html.FileHTML.closePastaGeral;
import static br.gov.mg.testeutil.report.html.FileHTML.closePastaGeralMinimized;
import static br.gov.mg.testeutil.report.html.FileHTML.closeSucessoProjeto;
import static br.gov.mg.testeutil.report.html.FileHTML.createFilesGeral;
import static br.gov.mg.testeutil.report.html.FileHTML.createFilesGeralMinimized;
import static br.gov.mg.testeutil.report.html.FileHTML.createFilesProjeto;
import static br.gov.mg.testeutil.report.html.FileHTML.deleteArquivosDatasAntigas;
import static br.gov.mg.testeutil.report.html.FileHTML.escreverArquivoFalha;
import static br.gov.mg.testeutil.report.html.FileHTML.escreverArquivoFalhaProjeto;
import static br.gov.mg.testeutil.report.html.FileHTML.escreverArquivoGeral;
import static br.gov.mg.testeutil.report.html.FileHTML.escreverArquivoGeralMinimized;
import static br.gov.mg.testeutil.report.html.FileHTML.escreverArquivoGeralProjeto;
import static br.gov.mg.testeutil.report.html.FileHTML.escreverArquivoSucessoProjeto;
import static br.gov.mg.testeutil.report.html.FileHTML.getPathReportGeral;
import static br.gov.mg.testeutil.report.html.FileHTML.getPathReportGeralMinimized;
import static br.gov.mg.testeutil.report.html.FileHTML.getPathReportGeralProjeto;
import static br.gov.mg.testeutil.report.html.FileHTML.openReportGeralMinimizedInBrowser;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import br.gov.mg.testeutil.report.rules.SuiteSiare;
import br.gov.mg.testeutil.report.vo.ClasseDeTesteVO;
import br.gov.mg.testeutil.report.vo.ExceptionVO;
import br.gov.mg.testeutil.report.vo.KeyMapVO;
import br.gov.mg.testeutil.report.vo.MetodoClasseTesteVO;
import br.gov.mg.testeutil.report.vo.QuantitativoRunVO;
import br.gov.mg.testeutil.report.vo.StackTraceElementVO;
import br.gov.mg.testeutil.report.vo.SuitePrincipalVO;
import br.gov.mg.testeutil.report.vo.SuiteVO;
import br.gov.mg.testeutil.util.DateUtil;

/**
 * @author sandra.rodrigues
 */
public class ReportHTML {

	private static final String SEPARATOR_RESULT_FINAL = "======================================================================================";
	// Parametros para comparação em código
	private static final String FAILED = "FAILED";
	private static final String SUCCESS = "SUCCESS";
	private static final String ERRO = "ERRO";
	private static final String SKIPPED = "SKIPPED";
	// Textos exibido em tela
	private static final String SUCESSOS = "Sucessos";
	private static final String IGNORADOS = "Ignorados";
	private static final String FALHAS = "Falhas";
	private static final String ERROS = "Erros";
	private static final String TEXTO_INICIO_DOS_TESTES = "Inicio dos Testes: ";
	private static final String TEXTO_FIM_DOS_TESTES = "Fim dos Testes: ";
	private static final String TEXTO_PROJETO = "Projeto: ";
	private static final String TEXTO_SUITE_PRINCIPAL = "Suite Principal: ";
	private static final String TEXTO_PROJETO_PRINCIPAL = "Projeto Principal: ";
	private static final String TEXTO_SUITE = "Suite: ";
	private static final String TEXTO_CLASSE = "Classe: ";
	private static final String TEXTO_METODO = " Método: ";
	private static final String TEXTO_NUMERO_FALHA = "Número de Falhas: ";
	private static final String TEXTO_NUMERO_SUCESSO = "Número de Sucessos: ";

	private static final String HTML_COLOR_SUCCESS = "#00AA00";
	private static final String HTML_COLOR_FAILED = "#446691";
	private static final String HTML_COLOR_ERRO = "#DD0000";
	private static final String HTML_COLOR_SKIPED = "#CCCC00";
	private static final String HTML_COLOR_BLACK = "#000000";
	private static final String HTML_COLOR_BLUE = "#0000ff";

	private static final String SEPARADOR = "-----------------------------------------------------------------------------";

	private static StringBuilder sbReportGeralMinimized = new StringBuilder();
	private static StringBuilder sbReportGeral = new StringBuilder();
	private static StringBuilder sbReportFalha = new StringBuilder();

	private static StringBuilder sbReportGeralProjeto = new StringBuilder();
	private static StringBuilder sbReportFalhaProjeto = new StringBuilder();
	private static StringBuilder sbReportSucessoProjeto = new StringBuilder();

	private static int countFalhasSuites;
	private static int countSucessoSuites;

	public static void createHTML(SuitePrincipalVO suitePrincipalVO) throws IOException {
		Date date = new Date();
		String nomeProjetoAnterior = "";
		SuiteVO suiteVO = null;
		String[] caminhoFileGeralProjeto = null;

		String nomePastaProjetoPrincipal = suitePrincipalVO.getNomeProjeto();

		String path = getPathReportGeral(nomePastaProjetoPrincipal);
		// Deleta as últimas 10 pastas referente ao projeto.
		deleteArquivosDatasAntigas(path, false);
		// Cria o arquivo html geral na pasta report.
		String[] caminhoFileGeral = createFilesGeral(nomePastaProjetoPrincipal, path, date);

		String pathMinimized = getPathReportGeralMinimized(nomePastaProjetoPrincipal);
		// Deleta as últimas 10 pastas referente ao projeto.
		deleteArquivosDatasAntigas(pathMinimized, false);
		// Cria o arquivo html geral na pasta report.
		String[] caminhoFileGeralMinimized = createFilesGeralMinimized(nomePastaProjetoPrincipal, pathMinimized, date);

		// Abre a tagHTML para os arquivos.
		openHTMLGeral();
		appendReportGeral(HTML_QUEBRA_LINHA, HTML_OPEN_NEGRITO, TEXTO_INICIO_DOS_TESTES,
				DateUtil.getDataFormatadaByFormato(suitePrincipalVO.getDataInicioExecucao(), DateUtil.FORMATO_DATA6),
				HTML_CLOSE_NEGRITO, HTML_QUEBRA_LINHA, HTML_QUEBRA_LINHA);
		appendReportGeral(HTML_OPEN_NEGRITO, TEXTO_SUITE_PRINCIPAL, HTML_CLOSE_NEGRITO, suitePrincipalVO.getNomeSuite(),
				HTML_QUEBRA_LINHA);
		if (StringUtils.isNotBlank(nomePastaProjetoPrincipal)) {
			appendReportGeral(HTML_OPEN_NEGRITO, TEXTO_PROJETO_PRINCIPAL, HTML_CLOSE_NEGRITO, nomePastaProjetoPrincipal,
					HTML_QUEBRA_LINHA);
		}

		openHTMLGeralMinimized();
		appendReportGeralMinimized(HTML_QUEBRA_LINHA, HTML_OPEN_NEGRITO, TEXTO_INICIO_DOS_TESTES,
				DateUtil.getDataFormatadaByFormato(suitePrincipalVO.getDataInicioExecucao(), DateUtil.FORMATO_DATA6),
				HTML_CLOSE_NEGRITO, HTML_QUEBRA_LINHA, HTML_QUEBRA_LINHA);

		appendReportGeralMinimized(HTML_OPEN_NEGRITO, TEXTO_SUITE_PRINCIPAL, HTML_CLOSE_NEGRITO,
				suitePrincipalVO.getNomeSuite(), HTML_QUEBRA_LINHA);
		if (StringUtils.isNotBlank(nomePastaProjetoPrincipal)) {
			appendReportGeralMinimized(HTML_OPEN_NEGRITO, TEXTO_PROJETO_PRINCIPAL, HTML_CLOSE_NEGRITO,
					nomePastaProjetoPrincipal, HTML_QUEBRA_LINHA);
		}

		Map<KeyMapVO<String, String>, SuiteVO> mapSuites = suitePrincipalVO.getSuitesFilhasByNome();

		if (MapUtils.isNotEmpty(mapSuites)) {
			for (Entry<KeyMapVO<String, String>, SuiteVO> suiteByKey : mapSuites.entrySet()) {

				KeyMapVO<String, String> key = suiteByKey.getKey();
				// Cria outro arquivo se o projeto for diferente do anterior e
				// inicia um novo arquivo.
				boolean existeProjetoAnterior = StringUtils.isNotBlank(nomeProjetoAnterior);
				boolean diferenteDoProjetoAnterior = existeProjetoAnterior
						&& !Objects.equals(key.getKey1(), nomeProjetoAnterior);
				if (existeProjetoAnterior) {
					if (diferenteDoProjetoAnterior) {
						encerrarHTMLAnteriorProjeto(suiteVO, suitePrincipalVO);
						sbReportGeralProjeto = new StringBuilder();
						sbReportFalhaProjeto = new StringBuilder();
						sbReportSucessoProjeto = new StringBuilder();
						String pathProjeto = getPathReportGeralProjeto(key.getKey1(), nomePastaProjetoPrincipal);
						// Deleta os arquivos antigos e mantém os últimos
						// gerados.
						deleteArquivosDatasAntigas(pathProjeto, true);
						// Cria a estrutura de report do projeto
						caminhoFileGeralProjeto = createFilesProjeto(key.getKey1(), nomePastaProjetoPrincipal,
								pathProjeto, date);
						openHTMLProjeto(key.getKey1());
					}
				} else {
					String pathProjeto = getPathReportGeralProjeto(key.getKey1(), nomePastaProjetoPrincipal);
					// Deleta os arquivos antigos e mantém os últimos gerados.
					deleteArquivosDatasAntigas(pathProjeto, true);
					// Cria a estrutura de report do projeto
					caminhoFileGeralProjeto = createFilesProjeto(key.getKey1(), nomePastaProjetoPrincipal, pathProjeto,
							date);
					openHTMLProjeto(key.getKey1());
				}

				suiteVO = suiteByKey.getValue();
				nomeProjetoAnterior = key.getKey1();
				appendCabecalho(suitePrincipalVO, suiteVO, diferenteDoProjetoAnterior, existeProjetoAnterior);
				appendClassesDeTeste(suiteVO, caminhoFileGeral, caminhoFileGeralProjeto, caminhoFileGeralMinimized);

				SuiteSiare.setQuantitativoRunVO(suiteVO.getQuantitativoRunVO(), suitePrincipalVO.getQuantitativoRun());
			}

			StringBuilder sbResultRun = new StringBuilder();
			if (suiteVO.getQuantitativoRunVO().getQuantidadeRun() > 0) {
				appendResultRun(suiteVO.getQuantitativoRunVO(), suiteVO.getDataInicioExecucao(),
						suiteVO.getDataFimExecucao(), sbResultRun, suitePrincipalVO);
			}
			appendReportGeralProjeto(HTML_QUEBRA_LINHA, HTML_OPEN_NEGRITO, TEXTO_FIM_DOS_TESTES,
					DateUtil.getDataFormatadaByFormato(suiteVO.getDataFimExecucao(), DateUtil.FORMATO_DATA6),
					HTML_CLOSE_NEGRITO, HTML_QUEBRA_LINHA);

			escreverArquivosHTMLProjeto(sbResultRun);
		}

		StringBuilder sbResultRun = new StringBuilder();
		if (suitePrincipalVO.getQuantitativoRun().getQuantidadeRun() > 0) {
			appendResultRun(suitePrincipalVO.getQuantitativoRun(), suitePrincipalVO.getDataInicioExecucao(),
					suitePrincipalVO.getDataFimExecucao(), sbResultRun, suitePrincipalVO);
		}
		appendReportGeral(HTML_QUEBRA_LINHA, HTML_OPEN_NEGRITO, TEXTO_FIM_DOS_TESTES,
				DateUtil.getDataFormatadaByFormato(suitePrincipalVO.getDataFimExecucao(), DateUtil.FORMATO_DATA6),
				HTML_CLOSE_NEGRITO, HTML_QUEBRA_LINHA);

		appendReportGeralMinimized(HTML_QUEBRA_LINHA, HTML_OPEN_NEGRITO, TEXTO_FIM_DOS_TESTES,
				DateUtil.getDataFormatadaByFormato(suitePrincipalVO.getDataFimExecucao(), DateUtil.FORMATO_DATA6),
				HTML_CLOSE_NEGRITO, HTML_QUEBRA_LINHA);

		escreverArquivosHTMLGeral(sbResultRun);
	}

	private static void appendCabecalho(SuitePrincipalVO suitePrincipalVO, SuiteVO suiteVO,
			boolean diferenteDoProjetoAnterior, boolean existeProjetoAnterior) {

		boolean escreverNomeProjeto = !existeProjetoAnterior || diferenteDoProjetoAnterior;
		if (escreverNomeProjeto) {
			appendReportGeral(SEPARADOR, HTML_QUEBRA_LINHA);
			appendReportGeral(HTML_OPEN_NEGRITO, TEXTO_PROJETO, HTML_CLOSE_NEGRITO, suiteVO.getNomeProjeto(),
					HTML_QUEBRA_LINHA);
		}
		appendReportGeral(HTML_OPEN_NEGRITO, TEXTO_SUITE, HTML_CLOSE_NEGRITO, suiteVO.getNomeSuite(),
				HTML_QUEBRA_LINHA);

		appendReportGeral(SEPARADOR, HTML_QUEBRA_LINHA);

		appendReportFalha(SEPARADOR, HTML_QUEBRA_LINHA);

		if (escreverNomeProjeto) {
			appendReportFalha(HTML_OPEN_NEGRITO, TEXTO_PROJETO, HTML_CLOSE_NEGRITO, suiteVO.getNomeProjeto(),
					HTML_QUEBRA_LINHA);
		}
		appendReportFalha(HTML_OPEN_NEGRITO, TEXTO_SUITE, HTML_CLOSE_NEGRITO, suiteVO.getNomeSuite(),
				HTML_QUEBRA_LINHA);
		appendReportFalha(SEPARADOR, HTML_QUEBRA_LINHA);

		if (escreverNomeProjeto) {
			appendReportGeralMinimized(SEPARADOR, HTML_QUEBRA_LINHA);
			appendReportGeralMinimized(HTML_OPEN_NEGRITO, TEXTO_PROJETO, HTML_CLOSE_NEGRITO, suiteVO.getNomeProjeto(),
					HTML_QUEBRA_LINHA);
			appendReportGeralMinimized(SEPARADOR, HTML_QUEBRA_LINHA);
		}
		appendReportGeralMinimized(HTML_OPEN_NEGRITO, TEXTO_SUITE, HTML_CLOSE_NEGRITO, suiteVO.getNomeSuite(),
				HTML_QUEBRA_LINHA);

		appendDataInicioTestesProjeto(suiteVO.getDataInicioExecucao());
		appendReportGeralProjeto(HTML_OPEN_NEGRITO, TEXTO_SUITE_PRINCIPAL, HTML_CLOSE_NEGRITO,
				suitePrincipalVO.getNomeSuite(), HTML_QUEBRA_LINHA);
		appendReportGeralProjeto(SEPARADOR, HTML_QUEBRA_LINHA);
		if (escreverNomeProjeto) {
			appendReportGeralProjeto(HTML_OPEN_NEGRITO, TEXTO_PROJETO, HTML_CLOSE_NEGRITO, suiteVO.getNomeProjeto(),
					HTML_QUEBRA_LINHA);
		}
		appendReportGeralProjeto(HTML_OPEN_NEGRITO, TEXTO_SUITE, HTML_CLOSE_NEGRITO, suiteVO.getNomeSuite(),
				HTML_QUEBRA_LINHA);
		appendReportGeralProjeto(SEPARADOR, HTML_QUEBRA_LINHA);

		appendReportFalhaProjeto(HTML_OPEN_NEGRITO, TEXTO_SUITE_PRINCIPAL, HTML_CLOSE_NEGRITO,
				suitePrincipalVO.getNomeSuite(), HTML_QUEBRA_LINHA);
		appendReportFalhaProjeto(SEPARADOR, HTML_QUEBRA_LINHA);
		if (escreverNomeProjeto) {
			appendReportFalhaProjeto(HTML_OPEN_NEGRITO, TEXTO_PROJETO, HTML_CLOSE_NEGRITO, suiteVO.getNomeProjeto(),
					HTML_QUEBRA_LINHA);
		}
		appendReportFalhaProjeto(HTML_OPEN_NEGRITO, TEXTO_SUITE, HTML_CLOSE_NEGRITO, suiteVO.getNomeSuite(),
				HTML_QUEBRA_LINHA);
		appendReportFalhaProjeto(SEPARADOR, HTML_QUEBRA_LINHA);

		appendReportSucessoProjeto(HTML_OPEN_NEGRITO, TEXTO_SUITE_PRINCIPAL, HTML_CLOSE_NEGRITO,
				suitePrincipalVO.getNomeSuite(), HTML_QUEBRA_LINHA);
		appendReportSucessoProjeto(SEPARADOR, HTML_QUEBRA_LINHA);
		if (escreverNomeProjeto) {
			appendReportSucessoProjeto(HTML_OPEN_NEGRITO, TEXTO_PROJETO, HTML_CLOSE_NEGRITO, suiteVO.getNomeProjeto(),
					HTML_QUEBRA_LINHA);
		}
		appendReportSucessoProjeto(HTML_OPEN_NEGRITO, TEXTO_SUITE, HTML_CLOSE_NEGRITO, suiteVO.getNomeSuite(),
				HTML_QUEBRA_LINHA);
		appendReportSucessoProjeto(SEPARADOR, HTML_QUEBRA_LINHA);
	}

	private static void encerrarHTMLAnteriorProjeto(SuiteVO suiteVO, SuitePrincipalVO suitePrincipalVO)
			throws IOException {
		StringBuilder sbResultRun = new StringBuilder();
		appendResultRun(suiteVO.getQuantitativoRunVO(), suiteVO.getDataInicioExecucao(), suiteVO.getDataFimExecucao(),
				sbResultRun, suitePrincipalVO);
		appendReportGeralProjeto(HTML_QUEBRA_LINHA, HTML_OPEN_NEGRITO, TEXTO_FIM_DOS_TESTES,
				DateUtil.getDataFormatadaByFormato(suiteVO.getDataFimExecucao(), DateUtil.FORMATO_DATA6),
				HTML_CLOSE_NEGRITO, HTML_QUEBRA_LINHA);
		escreverArquivosHTMLProjeto(sbResultRun);
	}

	public static void appendClassesDeTeste(SuiteVO suiteVO, String[] caminhoFileGeral,
			String[] caminhoFileGeralProjeto, String[] caminhoFileGeralMinimized) throws IOException {
		Map<String, ClasseDeTesteVO> classesDeTesteByName = suiteVO.getClassesDeTesteByName();
		if (MapUtils.isNotEmpty(classesDeTesteByName)) {
			String classeAnterior = "";
			for (Entry<String, ClasseDeTesteVO> ct : classesDeTesteByName.entrySet()) {
				classeAnterior = treathClasseDeTeste(classeAnterior, ct, caminhoFileGeral, caminhoFileGeralProjeto,
						caminhoFileGeralMinimized);
			}
		}
	}

	private static void appendDataInicioTestesProjeto(Date dataInicio) {
		appendReportGeralProjeto(HTML_OPEN_NEGRITO, TEXTO_INICIO_DOS_TESTES,
				DateUtil.getDataFormatadaByFormato(dataInicio, DateUtil.FORMATO_DATA6), HTML_CLOSE_NEGRITO,
				HTML_QUEBRA_LINHA);
	}

	private static String treathClasseDeTeste(String classeAnterior, Entry<String, ClasseDeTesteVO> ct,
			String[] caminhoFileGeral, String[] caminhoFileGeralProjeto, String[] caminhoFileGeralMinimized)
			throws IOException {
		ClasseDeTesteVO classeDeTeste = ct.getValue();
		List<MetodoClasseTesteVO> metodos = classeDeTeste.getMetodos();
		boolean[] isNewClass = { false };
		Double totalExecucaoMetodos = null;
		// Só entra nesse local uma vez a cada nome de classe diferente
		if (classeAnterior == null || !Objects.equals(classeAnterior, classeDeTeste.getNomeClasse())) {
			isNewClass[0] = true;
			classeAnterior = classeDeTeste.getNomeClasse();

			totalExecucaoMetodos = metodos.stream().map(item -> item.getDuracaoSeconds())
					.mapToDouble(BigDecimal::doubleValue).sum();

			appendReportGeral(HTML_QUEBRA_LINHA, HTML_OPEN_NEGRITO, TEXTO_CLASSE, HTML_CLOSE_NEGRITO,
					classeDeTeste.getNomeClasse(), "(", getValorArredondado(totalExecucaoMetodos), " s)",
					HTML_QUEBRA_LINHA);

			appendReportGeralProjeto(HTML_QUEBRA_LINHA, HTML_OPEN_NEGRITO, TEXTO_CLASSE, HTML_CLOSE_NEGRITO,
					classeDeTeste.getNomeClasse(), "(", getValorArredondado(totalExecucaoMetodos), " s)",
					HTML_QUEBRA_LINHA);
		}
		if (CollectionUtils.isNotEmpty(metodos)) {
			for (MetodoClasseTesteVO metodo : metodos) {
				treathMetodos(classeDeTeste, metodo, caminhoFileGeral, caminhoFileGeralProjeto,
						caminhoFileGeralMinimized, isNewClass, totalExecucaoMetodos);
			}
		}
		return classeAnterior;
	}

	private static String getValorArredondado(double totalExecucaoMetodos) {
		BigDecimal valorArredondado = BigDecimal.ZERO.equals(BigDecimal.valueOf(totalExecucaoMetodos)) ? BigDecimal.ZERO
				: arredondaValor(totalExecucaoMetodos, 3);
		return valorArredondado.toString();
	}

	private static void treathMetodos(ClasseDeTesteVO classeDeTeste, MetodoClasseTesteVO metodo,
			String[] caminhoFileGeral, String[] caminhoFileGeralProjeto, String[] caminhoFileGeralMinimized,
			boolean[] isNewClass, Double totalExecucaoMetodos) throws IOException {
		String openFontSuccess = openFont(HTML_COLOR_SUCCESS, "", "");
		String openFontErro = openFont(HTML_COLOR_ERRO, "", "");
		String openFontFalha = openFont(HTML_COLOR_FAILED, "", "");
		String openFontSkiped = openFont(HTML_COLOR_SKIPED, "", "");
		if (metodo.isFalha()) {
			treathFalhaAndErro(classeDeTeste, metodo, openFontFalha, FAILED, caminhoFileGeral, caminhoFileGeralProjeto,
					caminhoFileGeralMinimized, isNewClass, totalExecucaoMetodos);
		} else if (metodo.isErro()) {
			treathFalhaAndErro(classeDeTeste, metodo, openFontErro, ERRO, caminhoFileGeral, caminhoFileGeralProjeto,
					caminhoFileGeralMinimized, isNewClass, totalExecucaoMetodos);
		} else if (metodo.isSucess()) {
			appendInformacoesMetodo(metodo, openFontSuccess, SUCCESS);
		} else if (metodo.isSkiped()) {
			appendInformacoesMetodo(metodo, openFontSkiped, SKIPPED);
		}
		appendPilhaDeErro(metodo, caminhoFileGeral, caminhoFileGeralProjeto);
	}

	private static void treathFalhaAndErro(ClasseDeTesteVO classeDeTeste, MetodoClasseTesteVO metodo, String openFont,
			String tipoMetodo, String[] caminhoFileGeral, String[] caminhoFileGeralProjeto,
			String[] caminhoFileGeralMinimized, boolean[] isNewClass, Double totalExecucaoMetodos) throws IOException {
		appendInformacoesMetodo(metodo, openFont, tipoMetodo);
		boolean existException = CollectionUtils.isNotEmpty(metodo.getExceptions());
		if (existException) {
			for (ExceptionVO exception : metodo.getExceptions()) {
				appendException(exception, openFont);
				appendExceptionProjeto(exception, openFont);
				appendExceptionMinimized(classeDeTeste, metodo, exception, openFont, isNewClass, totalExecucaoMetodos);
			}
		}
		
		appendLinkProvaException(metodo, caminhoFileGeral, caminhoFileGeralProjeto, caminhoFileGeralMinimized);

		if (existException) {
			appendReportGeralMinimized(HTML_CLOSE_DETAILS, HTML_QUEBRA_LINHA);
		}
	}

	private static void appendPilhaDeErro(MetodoClasseTesteVO metodo, String[] caminhoFileGeral,
			String[] caminhoFileGeralProjeto) throws IOException {

		String href = "<a href='file:///";

		String destinoPrintPilhaErro = "";
		String caminhoPrintPilhaErro = metodo.getCaminhoPrintPilhaErro();
		String tituloPrintPilhaErro = "'>Print Pilha de Erro</a>";

		String destinoArquivoHTMLPilhaErro = "";
		String caminhoOrigemArquivoHTMLPilhaErro = metodo.getCaminhoArquivoHTMLPilhaErro();
		String tituloPilhaHTMLErro = "'>Pilha de Erro HTML</a>";

		String destinoArquivoTxtPilhaErro = "";
		String caminhoOrigemArquivoTXTPilhaErro = metodo.getCaminhoArquivoTXTPilhaErro();
		String tituloPilhaTxtErro = "'>Pilha de Erro TXT</a>";

		// link dados pilha erro aplicação
		String caminhoReportGeral = caminhoFileGeral[0];
		if (StringUtils.isNotBlank(caminhoOrigemArquivoHTMLPilhaErro)) {
			// html
			destinoArquivoHTMLPilhaErro = copyArquivoParaPastaDoReport(caminhoOrigemArquivoHTMLPilhaErro,
					caminhoReportGeral);
			sbReportGeral.append(href).append(destinoArquivoHTMLPilhaErro).append(tituloPilhaHTMLErro)
					.append(HTML_QUEBRA_LINHA);
			sbReportGeralMinimized.append(href).append(destinoArquivoHTMLPilhaErro).append(tituloPilhaHTMLErro)
					.append(HTML_QUEBRA_LINHA);
			// txt
			destinoArquivoTxtPilhaErro = copyArquivoParaPastaDoReport(caminhoOrigemArquivoTXTPilhaErro,
					caminhoReportGeral);
			sbReportGeral.append(href).append(destinoArquivoTxtPilhaErro).append(tituloPilhaTxtErro)
					.append(HTML_QUEBRA_LINHA);
			sbReportGeralMinimized.append(href).append(destinoArquivoTxtPilhaErro).append(tituloPilhaTxtErro)
					.append(HTML_QUEBRA_LINHA);

			// print
			destinoPrintPilhaErro = copyArquivoParaPastaDoReport(caminhoPrintPilhaErro, caminhoReportGeral);
			sbReportGeral.append(href).append(destinoPrintPilhaErro).append(tituloPrintPilhaErro)
					.append(HTML_QUEBRA_LINHA);
			sbReportGeralMinimized.append(href).append(destinoPrintPilhaErro).append(tituloPrintPilhaErro)
					.append(HTML_QUEBRA_LINHA);

		}

		// link dados pilha erro aplicação
		String caminhoReportFalha = caminhoFileGeral[1];
		if (StringUtils.isNotBlank(caminhoOrigemArquivoHTMLPilhaErro)) {
			// html
			destinoArquivoHTMLPilhaErro = copyArquivoParaPastaDoReport(caminhoOrigemArquivoHTMLPilhaErro,
					caminhoReportFalha);
			sbReportFalha.append(href).append(destinoArquivoHTMLPilhaErro).append(tituloPilhaHTMLErro)
					.append(HTML_QUEBRA_LINHA);
			// txt
			destinoArquivoTxtPilhaErro = copyArquivoParaPastaDoReport(caminhoOrigemArquivoTXTPilhaErro,
					caminhoReportGeral);
			sbReportFalha.append(href).append(destinoArquivoTxtPilhaErro).append(tituloPilhaTxtErro)
					.append(HTML_QUEBRA_LINHA);

			// print
			destinoPrintPilhaErro = copyArquivoParaPastaDoReport(caminhoPrintPilhaErro, caminhoReportFalha);
			sbReportFalha.append(href).append(destinoPrintPilhaErro).append(tituloPrintPilhaErro)
					.append(HTML_QUEBRA_LINHA);
		}

		// link dados pilha erro aplicação
		String caminhoReportGeralProjeto = caminhoFileGeralProjeto[0];
		if (StringUtils.isNotBlank(caminhoOrigemArquivoHTMLPilhaErro)) {
			// html
			destinoArquivoHTMLPilhaErro = copyArquivoParaPastaDoReport(caminhoOrigemArquivoHTMLPilhaErro,
					caminhoReportGeralProjeto);
			sbReportGeralProjeto.append(href).append(destinoArquivoHTMLPilhaErro).append(tituloPilhaHTMLErro)
					.append(HTML_QUEBRA_LINHA);
			// txt
			destinoArquivoTxtPilhaErro = copyArquivoParaPastaDoReport(caminhoOrigemArquivoTXTPilhaErro,
					caminhoReportGeral);
			sbReportGeralProjeto.append(href).append(destinoArquivoTxtPilhaErro).append(tituloPilhaTxtErro)
					.append(HTML_QUEBRA_LINHA);

			// print
			destinoPrintPilhaErro = copyArquivoParaPastaDoReport(caminhoPrintPilhaErro, caminhoReportGeralProjeto);
			sbReportGeralProjeto.append(href).append(destinoPrintPilhaErro).append(tituloPrintPilhaErro)
					.append(HTML_QUEBRA_LINHA);
		}

		// link dados pilha erro aplicação
		String caminhoReportFalhaProjeto = caminhoFileGeralProjeto[1];
		if (StringUtils.isNotBlank(caminhoOrigemArquivoHTMLPilhaErro)) {
			// html
			destinoArquivoHTMLPilhaErro = copyArquivoParaPastaDoReport(caminhoOrigemArquivoHTMLPilhaErro,
					caminhoReportFalhaProjeto);
			sbReportFalhaProjeto.append(href).append(destinoArquivoHTMLPilhaErro).append(tituloPilhaHTMLErro)
					.append(HTML_QUEBRA_LINHA);

			// txt
			destinoArquivoTxtPilhaErro = copyArquivoParaPastaDoReport(caminhoOrigemArquivoTXTPilhaErro,
					caminhoReportGeral);
			sbReportFalhaProjeto.append(href).append(destinoArquivoTxtPilhaErro).append(tituloPilhaTxtErro)
					.append(HTML_QUEBRA_LINHA);

			// print
			destinoPrintPilhaErro = copyArquivoParaPastaDoReport(caminhoPrintPilhaErro, caminhoReportFalhaProjeto);
			sbReportFalhaProjeto.append(href).append(destinoPrintPilhaErro).append(tituloPrintPilhaErro)
					.append(HTML_QUEBRA_LINHA);
		}
	}

	private static void appendInformacoesMetodo(MetodoClasseTesteVO metodo, String openFont, String tipoOcorrencia) {
		String nomeMetodo = metodo.getNome();
		String textoTempoTotalExecucaoEmSegundos = metodo.getTextoTempoTotalExecucaoEmSegundos();

		appendReportGeral(openFont, HTML_OPEN_SPAN, TEXTO_METODO, nomeMetodo, textoTempoTotalExecucaoEmSegundos,
				HTML_CLOSE_SPAN, HTML_CLOSE_FONT, HTML_QUEBRA_LINHA);

		appendReportGeralProjeto(openFont, HTML_OPEN_SPAN, TEXTO_METODO, nomeMetodo, textoTempoTotalExecucaoEmSegundos,
				HTML_CLOSE_SPAN, HTML_CLOSE_FONT, HTML_QUEBRA_LINHA);

		if (SUCCESS.equals(tipoOcorrencia)) {
			countSucessoSuites += 1;
			appendReportSucessoProjeto(openFont, HTML_OPEN_SPAN, TEXTO_METODO, nomeMetodo,
					textoTempoTotalExecucaoEmSegundos, HTML_CLOSE_SPAN, HTML_CLOSE_FONT, HTML_QUEBRA_LINHA);
		} else if (FAILED.equals(tipoOcorrencia) || ERRO.equals(tipoOcorrencia)) {
			countFalhasSuites += 1;
			appendReportFalha(openFont, HTML_OPEN_SPAN, TEXTO_METODO, nomeMetodo, textoTempoTotalExecucaoEmSegundos,
					HTML_CLOSE_SPAN, HTML_CLOSE_FONT, HTML_QUEBRA_LINHA);
			appendReportFalhaProjeto(openFont, HTML_OPEN_SPAN, TEXTO_METODO, nomeMetodo,
					textoTempoTotalExecucaoEmSegundos, HTML_CLOSE_SPAN, HTML_CLOSE_FONT, HTML_QUEBRA_LINHA);
		}
	}

	public static void appendResultRun(QuantitativoRunVO quantitativoVO, Date dataInicio, Date dataFim,
			StringBuilder sb, SuitePrincipalVO suitePrincipalVO) {

		String simboloPercentual = "% ";
		int quantidadeRun = quantitativoVO.getQuantidadeRun();
		int quantidadeFalha = quantitativoVO.getQuantidadeFalha();
		int quantidadeErro = quantitativoVO.getQuantidadeErro();
		int quantidadeSucesso = quantitativoVO.getQuantidadeSucesso();
		int quantidadeSkipped = quantitativoVO.getQuantidadeSkiped();

		StringBuilder sbTitulo = new StringBuilder();
		sbTitulo.append("<span style='font: 1em Arial, Helvetica, sans-serif; color:").append(HTML_COLOR_BLACK)
				.append("'>Total testes: ");

		StringBuilder sbCountFailed = new StringBuilder();
		sbCountFailed.append(FALHAS).append(": ").append(quantidadeFalha);

		StringBuilder sbCountErro = new StringBuilder();
		sbCountErro.append(ERROS).append(": ").append(quantidadeErro);

		StringBuilder sbCountSkipped = new StringBuilder();
		if (quantidadeSkipped > 0) {
			sbCountSkipped.append(IGNORADOS).append(": ").append(quantidadeSkipped);
		}

		StringBuilder sbPercentualSuccess = new StringBuilder();
		sbPercentualSuccess.append(SUCESSOS).append(": ")
				.append(arredondaValor((Double.valueOf(quantidadeSucesso) / Double.valueOf(quantidadeRun)) * 100, 2))
				.append(simboloPercentual);

		StringBuilder sbPercentualFailed = new StringBuilder();
		sbPercentualFailed.append(FALHAS).append(": ").append(getTotal(quantidadeRun, quantidadeFalha))
				.append(simboloPercentual);

		StringBuilder sbPercentualErro = new StringBuilder();
		sbPercentualErro.append(ERROS).append(": ").append(getTotal(quantidadeRun, quantidadeErro))
				.append(simboloPercentual);
		StringBuilder sbPercentualSkipped = new StringBuilder();
		if (quantidadeSkipped > 0) {
			sbPercentualErro.append(IGNORADOS).append(": ").append(getTotal(quantidadeRun, quantidadeSkipped))
					.append(simboloPercentual);
		}

		StringBuilder sbResultCount = new StringBuilder();
		sbResultCount.append(sbTitulo.toString()).append(HTML_OPEN_P).append("  Quantidade ").append(HTML_QUEBRA_LINHA)
				.append(HTML_OPEN_SPAN).append("Executados: ").append(quantidadeRun).append(HTML_CLOSE_SPAN)
				.append(HTML_QUEBRA_LINHA);

		// Quantidade ignorado só exibe se houve algum skipped
		if (quantidadeSkipped > 0) {
			sbResultCount.append(HTML_OPEN_SPAN).append(sbCountSkipped.toString()).append(HTML_CLOSE_SPAN);
		}

		sbResultCount.append(HTML_OPEN_SPAN).append(sbCountFailed.toString()).append(HTML_CLOSE_SPAN)
				.append(HTML_QUEBRA_LINHA).append(HTML_OPEN_SPAN).append(sbCountErro.toString())
				.append(HTML_CLOSE_SPAN);

		StringBuilder sbResultPercentual = new StringBuilder();
		sbResultPercentual.append(HTML_OPEN_P).append("  Percentual: ").append(HTML_QUEBRA_LINHA)
				.append(HTML_OPEN_SPAN);

		// Quantidade ignorado só exibe se houve algum skipped
		if (quantidadeSkipped > 0) {
			sbResultPercentual.append(sbPercentualSkipped.toString()).append(HTML_CLOSE_SPAN).append(HTML_QUEBRA_LINHA);
		}

		sbResultPercentual.append(sbPercentualSuccess.toString()).append(HTML_CLOSE_SPAN).append(HTML_QUEBRA_LINHA)
				.append(HTML_OPEN_SPAN).append(sbPercentualFailed.toString()).append(HTML_CLOSE_SPAN)
				.append(HTML_QUEBRA_LINHA).append(HTML_OPEN_SPAN).append(sbPercentualErro.toString())
				.append(HTML_CLOSE_SPAN);

		String duracaoTestes = getTotalDuracaoTestes(dataFim, dataInicio);

		sb.append(HTML_QUEBRA_LINHA);
		sb.append(SEPARATOR_RESULT_FINAL);

		appendQuantitativoProjetoAndSuite(sb, suitePrincipalVO);

		sb.append(HTML_OPEN_PRE).append(sbResultCount.toString()).append(HTML_CLOSE_PRE).append(HTML_CLOSE_P);
		sb.append(HTML_OPEN_PRE).append(sbResultPercentual.toString()).append(HTML_CLOSE_PRE).append(HTML_CLOSE_P);
		sb.append(HTML_OPEN_PRE).append("  Tempo total execução dos testes: ").append(duracaoTestes)
				.append(HTML_CLOSE_PRE).append(HTML_CLOSE_P);
		sb.append(SEPARATOR_RESULT_FINAL);
	}

	private static void appendQuantitativoProjetoAndSuite(StringBuilder sb, SuitePrincipalVO suitePrincipalVO) {
		Map<String, Set<String>> suitesByProjeto = new LinkedHashMap<String, Set<String>>();

		for (Entry<KeyMapVO<String, String>, SuiteVO> suiteByKey : suitePrincipalVO.getSuitesFilhasByNome()
				.entrySet()) {

			String projeto = suiteByKey.getKey().getKey1();
			String suite = suiteByKey.getValue().getNomeSuite();
			if (projeto.contains("\\")) {
				String projetoComSuite = projeto;
				int firtIndexOf = projetoComSuite.lastIndexOf("\\");
				String nomeProjeto = projetoComSuite.substring(0, firtIndexOf);
				projeto = nomeProjeto;
			}

			if (suitesByProjeto.get(projeto) == null) {
				suitesByProjeto.put(projeto, new HashSet<String>());
			}
			suitesByProjeto.get(projeto).add(suite);
		}

		sb.append(HTML_QUEBRA_LINHA).append(HTML_OPEN_PRE).append("Quantidade de Projetos Executados: ")
				.append(suitesByProjeto.size());
		sb.append("<ol>");
		for (Entry<String, Set<String>> suiteByProjeto : suitesByProjeto.entrySet()) {
			sb.append("<li>").append(suiteByProjeto.getKey()).append("</li>");
			sb.append("<ol>");
			for (String suite : suiteByProjeto.getValue()) {
				sb.append("<li>").append(suite).append("</li>");
			}
			sb.append("</ol>");
		}
		sb.append("</ol>").append(HTML_CLOSE_PRE);
	}

	/**
	 * Cria arquivos Geral.html e Falha.html na pasta "Report" e abre a tag html
	 * nos dois arquivos criados.
	 * 
	 * @throws IOException
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 10:02:58
	 *
	 */
	private static void openHTMLGeral() throws IOException {
		appendReportGeral(HTML_OPEN_HTML);
		appendReportFalha(HTML_OPEN_HTML);
	}

	/**
	 * Cria arquivos Geral.html e Falha.html na pasta "Report" e abre a tag html
	 * nos dois arquivos criados.
	 * 
	 * @throws IOException
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 10:02:58
	 *
	 */
	private static void openHTMLGeralMinimized() throws IOException {
		appendReportGeralMinimized("<!DOCTYPE html>");
		appendReportGeralMinimized("<html><head><meta charset=\"utf-8\">");
		appendReportGeralMinimized(
				"<!--[if lt IE 9]><script src=\"http://html5shiv.googlecode.com/svn/trunk/html5.js\"></script><![endif]-->");
		appendReportGeralMinimized("<style type=\"text/css\">");
		appendReportGeralMinimized(
				"details summary {cursor: pointer; font: bold 1em Arial, Helvetica, sans-serif; padding: 8px 0; position: relative; width: 100%; }");
		appendReportGeralMinimized("details summary::-webkit-details-marker {display: none}");
		appendReportGeralMinimized(
				"details summary:before{border-radius: 5px; content: ; color: #000; float: left; font-size: 1.3em; font-weight: bold; margin: -4px 10px 0 0; padding: 0; text-align: center; width: 10px;}");
		appendReportGeralMinimized("details summary::before { content:'+'}");
		appendReportGeralMinimized("details[open] summary::before { content:'-' }");
		appendReportGeralMinimized("</style>");
		appendReportGeralMinimized("</head><body>");
		appendReportGeralMinimized(
				"<script src='https://ajax.googleapis.com/ajax/libs/jquery/1.7.0/jquery.min.js'></script>");
		appendReportGeralMinimized(getJQueryDetailsIE());
	}

	public static String getJQueryDetailsIE() {
		StringBuilder sb = new StringBuilder();

		sb.append("<script >");
		sb.append("		");
		sb.append("/*! http://mths.be/details v0.0.6 by @mathias | includes http://mths.be/noselect v1.0.3 */");
		sb.append(";(function(document, $) {");
		sb.append("	var proto = $.fn,");
		sb.append("	    details,");
		sb.append("	    isOpera = Object.prototype.toString.call(window.opera) == '[object Opera]',");
		sb.append("	    isDetailsSupported = (function(doc) {");
		sb.append("	    	var el = doc.createElement('details'),");
		sb.append("	    	    fake,");
		sb.append("	    	    root,");
		sb.append("	    	    diff;");
		sb.append("	    	if (!('open' in el)) {");
		sb.append("	    		return false;");
		sb.append("	    	}");
		sb.append("	    	root = doc.body || (function() {");
		sb.append("	    		var de = doc.documentElement;");
		sb.append("	    		fake = true;");
		sb.append(
				"	    		return de.insertBefore(doc.createElement('body'), de.firstElementChild || de.firstChild);");
		sb.append("	    	}());");
		sb.append("	    	el.innerHTML = '<summary>a</summary>b';");
		sb.append("	    	el.style.display = 'block';");
		sb.append("	    	root.appendChild(el);");
		sb.append("	    	diff = el.offsetHeight;");
		sb.append("	    	el.open = true;");
		sb.append("	    	diff = diff != el.offsetHeight;");
		sb.append("	    	root.removeChild(el);");
		sb.append("	    	if (fake) {");
		sb.append("	    		root.parentNode.removeChild(root);");
		sb.append("	    	}");
		sb.append("	    	return diff;");
		sb.append("	    }(document)),");
		sb.append("	    toggleOpen = function($details, $detailsSummary, $detailsNotSummary, toggle) {");
		sb.append("	    	var isOpen = typeof $details.attr('open') == 'string',");
		sb.append("	    	    close = isOpen && toggle || !isOpen && !toggle;");
		sb.append("	    	if (close) {");
		sb.append("	    		$details.removeClass('open').prop('open', false).triggerHandler('close.details');");
		sb.append("	    		$detailsSummary.attr('aria-expanded', false);");
		sb.append("	    		$detailsNotSummary.hide();");
		sb.append("	    	} else {");
		sb.append("	    		$details.addClass('open').prop('open', true).triggerHandler('open.details');");
		sb.append("	    		$detailsSummary.attr('aria-expanded', true);");
		sb.append("	    		$detailsNotSummary.show();");
		sb.append("	    	}");
		sb.append("	    };");
		sb.append("	proto.noSelect = function() {");
		sb.append("		var none = 'none';");
		sb.append("		return this.bind('selectstart dragstart mousedown', function() {");
		sb.append("			return false;");
		sb.append("		}).css({");
		sb.append("			'MozUserSelect': none,");
		sb.append("			'msUserSelect': none,");
		sb.append("			'webkitUserSelect': none,");
		sb.append("			'userSelect': none");
		sb.append("		});");
		sb.append("	};");
		sb.append("	if (isDetailsSupported) {");
		sb.append("		details = proto.details = function() {");
		sb.append("			return this.each(function() {");
		sb.append("				var $details = $(this),");
		sb.append("				    $summary = $('summary', $details).first();");
		sb.append("				$summary.attr({");
		sb.append("					'role': 'button',");
		sb.append("					'aria-expanded': $details.prop('open')");
		sb.append("				}).on('click', function() {");
		sb.append("					var close = $details.prop('open');");
		sb.append("					$summary.attr('aria-expanded', !close);");
		sb.append("					$details.triggerHandler((close ? 'close' : 'open') + '.details');");
		sb.append("				});");
		sb.append("			});");
		sb.append("		};");
		sb.append("		details.support = isDetailsSupported;");
		sb.append("	} else {");
		sb.append("		details = proto.details = function() {");
		sb.append("			return this.each(function() {");
		sb.append("				var $details = $(this),");
		sb.append("				    $detailsSummary = $('summary', $details).first(),");
		sb.append("				    $detailsNotSummary = $details.children(':not(summary)'),");
		sb.append("				    $detailsNotSummaryContents = $details.contents(':not(summary)');");
		sb.append("				if (!$detailsSummary.length) {");
		sb.append("					$detailsSummary = $('<summary>').text('Details').prependTo($details);");
		sb.append("				}");
		sb.append("				if ($detailsNotSummary.length != $detailsNotSummaryContents.length) {");
		sb.append("					$detailsNotSummaryContents.filter(function() {");
		sb.append("						return this.nodeType == 3 && /[^ \\t\\n\\f\\r]/.test(this.data);");
		sb.append("					}).wrap('<span>');");
		sb.append("");
		sb.append("					$detailsNotSummary = $details.children(':not(summary)');");
		sb.append("				}");
		sb.append("				toggleOpen($details, $detailsSummary, $detailsNotSummary);");
		sb.append(
				"				$detailsSummary.attr('role', 'button').noSelect().prop('tabIndex', 0).on('click', function() {");
		sb.append("					$detailsSummary.focus();");
		sb.append("					toggleOpen($details, $detailsSummary, $detailsNotSummary, true);");
		sb.append("				}).keyup(function(event) {");
		sb.append("					if (32 == event.keyCode || (13 == event.keyCode && !isOpera)) {");
		sb.append("");
		sb.append("						event.preventDefault();");
		sb.append("						$detailsSummary.click();");
		sb.append("					}");
		sb.append("				});");
		sb.append("			});");
		sb.append("		};");
		sb.append("		details.support = isDetailsSupported;");
		sb.append("	}");
		sb.append("}(document, jQuery));");
		sb.append("		</script>");
		sb.append("		<script>");
		sb.append("			$(function() {");
		sb.append("				$('html').addClass($.fn.details.support ? 'details' : 'no-details');");
		sb.append("				$('details').details();");
		sb.append("			});");
		sb.append("		</script>");
		return sb.toString();
	}

	/**
	 * Cria arquivos GeralProjeto.html, FalhaProjeto.html e SucessoProjeto.html
	 * na pasta "<<Nome do Projeto>>" dentro de "Report" e abre a tag html nos
	 * arquivos criados.
	 * 
	 * @param key
	 * @throws IOException
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 16:58:11
	 *
	 */
	private static void openHTMLProjeto(String nomeProjeto) throws IOException {
		appendReportGeralProjeto(HTML_OPEN_HTML);
		appendReportFalhaProjeto(HTML_OPEN_HTML);
		appendReportSucessoProjeto(HTML_OPEN_HTML);
	}

	/**
	 * Passar vazio no parâmetro correspondente a tag que não deve aparecer na
	 * configuração da font. <br/>
	 * <b>Ex.: </b>openFont("red", "", ""), a única tag informada é cor, então a
	 * fonte só receberá configuração de cor.
	 *
	 * @param colorFonte
	 * @param sizeFonte
	 * @param typeFonte
	 * @return
	 */
	private static String openFont(String colorFonte, String sizeFonte, String typeFonte) {
		StringBuilder sbFont = new StringBuilder("<font face='Arial'");

		if (StringUtils.isNotBlank(colorFonte)) {
			sbFont.append(" color = '").append(colorFonte).append("'");
		}

		if (StringUtils.isNotBlank(sizeFonte)) {
			sbFont.append(" size = '").append(sizeFonte).append("'");
		}

		if (StringUtils.isNotBlank(typeFonte)) {
			sbFont.append(" face = '").append(typeFonte).append("'");
		}
		sbFont.append(">");

		return sbFont.toString();
	}

	private static BigDecimal getTotal(int totalTestesExecutados, int value) {
		return BigDecimal.ZERO.equals(BigDecimal.valueOf(totalTestesExecutados)) ? BigDecimal.ZERO
				: arredondaValor(Double.valueOf(100 * value) / totalTestesExecutados, 2);
	}

	private static BigDecimal arredondaValor(double valor, int casasDecimais) {
		return new BigDecimal(valor).setScale(casasDecimais, RoundingMode.HALF_UP);
	}

	private static String getTotalDuracaoTestes(Date dateFimTestes, Date dateInicioTestes) {
		Calendar cDateFim = Calendar.getInstance();
		cDateFim.setTime(dateFimTestes);

		Calendar cDateInicio = Calendar.getInstance();
		cDateInicio.setTime(dateInicioTestes);

		long diferenca = (cDateFim.getTimeInMillis() - cDateInicio.getTimeInMillis());

		long diferencaSegundos = (diferenca / 1000) % 60;
		long diferencaMinutos = (diferenca / (60 * 1000)) % 60;
		long diferencaHoras = (diferenca / (60 * 60 * 1000)) % 60;
		long diferencaDias = (diferenca / (60 * 60 * 1000) / 24);
		StringBuilder sbResult = new StringBuilder();
		sbResult.append(HTML_QUEBRA_LINHA).append(HTML_OPEN_SPAN).append(" Dias: ").append(diferencaDias)
				.append(HTML_CLOSE_SPAN).append(HTML_QUEBRA_LINHA).append(HTML_OPEN_SPAN).append(" Horas: ")
				.append(diferencaHoras).append(HTML_CLOSE_SPAN).append(HTML_QUEBRA_LINHA).append(HTML_OPEN_SPAN)
				.append(" Minutos: ").append(diferencaMinutos).append(HTML_CLOSE_SPAN).append(HTML_QUEBRA_LINHA)
				.append(HTML_OPEN_SPAN).append(" Segundos:").append(diferencaSegundos).append(HTML_CLOSE_SPAN);
		return sbResult.toString();
	}

	public static void appendReportGeral(String... texto) {
		for (String conteudo : texto) {
			sbReportGeral.append(conteudo);
		}
	}

	public static void appendReportGeralMinimized(String... texto) {
		for (String conteudo : texto) {
			sbReportGeralMinimized.append(conteudo);
		}
	}

	public static void appendReportFalha(String... texto) {
		for (String conteudo : texto) {
			sbReportFalha.append(conteudo);
		}
	}

	public static void appendReportGeralProjeto(String... texto) {
		for (String conteudo : texto) {
			sbReportGeralProjeto.append(conteudo);
		}
	}

	public static void appendReportFalhaProjeto(String... texto) {
		for (String conteudo : texto) {
			sbReportFalhaProjeto.append(conteudo);
		}
	}

	public static void appendReportSucessoProjeto(String... texto) {
		for (String conteudo : texto) {
			sbReportSucessoProjeto.append(conteudo);
		}
	}

	public static void appendExceptionMinimized(ClasseDeTesteVO classeDeTeste, MetodoClasseTesteVO metodo,
			ExceptionVO exception, String openFont, boolean[] isNewClass, Double totalExecucaoMetodos) {

		if (isNewClass[0]) {
			appendReportGeralMinimized(HTML_QUEBRA_LINHA, HTML_OPEN_NEGRITO, TEXTO_CLASSE, HTML_CLOSE_NEGRITO,
					classeDeTeste.getNomeClasse(), "(", getValorArredondado(totalExecucaoMetodos), " s)",
					HTML_QUEBRA_LINHA);
			isNewClass[0] = false;
		}
		appendReportGeralMinimized(HTML_OPEN_DETAILS, HTML_OPEN_SUMMARY, openFont, HTML_OPEN_NEGRITO, TEXTO_METODO,
				HTML_CLOSE_NEGRITO, metodo.getNome(), metodo.getTextoTempoTotalExecucaoEmSegundos(), HTML_CLOSE_FONT,
				HTML_CLOSE_SUMMARY);

		List<StackTraceElementVO> stacksTraceVO = exception.getStacksTraceVO();
		if (CollectionUtils.isNotEmpty(stacksTraceVO)) {
			for (StackTraceElementVO stackTraceElement : stacksTraceVO) {
				String methodName = stackTraceElement.getMethodName();
				String className = stackTraceElement.getClassName();
				String fileName = stackTraceElement.getFileName();
				String numeroLinha = stackTraceElement.getNumeroLinhaString();
				String openFontBlue = openFont(HTML_COLOR_BLUE, "", "");
				appendReportGeralMinimized(openFont, HTML_QUEBRA_LINHA, HTML_OPEN_SPAN, className, ".", methodName, "(",
						openFontBlue, fileName, ":", numeroLinha, HTML_CLOSE_FONT, ")", HTML_CLOSE_SPAN,
						HTML_CLOSE_FONT);

			}
		}

		if (StringUtils.isNotBlank(exception.getMessage())) {
			appendReportGeralMinimized(openFont, HTML_QUEBRA_LINHA, exception.getMessage(), HTML_CLOSE_FONT,
					HTML_QUEBRA_LINHA);
		}
	}

	public static void appendExceptionProjeto(ExceptionVO exception, String openFont) {
		String closeFont = HTML_CLOSE_FONT;

		String textoException = "#### Exception: ";

		appendReportGeralProjeto(openFont, HTML_QUEBRA_LINHA, textoException, closeFont, HTML_QUEBRA_LINHA);
		appendReportFalhaProjeto(openFont, HTML_QUEBRA_LINHA, textoException, closeFont, HTML_QUEBRA_LINHA);

		List<StackTraceElementVO> stacksTraceVO = exception.getStacksTraceVO();
		if (CollectionUtils.isNotEmpty(stacksTraceVO)) {
			for (StackTraceElementVO stackTraceElement : stacksTraceVO) {
				String methodName = stackTraceElement.getMethodName();
				String className = stackTraceElement.getClassName();
				String fileName = stackTraceElement.getFileName();
				String numeroLinha = stackTraceElement.getNumeroLinhaString();
				String openFontBlue = openFont(HTML_COLOR_BLUE, "", "");

				appendReportGeralProjeto(openFont, HTML_QUEBRA_LINHA, HTML_OPEN_SPAN, className, ".", methodName, "(",
						openFontBlue, fileName, ":", numeroLinha, closeFont, ")", HTML_CLOSE_SPAN, closeFont);
				appendReportFalhaProjeto(openFont, HTML_QUEBRA_LINHA, HTML_OPEN_SPAN, className, ".", methodName, "(",
						openFontBlue, fileName, ":", numeroLinha, closeFont, ")", HTML_CLOSE_SPAN, closeFont);
			}
		}

		if (StringUtils.isNotBlank(exception.getMessage())) {
			appendReportGeralProjeto(openFont, HTML_QUEBRA_LINHA, exception.getMessage(), closeFont);
			appendReportFalhaProjeto(openFont, HTML_QUEBRA_LINHA, exception.getMessage(), closeFont);
		}

		appendReportGeralProjeto(HTML_QUEBRA_LINHA, HTML_QUEBRA_LINHA);
		appendReportFalhaProjeto(HTML_QUEBRA_LINHA, HTML_QUEBRA_LINHA);
	}

	public static void appendException(ExceptionVO exception, String openFont) {
		String closeFont = HTML_CLOSE_FONT;

		String textoException = "#### Exception: ";

		appendReportGeral(openFont, HTML_QUEBRA_LINHA, textoException, closeFont, HTML_QUEBRA_LINHA);
		appendReportFalha(openFont, HTML_QUEBRA_LINHA, textoException, closeFont, HTML_QUEBRA_LINHA);

		List<StackTraceElementVO> stacksTraceVO = exception.getStacksTraceVO();
		if (CollectionUtils.isNotEmpty(stacksTraceVO)) {
			for (StackTraceElementVO stackTraceElement : stacksTraceVO) {
				String methodName = stackTraceElement.getMethodName();
				String className = stackTraceElement.getClassName();
				String fileName = stackTraceElement.getFileName();
				String numeroLinha = stackTraceElement.getNumeroLinhaString();
				String openFontBlue = openFont(HTML_COLOR_BLUE, "", "");
				appendReportGeral(openFont, HTML_QUEBRA_LINHA, HTML_OPEN_SPAN, className, ".", methodName, "(",
						openFontBlue, fileName, ":", numeroLinha, closeFont, ")", HTML_CLOSE_SPAN, closeFont);
				appendReportFalha(openFont, HTML_QUEBRA_LINHA, HTML_OPEN_SPAN, className, ".", methodName, "(",
						openFontBlue, fileName, ":", numeroLinha, closeFont, ")", HTML_CLOSE_SPAN, closeFont);
			}
		}

		if (StringUtils.isNotBlank(exception.getMessage())) {
			appendReportGeral(openFont, HTML_QUEBRA_LINHA, exception.getMessage(), closeFont);
			appendReportFalha(openFont, HTML_QUEBRA_LINHA, exception.getMessage(), closeFont);
		}

		appendReportGeral(HTML_QUEBRA_LINHA, HTML_QUEBRA_LINHA);
		appendReportFalha(HTML_QUEBRA_LINHA, HTML_QUEBRA_LINHA);
	}

	private static void appendLinkProvaException(MetodoClasseTesteVO metodo, String[] caminhoFileGeral,
			String[] caminhoFileGeralProjeto, String[] caminhoFileGeralMinimized) throws IOException {

		String caminhoOrigemPrint = metodo.getCaminhoPrintErro();

		String destinoArquivoPrintErro = "";
		String href = "<a href='file:///";
		String tituloPrint = "'>Print do Erro</a>";

		String caminhoReportGeral = caminhoFileGeral[0];
		destinoArquivoPrintErro = copyArquivoParaPastaDoReport(caminhoOrigemPrint, caminhoReportGeral);
		if (StringUtils.isNotBlank(destinoArquivoPrintErro)) {
			sbReportGeral.append(href).append(destinoArquivoPrintErro).append(tituloPrint).append(HTML_QUEBRA_LINHA);
		}

		String caminhoReportGeralMinimized = caminhoFileGeralMinimized[0];
		destinoArquivoPrintErro = copyArquivoParaPastaDoReport(caminhoOrigemPrint, caminhoReportGeralMinimized);
		if (StringUtils.isNotBlank(destinoArquivoPrintErro)) {
			sbReportGeralMinimized.append(href).append(destinoArquivoPrintErro).append(tituloPrint)
					.append(HTML_QUEBRA_LINHA);
		}
		String caminhoReportFalha = caminhoFileGeral[1];
		destinoArquivoPrintErro = copyArquivoParaPastaDoReport(caminhoOrigemPrint, caminhoReportFalha);
		if (StringUtils.isNotBlank(destinoArquivoPrintErro)) {
			sbReportFalha.append(href).append(destinoArquivoPrintErro).append(tituloPrint).append(HTML_QUEBRA_LINHA);
		}

		String caminhoReportGeralProjeto = caminhoFileGeralProjeto[0];
		destinoArquivoPrintErro = copyArquivoParaPastaDoReport(caminhoOrigemPrint, caminhoReportGeralProjeto);
		if (StringUtils.isNotBlank(destinoArquivoPrintErro)) {
			sbReportGeralProjeto.append(href).append(destinoArquivoPrintErro).append(tituloPrint)
					.append(HTML_QUEBRA_LINHA);
		}

		String caminhoReportFalhaProjeto = caminhoFileGeralProjeto[1];
		destinoArquivoPrintErro = copyArquivoParaPastaDoReport(caminhoOrigemPrint, caminhoReportFalhaProjeto);
		if (StringUtils.isNotBlank(destinoArquivoPrintErro)) {
			sbReportFalhaProjeto.append(href).append(destinoArquivoPrintErro).append(tituloPrint)
					.append(HTML_QUEBRA_LINHA);
		}
	}

	private static String copyArquivoParaPastaDoReport(String caminhoOrigem, String caminhoDestino) throws IOException {
		if (StringUtils.isBlank(caminhoOrigem)) {
			return "";
		}
		int lastIndexOf = 0;
		String destinoPrint = "";
		String destinoArquivo = "";
		lastIndexOf = caminhoDestino.lastIndexOf("\\");
		destinoPrint = caminhoDestino.substring(0, lastIndexOf + 1);
		destinoArquivo = copyArquivo(caminhoOrigem, destinoPrint);
		return destinoArquivo;
	}

	private static String copyArquivo(String origem, String destino) throws IOException {
		File destinoDir = new File(destino);
		File srcFile = new File(origem);
		FileUtils.copyFileToDirectory(srcFile, destinoDir, true);
		return destinoDir.getPath() + "\\" + srcFile.getName();
	}

	public static void escreverArquivosHTMLGeral(StringBuilder sbResultRun) throws IOException {
		if (StringUtils.isNotBlank(sbReportFalha.toString())) {
			appendReportFalha(HTML_CLOSE_HTML);
			escreverArquivoFalha(sbReportFalha.toString());
			closeFalha();
		}

		StringBuilder sbResultRunHTML = new StringBuilder();
		sbResultRunHTML.append(HTML_OPEN_HTML);
		sbResultRunHTML.append(sbResultRun.toString());
		sbResultRunHTML.append(HTML_CLOSE_HTML);

		escreverArquivoGeral(sbResultRunHTML.toString());
		if (StringUtils.isNotBlank(sbReportGeral.toString())) {
			appendReportGeral(getLegenda());
			appendReportGeral(HTML_CLOSE_HTML);
			escreverArquivoGeral(sbReportGeral.toString());
			closePastaGeral();
			// openReportGeralInBrowser();
		}

		escreverArquivoGeralMinimized(sbResultRunHTML.toString());
		if (StringUtils.isNotBlank(sbReportGeralMinimized.toString())) {
			appendReportGeralMinimized(getLegenda());
			appendReportGeralMinimized(HTML_CLOSE_HTML);
			escreverArquivoGeralMinimized(sbReportGeralMinimized.toString());
			closePastaGeralMinimized();
			openReportGeralMinimizedInBrowser();
		}

	}

	public static void escreverArquivosHTMLProjeto(StringBuilder sbResultRun) throws IOException {
		if (StringUtils.isNotBlank(sbReportSucessoProjeto.toString())) {

			appendReportSucessoProjeto(HTML_QUEBRA_LINHA, SEPARADOR);
			appendReportSucessoProjeto(HTML_QUEBRA_LINHA, HTML_OPEN_NEGRITO, TEXTO_NUMERO_SUCESSO,
					Integer.toString(countSucessoSuites), HTML_CLOSE_NEGRITO);
			appendReportSucessoProjeto(HTML_QUEBRA_LINHA, SEPARADOR);
			countSucessoSuites = 0;

			appendReportSucessoProjeto(HTML_CLOSE_HTML);
			escreverArquivoSucessoProjeto(sbReportSucessoProjeto.toString());
			closeSucessoProjeto();
		}
		if (StringUtils.isNotBlank(sbReportFalhaProjeto.toString())) {
			appendReportFalhaProjeto(HTML_QUEBRA_LINHA, SEPARADOR);
			appendReportFalhaProjeto(HTML_QUEBRA_LINHA, HTML_OPEN_NEGRITO, TEXTO_NUMERO_FALHA,
					Integer.toString(countFalhasSuites), HTML_CLOSE_NEGRITO);
			appendReportFalhaProjeto(HTML_QUEBRA_LINHA, SEPARADOR);
			countFalhasSuites = 0;

			appendReportFalhaProjeto(HTML_CLOSE_HTML);
			escreverArquivoFalhaProjeto(sbReportFalhaProjeto.toString());
			closeFalhaProjeto();
		}
		if (StringUtils.isNotBlank(sbReportGeralProjeto.toString())) {

			StringBuilder sbResultRunHTML = new StringBuilder();
			sbResultRunHTML.append(HTML_OPEN_HTML);
			sbResultRunHTML.append(sbResultRun.toString());
			sbResultRunHTML.append(HTML_CLOSE_HTML);

			sbResultRunHTML.append(HTML_QUEBRA_LINHA);
			appendReportGeralProjeto(getLegenda());
			appendReportGeralProjeto(HTML_CLOSE_HTML);
			escreverArquivoGeralProjeto(sbResultRunHTML.toString(), sbReportGeralProjeto.toString());
			closeGeralProjeto();
		}
	}

	public static String getLegenda() {
		String span = HTML_QUEBRA_LINHA + " <span style='color: ";

		String marcadorSucess = span + HTML_COLOR_SUCCESS + "'> &#9679; " + SUCESSOS
				+ ": Testes que concluiram sem nenhum erro ou falha." + "</span>";

		String marcadorFailed = span + HTML_COLOR_FAILED + "'>&#9679; " + FALHAS
				+ ": Algo que retorna um valor diferente do que é esperado, não impede a funcionalidade de prosseguir."
				+ "</span>";

		String marcadorSkiped = span + HTML_COLOR_SKIPED + "'>&#9679; " + IGNORADOS
				+ ": Testes que foram marcados para serem ignorados durante a execução." + "</span>";

		String marcadorErro = span + HTML_COLOR_ERRO + "'>&#9679; " + ERROS
				+ ": Quando não encontra o que está sendo buscado, impedindo a funcionalidade de prosseguir."
				+ "</span>";

		String legenda = "<br/> Lengenda: " + marcadorSucess + marcadorFailed + marcadorSkiped + marcadorErro;

		return legenda;
	}
}
