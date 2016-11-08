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

import com.gwn.xcbl.data.entity.ba.BaAlertSentLogTableMetadata;

@Entity
@Table(name = BaAlertSentLogTableMetadata.TABLE_NAME)
public class BaAlertSentLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id = -1L;
	
	@ManyToOne()
	@JoinColumn(name = BaAlertSentLogTableMetadata.COL_ALERT_ID, nullable = false)
	private BaAlert alert;
	
	@Column(name = BaAlertSentLogTableMetadata.COL_ALERTED_DATE, nullable = false)
	@Type(type = "com.gwn.xcbl.data.hibernate.ut.LocalDateTimeUserType")
	private LocalDateTime alertedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BaAlert getAlert() {
		return alert;
	}

	public void setAlert(BaAlert alert) {
		this.alert = alert;
	}

	public LocalDateTime getAlertedDate() {
		return alertedDate;
	}

	public void setAlertedDate(LocalDateTime alertedDate) {
		this.alertedDate = alertedDate;
	}
}
