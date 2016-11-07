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

import com.gwn.xcbl.data.entity.BillCableOptionsTableMetadata;
import com.gwn.xcbl.data.entity.BillTableMetadata;
import com.gwn.xcbl.data.shared.ILongId;

@Entity
@Table(name = BillCableOptionsTableMetadata.TABLE_NAME)
public class BillCableOptions implements ILongId {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id = -1L;
	
	@OneToOne
	@JoinColumn(name = BillCableOptionsTableMetadata.COL_BILL_ID, referencedColumnName = BillTableMetadata.COL_ID, nullable = false)
	private Bill bill;
	
	@Column(name = BillCableOptionsTableMetadata.COL_DVR_COUNT)
	private Integer dvrCount;
	
	@Column(name = BillCableOptionsTableMetadata.COL_BOX_COUNT)
	private Integer boxCount;
	
	@Column(name = BillCableOptionsTableMetadata.COL_SPECIAL_CHANNELS, nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean specialChannels;

	public BillCableOptions() {}

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

	public Integer getDvrCount() {
		return dvrCount;
	}

	public void setDvrCount(Integer dvrCount) {
		this.dvrCount = dvrCount;
	}

	public Integer getBoxCount() {
		return boxCount;
	}

	public void setBoxCount(Integer boxCount) {
		this.boxCount = boxCount;
	}

	public boolean isSpecialChannels() {
		return specialChannels;
	}

	public void setSpecialChannels(boolean specialChannels) {
		this.specialChannels = specialChannels;
	}
}
