package com.gwn.xcbl.data.hibernate.entity.ba;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.gwn.xcbl.data.hibernate.entity.Account;

@Entity
@Table(name = "ba_last_alert")
public class BaLastAlert {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id = -1L;
	
	@ManyToOne()
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;
	
	@Column(name = "alerted_date", nullable = false)
	@Type(type = "com.gwn.xcbl.data.hibernate.ut.LocalDateTimeUserType")
	private LocalDateTime alertedDate;

	public BaLastAlert() {
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

	public LocalDateTime getAlertedDate() {
		return alertedDate;
	}

	public void setAlertedDate(LocalDateTime alertedDate) {
		this.alertedDate = alertedDate;
	}
}
