package com.gwn.xcbl.data.hibernate.entity.bill.dsq;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gwn.xcbl.data.hibernate.entity.bill.Bill;

@Entity
@Table(name = "dsq_bill_thread")
public class DsqBillThread {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id = -1L;
	
	@ManyToOne()
	@JoinColumn(name = "bill_id", nullable = false)
	private Bill bill;
	
	@Column(name = "dsq_thread_id", nullable = false)
	private Long dsqThreadId;
	
	@Column(name = "dsq_posts")
	private Integer dsqPosts;
	
	@Column(name = "dsq_likes")
	private Integer dsqLikes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public Long getDsqThreadId() {
		return dsqThreadId;
	}

	public void setDsqThreadId(Long dsqThreadId) {
		this.dsqThreadId = dsqThreadId;
	}

	public Integer getDsqPosts() {
		return dsqPosts;
	}

	public void setDsqPosts(Integer dsqPosts) {
		this.dsqPosts = dsqPosts;
	}

	public Integer getDsqLikes() {
		return dsqLikes;
	}

	public void setDsqLikes(Integer dsqLikes) {
		this.dsqLikes = dsqLikes;
	}
}
