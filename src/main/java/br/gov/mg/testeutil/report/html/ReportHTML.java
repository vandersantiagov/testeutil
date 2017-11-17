package br.gov.mg.testeutil.report.html;

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
import org.apache.commons.lang3.StringUtils;

import br.gov.mg.testeutil.report.rules.SuiteSiare;
import br.gov.mg.testeutil.util.DateUtil;
import br.gov.mg.testeutil.vo.ClasseDeTesteVO;
import br.gov.mg.testeutil.vo.ExceptionVO;
import br.gov.mg.testeutil.vo.KeyMapVO;
import br.gov.mg.testeutil.vo.MetodoClasseTesteVO;
import br.gov.mg.testeutil.vo.QuantitativoRunVO;
import br.gov.mg.testeutil.vo.StackTraceElementVO;
import br.gov.mg.testeutil.vo.SuitePrincipalVO;
import br.gov.mg.testeutil.vo.SuiteVO;

/**
 * @author sandra.rodrigues
 */
public class ReportHTML {

	private static final String FAILED = "FAILED";
	private static final String SUCCESS = "SUCCESS";
	private static final String ERRO = "ERRO";
	private static final String SKIPED = "SKIPED";

	//private static final String NOME_PASTA_PRINTS_ERRO_E_FALHA = "Prints Erro e Falha";

	private static final String TEXTO_INICIO_DOS_TESTES = "Inicio dos Testes: ";
	private static final String TEXTO_FIM_DOS_TESTES = "Fim dos Testes: ";
	private static final String TEXTO_PROJETO = "Projeto: ";
	private static final String TEXTO_SUITE = "Suite: ";
	private static final String TEXTO_CLASSE = "Classe: ";
	private static final String TEXTO_METODO = " Método: ";

	private static final String HTML_COLOR_SUCCESS = "#00AA00";
	private static final String HTML_COLOR_FAILED = "#446691";
	private static final String HTML_COLOR_ERRO = "#DD0000";
	private static final String HTML_COLOR_SKIPED = "#CCCC00";
	private static final String HTML_COLOR_BLACK = "#000000";
	private static final String HTML_COLOR_BLUE = "#0000ff";
	private static final String HTML_OPEN_HTML = "<html><head><meta charset='UTF-8'></head><body>";
	private static final String HTML_CLOSE_HTML = "</body></html>";
	private static final String HTML_QUEBRA_LINHA = "<br/>";
	private static final String HTML_OPEN_NEGRITO = "<b>";
	private static final String HTML_CLOSE_NEGRITO = "</b>";
	private static final String HTML_OPEN_SPAN = "<span style='padding-left: 1cm;'>";
	private static final String HTML_CLOSE_SPAN = "</span>";
	private static final String HTML_OPEN_PRE = "<pre>";
	private static final String HTML_CLOSE_PRE = "</pre>";
	private static final String HTML_OPEN_P = "<p>";
	private static final String HTML_CLOSE_P = "</p>";
	private static final String HTML_CLOSE_FONT = "</font>";

	private static final String SEPARADOR_PROJETO = "-----------------------------------------------------------------------------";

	private static StringBuilder sbReportGeral = new StringBuilder();
	private static StringBuilder sbReportFalha = new StringBuilder();

	private static StringBuilder sbReportGeralProjeto = new StringBuilder();
	private static StringBuilder sbReportFalhaProjeto = new StringBuilder();
	private static StringBuilder sbReportSucessoProjeto = new StringBuilder();

	private static StringBuilder sbReportGeralSuite = new StringBuilder();
	private static StringBuilder sbReportFalhaSuite = new StringBuilder();
	private static StringBuilder sbReportSucessoSuite = new StringBuilder();
	//private static String diretorioPrints;

	public static void createHTML(SuitePrincipalVO suitePrincipalVO) throws IOException {
		String nomeProjetoAnterior = "";
		String nomeSuiteAnterior = "";

		//diretorioPrints = FileHTML.getDiretorio(FileHTML.PATH_DIRETORIO_REPORT + "\\", NOME_PASTA_PRINTS_ERRO_E_FALHA);
		FileHTML.createFilesGeral();
		appendArquivosHTMLPastaReport();

		appendReportGeral(HTML_OPEN_NEGRITO, TEXTO_INICIO_DOS_TESTES,
				DateUtil.getDataFormatadaByFormato(suitePrincipalVO.getDataInicioExecucao(), DateUtil.FORMATO_DATA5),
				HTML_CLOSE_NEGRITO, HTML_QUEBRA_LINHA);

		Map<KeyMapVO<String, String>, SuiteVO> mapSuites = suitePrincipalVO.getSuitesFilhasByNome();

		if (MapUtils.isNotEmpty(mapSuites)) {
			for (Entry<KeyMapVO<String, String>, SuiteVO> suiteByKey : mapSuites.entrySet()) {
				KeyMapVO<String, String> key = suiteByKey.getKey();
				if (StringUtils.isNotBlank(nomeProjetoAnterior)) {
					if (!Objects.equals(key.getKey1(), nomeProjetoAnterior)) {
						encerrarArquivoAnteriorECriarNovoHTMLProjeto(suitePrincipalVO, nomeProjetoAnterior, key);
					}
				} else {
					FileHTML.createFilesProjeto(key.getKey1());
					appendArquivosHTMLPastaProjeto(key.getKey1());
				}

				if (StringUtils.isNotBlank(nomeProjetoAnterior) && StringUtils.isNotBlank(nomeSuiteAnterior)) {
					if (!Objects.equals(key.getKey1(), nomeProjetoAnterior)
							&& !Objects.equals(key.getKey2(), nomeSuiteAnterior)) {
						encerrarArquivoAnteriorECriarNovoHTMLSuite(suitePrincipalVO, nomeProjetoAnterior,
								nomeSuiteAnterior);
					}
				} else {
					FileHTML.createFilesSuite(key.getKey1(), key.getKey2());
					appendArquivosHTMLPastaSuite(key.getKey1(), key.getKey2());
				}

				nomeProjetoAnterior = key.getKey1();
				nomeSuiteAnterior = key.getKey2();
				SuiteVO suiteVO = suiteByKey.getValue();
				appendSeparator();
				appendTituloProjetoESuite(suiteVO);
				appendClassesDeTeste(suiteVO);
				SuiteSiare.setQuantitativoRunVOSuitePrincipal(suiteVO.getQuantitativoRunVO(),
						suitePrincipalVO.getQuantitativoRun());
			}

			appendResultRun(suitePrincipalVO.getQuantitativoRun(), suitePrincipalVO.getDataInicioExecucao(),
					suitePrincipalVO.getDataFimExecucao(), sbReportGeralSuite);
			escreverArquivosHTMLSuite();
			appendResultRun(suitePrincipalVO.getQuantitativoRun(), suitePrincipalVO.getDataInicioExecucao(),
					suitePrincipalVO.getDataFimExecucao(), sbReportGeralProjeto);
			escreverArquivosHTMLProjeto();
		}

		appendResultRun(suitePrincipalVO.getQuantitativoRun(), suitePrincipalVO.getDataInicioExecucao(),
				suitePrincipalVO.getDataFimExecucao(), sbReportGeral);

		appendReportGeral(HTML_QUEBRA_LINHA, HTML_OPEN_NEGRITO, TEXTO_FIM_DOS_TESTES,
				DateUtil.getDataFormatadaByFormato(suitePrincipalVO.getDataFimExecucao(), DateUtil.FORMATO_DATA5),
				HTML_CLOSE_NEGRITO, HTML_QUEBRA_LINHA);

		appendReportGeral(getLegenda());
		appendReportGeral(HTML_CLOSE_HTML);
		appendReportFalha(HTML_CLOSE_HTML);

		escreverArquivosHTMLGeral();
	}

	private static void appendSeparator() {
		appendReportGeral(HTML_QUEBRA_LINHA, SEPARADOR_PROJETO, HTML_QUEBRA_LINHA);
		appendReportGeralProjeto(HTML_QUEBRA_LINHA, SEPARADOR_PROJETO, HTML_QUEBRA_LINHA);
		appendReportGeralSuite(HTML_QUEBRA_LINHA, SEPARADOR_PROJETO, HTML_QUEBRA_LINHA);
	}

	private static void appendTituloProjetoESuite(SuiteVO suiteVO) {
		appendReportGeral(HTML_OPEN_NEGRITO, TEXTO_PROJETO, HTML_CLOSE_NEGRITO, suiteVO.getNomeProjeto(),
				HTML_QUEBRA_LINHA);
		appendReportGeral(HTML_OPEN_NEGRITO, TEXTO_SUITE, HTML_CLOSE_NEGRITO, suiteVO.getNomeSuite(),
				HTML_QUEBRA_LINHA);

		appendReportGeralProjeto(HTML_OPEN_NEGRITO, TEXTO_PROJETO, HTML_CLOSE_NEGRITO, suiteVO.getNomeProjeto(),
				HTML_QUEBRA_LINHA);
		appendReportGeralProjeto(HTML_OPEN_NEGRITO, TEXTO_SUITE, HTML_CLOSE_NEGRITO, suiteVO.getNomeSuite(),
				HTML_QUEBRA_LINHA);

		appendReportGeralSuite(HTML_OPEN_NEGRITO, TEXTO_PROJETO, HTML_CLOSE_NEGRITO, suiteVO.getNomeProjeto(),
				HTML_QUEBRA_LINHA);
		appendReportGeralSuite(HTML_OPEN_NEGRITO, TEXTO_SUITE, HTML_CLOSE_NEGRITO, suiteVO.getNomeSuite(),
				HTML_QUEBRA_LINHA);
	}

	private static void encerrarArquivoAnteriorECriarNovoHTMLProjeto(SuitePrincipalVO suitePrincipalVO,
			String nomeProjeto, KeyMapVO<String, String> key) throws IOException {
		appendResultRun(suitePrincipalVO.getQuantitativoRun(), suitePrincipalVO.getDataInicioExecucao(),
				suitePrincipalVO.getDataFimExecucao(), sbReportGeralProjeto);
		escreverArquivosHTMLProjeto();
		sbReportGeralProjeto = new StringBuilder();
		sbReportFalhaProjeto = new StringBuilder();
		sbReportSucessoProjeto = new StringBuilder();
		FileHTML.createFilesProjeto(nomeProjeto);
		appendArquivosHTMLPastaProjeto(key.getKey1());
	}

	private static void encerrarArquivoAnteriorECriarNovoHTMLSuite(SuitePrincipalVO suitePrincipalVO,
			String nomeProjeto, String nomeSuite) throws IOException {
		appendResultRun(suitePrincipalVO.getQuantitativoRun(), suitePrincipalVO.getDataInicioExecucao(),
				suitePrincipalVO.getDataFimExecucao(), sbReportGeralSuite);
		escreverArquivosHTMLSuite();
		sbReportGeralSuite = new StringBuilder();
		sbReportFalhaSuite = new StringBuilder();
		sbReportSucessoSuite = new StringBuilder();
		FileHTML.createFilesSuite(nomeProjeto, nomeSuite);
		appendArquivosHTMLPastaSuite(nomeProjeto, nomeSuite);
	}

	public static void appendClassesDeTeste(SuiteVO suiteVO) throws IOException {
		appendReportGeralProjeto(HTML_OPEN_NEGRITO, TEXTO_INICIO_DOS_TESTES,
				DateUtil.getDataFormatadaByFormato(suiteVO.getDataInicioExecucao(), DateUtil.FORMATO_DATA5),
				HTML_CLOSE_NEGRITO, HTML_QUEBRA_LINHA);

		Map<String, ClasseDeTesteVO> classesDeTesteByName = suiteVO.getClassesDeTesteByName();
		if (MapUtils.isNotEmpty(classesDeTesteByName)) {
			String classeAnterior = "";
			for (Entry<String, ClasseDeTesteVO> ct : classesDeTesteByName.entrySet()) {
				classeAnterior = treathClasseDeTeste(classeAnterior, ct);
			}
		}
		appendResultRun(suiteVO.getQuantitativoRunVO(), suiteVO.getDataInicioExecucao(), suiteVO.getDataFimExecucao(),
				sbReportGeralProjeto);
		appendReportGeralProjeto(HTML_CLOSE_HTML);
		appendReportFalhaProjeto(HTML_CLOSE_HTML);
	}

	private static String treathClasseDeTeste(String classeAnterior, Entry<String, ClasseDeTesteVO> ct)
			throws IOException {
		ClasseDeTesteVO classeDeTeste = ct.getValue();
		List<MetodoClasseTesteVO> metodos = classeDeTeste.getMetodos();
		// Só entra nesse local uma vez a cada nome de classe diferente
		if (classeAnterior == null || !Objects.equals(classeAnterior, classeDeTeste.getNomeClasse())) {
			classeAnterior = classeDeTeste.getNomeClasse();

			double totalExecucaoMetodos = metodos.stream().map(item -> item.getDuracaoSeconds())
					.mapToDouble(BigDecimal::doubleValue).sum();

			appendReportGeral(HTML_QUEBRA_LINHA, HTML_OPEN_NEGRITO, TEXTO_CLASSE, HTML_CLOSE_NEGRITO,
					classeDeTeste.getNomeClasse(), "(", getValorArredondado(totalExecucaoMetodos), " s)",
					HTML_QUEBRA_LINHA);
			appendReportGeralProjeto(HTML_QUEBRA_LINHA, HTML_OPEN_NEGRITO, TEXTO_CLASSE, HTML_CLOSE_NEGRITO,
					classeDeTeste.getNomeClasse(), getValorArredondado(totalExecucaoMetodos), HTML_QUEBRA_LINHA);
		}
		if (CollectionUtils.isNotEmpty(metodos)) {
			for (MetodoClasseTesteVO metodo : metodos) {
				treathMetodos(classeDeTeste, metodo);
			}
		}
		return classeAnterior;
	}

	private static String getValorArredondado(double totalExecucaoMetodos) {
		BigDecimal valorArredondado = BigDecimal.ZERO.equals(BigDecimal.valueOf(totalExecucaoMetodos)) ? BigDecimal.ZERO
				: arredondaValor(totalExecucaoMetodos, 3);
		return valorArredondado.toString();
	}

	private static void treathMetodos(ClasseDeTesteVO classeDeTeste, MetodoClasseTesteVO metodo) throws IOException {
		String openFontSuccess = openFont(HTML_COLOR_SUCCESS, "", "");
		String openFontErro = openFont(HTML_COLOR_ERRO, "", "");
		String openFontFalha = openFont(HTML_COLOR_FAILED, "", "");
		String openFontSkiped = openFont(HTML_COLOR_SKIPED, "", "");
		if (metodo.isFalha()) {
			treathFalhaAndErro(classeDeTeste, metodo, openFontFalha, FAILED);
		} else if (metodo.isErro()) {
			treathFalhaAndErro(classeDeTeste, metodo, openFontErro, ERRO);
		} else if (metodo.isSucess()) {
			appendClasseEMetodo(classeDeTeste, metodo, openFontSuccess, classeDeTeste.getNomeClasse(), metodo.getNome(),
					SUCCESS);
		} else if (metodo.isSkiped()) {
			appendClasseEMetodo(classeDeTeste, metodo, openFontSkiped, classeDeTeste.getNomeClasse(), metodo.getNome(),
					SKIPED);
		}
	}

	private static void treathFalhaAndErro(ClasseDeTesteVO classeDeTeste, MetodoClasseTesteVO metodo, String openFont,
			String tipoMetodo) throws IOException {
		appendClasseEMetodo(classeDeTeste, metodo, openFont, classeDeTeste.getNomeClasse(), metodo.getNome(),
				tipoMetodo);
		if (CollectionUtils.isNotEmpty(metodo.getExceptions())) {
			for (ExceptionVO exception : metodo.getExceptions()) {
				appendException(exception, openFont);
			}
		}
		appendLinkProvaException(metodo.getCaminhoPrintErro());
	}

	private static void appendClasseEMetodo(ClasseDeTesteVO classeDeTeste, MetodoClasseTesteVO metodo, String openFont,
			String nomeClasse, String nomeMetodo, String tipoOcorrencia) {
		appendReportGeral(openFont, HTML_OPEN_SPAN, TEXTO_METODO, nomeMetodo,
				metodo.getTextoTempoTotalExecucaoEmSegundos() + HTML_CLOSE_SPAN + HTML_CLOSE_FONT + HTML_QUEBRA_LINHA);

		appendReportGeralProjeto(openFont, HTML_OPEN_SPAN, TEXTO_METODO, nomeMetodo,
				metodo.getTextoTempoTotalExecucaoEmSegundos() + HTML_CLOSE_SPAN + HTML_CLOSE_FONT + HTML_QUEBRA_LINHA);

		appendReportGeralSuite(openFont, HTML_OPEN_SPAN, TEXTO_METODO, nomeMetodo,
				metodo.getTextoTempoTotalExecucaoEmSegundos() + HTML_CLOSE_SPAN + HTML_CLOSE_FONT + HTML_QUEBRA_LINHA);

		if (SUCCESS.equals(tipoOcorrencia)) {
			appendReportSucessoProjeto(openFont, HTML_OPEN_SPAN, TEXTO_METODO, nomeMetodo,
					metodo.getTextoTempoTotalExecucaoEmSegundos() + HTML_CLOSE_SPAN + HTML_CLOSE_FONT
							+ HTML_QUEBRA_LINHA);
			appendReportSucessoSuite(openFont, HTML_OPEN_SPAN, TEXTO_METODO, nomeMetodo,
					metodo.getTextoTempoTotalExecucaoEmSegundos() + HTML_CLOSE_SPAN + HTML_CLOSE_FONT
							+ HTML_QUEBRA_LINHA);
		} else if (FAILED.equals(tipoOcorrencia) || ERRO.equals(tipoOcorrencia)) {
			appendReportFalha(openFont, HTML_OPEN_SPAN, TEXTO_METODO, nomeMetodo,
					metodo.getTextoTempoTotalExecucaoEmSegundos() + HTML_CLOSE_SPAN + HTML_CLOSE_FONT
							+ HTML_QUEBRA_LINHA);
			appendReportFalhaProjeto(openFont, HTML_OPEN_SPAN, TEXTO_METODO, nomeMetodo,
					metodo.getTextoTempoTotalExecucaoEmSegundos() + HTML_CLOSE_SPAN + HTML_CLOSE_FONT
							+ HTML_QUEBRA_LINHA);
		}
	}

	public static void appendResultRun(QuantitativoRunVO quantitativoVO, Date dataInicio, Date dataFim,
			StringBuilder sb) {
		String simboloPercentual = "% ";
		int quantidadeRun = quantitativoVO.getQuantidadeRun();
		int quantidadeFalha = quantitativoVO.getQuantidadeFalha();
		int quantidadeErro = quantitativoVO.getQuantidadeErro();
		int quantidadeSucesso = quantitativoVO.getQuantidadeSucesso();

		StringBuilder sbTitulo = new StringBuilder();
		sbTitulo.append("<span style='color:").append(HTML_COLOR_BLACK).append("'>Total testes: ");

		StringBuilder sbCountFailed = new StringBuilder();
		sbCountFailed.append(" Failures: ").append(quantidadeFalha);

		StringBuilder sbCountErro = new StringBuilder();
		sbCountErro.append(" Erro: ").append(quantidadeErro).append(HTML_CLOSE_SPAN);

		StringBuilder sbPercentualSuccess = new StringBuilder();
		sbPercentualSuccess.append("Success: ")
				.append(arredondaValor((Double.valueOf(quantidadeSucesso) / Double.valueOf(quantidadeRun)) * 100, 0))
				.append(simboloPercentual);

		StringBuilder sbPercentualFailed = new StringBuilder();
		sbPercentualFailed.append("Failures: ").append(getTotal(quantidadeRun, quantidadeFalha))
				.append(simboloPercentual);

		StringBuilder sbPercentualErro = new StringBuilder();
		sbPercentualErro.append("Erro: ").append(getTotal(quantidadeRun, quantidadeErro)).append(simboloPercentual);

		StringBuilder sbResultCount = new StringBuilder();
		sbResultCount.append(sbTitulo.toString()).append(HTML_OPEN_P).append("Quantidade runs: ").append(quantidadeRun)
				.append(sbCountFailed.toString()).append(sbCountErro.toString());

		StringBuilder sbResultPercentual = new StringBuilder();
		sbResultPercentual.append(HTML_OPEN_P).append("Percentual : ").append(sbPercentualSuccess.toString())
				.append(sbPercentualFailed.toString()).append(sbPercentualErro.toString());

		String duracaoTestes = getTotalDuracaoTestes(dataFim, dataInicio);

		sb.append(HTML_QUEBRA_LINHA);
		sb.append("======================================================================================");
		sb.append(HTML_OPEN_PRE).append(sbResultCount.toString()).append(HTML_CLOSE_PRE).append(HTML_CLOSE_P);
		sb.append(HTML_OPEN_PRE).append(sbResultPercentual.toString()).append(HTML_CLOSE_PRE).append(HTML_CLOSE_P);
		sb.append("<pre>Tempo total execução dos testes: ").append(duracaoTestes).append(HTML_CLOSE_PRE)
				.append(HTML_CLOSE_P);
		sb.append("======================================================================================");
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
	private static void appendArquivosHTMLPastaReport() throws IOException {
		appendReportGeral(HTML_OPEN_HTML);
		appendReportFalha(HTML_OPEN_HTML);
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
	private static void appendArquivosHTMLPastaProjeto(String nomeProjeto) throws IOException {
		appendReportGeralProjeto(HTML_OPEN_HTML);
		appendReportFalhaProjeto(HTML_OPEN_HTML);
		appendReportSucessoProjeto(HTML_OPEN_HTML);
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
	 *         16 de nov de 2017 16:58:01
	 *
	 */
	private static void appendArquivosHTMLPastaSuite(String nomeProjeto, String nomeSuite) throws IOException {
		appendReportGeralSuite(HTML_OPEN_HTML);
		appendReportFalhaSuite(HTML_OPEN_HTML);
		appendReportSucessoSuite(HTML_OPEN_HTML);
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
			sbFont.append(" color = '" + colorFonte + "'");
		}
		if (StringUtils.isNotBlank(sizeFonte)) {
			sbFont.append(" size = '" + sizeFonte + "'");
		}
		if (StringUtils.isNotBlank(typeFonte)) {
			sbFont.append(" face = '" + typeFonte + "'");
		}
		sbFont.append(">");

		return sbFont.toString();
	}

	private static BigDecimal getTotal(int totalTestesExecutados, int value) {
		return BigDecimal.ZERO.equals(BigDecimal.valueOf(totalTestesExecutados)) ? BigDecimal.ZERO
				: arredondaValor((100 * value) / totalTestesExecutados, 0);
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

		long diferencaSegundos = diferenca / 1000;
		long diferencaMinutos = diferenca / (60 * 1000);
		long diferencaHoras = diferenca / (60 * 60 * 1000);
		StringBuilder sbResult = new StringBuilder();
		sbResult.append(diferencaHoras).append(" horas ").append(diferencaMinutos).append(" minutos ")
				.append(diferencaSegundos).append(" segundos");
		return sbResult.toString();
	}

	public static void appendReportGeral(String... texto) {
		for (String conteudo : texto) {
			sbReportGeral.append(conteudo);
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

	public static void appendReportGeralSuite(String... texto) {
		for (String conteudo : texto) {
			sbReportGeralSuite.append(conteudo);
		}
	}

	public static void appendReportFalhaSuite(String... texto) {
		for (String conteudo : texto) {
			sbReportFalhaSuite.append(conteudo);
		}
	}

	public static void appendReportSucessoSuite(String... texto) {
		for (String conteudo : texto) {
			sbReportSucessoSuite.append(conteudo);
		}
	}

	public static void appendException(ExceptionVO exception, String openFont) {
		String closeFont = HTML_CLOSE_FONT;

		String textoException = "#### Exception: ";

		appendReportGeral(openFont, HTML_QUEBRA_LINHA, textoException, closeFont, HTML_QUEBRA_LINHA);
		appendReportFalha(openFont, HTML_QUEBRA_LINHA, textoException, closeFont, HTML_QUEBRA_LINHA);

		appendReportGeralProjeto(openFont, HTML_QUEBRA_LINHA, textoException, closeFont, HTML_QUEBRA_LINHA);
		appendReportFalhaProjeto(openFont, HTML_QUEBRA_LINHA, textoException, closeFont, HTML_QUEBRA_LINHA);

		appendReportGeralSuite(openFont, HTML_QUEBRA_LINHA, textoException, closeFont, HTML_QUEBRA_LINHA);
		appendReportFalhaSuite(openFont, HTML_QUEBRA_LINHA, textoException, closeFont, HTML_QUEBRA_LINHA);

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

				appendReportGeralProjeto(openFont, HTML_QUEBRA_LINHA, HTML_OPEN_SPAN, className, ".", methodName, "(",
						openFontBlue, fileName, ":", numeroLinha, closeFont, ")", HTML_CLOSE_SPAN, closeFont);
				appendReportFalhaProjeto(openFont, HTML_QUEBRA_LINHA, HTML_OPEN_SPAN, className, ".", methodName, "(",
						openFontBlue, fileName, ":", numeroLinha, closeFont, ")", HTML_CLOSE_SPAN, closeFont);

				appendReportGeralSuite(openFont, HTML_QUEBRA_LINHA, HTML_OPEN_SPAN, className, ".", methodName, "(",
						openFontBlue, fileName, ":", numeroLinha, closeFont, ")", HTML_CLOSE_SPAN, closeFont);
				appendReportFalhaSuite(openFont, HTML_QUEBRA_LINHA, HTML_OPEN_SPAN, className, ".", methodName, "(",
						openFontBlue, fileName, ":", numeroLinha, closeFont, ")", HTML_CLOSE_SPAN, closeFont);
			}
		}

		appendReportGeral(openFont, HTML_QUEBRA_LINHA, exception.getMessage(), closeFont, HTML_QUEBRA_LINHA);
		appendReportFalha(openFont, HTML_QUEBRA_LINHA, exception.getMessage(), closeFont, HTML_QUEBRA_LINHA);

		appendReportGeralProjeto(openFont, HTML_QUEBRA_LINHA, exception.getMessage(), closeFont, HTML_QUEBRA_LINHA);
		appendReportFalhaProjeto(openFont, HTML_QUEBRA_LINHA, exception.getMessage(), closeFont, HTML_QUEBRA_LINHA);

		appendReportGeralSuite(openFont, HTML_QUEBRA_LINHA, exception.getMessage(), closeFont, HTML_QUEBRA_LINHA);
		appendReportFalhaSuite(openFont, HTML_QUEBRA_LINHA, exception.getMessage(), closeFont, HTML_QUEBRA_LINHA);
	}

	private static void appendLinkProvaException(String caminhoPrint) throws IOException {
//		if (StringUtils.isNotBlank(diretorioPrints)) {
//			String copyArquivo = FileUtil.copyArquivo(diretorioPrints, caminhoPrint);
//			if (StringUtils.isNotBlank(copyArquivo)) {
//				caminhoPrint = copyArquivo;
//			}
//		}
		String href = "<a href='file:///";
		String tituloPrint = "'>Print do Erro</a>";
		sbReportGeral.append(href).append(caminhoPrint).append(tituloPrint).append(HTML_QUEBRA_LINHA);
		sbReportFalha.append(href).append(caminhoPrint).append(tituloPrint).append(HTML_QUEBRA_LINHA);

		sbReportGeralProjeto.append(href).append(caminhoPrint).append(tituloPrint).append(HTML_QUEBRA_LINHA);
		sbReportFalhaProjeto.append(href).append(caminhoPrint).append(tituloPrint).append(HTML_QUEBRA_LINHA);

		sbReportGeralSuite.append(href).append(caminhoPrint).append(tituloPrint).append(HTML_QUEBRA_LINHA);
		sbReportFalhaSuite.append(href).append(caminhoPrint).append(tituloPrint).append(HTML_QUEBRA_LINHA);
	}

	public static void escreverArquivosHTMLGeral() throws IOException {
		if (StringUtils.isNotBlank(sbReportFalha.toString())) {
			FileHTML.escreverArquivoFalha(sbReportFalha.toString());
			FileHTML.closeFalha();
		}
		if (StringUtils.isNotBlank(sbReportGeral.toString())) {
			FileHTML.escreverArquivoGeral(sbReportGeral.toString());
			FileHTML.closeGeral();
			FileHTML.openReportGeralInBrowser();
		}

	}

	public static void escreverArquivosHTMLProjeto() throws IOException {
		if (StringUtils.isNotBlank(sbReportFalhaProjeto.toString())) {
			FileHTML.escreverArquivoFalhaProjeto(sbReportFalhaProjeto.toString());
			FileHTML.closeFalhaProjeto();
		}
		if (StringUtils.isNotBlank(sbReportSucessoProjeto.toString())) {
			FileHTML.escreverArquivoSucessoProjeto(sbReportSucessoProjeto.toString());
			FileHTML.closeSucessoProjeto();
		}
		if (StringUtils.isNotBlank(sbReportGeralProjeto.toString())) {
			FileHTML.escreverArquivoGeralProjeto(sbReportGeralProjeto.toString());
			FileHTML.closeGeralProjeto();
		}
	}

	public static void escreverArquivosHTMLSuite() throws IOException {
		if (StringUtils.isNotBlank(sbReportFalhaSuite.toString())) {
			FileHTML.escreverArquivoFalhaSuite(sbReportFalhaSuite.toString());
			FileHTML.closeFalhaSuite();
		}
		if (StringUtils.isNotBlank(sbReportSucessoSuite.toString())) {
			FileHTML.escreverArquivoSucessoSuite(sbReportSucessoSuite.toString());
			FileHTML.closeSucessoSuite();
		}
		if (StringUtils.isNotBlank(sbReportGeralSuite.toString())) {
			FileHTML.escreverArquivoGeralSuite(sbReportGeralSuite.toString());
			FileHTML.closeGeralSuite();
		}
	}

	public static String getLegenda() {
		String marcadorSucess = " <span style='color: " + HTML_COLOR_SUCCESS + "'> &#9679; Sucess</span>";
		String marcadorFailed = " <span style='color: " + HTML_COLOR_FAILED + "'>&#9679; Failed</span>";
		String marcadorSkiped = " <span style='color: " + HTML_COLOR_SKIPED + "'>&#9679; Skiped</span>";
		String marcadorErro = " <span style='color: " + HTML_COLOR_ERRO + "'>&#9679; Erro</span>";
		String legenda = "<br/> Lengenda: " + marcadorSucess + marcadorFailed + marcadorSkiped + marcadorErro;
		return legenda;
	}
}
