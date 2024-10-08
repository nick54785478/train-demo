package com.example.demo.domain.share;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyDepositedData {
	
	private String uuid;

	private String username; // 使用者名稱
	
	private BigDecimal balance;	// 餘額
}
