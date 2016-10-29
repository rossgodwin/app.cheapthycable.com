package com.gwn.xcbl.data.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gwn.xcbl.data.entity.ProviderTableMetadata;
import com.gwn.xcbl.data.shared.ILongId;
import com.gwn.xcbl.data.shared.ProviderConstants;

@Entity
@Table(name = ProviderTableMetadata.TABLE_NAME)
public class Provider implements ILongId {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id = -1L;
	
	@Column(name = ProviderTableMetadata.COL_NAME, nullable = false, length = ProviderConstants.MAX_SIZE_NAME)
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
