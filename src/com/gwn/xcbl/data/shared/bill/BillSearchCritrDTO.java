package com.gwn.xcbl.data.shared.bill;

import java.math.BigDecimal;
import java.util.List;

public class BillSearchCritrDTO {

	private String zipCode;
	
	private Double mileRadius;
	
	private BigDecimal exactTotalAmount;
	
	private Long providerId;
	
	private String matchProviderName;
	
	private List<BillSort> sorts;

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Double getMileRadius() {
		return mileRadius;
	}

	public void setMileRadius(Double mileRadius) {
		this.mileRadius = mileRadius;
	}

	public BigDecimal getExactTotalAmount() {
		return exactTotalAmount;
	}

	public void setExactTotalAmount(BigDecimal exactTotalAmount) {
		this.exactTotalAmount = exactTotalAmount;
	}

	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}
	
	public String getMatchProviderName() {
		return matchProviderName;
	}

	public void setMatchProviderName(String matchProviderName) {
		this.matchProviderName = matchProviderName;
	}
	
	public List<BillSort> getSorts() {
		return sorts;
	}

	public void setSorts(List<BillSort> sorts) {
		this.sorts = sorts;
	}
}
