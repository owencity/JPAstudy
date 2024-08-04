package jjon.pop.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class Webconfiguration {
	
	@Autowired private JwtAuthenticationFilter jwtAuthenticationFilter;
	@Autowired private JwtExceptionFilter jwtExceptionFilter;
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		  CorsConfiguration configuration = new CorsConfiguration();
		    configuration.setAllowedOrigins(
		        Arrays.asList("http://localhost:3000", "http://127.0.0.1:3000"));
		    configuration.setAllowedMethods(List.of("GET", "POST", "PATCH", "DELETE"));
		    configuration.setAllowedHeaders(List.of("*"));
		    configuration.setAllowCredentials(true);
		    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		    source.registerCorsConfiguration("/api/v1/**", configuration);
		    return source;
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors(Customizer.withDefaults())
		.authorizeHttpRequests((request) -> request
				.requestMatchers(HttpMethod.POST, "/api/*/users", "/api/*/users/authenticate")
				.permitAll()
				.anyRequest().authenticated())
		.sessionManagement(
				(session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.csrf(CsrfConfigurer::disable)
		.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
		.addFilterBefore(jwtExceptionFilter, jwtAuthenticationFilter.getClass())
		.httpBasic(Customizer.withDefaults());
		
		return http.build();
		
	}
	
}
