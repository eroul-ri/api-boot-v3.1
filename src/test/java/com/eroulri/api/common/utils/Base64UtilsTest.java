package com.eroulri.api.common.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class Base64UtilsTest {
	@Test
	public void decodeTest() {
		String str = Base64Util.decode("c3ByaW5nYXBpdGVzdA==");
		log.info("message : {}", str);
		Assert.isTrue("springapitest".equals(str));
	}

	@Test
	public void encodeTest() {
		String encodeStr = Base64Util.encode("springapitest");
		log.info("message : {}", encodeStr);
		Assert.isTrue("c3ByaW5nYXBpdGVzdA==".equals(encodeStr));
	}
}
