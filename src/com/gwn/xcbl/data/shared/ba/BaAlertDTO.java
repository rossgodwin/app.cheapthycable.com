package com.gwn.xcbl.data.shared.ba;

import java.math.BigDecimal;

import com.gwn.xcbl.data.shared.AccountDTO;
import com.gwn.xcbl.data.shared.ILongId;

public class BaAlertDTO implements ILongId {

	private Long id = -1L;
	
	private AccountDTO account;
	
	private Integer receiveEmailFrequencyDays;
	
	private Double critrMileRadius;
	
	private BigDecimal critrAmountBelow;
	
	private boolean unsubscribed;
	
	public BaAlertDTO() {
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

	public boolean isUnsubscribed() {
		return unsubscribed;
	}

	public void setUnsubscribed(boolean unsubscribed) {
		this.unsubscribed = unsubscribed;
	}
}
