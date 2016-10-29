package com.gwn.xcbl.data.shared.bill.create;

import java.math.BigDecimal;

import com.gwn.xcbl.data.shared.ProviderDTO;

public class BillCreateDTO {

	private String zipCode;
	
	private ProviderDTO selectedProvider;
	
	private String typedProviderName;
	
	private String lookupProviderName;
	
	private BigDecimal totalAmount;
	
	private boolean internetService;
	
	private boolean cableService;
	
	private boolean phoneService;
	
	private BillCreateInternetOptionsDTO internetOptions;
	
	private BillCreateCableOptionsDTO cableOptions;
	
	private String comments;
	
	public BillCreateDTO() {
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public ProviderDTO getSelectedProvider() {
		return selectedProvider;
	}

	public void setSelectedProvider(ProviderDTO selectedProvider) {
		this.selectedProvider = selectedProvider;
	}

	public String getTypedProviderName() {
		return typedProviderName;
	}

	public void setTypedProviderName(String typedProviderName) {
		this.typedProviderName = typedProviderName;
	}
	
	public String getLookupProviderName() {
		return lookupProviderName;
	}

	public void setLookupProviderName(String lookupProviderName) {
		this.lookupProviderName = lookupProviderName;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public boolean isInternetService() {
		return internetService;
	}

	public void setInternetService(boolean internetService) {
		this.internetService = internetService;
	}

	public boolean isCableService() {
		return cableService;
	}

	public void setCableService(boolean cableService) {
		this.cableService = cableService;
	}

	public boolean isPhoneService() {
		return phoneService;
	}

	public void setPhoneService(boolean phoneService) {
		this.phoneService = phoneService;
	}

//	public BillCreateServiceDTO getService() {
//		return service;
//	}
//
//	public void setService(BillCreateServiceDTO service) {
//		this.service = service;
//	}

	public BillCreateInternetOptionsDTO getInternetOptions() {
		return internetOptions;
	}

	public void setInternetOptions(BillCreateInternetOptionsDTO internetOptions) {
		this.internetOptions = internetOptions;
	}

	public BillCreateCableOptionsDTO getCableOptions() {
		return cableOptions;
	}

	public void setCableOptions(BillCreateCableOptionsDTO cableOptions) {
		this.cableOptions = cableOptions;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
