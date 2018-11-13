package edu.nuc.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.fasterxml.jackson.annotation.JsonView;

import edu.nuc.exception.UserNotExistException;
import edu.nuc.web.dto.User;
import edu.nuc.web.dto.User.UserDetailView;
import edu.nuc.web.dto.User.UserSimpleView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RequestMapping("/user") // 全局路径拼接
@RestController
public class UserContorller {

	
	@Autowired
	private ProviderSignInUtils providerSignInUtils;
	
	@PostMapping("/regist")
	public void regist(User user, HttpServletRequest request) {
		// 注册或者绑定用户都会拿到一个唯一标识
		// 此处应该数据库增删改查
		String userId = user.getUsername();
		providerSignInUtils.doPostSignUp(userId, new ServletWebRequest(request));

	}

/*	@GetMapping("/me")
	public Object getCurrentUser() {

		return SecurityContextHolder.getContext().getAuthentication();

	}*/
/*	@GetMapping("/me")
	public Object getCurrentUser(Authentication authentication) {

		return authentication;

	}*/
	@GetMapping("/me")
	public Object getCurrentUser(@AuthenticationPrincipal UserDetails user) {
		
		return user;
		
	}
	
	// 映射
	@JsonView(UserSimpleView.class)
	// @RequestMapping(value = "/user", method = RequestMethod.GET)
	@GetMapping // 等价于requestmapping
	// 传入参数变量username
	
	@ApiOperation(value = "用户查询服务")
	public List<User> query(@RequestParam(name = "username", required = false) String stuname) {
		ArrayList<User> users = new ArrayList<>();
		users.add(new User("a", "111"));
		users.add(new User("b", "222"));
		users.add(new User("c", "333"));
		System.out.println(stuname);
		return users;

	}

	/*
	 * public List<User> query(UserQueryCondition condition,
	 * 
	 * @PageableDefault(page = 2, size = 15, sort = "username,asc") Pageable
	 * pageable) { ArrayList<User> users = new ArrayList<>(); //
	 * 自动注入UserQueryCondition // 反射转化(类似toString方法) // String string =
	 * ReflectionToStringBuilder.toString(condition, //
	 * ToStringStyle.MULTI_LINE_STYLE); // System.out.println(string); //
	 * System.out.println(condition);
	 * 
	 * // 分页参数介绍以及pageable和默认值 System.out.println(pageable.getPageSize());
	 * System.out.println(pageable.getSort());
	 * System.out.println(pageable.getPageNumber());
	 * 
	 * return users;
	 * 
	 * }
	 */

	// 传入参数的值
/*	@JsonView(UserDetailView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@GetMapping("/{id}")
	public User getUserInfo(@PathVariable String id) {
		User user = new User("nuc", "22");

		return user;

	}*/

	// 正则表达式
	
	@RequestMapping(value = "/{id:\\d+}", method = RequestMethod.GET)
	public User getUserInfoFail(@ApiParam("用户id值")@PathVariable String id) {
		//测试自定义异常
		//throw new UserNotExistException(id);
		System.out.println("调用getUserInfo服务");
		User user = new User("nuc", "22");

		return user;

	}
	 
	//@JsonView(UserDetailView.class)
	@PostMapping
	public User create(@Valid @RequestBody User user//, BindingResult errors
			) {
	/*	if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(error ->
			System.out.println(error.getDefaultMessage()));

		}*/
		user.setId(1);
		System.out.println(user);
		return user;

	}

	@JsonView(UserDetailView.class)
	@PutMapping("/{id}")
	public User update(@Valid @RequestBody User user, BindingResult errors) {

		
		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(error -> {
				FieldError fieldError = (FieldError) error;
				String message = fieldError.getField()+" "+error.getDefaultMessage();
				System.out.println(message);
			}
			);

		}
		user.setId(1);
		System.out.println(user);
		return user;

	}
	
	
	@DeleteMapping("/{id:\\d+}")
	public void delete(@PathVariable String id) {

		System.out.println(id);

	}
}


