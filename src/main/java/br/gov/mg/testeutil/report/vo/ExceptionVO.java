package br.gov.mg.testeutil.report.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @author sandra.rodrigues
 */
public class ExceptionVO {

	private Throwable exception;
	private List<StackTraceElementVO> stacksTraceVO;
	private String message;
	private boolean isFalha;

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
		if (exception != null) {
			StackTraceElement[] stackTrace = exception.getStackTrace();
			if (ArrayUtils.isNotEmpty(stackTrace)) {
				for (int i = 0; i <= stackTrace.length - 1; i++) {
					StackTraceElementVO stackVO = new StackTraceElementVO();
					StackTraceElement stackTraceElement = stackTrace[i];
					stackVO.setMethodName(stackTraceElement.getMethodName());
					stackVO.setClassName(stackTraceElement.getClassName());
					stackVO.setFileName(stackTraceElement.getFileName());
					stackVO.setNumeroLinha(stackTraceElement.getLineNumber());
					getStacksTraceVO().add(stackVO);
				}
			}
		}

	}

	public List<StackTraceElementVO> getStacksTraceVO() {
		if (stacksTraceVO == null) {
			stacksTraceVO = new ArrayList<StackTraceElementVO>();
		}
		return stacksTraceVO;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isFalha() {
		return isFalha;
	}

	public void setFalha(boolean isFalha) {
		this.isFalha = isFalha;
	}
}
