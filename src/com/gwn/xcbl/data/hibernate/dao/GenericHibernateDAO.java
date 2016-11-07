package com.gwn.xcbl.data.hibernate.dao;

import com.gwn.xcbl.data.dao.GenericDAO;
import com.gwn.xcbl.data.shared.ILongId;

/**
 * https://developer.jboss.org/wiki/GenericDataAccessObjects
 * 
 * @param <T>
 * @param <ID>
 */
public abstract class GenericHibernateDAO<T, ID extends ILongId> extends BaseDAO implements GenericDAO<T, ID> {

}
