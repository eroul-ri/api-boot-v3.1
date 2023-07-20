package com.eroulri.api.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonField {
	private boolean activated;
	private boolean deleted;

	private String createdBy;
	private String updatedBy;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
