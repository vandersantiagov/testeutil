package br.gov.mg.testeutil.report.html;

import static br.gov.mg.testeutil.util.FileUtil.createArquivo;
import static br.gov.mg.testeutil.util.FileUtil.createDiretorios;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import br.gov.mg.testeutil.enums.TipoArquivoEnum;
import br.gov.mg.testeutil.metodos.MetodosSiare;
import br.gov.mg.testeutil.util.DateUtil;
import br.gov.mg.testeutil.util.FileUtil;

/**
 * @author sandra.rodrigues
 */
public class FileHTML {

	protected FileHTML() {
	}

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
	private static int MANTER_QUANTIDADE = 10;

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

	public static List<String> logNotDeleted = new ArrayList<>();
	public static List<String> logDelete = new ArrayList<>();

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

		List<String> asList = Arrays.asList(fileReportGeral.getPath(), fileReportFalha.getPath());
		return asList.toArray(new String[0]);
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

	protected static String getPathReport(String nomePastaProjetoPrincipal) {
		String path = PATH_DIRETORIO_REPORT + DUAS_CONTRA_BARRAS;
		if (StringUtils.isNotBlank(nomePastaProjetoPrincipal)) {
			path = PATH_DIRETORIO_REPORT + DUAS_CONTRA_BARRAS + PROJETO + nomePastaProjetoPrincipal;
		}
		return path;
	}

	protected static void deleteArquivosDatasAntigas(String caminhoArquivo, boolean deletePastaProjeto) {
		deleteArquivoByPath(caminhoArquivo, deletePastaProjeto);
		System.out.println("Número de arquivos deletados: " + getLogDelete().size());
		for (String fileDeletado : getLogDelete()) {
			System.out.println(fileDeletado);
		}
	}

	/**
	 * Deleta todos os arquivos de extensão html, txt, jpeg e jpg que existe no
	 * local informado no parâmetro caminho. <br/>
	 * Ex.: Se informar "Z:\\ArtefatosWebdriver\\Report\\PROJETO_X\\" na
	 * variavel caminho então: <br/>
	 * Este método encontrará todos os arquivos .html ou .html ou .txt ou .jpeg
	 * ou .jpg na pasta PROJETO_X, que se encontra dentro da pasta Report, que
	 * está dentro de ArtefatosWebdriver, que está dentro de "Z:". <br/>
	 * Se for informado <b>false</b> na variável "deletePastaProjeto", pastas
	 * que contem o texto "PROJETO_" em seu nome <b>não</b> serão deletadas.
	 * 
	 * @param caminho
	 * @return
	 */
	private static void deleteArquivoByPath(String caminhoArquivo, boolean deletePastaProjeto) {
		File caminho = new File(caminhoArquivo);
		File arrayFiles[] = caminho.listFiles();

		String[] tiposArquivosToDelete = { TipoArquivoEnum.HTML.getTipoArquivo(), TipoArquivoEnum.TXT.getTipoArquivo(),
				TipoArquivoEnum.JPEG.getTipoArquivo(), TipoArquivoEnum.JPG.getTipoArquivo(),
				TipoArquivoEnum.PNG.getTipoArquivo() };

		List<File> arquivosToDelete = new ArrayList<File>();

		if (ArrayUtils.isNotEmpty(arrayFiles)) {
			if (!deletePastaProjeto) {
				arquivosToDelete = FileUtil.getArquivosToDelete(arrayFiles);
			}

			int quantidadeManterArquivos = MANTER_QUANTIDADE;
			if (arquivosToDelete.size() > quantidadeManterArquivos) {

				int count = arquivosToDelete.size() - quantidadeManterArquivos;
				FileUtil.ordenaArquivosPorDataModificacao(arquivosToDelete, true);
				for (File arquivo : arquivosToDelete) {
					boolean deletou = deleteArquivo(arquivo, deletePastaProjeto, tiposArquivosToDelete);
					if (deletou) {
						count--;
					}
					if (count <= 0) {
						break;
					}
				}
			}
		}
	}

	private static boolean isArquivoToDelete(File arquivo, String[] tiposArquivosToDelete) {
		boolean isPastaProjetoGeral = StringUtils.containsIgnoreCase(arquivo.getPath(), "AutomacaoSiareWebDriver_QA");
		boolean containsTextoProjeto = StringUtils.containsIgnoreCase(arquivo.getName(), "Projeto_");
		boolean isDirectory = arquivo.isDirectory();
		boolean isDirectoryEmpty = arquivo.length() <= 0;
		boolean isTipoArquivoToDelete = StringUtils.endsWithAny(arquivo.getName().toLowerCase(), tiposArquivosToDelete);
		return (isPastaProjetoGeral && !containsTextoProjeto)
				|| (!containsTextoProjeto && (isTipoArquivoToDelete || isDirectory || isDirectoryEmpty));
	}

	private static boolean deleteArquivo(File arquivo, boolean deletePastaProjeto, String[] tiposArquivosToDelete) {
		String nameArquivo = arquivo.getName();
		String lastDateModify = DateUtil.getDataFormatadaByFormato(new Date(arquivo.lastModified()),
				DateUtil.FORMATO_DATA7);

		boolean wasExcluded = false;

		boolean isArquivoToDelete = isArquivoToDelete(arquivo, tiposArquivosToDelete);
		if (deletePastaProjeto || isArquivoToDelete) {
			wasExcluded = arquivo.delete();
			if (!wasExcluded) {
				File[] listFiles = arquivo.listFiles();
				if (ArrayUtils.isNotEmpty(listFiles)) {
					for (File file : listFiles) {
						if (deletePastaProjeto || isArquivoToDelete(file, tiposArquivosToDelete)) {
							wasExcluded |= file.delete();
						} else {
							wasExcluded = false;
						}
					}
				}
			}
			wasExcluded = arquivo.delete();
		}
		String dadosArquivo = "Arquivo " + nameArquivo + ", data: " + lastDateModify;
		if (wasExcluded) {
			getLogDelete().add(dadosArquivo + " Foi deletado com sucesso!");
		} else {
			getLogNotDeleted().add(dadosArquivo + " Não foi deletado.");
		}
		return wasExcluded;
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
		if (bwReportPastaGeral != null) {
			for (String texto : textos) {
				try {
					bwReportPastaGeral.write(texto);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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
		if (bwReportPastaGeralMinimized != null) {
			for (String texto : textos) {
				try {
					bwReportPastaGeralMinimized.write(texto);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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
		if (bwReportFalha != null) {
			for (String texto : textos) {
				try {
					bwReportFalha.write(texto);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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
		if (bwReportGeralProjeto != null) {
			for (String texto : textos) {
				try {
					bwReportGeralProjeto.write(texto);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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
		if (bwReportFalhaProjeto != null) {
			for (String texto : textos) {
				try {
					bwReportFalhaProjeto.write(texto);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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
		if (bwReportSucessoProjeto != null) {
			for (String texto : textos) {
				try {
					bwReportSucessoProjeto.write(texto);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * Fechar escrita reportGeral (BufferedWriter);
	 * 
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:54:54
	 *
	 */
	public static void closePastaGeral() {
		try {
			bwReportPastaGeral.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fechar escrita reportGeral (BufferedWriter);
	 * 
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:54:54
	 *
	 */
	public static void closePastaGeralMinimized() {
		try {
			bwReportPastaGeralMinimized.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fechar escrita reportFalha (BufferedWriter);
	 * 
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:56:02
	 *
	 */
	public static void closeFalha() {
		try {
			bwReportFalha.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fechar escrita reportGeralProjeto (BufferedWriter);
	 * 
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:56:10
	 *
	 */
	public static void closeGeralProjeto() {
		try {
			bwReportGeralProjeto.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fechar escrita reportFalhaProjeto (BufferedWriter);
	 * 
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:56:19
	 *
	 */
	public static void closeFalhaProjeto() {
		try {
			bwReportFalhaProjeto.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fechar escrita reportSucessoProjeto (BufferedWriter);
	 * 
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:56:31
	 *
	 */
	public static void closeSucessoProjeto() {
		try {
			bwReportSucessoProjeto.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			if (filePilhaErro != null) {
				BufferedWriter bwEscrever = getBufferdWriter(filePilhaErro);
				for (String texto : conteudo) {
					bwEscrever.write(texto);
				}
				bwEscrever.close();
				return filePilhaErro.getPath();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	private static BufferedWriter getBufferdWriter(File filePilhaErro) throws IOException {
		try (FileWriter fileWriter = new FileWriter(filePilhaErro, true);
				BufferedWriter bwEscrever = new BufferedWriter(fileWriter);) {
			return bwEscrever;
		}
	}

	private static List<String> getLogDelete() {
		return logDelete;
	}

	private static List<String> getLogNotDeleted() {
		return logNotDeleted;
	}

	public static void setLogNotDeleted(List<String> logNotDeleted) {
		FileHTML.logNotDeleted = logNotDeleted;
	}

}
