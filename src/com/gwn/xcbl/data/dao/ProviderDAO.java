package com.gwn.xcbl.data.dao;

import java.util.List;

import com.gwn.xcbl.data.hibernate.entity.Provider;
import com.gwn.xcbl.data.shared.ILongId;
import com.gwn.xcbl.data.shared.ProviderSearchCritrDTO;

public interface ProviderDAO extends GenericDAO<Provider, ILongId> {

	public List<Provider> findByCritr(ProviderSearchCritrDTO critr, Integer offset, Integer limit);
	
	public int countByCritr(ProviderSearchCritrDTO critr);
	
	public Provider findByName(String name);
}
