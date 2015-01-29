package kr.ds.common;

public class ErrorResult {
	private String requestURL;
	protected int errorCode;
	protected String errorMessage;

	public ErrorResult() {
	}
	public ErrorResult(final String requestURL, final String errorMessage) {
		this.requestURL = requestURL;
		this.errorCode = ErrorCode.CLIENT_ERROR_CODE.getErrorCode();
		this.errorMessage = errorMessage;
	}
	public String getRequestURL() {
		return requestURL;
	}
	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}
	public int getErrorCodeInt(){
		return errorCode;
	}
	public ErrorCode getErrorCode(){
		return ErrorCode.valueOf(errorCode);
	}
	public String getErrorMessage(){
		return errorMessage;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		final StringBuilder sb = new StringBuilder("ErrorResult:{");
		sb.append("requestURL='").append(requestURL).append('\'');
	    sb.append(", errorCode=").append(errorCode);
	    sb.append(", errorMessage='").append(errorMessage).append('\'');
	    sb.append('}');
		return super.toString();
	}

}
