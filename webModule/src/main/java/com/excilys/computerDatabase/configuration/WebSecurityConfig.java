package com.excilys.computerDatabase.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.excilys.computerDatabase.services.ComputerDatabaseAuthService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends  WebSecurityConfigurerAdapter{

	@Autowired
	ComputerDatabaseAuthService serviceAuthentication;
	
	@Autowired
	public void configureAuthenticationManagerBuilder(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		
		authenticationManagerBuilder.userDetailsService(serviceAuthentication).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Autowired
	private UserDetailsService userDetailsService;
	 
	@Bean
	public DaoAuthenticationProvider authProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService);
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception  {
		
		httpSecurity.csrf().disable();
		//Les pages qui n'exigent pas la connexion
		httpSecurity.authorizeRequests().antMatchers( "/accueil","/login", "/logout").permitAll();
		//Tous les rôles peuvent acceder à l'url '/dashboard'
		httpSecurity.authorizeRequests().antMatchers("/dashboard").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
		//Seuls les admin peuvent accederr aux autres pages
		httpSecurity.authorizeRequests().antMatchers("/register","/addComputerPage","/editComputerPage","/delete","/editComputerPage","/editComputer").access("hasRole( 'ROLE_ADMIN')");
		//Quand le user est connecté mais essaie d'acceder à une page qui ne lui ai pas permis , l'execption suivante est levée.
		httpSecurity.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
		
		//Configuration de login form:
		httpSecurity.authorizeRequests().and().formLogin()
					//L'url de soumission pour les pages de login est le servlet indexé par j_spring_security_check dans spring security
					.loginProcessingUrl("/j_spring_security_check")
					.loginPage("/login")
					.defaultSuccessUrl("/dashboard")
					.failureUrl("/login?error=true")
					.usernameParameter("username")
					.passwordParameter("password")
					.and().logout().logoutUrl("/logout").logoutSuccessUrl("/home");
					
	
	}
}
