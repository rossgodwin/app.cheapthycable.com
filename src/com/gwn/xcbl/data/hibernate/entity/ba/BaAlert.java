package com.gwn.xcbl.data.hibernate.entity.ba;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.gwn.xcbl.data.entity.ba.BaAlertTableMetadata;
import com.gwn.xcbl.data.hibernate.entity.Account;
import com.gwn.xcbl.data.shared.ILongId;

@Entity
@Table(name = BaAlertTableMetadata.TABLE_NAME)
public class BaAlert implements ILongId {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id = -1L;
	
	@ManyToOne()
	@JoinColumn(name = BaAlertTableMetadata.COL_ACCOUNT_ID, nullable = false)
	private Account account;
	
	@Column(name = BaAlertTableMetadata.COL_RECEIVE_EMAIL, nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean receiveEmail;
	
	@Column(name = BaAlertTableMetadata.COL_RECEIVE_EMAIL_FREQUENCY_DAYS)
	private Integer receiveEmailFrequencyDays;
	
	@Column(name = "critr_mile_radius")
	private Double critrMileRadius;
	
	@Column(name = "critr_amount_below")
	private BigDecimal critrAmountBelow;

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

	public boolean isReceiveEmail() {
		return receiveEmail;
	}

	public void setReceiveEmail(boolean receiveEmail) {
		this.receiveEmail = receiveEmail;
	}

	public Integer getReceiveEmailFrequencyDays() {
		return receiveEmailFrequencyDays;
	}

	public void setReceiveEmailFrequencyDays(Integer receiveEmailFrequencyDays) {
		this.receiveEmailFrequencyDays = receiveEmailFrequencyDays;
	}

	public Double getCritrMileRadius() {
		return critrMileRadius;
	}

	public void setCritrMileRadius(Double critrMileRadius) {
		this.critrMileRadius = critrMileRadius;
	}

	public BigDecimal getCritrAmountBelow() {
		return critrAmountBelow;
	}

	public void setCritrAmountBelow(BigDecimal critrAmountBelow) {
		this.critrAmountBelow = critrAmountBelow;
	}
}
