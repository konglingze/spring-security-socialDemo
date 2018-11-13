package edu.nuc.security.core.propertie.browser.validatacode;

import edu.nuc.security.core.propertie.browser.validatacode.image.ImageCodeProperties;
import edu.nuc.security.core.propertie.browser.validatacode.sms.SmsCodeProperties;

/**
 * <p>
 * Title: 验证码默认值路径设置
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author konglingze
 * @date 2018年9月10日
 */
public class ValidateCodeProperties {

	private ImageCodeProperties image = new ImageCodeProperties();
	private SmsCodeProperties sms = new SmsCodeProperties();

	public SmsCodeProperties getSms() {
		return sms;
	}

	public void setSms(SmsCodeProperties sms) {
		this.sms = sms;
	}

	public ImageCodeProperties getImage() {
		return image;
	}

	public void setImage(ImageCodeProperties image) {
		this.image = image;
	}

}
