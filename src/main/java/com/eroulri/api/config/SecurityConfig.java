package com.eroulri.api.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.eroulri.api.common.filter.JWTAuthenticationFilter;
import com.eroulri.api.common.filter.JWTExceptionFilter;
import com.eroulri.api.common.handler.APIAccessDeniedHandler;
import com.eroulri.api.common.handler.APIAuthenticationEntryPoint;
import com.eroulri.api.common.utils.JWTProvider;
import com.eroulri.api.service.MemberDetailsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@Slf4j
public class SecurityConfig {
	private final APIAccessDeniedHandler apiAccessDeniedHandler;
	private final APIAuthenticationEntryPoint apiAuthenticationEntryPoint;
	private final JWTProvider jwtProvider;
	private final MemberDetailsService memberDetailsService;

	@Bean
	@Order(0)
	public SecurityFilterChain resources(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.authorizeHttpRequests(
							   registry -> registry.requestMatchers(PathRequest.toStaticResources().atCommonLocations()))
						   .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
						   .requestCache(RequestCacheConfigurer::disable)
						   .securityContext(AbstractHttpConfigurer::disable)
						   .sessionManagement(AbstractHttpConfigurer::disable).build();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.httpBasic(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable)
			.cors(Customizer.withDefaults())
			.headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
			.authorizeHttpRequests(
				registry -> registry.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**")
									.permitAll()
									.requestMatchers("/sample", "/v1/authentication")
									.permitAll()
									.anyRequest()
									.authenticated()
			)
			.sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.exceptionHandling(handler ->
				handler.authenticationEntryPoint(apiAuthenticationEntryPoint)
					   .accessDeniedHandler(apiAccessDeniedHandler)
			)
			.userDetailsService(memberDetailsService)
			.addFilterBefore(new JWTAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new JWTExceptionFilter(), JWTAuthenticationFilter.class);

		return httpSecurity.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
}
