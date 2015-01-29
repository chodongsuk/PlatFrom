package kr.ds.common;

public class TestException extends Exception{
	private ERROR_CODE code = ERROR_CODE.UNKNOWN;
	
	public enum ERROR_CODE {
		UNKNOWN;
	}
	public ERROR_CODE getCode(){
		return this.code;
	}
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return code != null ? code + ":" + super.getMessage() : super.getMessage();
	}
	public TestException() {
		// TODO Auto-generated constructor stub
	}
	public TestException(ERROR_CODE code, String e){
		super(e);
		this.code = code;
	}
}
