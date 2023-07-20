package com.eroulri.api.common.utils;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.eroulri.api.contant.INVALID_REASON;
import com.eroulri.api.contant.TOKEN_TYPE;
import com.eroulri.api.dto.TokenInfo;
import com.eroulri.api.exception.InvalidTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JWTProvider {
	private final Key key;
	private final JWTProperties jwtProperties;

	public JWTProvider(JWTProperties jwtProperties) {
		this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret()));
		this.jwtProperties = jwtProperties;
	}

	public TokenInfo.AccessToken generateToken(Authentication authentication) {
		// 권한 생성
		String authorities = authentication.getAuthorities().stream()
										   .map(GrantedAuthority::getAuthority)
										   .collect(Collectors.joining(","));

		long now = System.currentTimeMillis();

		Date accessTokenExpiresIn = new Date(now + jwtProperties.getExpiredIn());

		String accessToken = Jwts.builder()
								 .setSubject(authentication.getName())
								 .claim(jwtProperties.getClaimsKey(), authorities)
								 .setExpiration(accessTokenExpiresIn)
								 .signWith(key, SignatureAlgorithm.HS256)
								 .compact();

		return TokenInfo.AccessToken.builder()
									.tokenType(TOKEN_TYPE.Bearer)
									.accessToken(accessToken)
									.refreshToken(generateRefreshToken(now))
									.expiredIn(accessTokenExpiresIn)
									.build();
	}

	public String generateRefreshToken(long now) {
		long refreshExpiredTime = now + jwtProperties.getRefreshExpiredIn();

		return Jwts.builder()
				   .setExpiration(new Date(refreshExpiredTime))
				   .signWith(key, SignatureAlgorithm.HS256)
				   .compact();
	}

	public Authentication getAuthentication(String accessToken) {
		Claims claims = parseClaims(accessToken);
		String claimKey = jwtProperties.getClaimsKey();
		// 권한 정보 없음
		if(claims.get(claimKey) == null) {
			throw new InvalidTokenException(INVALID_REASON.AUTHORITY);
		}
		List<GrantedAuthority> grantedAuthorities = parseStringAuthority(claims.get(claimKey).toString());

		UserDetails principal = new User(claims.getSubject(), StringUtils.EMPTY, grantedAuthorities);
		return new UsernamePasswordAuthenticationToken(principal, StringUtils.EMPTY, grantedAuthorities);
	}

	public boolean isValidToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token);
			return true;
		} catch(io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			throw new InvalidTokenException(INVALID_REASON.MALFORMED);
		} catch(ExpiredJwtException e) {
			throw new InvalidTokenException(INVALID_REASON.EXPIRED);
		} catch(UnsupportedJwtException e) {
			throw new InvalidTokenException(INVALID_REASON.UNSUPPORTED);
		} catch(IllegalArgumentException e) {
			throw new InvalidTokenException(INVALID_REASON.ETC);
		}
	}

	private Claims parseClaims(String accessToken) {

		return Jwts.parserBuilder()
				   .setSigningKey(key)
				   .build()
				   .parseClaimsJws(accessToken)
				   .getBody();
	}

	private List<GrantedAuthority> parseStringAuthority(String authority) {
		return Arrays.stream(authority.split(","))
					 .map(SimpleGrantedAuthority::new)
					 .collect(Collectors.toList());
	}
}
