package br.gov.mg.testeutil.report.html;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import br.gov.mg.testeutil.enums.TipoArquivoEnum;
import br.gov.mg.testeutil.metodos.MetodosSiare;
import br.gov.mg.testeutil.util.DateUtil;
import static br.gov.mg.testeutil.util.FileUtil.*;

/**
 * @author sandra.rodrigues
 */
public class FileHTML {

	public static final String PATH_DIRETORIO_REPORT = MetodosSiare.diretorioPrincipal + "Report";
	private static final String FORMATO_DATA_CRIACAO_ARQUIVO = DateUtil.FORMATO_DATA1;
	private static final String GERAL = " Geral";
	private static final String SUCESSO = " Sucesso";
	private static final String FALHA = " Falha";
	private static final String DUAS_CONTRA_BARRAS = "\\";

	private static File fileReportGeral;
	private static File fileReportFalha;

	private static File fileReportGeralProjeto;
	private static File fileReportFalhaProjeto;
	private static File fileReportSucessoProjeto;

	private static File fileReportGeralSuite;
	private static File fileReportFalhaSuite;
	private static File fileReportSucessoSuite;

	public static BufferedWriter bwReportPastaGeral;
	public static BufferedWriter bwReportFalha;

	public static BufferedWriter bwReportGeralProjeto;
	public static BufferedWriter bwReportFalhaProjeto;
	public static BufferedWriter bwReportSucessoProjeto;

	public static BufferedWriter bwReportGeralSuite;
	public static BufferedWriter bwReportFalhaSuite;
	public static BufferedWriter bwReportSucessoSuite;

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
	public static String[] createFilesGeral() throws IOException {
		String dataFormatada = getDataFormatada(FORMATO_DATA_CRIACAO_ARQUIVO);
		String caminhoArquivo = PATH_DIRETORIO_REPORT + DUAS_CONTRA_BARRAS + dataFormatada;
		createDiretorios(caminhoArquivo);
		fileReportGeral = createArquivo(caminhoArquivo + DUAS_CONTRA_BARRAS + dataFormatada + GERAL,
				TipoArquivoEnum.HTML);
		bwReportPastaGeral = new BufferedWriter(new FileWriter(fileReportGeral, true));

		fileReportFalha = createArquivo(caminhoArquivo + DUAS_CONTRA_BARRAS + dataFormatada + FALHA,
				TipoArquivoEnum.HTML);
		bwReportFalha = new BufferedWriter(new FileWriter(fileReportFalha, true));

		String path[] = { fileReportGeral.getPath(), fileReportFalha.getPath() };
		return path;
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
	public static String[] createFilesProjeto(String nomeProjeto) throws IOException {
		String dataFormatada = getDataFormatada(FORMATO_DATA_CRIACAO_ARQUIVO);
		String caminhoArquivo = PATH_DIRETORIO_REPORT + DUAS_CONTRA_BARRAS + nomeProjeto + DUAS_CONTRA_BARRAS
				+ dataFormatada;
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

		String path[] = { fileReportGeralProjeto.getPath(), fileReportFalhaProjeto.getPath() };

		return path;
	}

	/**
	 * Cria arquivos dentro da pasta Report.
	 * 
	 * @throws IOException
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:18:51
	 *
	 */
	public static void createFilesPastaGeral() throws IOException {
		String dataFormatada = getDataFormatada(FORMATO_DATA_CRIACAO_ARQUIVO);
		String caminhoArquivo = PATH_DIRETORIO_REPORT + DUAS_CONTRA_BARRAS + dataFormatada;
		createDiretorios(caminhoArquivo);
		fileReportGeral = createArquivo(caminhoArquivo + DUAS_CONTRA_BARRAS + dataFormatada + GERAL,
				TipoArquivoEnum.HTML);
		bwReportPastaGeral = new BufferedWriter(new FileWriter(fileReportGeral, true));
	}

	/**
	 * Cria arquivos dentro da pasta Report.
	 * 
	 * @throws IOException
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:18:51
	 *
	 */
	public static void createFilesFalhaPastaGeral() throws IOException {
		String dataFormatada = getDataFormatada(FORMATO_DATA_CRIACAO_ARQUIVO);
		String caminhoArquivo = PATH_DIRETORIO_REPORT + DUAS_CONTRA_BARRAS + dataFormatada;
		createDiretorios(caminhoArquivo);
		fileReportFalha = createArquivo(caminhoArquivo + DUAS_CONTRA_BARRAS + dataFormatada + FALHA,
				TipoArquivoEnum.HTML);
		bwReportFalha = new BufferedWriter(new FileWriter(fileReportFalha, true));
	}

	/**
	 * Cria arquivos dentro da pasta projeto.
	 * 
	 * @param nomeProjeto
	 * @throws IOException
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:19:09
	 *
	 */
	public static void createFilesPastaProjeto(String nomeProjeto) throws IOException {
		String dataFormatada = getDataFormatada(FORMATO_DATA_CRIACAO_ARQUIVO);
		String caminhoArquivo = PATH_DIRETORIO_REPORT + DUAS_CONTRA_BARRAS + nomeProjeto + DUAS_CONTRA_BARRAS
				+ dataFormatada;
		createDiretorios(caminhoArquivo);
		fileReportGeralProjeto = createArquivo(caminhoArquivo + DUAS_CONTRA_BARRAS + dataFormatada + GERAL,
				TipoArquivoEnum.HTML);
		bwReportGeralProjeto = new BufferedWriter(new FileWriter(fileReportGeralProjeto, true));
	}

	/**
	 * Cria arquivos dentro da pasta projeto.
	 * 
	 * @param nomeProjeto
	 * @throws IOException
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:19:09
	 *
	 */
	public static void createFilesFalhaProjeto(String nomeProjeto) throws IOException {
		String dataFormatada = getDataFormatada(FORMATO_DATA_CRIACAO_ARQUIVO);
		String caminhoArquivo = PATH_DIRETORIO_REPORT + DUAS_CONTRA_BARRAS + nomeProjeto + DUAS_CONTRA_BARRAS
				+ dataFormatada;
		createDiretorios(caminhoArquivo);
		fileReportFalhaProjeto = createArquivo(caminhoArquivo + DUAS_CONTRA_BARRAS + dataFormatada + FALHA,
				TipoArquivoEnum.HTML);
		bwReportFalhaProjeto = new BufferedWriter(new FileWriter(fileReportFalhaProjeto, true));
	}

	/**
	 * Cria arquivos dentro da pasta projeto.
	 * 
	 * @param nomeProjeto
	 * @throws IOException
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:19:09
	 *
	 */
	public static void createFilesSucessoProjeto(String nomeProjeto) throws IOException {
		String dataFormatada = getDataFormatada(FORMATO_DATA_CRIACAO_ARQUIVO);
		String caminhoArquivo = PATH_DIRETORIO_REPORT + DUAS_CONTRA_BARRAS + nomeProjeto + DUAS_CONTRA_BARRAS
				+ dataFormatada;
		createDiretorios(caminhoArquivo);
		fileReportSucessoProjeto = createArquivo(caminhoArquivo + DUAS_CONTRA_BARRAS + dataFormatada + SUCESSO,
				TipoArquivoEnum.HTML);
		bwReportSucessoProjeto = new BufferedWriter(new FileWriter(fileReportSucessoProjeto, true));
	}

	/**
	 * Cria arquivos dentro da pasta suite.
	 * 
	 * @param nomeSuite
	 * @throws IOException
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:19:20
	 *
	 */
	public static void createFilesSuite(String nomeProjeto, String nomeSuite) throws IOException {
		String dataFormatada = getDataFormatada(FORMATO_DATA_CRIACAO_ARQUIVO);
		String caminhoArquivo = PATH_DIRETORIO_REPORT + DUAS_CONTRA_BARRAS + nomeProjeto + DUAS_CONTRA_BARRAS
				+ nomeSuite + DUAS_CONTRA_BARRAS + dataFormatada;
		createDiretorios(caminhoArquivo);
		fileReportGeralSuite = createArquivo(caminhoArquivo + DUAS_CONTRA_BARRAS + dataFormatada + GERAL,
				TipoArquivoEnum.HTML);
		bwReportGeralSuite = new BufferedWriter(new FileWriter(fileReportGeralSuite, true));
	}

	/**
	 * Cria arquivos dentro da pasta suite.
	 * 
	 * @param nomeSuite
	 * @throws IOException
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:19:20
	 *
	 */
	public static void createFilesFalhaSuite(String nomeProjeto, String nomeSuite) throws IOException {
		String dataFormatada = getDataFormatada(FORMATO_DATA_CRIACAO_ARQUIVO);
		String caminhoArquivo = PATH_DIRETORIO_REPORT + DUAS_CONTRA_BARRAS + nomeProjeto + DUAS_CONTRA_BARRAS
				+ nomeSuite + DUAS_CONTRA_BARRAS + dataFormatada;
		createDiretorios(caminhoArquivo);
		fileReportFalhaSuite = createArquivo(caminhoArquivo + DUAS_CONTRA_BARRAS + dataFormatada + FALHA,
				TipoArquivoEnum.HTML);
		bwReportFalhaSuite = new BufferedWriter(new FileWriter(fileReportFalhaSuite, true));
	}

	/**
	 * Cria arquivos dentro da pasta suite.
	 * 
	 * @param nomeSuite
	 * @throws IOException
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:19:20
	 *
	 */
	public static void createFilesSucessoSuite(String nomeProjeto, String nomeSuite) throws IOException {
		String dataFormatada = getDataFormatada(FORMATO_DATA_CRIACAO_ARQUIVO);
		String caminhoArquivo = PATH_DIRETORIO_REPORT + DUAS_CONTRA_BARRAS + nomeProjeto + DUAS_CONTRA_BARRAS
				+ nomeSuite + DUAS_CONTRA_BARRAS + dataFormatada;
		createDiretorios(caminhoArquivo);
		fileReportSucessoSuite = createArquivo(caminhoArquivo + DUAS_CONTRA_BARRAS + dataFormatada + SUCESSO,
				TipoArquivoEnum.HTML);
		bwReportSucessoSuite = new BufferedWriter(new FileWriter(fileReportSucessoSuite, true));
	}

	/**
	 * Retorna a data formatada conforme formato informado no parâmetro.
	 * 
	 * @return
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:44:19
	 */
	private static String getDataFormatada(String formato) {
		return DateUtil.getDataFormatadaByFormato(new Date(), formato);
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
	 * Escreve no arquivo de report geral suite o texto passado no parâmetro.
	 * 
	 * @param texto
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:50:45
	 *
	 */
	public static void escreverArquivoGeralSuite(String... textos) {
		try {
			if (bwReportGeralSuite != null) {
				for (String texto : textos) {
					bwReportGeralSuite.write(texto);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * Escreve no arquivo de report falha suite o texto passado no parâmetro.
	 * 
	 * @param texto
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:51:14
	 *
	 */
	public static void escreverArquivoFalhaSuite(String... textos) {
		try {
			if (bwReportFalhaSuite != null) {
				for (String texto : textos) {
					bwReportFalhaSuite.write(texto);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * Escreve no arquivo de report sucesso suite o texto passado no
	 * parâmetro.
	 * 
	 * @param texto
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:51:24
	 *
	 */
	public static void escreverArquivoSucessoSuite(String... textos) {
		try {
			if (bwReportSucessoSuite != null) {
				for (String texto : textos) {
					bwReportSucessoSuite.write(texto);
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

	/**
	 * Fechar escrita reportGeralSuite (BufferedWriter);
	 * 
	 * @throws IOException
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:57:19
	 *
	 */
	public static void closeGeralSuite() throws IOException {
		bwReportGeralSuite.close();
	}

	/**
	 * Fechar escrita reportFalhaSuite (BufferedWriter);
	 * 
	 * @throws IOException
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:57:27
	 *
	 */
	public static void closeFalhaSuite() throws IOException {
		bwReportFalhaSuite.close();
	}

	/**
	 * Fechar escrita reportSucessoSuite (BufferedWriter);
	 * 
	 * @throws IOException
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:57:35
	 *
	 */
	public static void closeSucessoSuite() throws IOException {
		bwReportSucessoSuite.close();
	}

	public static void openReportGeralInBrowser() throws IOException {
		Desktop.getDesktop().browse(fileReportGeral.toURI());
	}
}
