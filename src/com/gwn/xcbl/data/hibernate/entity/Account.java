package com.gwn.xcbl.data.hibernate.entity;

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
	
	@Column(name = "dsq_bill_post_notify", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean dsqBillPostNotify;

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

	public boolean isDsqBillPostNotify() {
		return dsqBillPostNotify;
	}

	public void setDsqBillPostNotify(boolean dsqBillPostNotify) {
		this.dsqBillPostNotify = dsqBillPostNotify;
	}
}
