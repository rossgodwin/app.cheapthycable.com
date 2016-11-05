package com.gwn.xcbl.data.hibernate.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vw_current_bill")
public class CurrentBill implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne()
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;
	
	@ManyToOne()
	@JoinColumn(name = "bill_id", nullable = false)
	private Bill bill;

	public CurrentBill() {
	}
	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}
}
