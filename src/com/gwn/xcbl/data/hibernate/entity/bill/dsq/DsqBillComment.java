package com.gwn.xcbl.data.hibernate.entity.bill.dsq;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gwn.xcbl.data.entity.BillCableOptionsTableMetadata;
import com.gwn.xcbl.data.hibernate.entity.Account;
import com.gwn.xcbl.data.hibernate.entity.bill.Bill;

@Entity
@Table(name = "dsq_bill_comment")
public class DsqBillComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id = -1L;
	
	@ManyToOne()
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;
	
	@ManyToOne()
	@JoinColumn(name = "bill_id", nullable = false)
	private Bill bill;
	
	@Column(name = "dsq_comment_id", nullable = false)
	private Long dsqCommentId;
	
	public DsqBillComment() {
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

	public Long getDsqCommentId() {
		return dsqCommentId;
	}

	public void setDsqCommentId(Long dsqCommentId) {
		this.dsqCommentId = dsqCommentId;
	}
}
