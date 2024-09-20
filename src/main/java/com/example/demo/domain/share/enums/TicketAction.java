package com.example.demo.domain.share.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TicketAction {

	BOOK("BOOK", "預約"), CHECK_IN("CHECK_IN", "搭乘"), REFUNDED("REFUNDED", "取消訂位");
	
	
	@Getter
	private String name;
	
	@Getter
	private String value;
}
