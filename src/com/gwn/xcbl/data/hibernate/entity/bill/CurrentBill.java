package com.gwn.xcbl.data.hibernate.entity.bill;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gwn.xcbl.data.entity.CurrentBillViewMetadata;
import com.gwn.xcbl.data.hibernate.entity.Account;

@Entity
@Table(name = CurrentBillViewMetadata.TABLE_NAME)
public class CurrentBill implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne()
	@JoinColumn(name = CurrentBillViewMetadata.COL_ACCOUNT_ID, nullable = false)
	private Account account;
	
	@ManyToOne()
	@JoinColumn(name = CurrentBillViewMetadata.COL_BILL_ID, nullable = false)
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
