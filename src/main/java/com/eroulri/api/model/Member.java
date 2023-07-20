package com.eroulri.api.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.eroulri.api.contant.MEMBER_TYPE;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member extends CommonField implements UserDetails {
	private long memberId;

	private String loginId;
	private String email;
	private String memberNm;
	private String pwd;
	private String description;
	private String blockBy;

	private MEMBER_TYPE memberType;
	private LocalDateTime blockAt;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();

		authorities.add(new SimpleGrantedAuthority("ROLE_".concat(this.getMemberType().getCode())));

		return authorities;
	}

	@Override
	public String getPassword() {
		return this.pwd;
	}

	@Override
	public String getUsername() {
		return this.loginId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.blockAt == null;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.isActivated();
	}
}
