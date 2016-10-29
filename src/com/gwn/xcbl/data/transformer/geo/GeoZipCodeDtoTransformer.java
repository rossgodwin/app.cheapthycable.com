package com.gwn.xcbl.data.transformer.geo;

import org.apache.commons.collections4.Transformer;

import com.gwn.xcbl.data.hibernate.entity.GeoZipCode;
import com.gwn.xcbl.data.shared.geo.GeoZipCodeDTO;

public class GeoZipCodeDtoTransformer implements Transformer<GeoZipCode, GeoZipCodeDTO> {

	@Override
	public GeoZipCodeDTO transform(GeoZipCode input) {
		GeoZipCodeDTO r = new GeoZipCodeDTO();
		r.setId(input.getId());
		r.setZipCode(input.getZipCode());
		r.setCity(input.getCity());
		r.setStateCode(input.getStateCode());
		return r;
	}
}
