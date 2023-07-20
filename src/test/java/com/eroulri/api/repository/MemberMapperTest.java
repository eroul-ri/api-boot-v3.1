package com.eroulri.api.repository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import com.eroulri.api.model.Member;

import io.jsonwebtoken.lang.Assert;

@MybatisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberMapperTest {

	@Autowired
	MemberDetailsMapper memberDetailsMapper;

	@Test
	public void findMemberTest() {
		Optional<Member> member = memberDetailsMapper.findMember("ADMIN");
		Assert.notNull(member);
	}
}
