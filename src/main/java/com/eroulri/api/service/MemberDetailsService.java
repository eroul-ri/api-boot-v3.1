package com.eroulri.api.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eroulri.api.dao.MemberDetailsDomain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberDetailsService implements UserDetailsService {
	private final MemberDetailsDomain memberDetailsDomain;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// @TODO eroul-ri username not found
		return memberDetailsDomain.findMemberByMemberNm(username)
								  .orElseThrow(() -> new UsernameNotFoundException(""));
	}
}
