package com.gwn.xcbl.data.shared.bill;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.gwn.xcbl.data.shared.AccountDTO;
import com.gwn.xcbl.data.shared.ILongId;
import com.gwn.xcbl.data.shared.ProviderDTO;
import com.gwn.xcbl.data.shared.geo.GeoZipCodeDTO;

public class BillDTO implements Serializable, ILongId {

	/**
	 * 
	 */
	private static final long serialVersionUID = 100079555600286579L;

	private Long id = -1L;
	
	private AccountDTO account;
	
	private ProviderDTO provider;
	
	private String providerOther;
	
	private Date validDate;
	
	private Long validDateInMillis;
	
	private GeoZipCodeDTO geoZipCode;
	
	private BigDecimal totalAmount;
	
	private BigDecimal additionalFeesTotalAmount;
	
	private boolean internetService;
	
	private boolean cableService;
	
	private boolean phoneService;
	
	private BillInternetOptionsDTO internetOptions;
	
	private BillCableOptionsDTO cableOptions;
	
	private String comments;
	
	/**
	 * Derived value - may be null
	 */
	private BillLocationStatsDTO stats;
	
	/**
	 * Derived value - may be null
	 */
	private Integer dsqPostCount;
	
	public BillDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AccountDTO getAccount() {
		return account;
	}

	public void setAccount(AccountDTO account) {
		this.account = account;
	}

	public ProviderDTO getProvider() {
		return provider;
	}

	public void setProvider(ProviderDTO provider) {
		this.provider = provider;
	}

	public String getProviderOther() {
		return providerOther;
	}

	public void setProviderOther(String providerOther) {
		this.providerOther = providerOther;
	}

	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}
	
	public Long getValidDateInMillis() {
		return validDateInMillis;
	}

	public void setValidDateInMillis(Long validDateInMillis) {
		this.validDateInMillis = validDateInMillis;
	}

	public GeoZipCodeDTO getGeoZipCode() {
		return geoZipCode;
	}

	public void setGeoZipCode(GeoZipCodeDTO geoZipCode) {
		this.geoZipCode = geoZipCode;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getAdditionalFeesTotalAmount() {
		return additionalFeesTotalAmount;
	}

	public void setAdditionalFeesTotalAmount(BigDecimal additionalFeesTotalAmount) {
		this.additionalFeesTotalAmount = additionalFeesTotalAmount;
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

	public BillInternetOptionsDTO getInternetOptions() {
		return internetOptions;
	}

	public void setInternetOptions(BillInternetOptionsDTO internetOptions) {
		this.internetOptions = internetOptions;
	}

	public BillCableOptionsDTO getCableOptions() {
		return cableOptions;
	}

	public void setCableOptions(BillCableOptionsDTO cableOptions) {
		this.cableOptions = cableOptions;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public BillLocationStatsDTO getStats() {
		return stats;
	}

	public void setStats(BillLocationStatsDTO stats) {
		this.stats = stats;
	}

	public Integer getDsqPostCount() {
		return dsqPostCount;
	}

	public void setDsqPostCount(Integer dsqPostCount) {
		this.dsqPostCount = dsqPostCount;
	}
}
