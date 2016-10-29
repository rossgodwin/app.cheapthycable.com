package com.gwn.xcbl.data.hibernate.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.gwn.xcbl.data.shared.EmailInviteConsts;
import com.gwn.xcbl.data.shared.ILongId;

@Entity
@Table(name = "email_invite")
public class EmailInvite implements ILongId {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id = -1L;
	
	@ManyToOne()
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;
	
	@Column(name = "from_name", length = EmailInviteConsts.MAX_SIZE_FROM_NAME, nullable = false)
	private String fromName;
	
	@Column(name = "email", length = EmailInviteConsts.MAX_SIZE_EMAIL, nullable = false)
	private String email;
	
	@Column(name = "invite_date", nullable = false)
	@Type(type = "com.gwn.xcbl.data.hibernate.ut.LocalDateUserType")
	private LocalDate inviteDate;
	
	@Column(name = "emailed_date")
	@Type(type = "com.gwn.xcbl.data.hibernate.ut.LocalDateUserType")
	private LocalDate emailedDate;
	
	public EmailInvite() {}

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

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getInviteDate() {
		return inviteDate;
	}

	public void setInviteDate(LocalDate inviteDate) {
		this.inviteDate = inviteDate;
	}

	public LocalDate getEmailedDate() {
		return emailedDate;
	}

	public void setEmailedDate(LocalDate emailedDate) {
		this.emailedDate = emailedDate;
	}
}
