package com.eroulri.api.dao;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.eroulri.api.model.Member;
import com.eroulri.api.repository.MemberDetailsMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberDetailsDomain {
	private final MemberDetailsMapper memberDetailsMapper;

	public Optional<Member> findMemberByMemberNm(String username) {
		return memberDetailsMapper.findMember(username);
	}
}
