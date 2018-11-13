package edu.nuc.security.browser.support;

public class SimpleResponse {

	private Object content;

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "SimpleResponse [content=" + content + "]";
	}

	public SimpleResponse(Object content) {
		super();
		this.content = content;
	}

}
