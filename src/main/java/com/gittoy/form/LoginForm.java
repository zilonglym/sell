package com.gittoy.form;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class LoginForm {

	@NotEmpty(message = "账号必填")
	private String Username;
	
	@NotEmpty(message = "密码必填")
	private String Password;
}
