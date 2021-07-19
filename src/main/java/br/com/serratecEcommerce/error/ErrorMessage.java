package br.com.serratecEcommerce.error;

public class ErrorMessage {

	private String title;
	private Integer status;
	private String detail;
		
	public ErrorMessage(String title, Integer status, String detail) {
		this.title = title;
		this.status = status;
		this.detail = detail;
	}

	public String getTitle() {
		return title;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public String getDetail() {
		return detail;
	}

}

