package com.gwn.xcbl.data.dao;

import java.util.List;

import com.gwn.xcbl.data.hibernate.entity.GeoZipCode;
import com.gwn.xcbl.data.shared.ILongId;

public interface GeoZipCodeDAO extends GenericDAO<GeoZipCode, ILongId> {

	public List<GeoZipCode> findByZipCode(String zipCode);
	
	public int countByZipCode(String zipCode);
}
