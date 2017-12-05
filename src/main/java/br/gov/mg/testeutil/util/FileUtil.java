package br.gov.mg.testeutil.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import br.gov.mg.testeutil.enums.TipoArquivoEnum;

public class FileUtil {

	private static List<String> logNotDeleted = new ArrayList<String>();
	private static List<String> logDelete = new ArrayList<String>();
	private static int MANTER_QUANTIDADE = 10;

	public FileUtil() {
	}

	public FileUtil(int manterQuantidade) {
		MANTER_QUANTIDADE = manterQuantidade;
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
	public static void deleteArquivoByPath(File caminho, boolean deletePastaProjeto) {
		File arrayFiles[] = caminho.listFiles();

		String[] tiposArquivosToDelete = { TipoArquivoEnum.HTML.getTipoArquivo(), TipoArquivoEnum.TXT.getTipoArquivo(),
				TipoArquivoEnum.JPEG.getTipoArquivo(), TipoArquivoEnum.JPG.getTipoArquivo() };

		List<File> arquivosToDelete = new ArrayList<File>();

		if (ArrayUtils.isNotEmpty(arrayFiles)) {
			if (!deletePastaProjeto) {
				arquivosToDelete = getArquivosToDelete(arrayFiles);
			}

			if (arquivosToDelete.size() > MANTER_QUANTIDADE) {

				int count = arquivosToDelete.size() - MANTER_QUANTIDADE;
				ordenaArquivosPorDataModificacao(arquivosToDelete, true);
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

	private static List<File> getArquivosToDelete(File[] files) {
		List<File> filesToDelete = new ArrayList<File>();
		for (File arquivo : files) {
			boolean isPastaProjetoGeral = StringUtils.containsIgnoreCase(arquivo.getPath(),
					"AutomacaoSiareWebDriver_QA");
			boolean isPastaProjeto = StringUtils.containsIgnoreCase(arquivo.getPath(), "Projeto_");
			if (!isPastaProjeto && !isPastaProjetoGeral) {
				filesToDelete.add(arquivo);
			}
		}
		return filesToDelete;
	}

	/**
	 * <b> <font color="gray">Ordena arquivos conforme ordem passada no
	 * parâmentro. <br/>
	 * </font> </b> <font color="red">Obs.: Informar true para ASC e false para
	 * DESC.</font>
	 * 
	 * @param arquivos
	 * @param asc
	 */
	private static void ordenaArquivosPorDataModificacao(List<File> arquivos, boolean asc) {
		Collections.sort(arquivos, (f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified()));
	}

	private static boolean isArquivoToDelete(File arquivo, String[] tiposArquivosToDelete) {
		boolean isPastaProjetoGeral = StringUtils.containsIgnoreCase(arquivo.getPath(), "AutomacaoSiareWebDriver_QA");
		boolean containsTextoProjeto = StringUtils.containsIgnoreCase(arquivo.getPath(), "Projeto_");
		boolean isDirectory = arquivo.isDirectory();
		boolean isDirectoryEmpty = arquivo.length() <= 0;
		boolean isTipoArquivoToDelete = StringUtils.endsWithAny(arquivo.getName().toLowerCase(), tiposArquivosToDelete);
		return !isPastaProjetoGeral && !containsTextoProjeto
				&& (isTipoArquivoToDelete || isDirectory || isDirectoryEmpty);
	}

	private static boolean deleteArquivo(File arquivo, boolean deletePastaProjeto, String[] tiposArquivosToDelete) {
		String nameArquivo = arquivo.getName();
		String lastDateModify = DateUtil.getDataFormatadaByFormato(new Date(arquivo.lastModified()),
				DateUtil.FORMATO_DATA7);

		boolean wasExcluded = false;

		if (deletePastaProjeto || isArquivoToDelete(arquivo, tiposArquivosToDelete)) {
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
			logDelete.add(dadosArquivo + " Foi deletado com sucesso!");
		} else {
			logNotDeleted.add(dadosArquivo + " Não foi deletado.");
		}
		return wasExcluded;
	}

	public static List<String> getLogDelete() {
		return logDelete;
	}

	public static void setLogDelete(List<String> logDelete) {
		FileUtil.logDelete = logDelete;
	}

	public static List<String> getLogNotDeleted() {
		return logNotDeleted;
	}

	public static void setLogNotDeleted(List<String> logNotDeleted) {
		FileUtil.logNotDeleted = logNotDeleted;
	}

	/**
	 * Criar pasta caso ela não exista.
	 * 
	 * @author sandra.rodrigues
	 * @param enderecoCriacao
	 */
	public static File createPastaCaseNotExists(String enderecoCriacao) {
		File pastaFile = new File(enderecoCriacao);
		if (!pastaFile.exists()) {
			pastaFile.mkdir();
		}
		return pastaFile;
	}

	/**
	 * @author sandra.rodrigues 10/11/2017
	 * @throws IOException
	 */
	public static File createArquivo(String caminhoPasta, String caminhoArquivo, TipoArquivoEnum tipoArquivo)
			throws IOException {
		createPastaCaseNotExists(caminhoPasta);
		File file = new File(caminhoArquivo + tipoArquivo.getTipoArquivo());
		return file;
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
	 * @throws IOException
	 *
	 */
	public static String getDiretorio(String local, String nomeDiretorio) throws IOException {
		String pasta = local + nomeDiretorio;
		createDiretorios(pasta);
		return pasta;
	}

	/**
	 * Retorna o arquivo encontrado na pasta do caminho path com o nome
	 * informado no parametro e extensão informada.
	 * 
	 * @author sandra.rodrigues
	 */
	public static File getFileByPath(String path, String nomeArquivo, TipoArquivoEnum extension) {
		File fileReturn = null;
		File file = new File(path);
		if (ArrayUtils.isNotEmpty(file.listFiles())) {
			for (File arquivo : file.listFiles()) {
				if (Objects.equals(arquivo.getName(), nomeArquivo + extension.getTipoArquivo())) {
					fileReturn = arquivo;
					break;
				}
			}
		}
		return fileReturn;
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
	public static File createArquivo(String caminhoArquivo, TipoArquivoEnum tipoArquivo) throws IOException {
		File file = new File(caminhoArquivo + tipoArquivo.getTipoArquivo());
		return file;
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
	public static void createDiretorios(String caminhoArquivo) throws IOException {
		Files.createDirectories(Paths.get(caminhoArquivo));
	}

	public static String generateTxtByText(String path, String textoToWriter) {
		File file = new File(path);
		try {
			file.mkdirs();
			FileWriter canal = new FileWriter(file + TipoArquivoEnum.TXT.getTipoArquivo());
			PrintWriter escrever = new PrintWriter(canal);
			escrever.println(textoToWriter);
			escrever.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file.getPath() + TipoArquivoEnum.TXT.getTipoArquivo();
	}
}
