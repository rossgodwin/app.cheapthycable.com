package com.gwn.xcbl.data.hibernate.entity.ba;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.gwn.xcbl.data.hibernate.entity.Account;

@Entity
@Table(name = "ba_vw_next_alert")
public class BaNextAlert implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne()
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;
	
	@Column(name = "next_alert_date", nullable = false)
	@Type(type = "com.gwn.xcbl.data.hibernate.ut.LocalDateTimeUserType")
	private LocalDateTime nextAlertDate;

	public BaNextAlert() {
	}
	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public LocalDateTime getNextAlertDate() {
		return nextAlertDate;
	}

	public void setNextAlertDate(LocalDateTime nextAlertDate) {
		this.nextAlertDate = nextAlertDate;
	}
}
