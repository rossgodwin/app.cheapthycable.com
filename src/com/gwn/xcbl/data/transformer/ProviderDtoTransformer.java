package com.gwn.xcbl.data.transformer;

import org.apache.commons.collections4.Transformer;

import com.gwn.xcbl.data.hibernate.entity.Provider;
import com.gwn.xcbl.data.shared.ProviderDTO;

public class ProviderDtoTransformer implements Transformer<Provider, ProviderDTO> {

	@Override
	public ProviderDTO transform(Provider input) {
		ProviderDTO r = new ProviderDTO();
		r.setId(input.getId());
		r.setName(input.getName());
		return r;
	}
}
