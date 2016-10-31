package com.gwn.xcbl.data.shared.bill;

import java.math.BigDecimal;
import java.util.List;

import com.gwn.xcbl.data.shared.SetOperator;

public class BillSearchCritrDTO {

	private String zipCode;
	
	private Double mileRadius;
	
	private BigDecimal exactTotalAmount;
	
	private Long providerId;
	
	private String matchProviderName;
	
	private SetOperator servicesSetOperator = SetOperator.CONTAINS;
	
	private Boolean internetService;
	
	private Boolean cableService;
	
	private Boolean phoneService;
	
	private Integer cableOptionBoxCount;
	
	private Integer cableOptionDvrCount;
	
	private Boolean cableOptionSpecialChannels;
	
	private List<BillSort> sorts;

	public BillSearchCritrDTO() {
	}

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

	public SetOperator getServicesSetOperator() {
		return servicesSetOperator;
	}

	public void setServicesSetOperator(SetOperator servicesSetOperator) {
		this.servicesSetOperator = servicesSetOperator;
	}

	public Boolean getInternetService() {
		return internetService;
	}

	public void setInternetService(Boolean internetService) {
		this.internetService = internetService;
	}

	public Boolean getCableService() {
		return cableService;
	}

	public void setCableService(Boolean cableService) {
		this.cableService = cableService;
	}

	public Boolean getPhoneService() {
		return phoneService;
	}

	public void setPhoneService(Boolean phoneService) {
		this.phoneService = phoneService;
	}

	public Integer getCableOptionBoxCount() {
		return cableOptionBoxCount;
	}

	public void setCableOptionBoxCount(Integer cableOptionBoxCount) {
		this.cableOptionBoxCount = cableOptionBoxCount;
	}

	public Integer getCableOptionDvrCount() {
		return cableOptionDvrCount;
	}

	public void setCableOptionDvrCount(Integer cableOptionDvrCount) {
		this.cableOptionDvrCount = cableOptionDvrCount;
	}

	public Boolean getCableOptionSpecialChannels() {
		return cableOptionSpecialChannels;
	}

	public void setCableOptionSpecialChannels(Boolean cableOptionSpecialChannels) {
		this.cableOptionSpecialChannels = cableOptionSpecialChannels;
	}

	public List<BillSort> getSorts() {
		return sorts;
	}

	public void setSorts(List<BillSort> sorts) {
		this.sorts = sorts;
	}
}
