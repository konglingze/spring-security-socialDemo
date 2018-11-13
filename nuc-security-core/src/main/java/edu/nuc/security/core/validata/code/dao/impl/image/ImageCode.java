package edu.nuc.security.core.validata.code.dao.impl.image;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.time.LocalDateTime;

import edu.nuc.security.core.validata.code.dao.ValidateCode;

public class ImageCode implements ValidateCode,Serializable {


	private BufferedImage image;

	private String code;

	// 过期时间
	private LocalDateTime lcDateTime;

	public ImageCode(BufferedImage image, String code, int expectIn) {
		super();
		this.image = image;
		this.code = code;
		//未来时间(+60秒)
		this.lcDateTime = LocalDateTime.now().plusSeconds(expectIn);
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public LocalDateTime getLcDateTime() {
		return lcDateTime;
	}

	public void setLcDateTime(LocalDateTime lcDateTime) {
		this.lcDateTime = lcDateTime;
	}

	//是否过期
	public boolean isExpried() {
		return LocalDateTime.now().isAfter(lcDateTime);
	}

}
