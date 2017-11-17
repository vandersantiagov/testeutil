package br.gov.mg.testeutil.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;

import br.gov.mg.testeutil.enums.TipoArquivoEnum;

public class FileUtil {

	private static final String EXTENSAO_HTML = ".html";
	private static List<String> logDelete = new ArrayList<String>();
	private static int MANTER_QUANTIDADE = 5;
	private static DateFormat formatData = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public FileUtil() {
	}

	public FileUtil(int manterQuantidade) {
		MANTER_QUANTIDADE = manterQuantidade;
	}

	/**
	 * Deleta todos os aruivos da extensão html da pasta informada no parâmetro
	 * caminho. <br/>
	 * Ex.: "Z:\\ArtefatosWebdriver\\Projeto\\" <br/>
	 * Vai buscar todos os arquivos .html na pasta Projeto, que se encontra
	 * dentro da pasta ArtefatosWebdriver, que está dentro de z:
	 * 
	 * @param caminho
	 */
	public static void deleteArquivoHtmlByPath(File caminho) {
		File arrayFiles[] = caminho.listFiles();

		if (ArrayUtils.isNotEmpty(arrayFiles)) {
			if (arrayFiles.length > MANTER_QUANTIDADE) {

				int count = arrayFiles.length - MANTER_QUANTIDADE;

				List<File> arquivosToDelete = Arrays.asList(arrayFiles);
				ordenaArquivosPorDataModificacao(arquivosToDelete, true);

				for (File arquivo : arquivosToDelete) {
					count--;
					if (isArquivoHtml(arquivo)) {
						deleteArquivo(arquivo);
					}

					if (count <= 0) {
						break;
					}
				}
			}
		}
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

	private static boolean isArquivoHtml(File arquivo) {
		return arquivo.getName().toLowerCase().endsWith(EXTENSAO_HTML);
	}

	private static void deleteArquivo(File arquivo) {
		String nameArquivo = arquivo.getName();
		long totalSpace = arquivo.getTotalSpace();
		long usableSpace = arquivo.getUsableSpace();
		String lastDateModify = formatData.format(new Date(arquivo.lastModified()));

		boolean wasExcluded = arquivo.delete();
		String dadosArquivo = "Arquivo " + nameArquivo + ", data: " + lastDateModify + ", totalSpace: " + totalSpace
				+ ", usableSpace: " + usableSpace;
		if (wasExcluded) {
			logDelete.add(dadosArquivo + " Foi deletado com sucesso!");
		} else {
			logDelete.add(dadosArquivo + " Não foi deletado.");
		}
	}

	public static List<String> getLogDelete() {
		return logDelete;
	}

	public static void setLogDelete(List<String> logDelete) {
		FileUtil.logDelete = logDelete;
	}

	public static String copyArquivo(String diretorioPrints, String destinoPrints) throws IOException {

		String pathVirgula = destinoPrints.replace("\\", ",");
		String pathArray[] = pathVirgula.split(Pattern.quote(","));
		String nomeAndExtensaoArquivo = "";
		StringBuilder pathPrint = new StringBuilder();
		for (String pa : pathArray) {
			if (pa.contains(TipoArquivoEnum.JPEG.toString())) {
				nomeAndExtensaoArquivo = pa;
			} else {
				pathPrint.append(pa).append("\\\\");
			}
		}
		Path copy_from_1 = Paths.get(diretorioPrints);

		Path copy_to_1 = Paths.get(pathPrint + "\\", nomeAndExtensaoArquivo);
		Files.copy(copy_from_1, copy_to_1, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);

		String caminhoArquivo = pathPrint + nomeAndExtensaoArquivo;

		return caminhoArquivo;
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
}
