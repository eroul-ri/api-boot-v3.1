package com.eroulri.api.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.eroulri.api.model.Member;

@Mapper
public interface MemberDetailsMapper {
	Optional<Member> findMember(String username);
}
