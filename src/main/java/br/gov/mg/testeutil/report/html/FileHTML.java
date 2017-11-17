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
import br.gov.mg.testeutil.util.FileUtil;

/**
 * @author sandra.rodrigues
 */
public class FileHTML {

	public static final String PATH_DIRETORIO_REPORT = FileHTML.getDiretorio(MetodosSiare.diretorioPrincipal,
			"Report");
	private static final String FORMATO_DATA_CRIACAO_ARQUIVO = DateUtil.FORMATO_DATA1;
	private static final String SUCESSO = " Sucesso";
	private static final String FALHA = " Falha";
	private static final String CONTRA_BARRA = "\\";

	private static File fileReportGeral;
	private static File fileReportFalha;

	private static File fileReportGeralProjeto;
	private static File fileReportFalhaProjeto;
	private static File fileReportSucessoProjeto;

	private static File fileReportGeralSuite;
	private static File fileReportFalhaSuite;
	private static File fileReportSucessoSuite;

	public static BufferedWriter bwReportGeral;
	public static BufferedWriter bwReportFalha;

	public static BufferedWriter bwReportGeralProjeto;
	public static BufferedWriter bwReportFalhaProjeto;
	public static BufferedWriter bwReportSucessoProjeto;

	public static BufferedWriter bwReportGeralSuite;
	public static BufferedWriter bwReportFalhaSuite;
	public static BufferedWriter bwReportSucessoSuite;

	/**
	 * Cria arquivos dentro da pasta Report.
	 * 
	 * @throws IOException
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:18:51
	 *
	 */
	public static void createFilesGeral() throws IOException {
		String caminhoArquivo = PATH_DIRETORIO_REPORT + CONTRA_BARRA + getDataFormatada(FORMATO_DATA_CRIACAO_ARQUIVO);

		fileReportGeral = createArquivo(caminhoArquivo, TipoArquivoEnum.HTML);
		bwReportGeral = new BufferedWriter(new FileWriter(fileReportGeral, true));

		fileReportFalha = createArquivo(caminhoArquivo + FALHA, TipoArquivoEnum.HTML);
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
	public static void createFilesProjeto(String nomeProjeto) throws IOException {

		String pathProjeto = FileHTML.getDiretorio(PATH_DIRETORIO_REPORT + "\\", nomeProjeto);

		String caminhoArquivo = pathProjeto + CONTRA_BARRA + getDataFormatada(FORMATO_DATA_CRIACAO_ARQUIVO);

		fileReportGeralProjeto = createArquivo(caminhoArquivo, TipoArquivoEnum.HTML);
		bwReportGeralProjeto = new BufferedWriter(new FileWriter(fileReportGeralProjeto, true));

		fileReportFalhaProjeto = createArquivo(caminhoArquivo + FALHA, TipoArquivoEnum.HTML);
		bwReportFalhaProjeto = new BufferedWriter(new FileWriter(fileReportFalhaProjeto, true));

		fileReportSucessoProjeto = createArquivo(caminhoArquivo + SUCESSO, TipoArquivoEnum.HTML);
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

		String pathProjeto = FileHTML.getDiretorio(PATH_DIRETORIO_REPORT + "\\", nomeProjeto);

		String pathSuite = FileHTML.getDiretorio(pathProjeto + "\\", nomeSuite);

		String caminhoArquivo = pathSuite + CONTRA_BARRA + getDataFormatada(FORMATO_DATA_CRIACAO_ARQUIVO);

		fileReportGeralSuite = createArquivo(caminhoArquivo, TipoArquivoEnum.HTML);
		bwReportGeralSuite = new BufferedWriter(new FileWriter(fileReportGeralSuite, true));

		fileReportFalhaSuite = createArquivo(caminhoArquivo + FALHA, TipoArquivoEnum.HTML);
		bwReportFalhaSuite = new BufferedWriter(new FileWriter(fileReportFalhaSuite, true));

		fileReportSucessoSuite = createArquivo(caminhoArquivo + SUCESSO, TipoArquivoEnum.HTML);
		bwReportSucessoSuite = new BufferedWriter(new FileWriter(fileReportSucessoSuite, true));
	}

	/**
	 * Retorna o caminho do diretório (pasta) com o nome informado no parâmetro
	 * "nomeDiretorio" e o caminho informado na variável local.
	 * Caso o diretório não exista o mesmo é criado.
	 * <br/>
	 * <b>Atenção:</b> Caminho do diretório deve ser informado da seguinte
	 * forma:
	 * "Z:\\ArtefatosWebdriver\\", com duas contra-barras (barras invertidas
	 * ("\\")).
	 * Informando o local dessa forma a pasta será criada dentro de
	 * ArtefatosWebdriver existente em "Z:".
	 * 
	 * @param local
	 * @param nomeDiretorio
	 * @return
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 08:45:34
	 *
	 */
	public static String getDiretorio(String local, String nomeDiretorio) {
		String pasta = local + nomeDiretorio;
		FileUtil.createPastaCaseNotExists(pasta);
		return pasta;
	}

	/**
	 * Cria arquivo.
	 * 
	 * @param caminhoArquivo
	 * @param tipoArquivo
	 * @return
	 * @throws IOException
	 *
	 * @author sandra.rodrigues
	 *         16 de nov de 2017 09:45:30
	 *
	 */
	private static File createArquivo(String caminhoArquivo, TipoArquivoEnum tipoArquivo) throws IOException {
		File file = new File(caminhoArquivo + tipoArquivo.getTipoArquivo());
		return file;
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
	public static void escreverArquivoGeral(String texto) {
		try {
			if (bwReportGeral != null) {
				bwReportGeral.write(texto);
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
	public static void escreverArquivoFalha(String texto) {
		try {
			if (bwReportFalha != null) {
				bwReportFalha.write(texto);
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
	public static void escreverArquivoGeralProjeto(String texto) {
		try {
			if (bwReportGeralProjeto != null) {
				bwReportGeralProjeto.write(texto);
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
	public static void escreverArquivoFalhaProjeto(String texto) {
		try {
			if (bwReportFalhaProjeto != null) {
				bwReportFalhaProjeto.write(texto);
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
	public static void escreverArquivoSucessoProjeto(String texto) {
		try {
			if (bwReportSucessoProjeto != null) {
				bwReportSucessoProjeto.write(texto);
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
	public static void escreverArquivoGeralSuite(String texto) {
		try {
			if (bwReportGeralSuite != null) {
				bwReportGeralSuite.write(texto);
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
	public static void escreverArquivoFalhaSuite(String texto) {
		try {
			if (bwReportFalhaSuite != null) {
				bwReportFalhaSuite.write(texto);
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
	public static void escreverArquivoSucessoSuite(String texto) {
		try {
			if (bwReportSucessoSuite != null) {
				bwReportSucessoSuite.write(texto);
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
	public static void closeGeral() throws IOException {
		bwReportGeral.close();
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
