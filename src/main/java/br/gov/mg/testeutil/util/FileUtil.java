package br.gov.mg.testeutil.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import br.gov.mg.testeutil.enums.TipoArquivoEnum;

public class FileUtil {

	private static final String SEPARADOR_EXTENSAO = ".";

	protected FileUtil() {
	}

	public static List<File> getArquivoByNameAndPath(String path, String... nomes) {
		List<File> listReturn = new ArrayList<File>();
		try {
			File file = new File(path);
			List<File> listFiles = Arrays.asList(file.listFiles());
			for (File fileEncontrado : listFiles) {
				String nomeArquivoComExtensao = fileEncontrado.getName();
				String nameArquivo = nomeArquivoComExtensao;
				if (nomeArquivoComExtensao.indexOf(SEPARADOR_EXTENSAO) > 0) {
					nameArquivo = nomeArquivoComExtensao.substring(0, nomeArquivoComExtensao.indexOf("."));
				}
				for (String nome : nomes) {
					if (Objects.equals(nameArquivo, nome)) {
						listReturn.add(fileEncontrado);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listReturn;
	}

	public static boolean existeArquivo(String path, String nomeArquivo) {
		boolean existe = false;
		List<File> filesByPath = getFilesByPath(path);
		for (File file : filesByPath) {
			if (!existe) {
				existe = StringUtils.equals(file.getName(), nomeArquivo);
			}
		}

		return existe;
	}

	public static List<File> getFilesByPath(String path) {
		List<File> arquivosEncontrados = new ArrayList<>();
		File file = new File(path);
		File[] listFiles = file.listFiles();
		if (ArrayUtils.isNotEmpty(listFiles)) {
			for (File fileEncontrado : listFiles) {
				if (!fileEncontrado.isDirectory()) {
					arquivosEncontrados.add(fileEncontrado);
				}
			}
		}
		return arquivosEncontrados;
	}

	public static List<File> getArquivosToDelete(File[] files) {
		List<File> filesToDelete = new ArrayList<>();
		for (File arquivo : files) {
			boolean isPastaProjetoGeral = StringUtils.containsIgnoreCase(arquivo.getPath(),
					"AutomacaoSiareWebDriver_QA");
			boolean isPastaProjeto = StringUtils.containsIgnoreCase(arquivo.getName(), "Projeto_");
			if (isPastaProjetoGeral && !isPastaProjeto) {
				filesToDelete.add(arquivo);
			} else if (!isPastaProjeto) {
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
	public static void ordenaArquivosPorDataModificacao(List<File> arquivos, boolean asc) {
		Collections.sort(arquivos, (f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified()));
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
	public static File createArquivo(String caminhoPasta, String caminhoArquivo, TipoArquivoEnum tipoArquivo) {
		createPastaCaseNotExists(caminhoPasta);
		return new File(caminhoArquivo + tipoArquivo.getTipoArquivo());
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

	public static String lerArquivo(File file) throws FileNotFoundException, IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String curLine = "";
		while ((curLine = br.readLine()) != null) {
			sb.append(curLine);
		}
		br.close();

		return sb.toString();
	}
}
