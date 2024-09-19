package com.example.demo.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum StatusCode {

	SUCCESS("SUCCESS"), ERROR("ERROR");

	@Getter
	private final String value;
}
