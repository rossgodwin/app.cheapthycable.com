package com.gwn.xcbl.data.dao;

import com.gwn.xcbl.data.shared.ILongId;

/**
 * https://developer.jboss.org/wiki/GenericDataAccessObjects
 *
 * @param <T>
 * @param <ID>
 */
public interface GenericDAO<T, ID extends ILongId> {

	T findByILongId(ID id, boolean readOnly);
	
	public T findById(Long id, boolean readOnly);
}
