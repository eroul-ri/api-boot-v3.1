package com.eroulri.api.common.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class MessageUtilTest {
	@Autowired
	private MessageUtil messageUtil;

	@Test
	public void test() {
		String message = messageUtil.getMessage("hi");

		log.info("message : {}", message);
		Assert.isTrue("안녕~~".equals(message), "message source value");
	}
}
