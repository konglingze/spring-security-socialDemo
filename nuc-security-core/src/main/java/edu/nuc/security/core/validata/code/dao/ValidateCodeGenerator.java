/**  
* <p>Title: </p>  
* <p>Description: </p>  
* <p>Copyright: Copyright (c) 2018</p>  
* <p>Company: www.nuc.edu</p>  
* @author konglingze
* @date 2018年9月10日  
* @version 1.0  
*/  
package edu.nuc.security.core.validata.code.dao;

import org.springframework.web.context.request.ServletWebRequest;

/**  
* <p>Title: 校验码生成器</p>  
* <p>Description: </p>  
* @author konglingze
* @date 2018年9月10日  
*/
public interface ValidateCodeGenerator {
	
	public ValidateCode generate(ServletWebRequest request);
	

}
