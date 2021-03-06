package com.gwn.xcbl.data.hibernate.entity.bill.dsq;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gwn.xcbl.data.hibernate.entity.Account;
import com.gwn.xcbl.data.hibernate.entity.bill.Bill;

@Entity
@Table(name = "dsq_bill_post")
public class DsqBillPost {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id = -1L;
	
	@ManyToOne()
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;
	
	@ManyToOne()
	@JoinColumn(name = "bill_id", nullable = false)
	private Bill bill;
	
	@Column(name = "dsq_post_id", nullable = false)
	private Long dsqPostId;
	
	@Column(name = "dsq_thread_id")
	private Long dsqThreadId;
	
	public DsqBillPost() {
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

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public Long getDsqPostId() {
		return dsqPostId;
	}

	public void setDsqPostId(Long dsqPostId) {
		this.dsqPostId = dsqPostId;
	}

	public Long getDsqThreadId() {
		return dsqThreadId;
	}

	public void setDsqThreadId(Long dsqThreadId) {
		this.dsqThreadId = dsqThreadId;
	}
}
