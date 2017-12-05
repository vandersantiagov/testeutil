package br.gov.mg.testeutil.report.html;

import static br.gov.mg.testeutil.report.html.FileHTML.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

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
	// private static String diretorioPrints;

	public static void createHTML(SuitePrincipalVO suitePrincipalVO) throws IOException {
		String nomeProjetoAnterior = "";
		SuiteVO suiteVO = null;
		String[] caminhoFileGeralProjeto = null;

		String nomePastaProjetoPrincipal = suitePrincipalVO.getNomeProjeto();

		String path = getPathReportGeral(nomePastaProjetoPrincipal);
		// Deleta as últimas 10 pastas referente ao projeto.
		deleteArquivosDatasAntigas(path, false);
		// Cria o arquivo html geral na pasta report.
		String[] caminhoFileGeral = createFilesGeral(nomePastaProjetoPrincipal, path);

		String pathMinimized = getPathReportGeralMinimized(nomePastaProjetoPrincipal);
		// Deleta as últimas 10 pastas referente ao projeto.
		deleteArquivosDatasAntigas(pathMinimized, false);
		// Cria o arquivo html geral na pasta report.
		String[] caminhoFileGeralMinimized = createFilesGeralMinimized(nomePastaProjetoPrincipal, pathMinimized);

		// Abre a tagHTML para os arquivos.
		openHTMLGeral();
		openHTMLGeralMinimized();
		appendReportGeral(HTML_OPEN_NEGRITO, TEXTO_INICIO_DOS_TESTES,
				DateUtil.getDataFormatadaByFormato(suitePrincipalVO.getDataInicioExecucao(), DateUtil.FORMATO_DATA6),
				HTML_CLOSE_NEGRITO, HTML_QUEBRA_LINHA);
		appendReportGeral(HTML_OPEN_NEGRITO, TEXTO_SUITE_PRINCIPAL, HTML_CLOSE_NEGRITO, suitePrincipalVO.getNomeSuite(),
				HTML_QUEBRA_LINHA);
		if (StringUtils.isNotBlank(nomePastaProjetoPrincipal)) {
			appendReportGeral(HTML_OPEN_NEGRITO, TEXTO_PROJETO_PRINCIPAL, HTML_CLOSE_NEGRITO, nomePastaProjetoPrincipal,
					HTML_QUEBRA_LINHA);
		}

		Map<KeyMapVO<String, String>, SuiteVO> mapSuites = suitePrincipalVO.getSuitesFilhasByNome();

		if (MapUtils.isNotEmpty(mapSuites)) {
			for (Entry<KeyMapVO<String, String>, SuiteVO> suiteByKey : mapSuites.entrySet()) {

				KeyMapVO<String, String> key = suiteByKey.getKey();
				// Cria outro arquivo se o projeto for diferente do anterior e
				// inicia um novo arquivo.
				if (StringUtils.isNotBlank(nomeProjetoAnterior)) {
					if (!Objects.equals(key.getKey1(), nomeProjetoAnterior)) {
						encerrarHTMLAnteriorProjeto(suiteVO);
						sbReportGeralProjeto = new StringBuilder();
						sbReportFalhaProjeto = new StringBuilder();
						sbReportSucessoProjeto = new StringBuilder();
						String pathProjeto = getPathReportGeralProjeto(key.getKey1(), nomePastaProjetoPrincipal);
						// Deleta os arquivos antigos e mantém os últimos
						// gerados.
						deleteArquivosDatasAntigas(pathProjeto, true);
						// Cria a estrutura de report do projeto
						caminhoFileGeralProjeto = createFilesProjeto(key.getKey1(), nomePastaProjetoPrincipal,
								pathProjeto);
						openHTMLProjeto(key.getKey1());
					}
				} else {
					String pathProjeto = getPathReportGeralProjeto(key.getKey1(), nomePastaProjetoPrincipal);
					// Deleta os arquivos antigos e mantém os últimos gerados.
					deleteArquivosDatasAntigas(pathProjeto, true);
					// Cria a estrutura de report do projeto
					caminhoFileGeralProjeto = createFilesProjeto(key.getKey1(), nomePastaProjetoPrincipal, pathProjeto);
					openHTMLProjeto(key.getKey1());
				}

				suiteVO = suiteByKey.getValue();
				nomeProjetoAnterior = key.getKey1();
				appendCabecalho(suitePrincipalVO, suiteVO);
				appendClassesDeTeste(suiteVO, caminhoFileGeral, caminhoFileGeralProjeto, caminhoFileGeralMinimized);

				SuiteSiare.setQuantitativoRunVO(suiteVO.getQuantitativoRunVO(), suitePrincipalVO.getQuantitativoRun());
			}

			StringBuilder sbResultRun = new StringBuilder();
			if (suiteVO.getQuantitativoRunVO().getQuantidadeRun() > 0) {
				appendResultRun(suiteVO.getQuantitativoRunVO(), suiteVO.getDataInicioExecucao(),
						suiteVO.getDataFimExecucao(), sbResultRun);
			}
			appendReportGeralProjeto(HTML_QUEBRA_LINHA, HTML_OPEN_NEGRITO, TEXTO_FIM_DOS_TESTES,
					DateUtil.getDataFormatadaByFormato(suiteVO.getDataFimExecucao(), DateUtil.FORMATO_DATA6),
					HTML_CLOSE_NEGRITO, HTML_QUEBRA_LINHA);

			escreverArquivosHTMLProjeto(sbResultRun);
		}

		StringBuilder sbResultRun = new StringBuilder();
		if (suitePrincipalVO.getQuantitativoRun().getQuantidadeRun() > 0) {
			appendResultRun(suitePrincipalVO.getQuantitativoRun(), suitePrincipalVO.getDataInicioExecucao(),
					suitePrincipalVO.getDataFimExecucao(), sbResultRun);
		}
		appendReportGeral(HTML_QUEBRA_LINHA, HTML_OPEN_NEGRITO, TEXTO_FIM_DOS_TESTES,
				DateUtil.getDataFormatadaByFormato(suitePrincipalVO.getDataFimExecucao(), DateUtil.FORMATO_DATA6),
				HTML_CLOSE_NEGRITO, HTML_QUEBRA_LINHA);

		escreverArquivosHTMLGeral(sbResultRun);
	}

	private static void appendCabecalho(SuitePrincipalVO suitePrincipalVO, SuiteVO suiteVO) {

		appendReportGeral(SEPARADOR, HTML_QUEBRA_LINHA);
		appendReportGeral(HTML_OPEN_NEGRITO, TEXTO_PROJETO, HTML_CLOSE_NEGRITO, suiteVO.getNomeProjeto(),
				HTML_QUEBRA_LINHA);
		appendReportGeral(HTML_OPEN_NEGRITO, TEXTO_SUITE, HTML_CLOSE_NEGRITO, suiteVO.getNomeSuite(),
				HTML_QUEBRA_LINHA);
		appendReportGeral(SEPARADOR, HTML_QUEBRA_LINHA);

		appendReportFalha(SEPARADOR, HTML_QUEBRA_LINHA);
		appendReportFalha(HTML_OPEN_NEGRITO, TEXTO_PROJETO, HTML_CLOSE_NEGRITO, suiteVO.getNomeProjeto(),
				HTML_QUEBRA_LINHA);
		appendReportFalha(HTML_OPEN_NEGRITO, TEXTO_SUITE, HTML_CLOSE_NEGRITO, suiteVO.getNomeSuite(),
				HTML_QUEBRA_LINHA);
		appendReportFalha(SEPARADOR, HTML_QUEBRA_LINHA);

		appendReportGeralMinimized(SEPARADOR, HTML_QUEBRA_LINHA);
		appendReportGeralMinimized(HTML_OPEN_NEGRITO, TEXTO_PROJETO, HTML_CLOSE_NEGRITO, suiteVO.getNomeProjeto(),
				HTML_QUEBRA_LINHA);
		appendReportGeralMinimized(HTML_OPEN_NEGRITO, TEXTO_SUITE, HTML_CLOSE_NEGRITO, suiteVO.getNomeSuite(),
				HTML_QUEBRA_LINHA);
		appendReportGeralMinimized(SEPARADOR, HTML_QUEBRA_LINHA);

		appendDataInicioTestesProjeto(suiteVO.getDataInicioExecucao());
		appendReportGeralProjeto(HTML_OPEN_NEGRITO, TEXTO_SUITE_PRINCIPAL, HTML_CLOSE_NEGRITO,
				suitePrincipalVO.getNomeSuite(), HTML_QUEBRA_LINHA);
		appendReportGeralProjeto(SEPARADOR, HTML_QUEBRA_LINHA);
		appendReportGeralProjeto(HTML_OPEN_NEGRITO, TEXTO_PROJETO, HTML_CLOSE_NEGRITO, suiteVO.getNomeProjeto(),
				HTML_QUEBRA_LINHA);
		appendReportGeralProjeto(HTML_OPEN_NEGRITO, TEXTO_SUITE, HTML_CLOSE_NEGRITO, suiteVO.getNomeSuite(),
				HTML_QUEBRA_LINHA);
		appendReportGeralProjeto(SEPARADOR, HTML_QUEBRA_LINHA);

		appendReportFalhaProjeto(HTML_OPEN_NEGRITO, TEXTO_SUITE_PRINCIPAL, HTML_CLOSE_NEGRITO,
				suitePrincipalVO.getNomeSuite(), HTML_QUEBRA_LINHA);
		appendReportFalhaProjeto(SEPARADOR, HTML_QUEBRA_LINHA);
		appendReportFalhaProjeto(HTML_OPEN_NEGRITO, TEXTO_PROJETO, HTML_CLOSE_NEGRITO, suiteVO.getNomeProjeto(),
				HTML_QUEBRA_LINHA);
		appendReportFalhaProjeto(HTML_OPEN_NEGRITO, TEXTO_SUITE, HTML_CLOSE_NEGRITO, suiteVO.getNomeSuite(),
				HTML_QUEBRA_LINHA);
		appendReportFalhaProjeto(SEPARADOR, HTML_QUEBRA_LINHA);

		appendReportSucessoProjeto(HTML_OPEN_NEGRITO, TEXTO_SUITE_PRINCIPAL, HTML_CLOSE_NEGRITO,
				suitePrincipalVO.getNomeSuite(), HTML_QUEBRA_LINHA);
		appendReportSucessoProjeto(SEPARADOR, HTML_QUEBRA_LINHA);
		appendReportSucessoProjeto(HTML_OPEN_NEGRITO, TEXTO_PROJETO, HTML_CLOSE_NEGRITO, suiteVO.getNomeProjeto(),
				HTML_QUEBRA_LINHA);
		appendReportSucessoProjeto(HTML_OPEN_NEGRITO, TEXTO_SUITE, HTML_CLOSE_NEGRITO, suiteVO.getNomeSuite(),
				HTML_QUEBRA_LINHA);
		appendReportSucessoProjeto(SEPARADOR, HTML_QUEBRA_LINHA);
	}

	private static void encerrarHTMLAnteriorProjeto(SuiteVO suiteVO) throws IOException {
		StringBuilder sbResultRun = new StringBuilder();
		appendResultRun(suiteVO.getQuantitativoRunVO(), suiteVO.getDataInicioExecucao(), suiteVO.getDataFimExecucao(),
				sbResultRun);
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
		if (CollectionUtils.isNotEmpty(metodo.getExceptions())) {
			for (ExceptionVO exception : metodo.getExceptions()) {
				appendException(exception, openFont);
				appendExceptionProjeto(exception, openFont);
				appendExceptionMinimized(classeDeTeste, metodo, exception, openFont, isNewClass, totalExecucaoMetodos);
			}
		}
		appendLinkProvaException(metodo, caminhoFileGeral, caminhoFileGeralProjeto, caminhoFileGeralMinimized);
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
			StringBuilder sb) {
		String simboloPercentual = "% ";
		int quantidadeRun = quantitativoVO.getQuantidadeRun();
		int quantidadeFalha = quantitativoVO.getQuantidadeFalha();
		int quantidadeErro = quantitativoVO.getQuantidadeErro();
		int quantidadeSucesso = quantitativoVO.getQuantidadeSucesso();
		int quantidadeSkipped = quantitativoVO.getQuantidadeSkiped();

		StringBuilder sbTitulo = new StringBuilder();
		sbTitulo.append("<span style='color:").append(HTML_COLOR_BLACK).append("'>Total testes: ");

		StringBuilder sbCountFailed = new StringBuilder();
		sbCountFailed.append(FALHAS).append(": ").append(quantidadeFalha);

		StringBuilder sbCountErro = new StringBuilder();
		sbCountErro.append(ERROS).append(": ").append(quantidadeErro).append(HTML_CLOSE_SPAN);

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
		sb.append(HTML_OPEN_PRE).append(sbResultCount.toString()).append(HTML_CLOSE_PRE).append(HTML_CLOSE_P);
		sb.append(HTML_OPEN_PRE).append(sbResultPercentual.toString()).append(HTML_CLOSE_PRE).append(HTML_CLOSE_P);
		sb.append(HTML_OPEN_PRE).append("  Tempo total execução dos testes: ").append(duracaoTestes)
				.append(HTML_CLOSE_PRE).append(HTML_CLOSE_P);
		sb.append(SEPARATOR_RESULT_FINAL);
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
		appendReportGeralMinimized("<html><head><meta charset='UTF-8'>");
		appendReportGeralMinimized("<style>");
		appendReportGeralMinimized("details summary {cursor: pointer; font: bold 1em, Helvetica, sans-serif; padding: 8px 0; position: relative; width: 100%; }");
		appendReportGeralMinimized("details summary::-webkit-details-marker {display: none}");
		appendReportGeralMinimized("details summary:before{border-radius: 5px; content: ; color: #000; float: left; font-size: 1.3em; font-weight: bold; margin: -4px 10px 0 0; padding: 0; text-align: center; width: 10px;}");
		appendReportGeralMinimized("details summary::before { content:'+'}");
		appendReportGeralMinimized("details[open] summary::before { content:'-' }");
		appendReportGeralMinimized("</style>");
		appendReportGeralMinimized("</head><body>");
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
		StringBuilder sbFont = new StringBuilder("<font");

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
			appendReportGeralMinimized(openFont, HTML_QUEBRA_LINHA, exception.getMessage(), HTML_CLOSE_FONT);
		}

		appendReportGeralMinimized(HTML_CLOSE_DETAILS, HTML_QUEBRA_LINHA);
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
		if (StringUtils.isNotBlank(sbReportGeral.toString())) {
			sbResultRun.append(HTML_QUEBRA_LINHA);
			appendReportGeral(getLegenda());
			appendReportGeral(HTML_CLOSE_HTML);
			escreverArquivoGeral(sbResultRun.toString(), sbReportGeral.toString());
			escreverArquivoGeralMinimized(sbResultRun.toString(), sbReportGeralMinimized.toString());
			closePastaGeral();
			openReportGeralInBrowser();
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
			sbResultRun.append(HTML_QUEBRA_LINHA);
			appendReportGeralProjeto(getLegenda());
			appendReportGeralProjeto(HTML_CLOSE_HTML);
			escreverArquivoGeralProjeto(sbResultRun.toString(), sbReportGeralProjeto.toString());
			closeGeralProjeto();
		}
	}

	public static String getLegenda() {
		String marcadorSucess = " <span style='color: " + HTML_COLOR_SUCCESS + "'> &#9679; " + SUCESSOS + "</span>";
		String marcadorFailed = " <span style='color: " + HTML_COLOR_FAILED + "'>&#9679; " + FALHAS + "</span>";
		String marcadorSkiped = " <span style='color: " + HTML_COLOR_SKIPED + "'>&#9679; " + IGNORADOS + "</span>";
		String marcadorErro = " <span style='color: " + HTML_COLOR_ERRO + "'>&#9679; " + ERROS + "</span>";
		String legenda = "<br/> Lengenda: " + marcadorSucess + marcadorFailed + marcadorSkiped + marcadorErro;
		return legenda;
	}
}
