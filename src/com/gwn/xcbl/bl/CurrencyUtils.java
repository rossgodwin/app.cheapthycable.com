package com.gwn.xcbl.bl;

import java.math.BigDecimal;

public class CurrencyUtils {

	public static BigDecimal roundCurrency(BigDecimal amount) {
		BigDecimal roundedAmount = amount.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return roundedAmount;
	}
}
