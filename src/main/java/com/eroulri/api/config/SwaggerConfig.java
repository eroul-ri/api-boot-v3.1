package com.eroulri.api.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPIDefinition(
	info = @Info(
		title = "API API Document",
		description = "API API Document",
		version = "v0.0.1"
	),
	tags = {
		@Tag(name = "00. Sample", description = "샘플"),
		@Tag(name = "01. Common", description = "공통"),
		@Tag(name = "02. User", description = "사용자"),
	},
	security = {
		@SecurityRequirement(name = "Authorization")
	}
)
@SecuritySchemes({
	@SecurityScheme(name = "Authorization",
		type = SecuritySchemeType.APIKEY,
		description = "access token",
		in = SecuritySchemeIn.HEADER,
		paramName = "Authorization"),
})
@Configuration
public class SwaggerConfig {

}
