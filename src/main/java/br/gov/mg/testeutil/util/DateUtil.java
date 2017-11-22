package br.gov.mg.testeutil.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author sandra.rodrigues
 */
public class DateUtil {

	private static final Locale localeBR = new Locale("pt", "BR");

	/**
	 * yyyyMMdd
	 * <br/>
	 * Ex.: a hora 13/11/2017 16:38:00 será apresentada assim: 20171113
	 */
	public static final String FORMATO_DATA = "yyyyMMdd";

	/**
	 * yyyyddMM HHmmss
	 * <br/>
	 * Ex.: a hora 13/11/2017 16:38:00 será apresentada assim: 20171113 163800
	 */
	public static final String FORMATO_DATA1 = "yyyyMMdd HHmmss";

	/**
	 * yyyyddMM
	 * <br/>
	 * Ex.: a hora 13/11/2017 16:38:00 será apresentada assim: 20171113
	 */
	public static final String FORMATO_DATA2 = "yyyyddMM";
	/**
	 * dd-MMMM-yyyy HH:mm:ss
	 * <br/>
	 * Ex.: a hora 13/11/2017 16:38:00 será apresentada assim: 13-novembro-2017
	 * 16:38:00
	 */
	public static final String FORMATO_DATA3 = "dd-MMMM-yyyy HH:mm:ss";
	/**
	 * yyyyddMMHHmmss
	 * <br/>
	 * Ex.: a hora 13/11/2017 16:38:00 será apresentada assim: 20171311163800
	 */
	public static final String FORMATO_DATA4 = "yyyyddMMHHmmss";
	/**
	 * dd 'de' MMMM 'de' yyyy HH:mm
	 * <br/>
	 * Ex.: a hora 13/11/2017 16:38:00 será apresentada assim: 13 de Novembro de
	 * 2017 16:38
	 */
	public static final String FORMATO_DATA5 = "dd 'de' MMMM 'de' yyyy HH:mm";

	/**
	 * dd 'de' MMMM 'de' yyyy HH:mm:ss
	 * <br/>
	 * Ex.: a hora 13/11/2017 16:38:00 será apresentada assim: 13 de Novembro de
	 * 2017 16:38:00
	 */
	public static final String FORMATO_DATA6 = "dd 'de' MMMM 'de' yyyy HH:mm:ss";

	/**
	 * dd/mm/yyyy HH:mm:ss
	 * <br/>
	 * Ex.: a hora 13/11/2017 16:38:00 será apresentada assim: 13/11/2017
	 * 16:38:00
	 */
	public static final String FORMATO_DATA7 = "dd/MM/yyyy HH:mm:ss";

	private static SimpleDateFormat dateFormate;

	public static long getDiferencaEmMilliSegundosEntreDatas(Date dateFimTestes, Date dateInicioTestes) {
		Calendar cDateFim = Calendar.getInstance();
		cDateFim.setTime(dateFimTestes);

		Calendar cDateInicio = Calendar.getInstance();
		cDateInicio.setTime(dateInicioTestes);

		long diferenca = (cDateFim.getTimeInMillis() - cDateInicio.getTimeInMillis());

		return diferenca;
	}

	public static long getDiferencaEmSegundosEntreDatas(Date dateFimTestes, Date dateInicioTestes) {
		long diferenca = getDiferencaEmMilliSegundosEntreDatas(dateFimTestes, dateInicioTestes);
		long diferencaSegundos = diferenca / 1000;
		return diferencaSegundos;
	}

	public static long getDiferencaEmMinutosEntreDatas(Date dateFimTestes, Date dateInicioTestes) {
		long diferenca = getDiferencaEmMilliSegundosEntreDatas(dateFimTestes, dateInicioTestes);
		long diferencaMinutos = diferenca / (60 * 1000);
		return diferencaMinutos;
	}

	public static long getDiferencaEmHorasEntreDatas(Date dateFimTestes, Date dateInicioTestes) {
		long diferenca = getDiferencaEmMilliSegundosEntreDatas(dateFimTestes, dateInicioTestes);
		long diferencaHoras = diferenca / (60 * 60 * 1000);
		return diferencaHoras;
	}

	public static long getSegundos(long milegundos) {
		long diferencaSegundos = milegundos / 1000;
		return diferencaSegundos;
	}

	public static long getMinutos(long milegundos) {
		long diferencaMinutos = milegundos / (60 * 1000);
		return diferencaMinutos;
	}

	public static long getHoras(long milegundos) {
		long diferencaHoras = milegundos / (60 * 60 * 1000);
		return diferencaHoras;
	}

	public static SimpleDateFormat getDateFormate() {
		return dateFormate;
	}

	public static SimpleDateFormat getDateFormateByFormato(String formato) {
		return new SimpleDateFormat(formato, localeBR);
	}

	public static SimpleDateFormat getDateFormateByFormatoAndLocale(String formato, Locale locale) {
		return new SimpleDateFormat(formato, locale);
	}

	public static String getDataFormatadaByFormato(Date data, String formato) {
		dateFormate = new SimpleDateFormat(formato);
		return getDateFormate().format(data);
	}

	public static Date getDate(String dateString) {
		try {
			SimpleDateFormat dateFormateBR = getDateFormateByFormato(FORMATO_DATA7);
			Date data = dateFormateBR.parse(dateString);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date getDate01011970() {
		try {
			SimpleDateFormat dateFormateBR = getDateFormateByFormato(FORMATO_DATA7);
			Date data = dateFormateBR.parse("01/01/1970");
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date getDate12123999() {
		try {
			SimpleDateFormat dateFormateBR = getDateFormateByFormato(FORMATO_DATA7);
			Date data = dateFormateBR.parse("12/12/3999");
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
