package edu.nuc.security.core.validata.mobile.authentication.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import edu.nuc.security.core.validata.mobile.authentication.SmsCodeAuthenticationFilter;
import edu.nuc.security.core.validata.mobile.authentication.SmsCodeAuthenticationProvider;

@Configuration
public class SmsCodeAuthenticationSecurityConfig
		extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	@Autowired
	private AuthenticationSuccessHandler nucAuthenticationSuccessHandler;

	@Autowired
	private AuthenticationFailureHandler nucAuthenticationFailHandler;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public void configure(HttpSecurity http) throws Exception {

		SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
		smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(nucAuthenticationSuccessHandler);
		smsCodeAuthenticationFilter.setAuthenticationFailureHandler(nucAuthenticationFailHandler);

		SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();

		smsCodeAuthenticationProvider.setUserDetailsService(userDetailsService);

		http
		.authenticationProvider(smsCodeAuthenticationProvider)
		.addFilterAfter(smsCodeAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);

	}

}
