package com.eroulri.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eroulri.api.common.utils.MessageUtil;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "00. Sample")
@RestController
public class TestController {
	private final MessageUtil messageUtil;

	public TestController(MessageUtil messageUtil) {
		this.messageUtil = messageUtil;
	}

	@GetMapping("sample")
	public String test() {
		return messageUtil.getMessage("hi");
	}
}
