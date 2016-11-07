package com.gwn.xcbl.data.hibernate.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.gwn.xcbl.data.shared.ILongId;

@Entity
@Table(name = "account")
public class Account implements ILongId {

	public static Account idInstance(long id) {
		Account r = new Account();
		r.setId(id);
		return r;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id = -1L;
	
	@Column(name = "create_date", nullable = false)
	@Type(type = "com.gwn.xcbl.data.hibernate.ut.LocalDateTimeUserType")
	private LocalDateTime createDate;

	@Column(name = "bill_alert_receive", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean billAlertReceive;
	
	@Column(name = "bill_alert_receive_frequency_days")
	private Integer billAlertReceiveFrequencyDays;
	
	@Column(name = "bill_alert_receive_mile_radius")
	private Double billAlertReceiveMileRadius;
	
	@Column(name = "bill_alert_receive_amount_below")
	private BigDecimal billAlertReceiveAmountBelow;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public boolean isBillAlertReceive() {
		return billAlertReceive;
	}

	public void setBillAlertReceive(boolean billAlertReceive) {
		this.billAlertReceive = billAlertReceive;
	}

	public Integer getBillAlertReceiveFrequencyDays() {
		return billAlertReceiveFrequencyDays;
	}

	public void setBillAlertReceiveFrequencyDays(Integer billAlertReceiveFrequencyDays) {
		this.billAlertReceiveFrequencyDays = billAlertReceiveFrequencyDays;
	}

	public Double getBillAlertReceiveMileRadius() {
		return billAlertReceiveMileRadius;
	}

	public void setBillAlertReceiveMileRadius(Double billAlertReceiveMileRadius) {
		this.billAlertReceiveMileRadius = billAlertReceiveMileRadius;
	}

	public BigDecimal getBillAlertReceiveAmountBelow() {
		return billAlertReceiveAmountBelow;
	}

	public void setBillAlertReceiveAmountBelow(BigDecimal billAlertReceiveAmountBelow) {
		this.billAlertReceiveAmountBelow = billAlertReceiveAmountBelow;
	}
}
