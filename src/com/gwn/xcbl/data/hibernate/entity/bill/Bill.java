package com.gwn.xcbl.data.hibernate.entity.bill;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.gwn.xcbl.data.entity.BillTableMetadata;
import com.gwn.xcbl.data.hibernate.entity.Account;
import com.gwn.xcbl.data.hibernate.entity.GeoZipCode;
import com.gwn.xcbl.data.hibernate.entity.Provider;
import com.gwn.xcbl.data.shared.ILongId;
import com.gwn.xcbl.data.shared.bill.BillConstants;

@Entity
@Table(name = BillTableMetadata.TABLE_NAME)
public class Bill implements ILongId {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id = -1L;
	
	@ManyToOne()
	@JoinColumn(name = BillTableMetadata.COL_ACCOUNT_ID, nullable = false)
	private Account account;
	
	@ManyToOne()
	@JoinColumn(name = BillTableMetadata.COL_PROVIDER_ID)
	private Provider provider;
	
	@Column(name = BillTableMetadata.COL_PROVIDER_OTHER, length = BillConstants.MAX_SIZE_PROVIDER_OTHER)
	private String providerOther;
	
	@Column(name = BillTableMetadata.COL_VALID_DATE, nullable = false)
	@Type(type = "com.gwn.xcbl.data.hibernate.ut.LocalDateUserType")
	private LocalDate validDate;
	
	@ManyToOne()
	@JoinColumn(name = BillTableMetadata.COL_GEO_ZIP_CODE_ID, nullable = false)
	private GeoZipCode geoZipCode;
	
	@Column(name = BillTableMetadata.COL_TOTAL_AMOUNT, nullable = false)
	private BigDecimal totalAmount;
	
	@Column(name = "additional_fees_total_amount", nullable = true)
	private BigDecimal additionalFeesTotalAmount;
	
	@Column(name = BillTableMetadata.COL_INTERNET_SERVICE, nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean internetService;
	
	@Column(name = BillTableMetadata.COL_CABLE_SERVICE, nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean cableService;
	
	@Column(name = BillTableMetadata.COL_PHONE_SERVICE, nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean phoneService;
	
	@OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "bill")
	private BillInternetOptions internetOptions;
	
	@OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "bill")
	private BillCableOptions cableOptions;
	
	@Column(name = "comments", nullable = true)
	private String comments;
	
	public Bill() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public String getProviderOther() {
		return providerOther;
	}

	public void setProviderOther(String providerOther) {
		this.providerOther = providerOther;
	}

	public LocalDate getValidDate() {
		return validDate;
	}

	public void setValidDate(LocalDate validDate) {
		this.validDate = validDate;
	}

	public GeoZipCode getGeoZipCode() {
		return geoZipCode;
	}

	public void setGeoZipCode(GeoZipCode geoZipCode) {
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

	public BillInternetOptions getInternetOptions() {
		return internetOptions;
	}

	public void setInternetOptions(BillInternetOptions internetOptions) {
		this.internetOptions = internetOptions;
	}

	public BillCableOptions getCableOptions() {
		return cableOptions;
	}

	public void setCableOptions(BillCableOptions cableOptions) {
		this.cableOptions = cableOptions;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
