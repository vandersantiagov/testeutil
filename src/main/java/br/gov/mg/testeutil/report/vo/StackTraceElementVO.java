package br.gov.mg.testeutil.report.vo;

public class StackTraceElementVO {
	private String methodName;
	private String className;
	private String fileName;
	private int numeroLinha;

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getNumeroLinha() {
		return numeroLinha;
	}

	public void setNumeroLinha(int numeroLinha) {
		this.numeroLinha = numeroLinha;
	}

	public String getNumeroLinhaString() {
		return Integer.toString(numeroLinha);
	}
}
