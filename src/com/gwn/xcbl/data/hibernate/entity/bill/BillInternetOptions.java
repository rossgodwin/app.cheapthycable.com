package com.gwn.xcbl.data.hibernate.entity.bill;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.gwn.xcbl.data.entity.BillInternetOptionsTableMetadata;
import com.gwn.xcbl.data.entity.BillTableMetadata;
import com.gwn.xcbl.data.shared.ILongId;

@Entity
@Table(name = BillInternetOptionsTableMetadata.TABLE_NAME)
public class BillInternetOptions implements ILongId {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id = -1L;
	
	@OneToOne
	@JoinColumn(name = BillInternetOptionsTableMetadata.COL_BILL_ID, referencedColumnName = BillTableMetadata.COL_ID, nullable = false)
	private Bill bill;
	
	@Column(name = "modem", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean modem;
	
	@Column(name = "download_speed_mbps")
	private Integer downloadSpeedMbps;
	
	@Column(name = "download_speed_mbps_rng_lower")
	private Integer downloadSpeedMbpsRngLower;
	
	@Column(name = "download_speed_mbps_rng_upper")
	private Integer downloadSpeedMbpsRngUpper;
	
	@Column(name = "upload_speed_mbps")
	private Integer uploadSpeedMbps;
	
	public BillInternetOptions() {}

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

	public boolean isModem() {
		return modem;
	}

	public void setModem(boolean modem) {
		this.modem = modem;
	}

	public Integer getDownloadSpeedMbps() {
		return downloadSpeedMbps;
	}

	public void setDownloadSpeedMbps(Integer downloadSpeedMbps) {
		this.downloadSpeedMbps = downloadSpeedMbps;
	}

	public Integer getDownloadSpeedMbpsRngLower() {
		return downloadSpeedMbpsRngLower;
	}

	public void setDownloadSpeedMbpsRngLower(Integer downloadSpeedMbpsRngLower) {
		this.downloadSpeedMbpsRngLower = downloadSpeedMbpsRngLower;
	}

	public Integer getDownloadSpeedMbpsRngUpper() {
		return downloadSpeedMbpsRngUpper;
	}

	public void setDownloadSpeedMbpsRngUpper(Integer downloadSpeedMbpsRngUpper) {
		this.downloadSpeedMbpsRngUpper = downloadSpeedMbpsRngUpper;
	}

	public Integer getUploadSpeedMbps() {
		return uploadSpeedMbps;
	}

	public void setUploadSpeedMbps(Integer uploadSpeedMbps) {
		this.uploadSpeedMbps = uploadSpeedMbps;
	}
}
