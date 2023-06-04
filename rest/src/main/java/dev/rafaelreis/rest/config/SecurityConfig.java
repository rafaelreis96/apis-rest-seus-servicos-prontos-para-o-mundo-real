package dev.rafaelreis.rest.config;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
public class SecurityConfig  {
 
	private static final String[] WHITE_LIST = {
			"/swagger-ui.html", 
			"/swagger-ui/**", 
			"/v3/api-docs/**"
	};
	
	@Bean
	DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
			.setType(EmbeddedDatabaseType.H2)
			.addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
			.build();
	}
	 
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf()
			.disable();
		http.cors();
		
		http.headers()
			.frameOptions()
			.disable();
		
		http.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers(WHITE_LIST)
				.permitAll()
				.anyRequest()
				.authenticated())
		 	.httpBasic();
		
		return http.build();
	}

//	@Bean
//	public UserDetailsService userDetailsService() {
//		
//		String password = "{noop}password";
//				
//		UserDetails driver = User.builder()
//				.username("driver")
//				.password(password)
//				.roles("DRIVER")
//				.build();
//		
//		UserDetails passenger = User.builder()
//				.username("passenger")
//				.password(password)
//				.roles("PASSENGER")
//				.build();
//		
//		UserDetails admin = User.builder()
//				.username("admin")
//				.password(password)
//				.roles("ADMIN")
//				.build();
//			
//		return new InMemoryUserDetailsManager(driver, passenger, admin);
//	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		
		String password = "password";
				
		UserDetails driver = User.builder()
				.username("driver")
				.password(passwordEncoder().encode(password))
				.roles("DRIVER")
				.build();
		
		UserDetails passenger = User.builder()
				.username("passenger")
				.password(passwordEncoder().encode(password))
				.roles("PASSENGER")
				.build();
		
		UserDetails admin = User.builder()
				.username("admin")
				.password(passwordEncoder().encode(password))
				.roles("ADMIN")
				.build();
			 
		JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource());
		users.createUser(driver);
		users.createUser(passenger);
		users.createUser(admin);
		return users;
	}
		
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("https://bestcars.com"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH"));
		configuration.addAllowedHeader("*");
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		
		return source;
	}

}
