package kr.ds.common;

import java.util.HashMap;
import java.util.Map;

public enum ErrorCode {
	CLIENT_ERROR_CODE(-777),
	UNDEFINED_ERROR_CODE(-888);
	private final int errorCode;
	private static final Map<Integer, ErrorCode> reverseMap = new HashMap<Integer, ErrorCode>(
			1);
	static {//static values()  enum 상수의 배열을 리턴
		for (ErrorCode errorCode : ErrorCode.values()) {
			reverseMap.put(errorCode.getErrorCode(), errorCode);
		}
	}

	ErrorCode(final int errorCode) {//생성자를 이용할경우!!!
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
	
	public static ErrorCode valueOf(final Integer i) {
		if (i == null)
			return null;
		ErrorCode errorCode = reverseMap.get(i);
		if (errorCode != null)
			return errorCode;
		else
			return UNDEFINED_ERROR_CODE;
	}

}
