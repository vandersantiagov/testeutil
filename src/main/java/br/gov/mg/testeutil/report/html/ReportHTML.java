package br.gov.mg.testeutil.report.html;

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

	private static final String SEPARATOR_RESULT_FINAL = "======================================================================================";
	private static final String FAILED = "FAILED";
	private static final String SUCCESS = "SUCCESS";
	private static final String ERRO = "ERRO";
	private static final String SKIPED = "SKIPED";

	private static final String TEXTO_INICIO_DOS_TESTES = "Inicio dos Testes: ";
	private static final String TEXTO_FIM_DOS_TESTES = "Fim dos Testes: ";
	private static final String TEXTO_PROJETO = "Projeto: ";
	private static final String TEXTO_SUITE_PRINCIPAL = "Suite Principal: ";
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

	private static final String SEPARADOR = "-----------------------------------------------------------------------------";

	private static StringBuilder sbReportGeral = new StringBuilder();
	private static StringBuilder sbReportFalha = new StringBuilder();

	private static StringBuilder sbReportGeralProjeto = new StringBuilder();
	private static StringBuilder sbReportFalhaProjeto = new StringBuilder();
	private static StringBuilder sbReportSucessoProjeto = new StringBuilder();

	private static StringBuilder sbReportGeralSuite = new StringBuilder();
	private static StringBuilder sbReportFalhaSuite = new StringBuilder();
	private static int countFalhasSuites;
	private static int countSucessoSuites;
	// private static String diretorioPrints;

	public static void createHTML(SuitePrincipalVO suitePrincipalVO) throws IOException {
		String nomeProjetoAnterior = "";
		SuiteVO suiteVO = null;
		String[] caminhoFileGeralProjeto = null;

		// Cria o arquivo html geral na pasta report.
		String[] caminhoFileGeral = FileHTML.createFilesGeral();

		// Abre a tagHTML para os arquivos.
		appendArquivosHTMLPastaReport();

		appendReportGeral(HTML_OPEN_NEGRITO, TEXTO_INICIO_DOS_TESTES,
				DateUtil.getDataFormatadaByFormato(suitePrincipalVO.getDataInicioExecucao(), DateUtil.FORMATO_DATA6),
				HTML_CLOSE_NEGRITO, HTML_QUEBRA_LINHA);
		appendReportGeral(HTML_OPEN_NEGRITO, TEXTO_SUITE_PRINCIPAL, HTML_CLOSE_NEGRITO, suitePrincipalVO.getNomeSuite(),
				HTML_QUEBRA_LINHA);

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
						caminhoFileGeralProjeto = FileHTML.createFilesProjeto(key.getKey1());
						appendArquivosHTMLPastaProjeto(key.getKey1());
					}
				} else {
					caminhoFileGeralProjeto = FileHTML.createFilesProjeto(key.getKey1());
					appendArquivosHTMLPastaProjeto(key.getKey1());
				}

				suiteVO = suiteByKey.getValue();
				nomeProjetoAnterior = key.getKey1();
				appendCabecalho(suitePrincipalVO, suiteVO);
				appendClassesDeTeste(suiteVO, caminhoFileGeral, caminhoFileGeralProjeto);

				SuiteSiare.setQuantitativoRunVO(suiteVO.getQuantitativoRunVO(), suitePrincipalVO.getQuantitativoRun());
			}
			appendResultRun(suiteVO.getQuantitativoRunVO(), suiteVO.getDataInicioExecucao(),
					suiteVO.getDataFimExecucao(), sbReportGeralProjeto);

			appendReportGeralProjeto(HTML_QUEBRA_LINHA, HTML_OPEN_NEGRITO, TEXTO_FIM_DOS_TESTES,
					DateUtil.getDataFormatadaByFormato(suiteVO.getDataFimExecucao(), DateUtil.FORMATO_DATA6),
					HTML_CLOSE_NEGRITO, HTML_QUEBRA_LINHA);

			escreverArquivosHTMLProjeto();
		}

		appendResultRun(suitePrincipalVO.getQuantitativoRun(), suitePrincipalVO.getDataInicioExecucao(),
				suitePrincipalVO.getDataFimExecucao(), sbReportGeral);

		appendReportGeral(HTML_QUEBRA_LINHA, HTML_OPEN_NEGRITO, TEXTO_FIM_DOS_TESTES,
				DateUtil.getDataFormatadaByFormato(suitePrincipalVO.getDataFimExecucao(), DateUtil.FORMATO_DATA6),
				HTML_CLOSE_NEGRITO, HTML_QUEBRA_LINHA);

		escreverArquivosHTMLGeral();
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
		appendResultRun(suiteVO.getQuantitativoRunVO(), suiteVO.getDataInicioExecucao(), suiteVO.getDataFimExecucao(),
				sbReportGeralProjeto);
		appendReportGeralProjeto(HTML_QUEBRA_LINHA, HTML_OPEN_NEGRITO, TEXTO_FIM_DOS_TESTES,
				DateUtil.getDataFormatadaByFormato(suiteVO.getDataFimExecucao(), DateUtil.FORMATO_DATA6),
				HTML_CLOSE_NEGRITO, HTML_QUEBRA_LINHA);
		escreverArquivosHTMLProjeto();
	}

	public static void appendClassesDeTeste(SuiteVO suiteVO, String[] caminhoFileGeral,
			String[] caminhoFileGeralProjeto) throws IOException {
		Map<String, ClasseDeTesteVO> classesDeTesteByName = suiteVO.getClassesDeTesteByName();
		if (MapUtils.isNotEmpty(classesDeTesteByName)) {
			String classeAnterior = "";
			for (Entry<String, ClasseDeTesteVO> ct : classesDeTesteByName.entrySet()) {
				classeAnterior = treathClasseDeTeste(classeAnterior, ct, caminhoFileGeral, caminhoFileGeralProjeto);
			}
		}
	}

	private static void appendDataInicioTestesProjeto(Date dataInicio) {
		appendReportGeralProjeto(HTML_OPEN_NEGRITO, TEXTO_INICIO_DOS_TESTES,
				DateUtil.getDataFormatadaByFormato(dataInicio, DateUtil.FORMATO_DATA6), HTML_CLOSE_NEGRITO,
				HTML_QUEBRA_LINHA);
	}

	private static String treathClasseDeTeste(String classeAnterior, Entry<String, ClasseDeTesteVO> ct,
			String[] caminhoFileGeral, String[] caminhoFileGeralProjeto) throws IOException {
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
					classeDeTeste.getNomeClasse(), "(", getValorArredondado(totalExecucaoMetodos), " s)",
					HTML_QUEBRA_LINHA);
		}
		if (CollectionUtils.isNotEmpty(metodos)) {
			for (MetodoClasseTesteVO metodo : metodos) {
				treathMetodos(classeDeTeste, metodo, caminhoFileGeral, caminhoFileGeralProjeto);
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
			String[] caminhoFileGeral, String[] caminhoFileGeralProjeto) throws IOException {
		String openFontSuccess = openFont(HTML_COLOR_SUCCESS, "", "");
		String openFontErro = openFont(HTML_COLOR_ERRO, "", "");
		String openFontFalha = openFont(HTML_COLOR_FAILED, "", "");
		String openFontSkiped = openFont(HTML_COLOR_SKIPED, "", "");
		if (metodo.isFalha()) {
			treathFalhaAndErro(classeDeTeste, metodo, openFontFalha, FAILED, caminhoFileGeral, caminhoFileGeralProjeto);
		} else if (metodo.isErro()) {
			treathFalhaAndErro(classeDeTeste, metodo, openFontErro, ERRO, caminhoFileGeral, caminhoFileGeralProjeto);
		} else if (metodo.isSucess()) {
			appendInformacoesMetodo(metodo, openFontSuccess, metodo.getNome(), SUCCESS);
		} else if (metodo.isSkiped()) {
			appendInformacoesMetodo(metodo, openFontSkiped, metodo.getNome(), SKIPED);
		}
	}

	private static void treathFalhaAndErro(ClasseDeTesteVO classeDeTeste, MetodoClasseTesteVO metodo, String openFont,
			String tipoMetodo, String[] caminhoFileGeral, String[] caminhoFileGeralProjeto) throws IOException {
		appendInformacoesMetodo(metodo, openFont, metodo.getNome(), tipoMetodo);
		if (CollectionUtils.isNotEmpty(metodo.getExceptions())) {
			for (ExceptionVO exception : metodo.getExceptions()) {
				appendException(exception, openFont);
			}
		}
		appendLinkProvaException(metodo.getCaminhoPrintErro(), caminhoFileGeral, caminhoFileGeralProjeto);
	}

	private static void appendInformacoesMetodo(MetodoClasseTesteVO metodo, String openFont, String nomeMetodo,
			String tipoOcorrencia) {
		appendReportGeral(openFont, HTML_OPEN_SPAN, TEXTO_METODO, nomeMetodo,
				metodo.getTextoTempoTotalExecucaoEmSegundos() + HTML_CLOSE_SPAN + HTML_CLOSE_FONT + HTML_QUEBRA_LINHA);

		appendReportGeralProjeto(openFont, HTML_OPEN_SPAN, TEXTO_METODO, nomeMetodo,
				metodo.getTextoTempoTotalExecucaoEmSegundos() + HTML_CLOSE_SPAN + HTML_CLOSE_FONT + HTML_QUEBRA_LINHA);

		if (SUCCESS.equals(tipoOcorrencia)) {
			countSucessoSuites += 1;
			appendReportSucessoProjeto(openFont, HTML_OPEN_SPAN, TEXTO_METODO, nomeMetodo,
					metodo.getTextoTempoTotalExecucaoEmSegundos() + HTML_CLOSE_SPAN + HTML_CLOSE_FONT
							+ HTML_QUEBRA_LINHA);
		} else if (FAILED.equals(tipoOcorrencia) || ERRO.equals(tipoOcorrencia)) {
			countFalhasSuites += 1;
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
		sbCountFailed.append("Failures: ").append(quantidadeFalha);

		StringBuilder sbCountErro = new StringBuilder();
		sbCountErro.append("Erro: ").append(quantidadeErro).append(HTML_CLOSE_SPAN);

		StringBuilder sbPercentualSuccess = new StringBuilder();
		sbPercentualSuccess.append("Success: ")
				.append(arredondaValor((Double.valueOf(quantidadeSucesso) / Double.valueOf(quantidadeRun)) * 100, 2))
				.append(simboloPercentual);

		StringBuilder sbPercentualFailed = new StringBuilder();
		sbPercentualFailed.append("Failures: ").append(getTotal(quantidadeRun, quantidadeFalha))
				.append(simboloPercentual);

		StringBuilder sbPercentualErro = new StringBuilder();
		sbPercentualErro.append("Erro: ").append(getTotal(quantidadeRun, quantidadeErro)).append(simboloPercentual);

		StringBuilder sbResultCount = new StringBuilder();
		sbResultCount.append(sbTitulo.toString()).append(HTML_OPEN_P).append("  Quantidade: ").append(HTML_QUEBRA_LINHA)
				.append(HTML_OPEN_SPAN).append("Runs: ").append(quantidadeRun).append(HTML_CLOSE_SPAN)
				.append(HTML_QUEBRA_LINHA).append(HTML_OPEN_SPAN).append(sbCountFailed.toString())
				.append(HTML_CLOSE_SPAN).append(HTML_QUEBRA_LINHA).append(HTML_OPEN_SPAN).append(sbCountErro.toString())
				.append(HTML_CLOSE_SPAN);

		StringBuilder sbResultPercentual = new StringBuilder();
		sbResultPercentual.append(HTML_OPEN_P).append("  Percentual: ").append(HTML_QUEBRA_LINHA).append(HTML_OPEN_SPAN)
				.append(sbPercentualSuccess.toString()).append(HTML_CLOSE_SPAN).append(HTML_QUEBRA_LINHA)
				.append(HTML_OPEN_SPAN).append(sbPercentualFailed.toString()).append(HTML_CLOSE_SPAN)
				.append(HTML_QUEBRA_LINHA).append(HTML_OPEN_SPAN).append(sbPercentualErro.toString())
				.append(HTML_CLOSE_SPAN);

		String duracaoTestes = getTotalDuracaoTestes(dataFim, dataInicio);

		sb.append(HTML_QUEBRA_LINHA);
		sb.append(SEPARATOR_RESULT_FINAL);
		sb.append(HTML_OPEN_PRE).append(sbResultCount.toString()).append(HTML_CLOSE_PRE).append(HTML_CLOSE_P);
		sb.append(HTML_OPEN_PRE).append(sbResultPercentual.toString()).append(HTML_CLOSE_PRE).append(HTML_CLOSE_P);
		sb.append("<pre>Tempo total execução dos testes: ").append(duracaoTestes).append(HTML_CLOSE_PRE)
				.append(HTML_CLOSE_P);
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
		sbResult.append(diferencaDias).append(" dias ").append(diferencaHoras).append(" horas ")
				.append(diferencaMinutos).append(" minutos ").append(diferencaSegundos).append(" segundos");
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

	public static void appendException(ExceptionVO exception, String openFont) {
		String closeFont = HTML_CLOSE_FONT;

		String textoException = "#### Exception: ";

		appendReportGeral(openFont, HTML_QUEBRA_LINHA, textoException, closeFont, HTML_QUEBRA_LINHA);
		appendReportFalha(openFont, HTML_QUEBRA_LINHA, textoException, closeFont, HTML_QUEBRA_LINHA);

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
				appendReportGeral(openFont, HTML_QUEBRA_LINHA, HTML_OPEN_SPAN, className, ".", methodName, "(",
						openFontBlue, fileName, ":", numeroLinha, closeFont, ")", HTML_CLOSE_SPAN, closeFont);
				appendReportFalha(openFont, HTML_QUEBRA_LINHA, HTML_OPEN_SPAN, className, ".", methodName, "(",
						openFontBlue, fileName, ":", numeroLinha, closeFont, ")", HTML_CLOSE_SPAN, closeFont);

				appendReportGeralProjeto(openFont, HTML_QUEBRA_LINHA, HTML_OPEN_SPAN, className, ".", methodName, "(",
						openFontBlue, fileName, ":", numeroLinha, closeFont, ")", HTML_CLOSE_SPAN, closeFont);
				appendReportFalhaProjeto(openFont, HTML_QUEBRA_LINHA, HTML_OPEN_SPAN, className, ".", methodName, "(",
						openFontBlue, fileName, ":", numeroLinha, closeFont, ")", HTML_CLOSE_SPAN, closeFont);

			}
		}

		appendReportGeral(openFont, HTML_QUEBRA_LINHA, exception.getMessage(), closeFont, HTML_QUEBRA_LINHA);
		appendReportFalha(openFont, HTML_QUEBRA_LINHA, exception.getMessage(), closeFont, HTML_QUEBRA_LINHA);

		appendReportGeralProjeto(openFont, HTML_QUEBRA_LINHA, exception.getMessage(), closeFont, HTML_QUEBRA_LINHA);
		appendReportFalhaProjeto(openFont, HTML_QUEBRA_LINHA, exception.getMessage(), closeFont, HTML_QUEBRA_LINHA);

	}

	private static void appendLinkProvaException(String caminhoOrigemPrint, String[] caminhoFileGeral,
			String[] caminhoFileGeralProjeto) throws IOException {

		int lastIndexOf = 0;
		String destinoArquivo = "";
		String destinoPrint = "";
		String href = "<a href='file:///";
		String tituloPrint = "'>Print do Erro</a>";

		String caminhoReportGeral = caminhoFileGeral[0];
		lastIndexOf = caminhoReportGeral.lastIndexOf("\\");
		destinoPrint = caminhoReportGeral.substring(0, lastIndexOf + 1);
		destinoArquivo = copyArquivo(caminhoOrigemPrint, destinoPrint);
		sbReportGeral.append(href).append(destinoArquivo).append(tituloPrint).append(HTML_QUEBRA_LINHA);

		String caminhoReportFalha = caminhoFileGeral[1];
		lastIndexOf = caminhoReportFalha.lastIndexOf("\\");
		destinoPrint = caminhoReportFalha.substring(0, lastIndexOf + 1);
		destinoArquivo = copyArquivo(caminhoOrigemPrint, destinoPrint);
		sbReportFalha.append(href).append(destinoArquivo).append(tituloPrint).append(HTML_QUEBRA_LINHA);

		String caminhoReportGeralProjeto = caminhoFileGeralProjeto[0];
		lastIndexOf = caminhoReportGeralProjeto.lastIndexOf("\\");
		destinoPrint = caminhoReportGeralProjeto.substring(0, lastIndexOf + 1);
		destinoArquivo = copyArquivo(caminhoOrigemPrint, destinoPrint);
		sbReportGeralProjeto.append(href).append(destinoArquivo).append(tituloPrint).append(HTML_QUEBRA_LINHA);

		String caminhoReportFalhaProjeto = caminhoFileGeralProjeto[1];
		lastIndexOf = caminhoReportFalhaProjeto.lastIndexOf("\\");
		destinoPrint = caminhoReportFalhaProjeto.substring(0, lastIndexOf + 1);
		destinoArquivo = copyArquivo(caminhoOrigemPrint, destinoPrint);
		sbReportFalhaProjeto.append(href).append(destinoArquivo).append(tituloPrint).append(HTML_QUEBRA_LINHA);

		sbReportGeralSuite.append(href).append(destinoArquivo).append(tituloPrint).append(HTML_QUEBRA_LINHA);
		sbReportFalhaSuite.append(href).append(destinoArquivo).append(tituloPrint).append(HTML_QUEBRA_LINHA);
	}

	private static String copyArquivo(String origemPrint, String destinoPrint) throws IOException {
		File destinoDir = new File(destinoPrint);
		File srcFile = new File(origemPrint);
		FileUtils.copyFileToDirectory(srcFile, destinoDir, true);
		return destinoDir.getPath() + "\\" + srcFile.getName();
	}

	public static void escreverArquivosHTMLGeral() throws IOException {
		if (StringUtils.isNotBlank(sbReportFalha.toString())) {
			appendReportFalha(HTML_CLOSE_HTML);
			FileHTML.escreverArquivoFalha(sbReportFalha.toString());
			FileHTML.closeFalha();
		}
		if (StringUtils.isNotBlank(sbReportGeral.toString())) {
			appendReportGeral(getLegenda());
			appendReportGeral(HTML_CLOSE_HTML);
			FileHTML.escreverArquivoGeral(sbReportGeral.toString());
			FileHTML.closePastaGeral();
			FileHTML.openReportGeralInBrowser();
		}

	}

	public static void escreverArquivosHTMLProjeto() throws IOException {
		if (StringUtils.isNotBlank(sbReportSucessoProjeto.toString())) {

			appendReportSucessoProjeto(HTML_QUEBRA_LINHA, SEPARADOR);
			appendReportSucessoProjeto(HTML_QUEBRA_LINHA, HTML_OPEN_NEGRITO, TEXTO_NUMERO_SUCESSO,
					Integer.toString(countSucessoSuites), HTML_CLOSE_NEGRITO);
			appendReportSucessoProjeto(HTML_QUEBRA_LINHA, SEPARADOR);
			countSucessoSuites = 0;

			appendReportSucessoProjeto(HTML_CLOSE_HTML);
			FileHTML.escreverArquivoSucessoProjeto(sbReportSucessoProjeto.toString());
			FileHTML.closeSucessoProjeto();
		}
		if (StringUtils.isNotBlank(sbReportFalhaProjeto.toString())) {
			appendReportFalhaProjeto(HTML_QUEBRA_LINHA, SEPARADOR);
			appendReportFalhaProjeto(HTML_QUEBRA_LINHA, HTML_OPEN_NEGRITO, TEXTO_NUMERO_FALHA,
					Integer.toString(countFalhasSuites), HTML_CLOSE_NEGRITO);
			appendReportFalhaProjeto(HTML_QUEBRA_LINHA, SEPARADOR);
			countFalhasSuites = 0;

			appendReportFalhaProjeto(HTML_CLOSE_HTML);
			FileHTML.escreverArquivoFalhaProjeto(sbReportFalhaProjeto.toString());
			FileHTML.closeFalhaProjeto();
		}
		if (StringUtils.isNotBlank(sbReportGeralProjeto.toString())) {
			appendReportGeralProjeto(getLegenda());
			appendReportGeralProjeto(HTML_CLOSE_HTML);
			FileHTML.escreverArquivoGeralProjeto(sbReportGeralProjeto.toString());
			FileHTML.closeGeralProjeto();
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
