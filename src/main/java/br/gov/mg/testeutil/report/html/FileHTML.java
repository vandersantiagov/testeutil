package br.gov.mg.testeutil.report.html;

import static br.gov.mg.testeutil.util.FileUtil.createArquivo;
import static br.gov.mg.testeutil.util.FileUtil.createDiretorios;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import br.gov.mg.testeutil.enums.TipoArquivoEnum;
import br.gov.mg.testeutil.metodos.MetodosSiare;
import br.gov.mg.testeutil.util.DateUtil;
import br.gov.mg.testeutil.util.FileUtil;

/**
 * @author sandra.rodrigues
 */
public class FileHTML {

	public static final String HTML_OPEN_HTML = "<html><head><meta charset='UTF-8'></head><body style='font: 1em Arial, Helvetica, sans-serif;'>";
	public static final String HTML_CLOSE_HTML = "</body></html>";
	public static final String HTML_OPEN_TABLE_HTML = "<table border='0' bgcolor = 'EFEFEF' width='100.0%' height='20'>";
	public static final String HTML_CLOSE_TABLE_HTML = "</table>";
	public static final String HTML_OPEN_TR_HTML = "<tr>";
	public static final String HTML_CLOSE_TR_HTML = "</tr>";
	public static final String HTML_OPEN_TD_HTML = "<td>";
	public static final String HTML_CLOSE_TD_HTML = "</td>";
	public static final String HTML_QUEBRA_LINHA = "<br/>";
	public static final String HTML_OPEN_NEGRITO = "<b>";
	public static final String HTML_CLOSE_NEGRITO = "</b>";
	public static final String HTML_OPEN_SPAN = "<span style='padding-left: 1cm; font: 1em Arial, Helvetica, sans-serif;'>";
	public static final String HTML_CLOSE_SPAN = "</span>";
	public static final String HTML_OPEN_PRE = "<pre style='font: 1em Arial, Helvetica, sans-serif;'>";
	public static final String HTML_CLOSE_PRE = "</pre>";
	public static final String HTML_OPEN_P = "<p style='font: 1em Arial, Helvetica, sans-serif;'>";
	public static final String HTML_CLOSE_P = "</p>";
	public static final String HTML_CLOSE_FONT = "</font>";
	public static final String HTML_OPEN_DETAILS = "<details>";
	public static final String HTML_CLOSE_DETAILS = "</details>";
	public static final String HTML_OPEN_SUMMARY = "<summary>";
	public static final String HTML_CLOSE_SUMMARY = "</summary>";

	public static final String PATH_DIRETORIO_REPORT = MetodosSiare.diretorioPrincipal + "Relatorios";
	private static final String FORMATO_DATA_CRIACAO_ARQUIVO = DateUtil.FORMATO_DATA1;
	private static final String PROJETO = "PROJETO_";
	private static final String GERAL = "_Geral";
	private static final String GERALM = "_GeralM";
	private static final String SUCESSO = "_Sucesso";
	private static final String FALHA = "_Falha";
	private static final String DUAS_CONTRA_BARRAS = "\\";

	private static File fileReportGeralMinimized;

	private static File fileReportGeral;
	private static File fileReportFalha;

	private static File fileReportGeralProjeto;
	private static File fileReportFalhaProjeto;
	private static File fileReportSucessoProjeto;

	public static BufferedWriter bwReportPastaGeral;
	public static BufferedWriter bwReportPastaGeralMinimized;
	public static BufferedWriter bwReportFalha;

	public static BufferedWriter bwReportGeralProjeto;
	public static BufferedWriter bwReportFalhaProjeto;
	public static BufferedWriter bwReportSucessoProjeto;

	/**
	 * Cria arquivos dentro da pasta Report e retorna o caminho do relatorio
	 * geral.
	 * 
	 * @throws IOException
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:18:51
	 *
	 */
	protected static String[] createFilesGeral(String nomePastaProjetoPrincipal, String path, Date date)
			throws IOException {

		String dataFormatada = getDataFormatada(date, FORMATO_DATA_CRIACAO_ARQUIVO);
		String caminhoArquivo = PATH_DIRETORIO_REPORT + DUAS_CONTRA_BARRAS + dataFormatada;
		if (StringUtils.isNotBlank(nomePastaProjetoPrincipal)) {
			caminhoArquivo = path + DUAS_CONTRA_BARRAS + dataFormatada;
		}
		createDiretorios(caminhoArquivo);
		fileReportGeral = createArquivo(caminhoArquivo + DUAS_CONTRA_BARRAS + dataFormatada + GERAL,
				TipoArquivoEnum.HTML);
		bwReportPastaGeral = new BufferedWriter(new FileWriter(fileReportGeral, true));

		fileReportFalha = createArquivo(caminhoArquivo + DUAS_CONTRA_BARRAS + dataFormatada + FALHA,
				TipoArquivoEnum.HTML);
		bwReportFalha = new BufferedWriter(new FileWriter(fileReportFalha, true));

		String pathReturn[] = { fileReportGeral.getPath(), fileReportFalha.getPath() };
		return pathReturn;
	}

	/**
	 * Cria arquivos dentro da pasta Report e retorna o caminho do relatorio
	 * geral.
	 * 
	 * @throws IOException
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:18:51
	 *
	 */
	protected static String[] createFilesGeralMinimized(String nomePastaProjetoPrincipal, String path, Date date)
			throws IOException {

		String dataFormatada = getDataFormatada(date, FORMATO_DATA_CRIACAO_ARQUIVO);
		String caminhoArquivo = PATH_DIRETORIO_REPORT + DUAS_CONTRA_BARRAS + dataFormatada;
		if (StringUtils.isNotBlank(nomePastaProjetoPrincipal)) {
			caminhoArquivo = path + DUAS_CONTRA_BARRAS + dataFormatada;
		}
		createDiretorios(caminhoArquivo);

		fileReportGeralMinimized = createArquivo(caminhoArquivo + DUAS_CONTRA_BARRAS + dataFormatada + GERALM,
				TipoArquivoEnum.HTML);
		bwReportPastaGeralMinimized = new BufferedWriter(new FileWriter(fileReportGeralMinimized, true));

		String pathReturn[] = { fileReportGeralMinimized.getPath() };
		return pathReturn;
	}

	protected static String getPathReportGeral(String nomePastaProjetoPrincipal) {
		String path = PATH_DIRETORIO_REPORT + DUAS_CONTRA_BARRAS;
		if (StringUtils.isNotBlank(nomePastaProjetoPrincipal)) {
			path = PATH_DIRETORIO_REPORT + DUAS_CONTRA_BARRAS + PROJETO + nomePastaProjetoPrincipal;
		}
		return path;
	}

	protected static String getPathReportGeralMinimized(String nomePastaProjetoPrincipal) {
		String path = PATH_DIRETORIO_REPORT + DUAS_CONTRA_BARRAS;
		if (StringUtils.isNotBlank(nomePastaProjetoPrincipal)) {
			path = PATH_DIRETORIO_REPORT + DUAS_CONTRA_BARRAS + PROJETO + nomePastaProjetoPrincipal;
		}
		return path;
	}

	protected static void deleteArquivosDatasAntigas(String caminhoArquivo, boolean deletePastaProjeto) {
		FileUtil.deleteArquivoByPath(new File(caminhoArquivo), deletePastaProjeto);
		System.out.println("Número de arquivos deletados: " + FileUtil.getLogDelete().size());
		for (String fileDeletado : FileUtil.getLogDelete()) {
			System.out.println(fileDeletado);
		}
	}

	/**
	 * Cria arquivos dentro da pasta projeto e retorna o caminho do relatorio
	 * geral.
	 * 
	 * @param nomeProjeto
	 * @throws IOException
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:19:09
	 *
	 */
	public static String[] createFilesProjeto(String nomeProjeto, String nomeProjetoPrincipal, String path, Date date)
			throws IOException {
		String dataFormatada = getDataFormatada(date, FORMATO_DATA_CRIACAO_ARQUIVO);
		String caminhoArquivo = path + DUAS_CONTRA_BARRAS + dataFormatada;
		if (StringUtils.isNotBlank(nomeProjetoPrincipal)) {
			caminhoArquivo = path + DUAS_CONTRA_BARRAS + dataFormatada;
		}
		createDiretorios(caminhoArquivo);
		fileReportGeralProjeto = createArquivo(caminhoArquivo + DUAS_CONTRA_BARRAS + dataFormatada + GERAL,
				TipoArquivoEnum.HTML);
		bwReportGeralProjeto = new BufferedWriter(new FileWriter(fileReportGeralProjeto, true));

		fileReportFalhaProjeto = createArquivo(caminhoArquivo + DUAS_CONTRA_BARRAS + dataFormatada + FALHA,
				TipoArquivoEnum.HTML);
		bwReportFalhaProjeto = new BufferedWriter(new FileWriter(fileReportFalhaProjeto, true));

		fileReportSucessoProjeto = createArquivo(caminhoArquivo + DUAS_CONTRA_BARRAS + dataFormatada + SUCESSO,
				TipoArquivoEnum.HTML);
		bwReportSucessoProjeto = new BufferedWriter(new FileWriter(fileReportSucessoProjeto, true));

		String pathReturn[] = { fileReportGeralProjeto.getPath(), fileReportFalhaProjeto.getPath() };

		return pathReturn;
	}

	protected static String getPathReportGeralProjeto(String nomeProjeto, String nomeProjetoPrincipal) {
		String path = PATH_DIRETORIO_REPORT + DUAS_CONTRA_BARRAS + PROJETO + nomeProjeto;
		if (StringUtils.isNotBlank(nomeProjetoPrincipal)) {
			path = PATH_DIRETORIO_REPORT + DUAS_CONTRA_BARRAS + PROJETO + nomeProjetoPrincipal + DUAS_CONTRA_BARRAS
					+ PROJETO + nomeProjeto;
		}
		return path;
	}

	/**
	 * Retorna a data formatada conforme formato informado no parâmetro.
	 * 
	 * @return
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:44:19
	 */
	private static String getDataFormatada(Date date, String formato) {
		return DateUtil.getDataFormatadaByFormato(date, formato);
	}

	/**
	 * Escreve no arquivo de report geral o texto passado no parâmetro.
	 * 
	 * @param texto
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:47:16
	 *
	 */
	public static void escreverArquivoGeral(String... textos) {
		try {
			if (bwReportPastaGeral != null) {
				for (String texto : textos) {
					bwReportPastaGeral.write(texto);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * Escreve no arquivo de report geral o texto passado no parâmetro.
	 * 
	 * @param texto
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:47:16
	 *
	 */
	public static void escreverArquivoGeralMinimized(String... textos) {
		try {
			if (bwReportPastaGeralMinimized != null) {
				for (String texto : textos) {
					bwReportPastaGeralMinimized.write(texto);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * Escreve no arquivo de report geral falha o texto passado no parâmetro.
	 * 
	 * @param texto
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:47:34
	 *
	 */
	public static void escreverArquivoFalha(String... textos) {
		try {
			if (bwReportFalha != null) {
				for (String texto : textos) {
					bwReportFalha.write(texto);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * Escreve no arquivo de report geral projeto o texto passado no parâmetro.
	 * 
	 * @param texto
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:49:39
	 *
	 */
	public static void escreverArquivoGeralProjeto(String... textos) {
		try {
			if (bwReportGeralProjeto != null) {
				for (String texto : textos) {
					bwReportGeralProjeto.write(texto);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * Escreve no arquivo de report falha projeto o texto passado no parâmetro.
	 * 
	 * @param texto
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:49:45
	 *
	 */
	public static void escreverArquivoFalhaProjeto(String... textos) {
		try {
			if (bwReportFalhaProjeto != null) {
				for (String texto : textos) {
					bwReportFalhaProjeto.write(texto);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * Escreve no arquivo de report sucesso projeto o texto passado no
	 * parâmetro.
	 * 
	 * @param texto
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:49:53
	 *
	 */
	public static void escreverArquivoSucessoProjeto(String... textos) {
		try {
			if (bwReportSucessoProjeto != null) {
				for (String texto : textos) {
					bwReportSucessoProjeto.write(texto);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fechar escrita reportGeral (BufferedWriter);
	 * 
	 * @throws IOException
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:54:54
	 *
	 */
	public static void closePastaGeral() throws IOException {
		bwReportPastaGeral.close();
	}

	/**
	 * Fechar escrita reportGeral (BufferedWriter);
	 * 
	 * @throws IOException
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:54:54
	 *
	 */
	public static void closePastaGeralMinimized() throws IOException {
		bwReportPastaGeralMinimized.close();
	}

	/**
	 * Fechar escrita reportFalha (BufferedWriter);
	 * 
	 * @throws IOException
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:56:02
	 *
	 */
	public static void closeFalha() throws IOException {
		bwReportFalha.close();
	}

	/**
	 * Fechar escrita reportGeralProjeto (BufferedWriter);
	 * 
	 * @throws IOException
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:56:10
	 *
	 */
	public static void closeGeralProjeto() throws IOException {
		bwReportGeralProjeto.close();
	}

	/**
	 * Fechar escrita reportFalhaProjeto (BufferedWriter);
	 * 
	 * @throws IOException
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:56:19
	 *
	 */
	public static void closeFalhaProjeto() throws IOException {
		bwReportFalhaProjeto.close();
	}

	/**
	 * Fechar escrita reportSucessoProjeto (BufferedWriter);
	 * 
	 * @throws IOException
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:56:31
	 *
	 */
	public static void closeSucessoProjeto() throws IOException {
		bwReportSucessoProjeto.close();
	}

	public static void openReportGeralInBrowser() throws IOException {
		Desktop.getDesktop().browse(fileReportGeral.toURI());
	}

	public static void openReportGeralMinimizedInBrowser() throws IOException {
		Desktop.getDesktop().browse(fileReportGeralMinimized.toURI());
	}

	public static String generateHTMLByText(String path, String... conteudo) {
		File filePilhaErro = null;
		try {

			filePilhaErro = createArquivo(path, TipoArquivoEnum.HTML);
			BufferedWriter bwEscrever = new BufferedWriter(new FileWriter(filePilhaErro, true));

			if (bwEscrever != null) {
				for (String texto : conteudo) {
					bwEscrever.write(texto);
				}
			}

			bwEscrever.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return filePilhaErro.getPath();
	}
}
