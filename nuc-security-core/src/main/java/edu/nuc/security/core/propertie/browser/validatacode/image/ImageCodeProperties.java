
package edu.nuc.security.core.propertie.browser.validatacode.image;

public class ImageCodeProperties {

	// 图形验证码的宽高,长度以及过期时间
	private int width = 67;
	private int height = 23;
	private int length = 4;
	private int expireIn = 60;

	private String url ;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getExpireIn() {
		return expireIn;
	}

	public void setExpireIn(int expireIn) {
		this.expireIn = expireIn;
	}

}
