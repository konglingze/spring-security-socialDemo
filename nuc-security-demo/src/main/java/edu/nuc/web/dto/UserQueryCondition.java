package edu.nuc.web.dto;

import io.swagger.annotations.ApiModelProperty;

public class UserQueryCondition {
	@ApiModelProperty(value = "用户名字")
	private String username;
	@ApiModelProperty(value = "用户年龄")
	private int age;
	private int sex;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "UserQueryCondition [username=" + username + ", age=" + age + ", sex=" + sex + "]";
	}

}
