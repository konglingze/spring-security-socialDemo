/**
 * 
 */
package edu.nuc.web.controller;

import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import edu.nuc.exception.UserNotExistException;

/**
 * @author 孔先生
 *
 */
@ControllerAdvice
public class ControllerExceptionHandler {
    
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	@ExceptionHandler(UserNotExistException.class)
	public Map<String, Object> handleUserNotExistException(UserNotExistException ex) {
		@SuppressWarnings("unchecked")
		Map<String, Object> result = new HashedMap();
		result.put("message", ex.getMessage());
		result.put("id", ex.getId());
		return result;

	}
}
