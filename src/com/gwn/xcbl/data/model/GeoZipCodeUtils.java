package com.gwn.xcbl.data.model;

import java.text.MessageFormat;

import com.gwn.xcbl.data.hibernate.entity.GeoZipCode;

public class GeoZipCodeUtils {

	/**
	 * 
	 * @param geoZipCode
	 * @return City, State Code Zip Code
	 */
	public static String getCityStateCodeZip(GeoZipCode geoZipCode) {
		String pattern = "{0}, {1} {2}";
		String r = MessageFormat.format(pattern, geoZipCode.getCity(), geoZipCode.getStateCode(), geoZipCode.getZipCode());
		return r;
	}
}
