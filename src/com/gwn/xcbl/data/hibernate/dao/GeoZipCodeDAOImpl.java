package com.gwn.xcbl.data.hibernate.dao;

import java.util.List;

import org.hibernate.Query;

import com.gwn.xcbl.data.dao.GeoZipCodeDAO;
import com.gwn.xcbl.data.hibernate.entity.GeoZipCode;
import com.gwn.xcbl.data.shared.ILongId;

public class GeoZipCodeDAOImpl extends GenericHibernateDAO<GeoZipCode, ILongId> implements GeoZipCodeDAO {

	@Override
	public GeoZipCode findByILongId(ILongId id, boolean readOnly) {
		return findById(id.getId(), readOnly);
	}

	@Override
	public GeoZipCode findById(Long id, boolean readOnly) {
		Query q = getSession().createQuery("from " + GeoZipCode.class.getSimpleName() + " u where u.id = :id");
		q.setParameter("id", id);
		GeoZipCode r = (GeoZipCode) q.uniqueResult();
		return r;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GeoZipCode> findByZipCode(String zipCode) {
		Query q = getSession().createQuery("from " + GeoZipCode.class.getSimpleName() + " u where u.zipCode = :zipCode");
		q.setParameter("zipCode", zipCode);
		List<GeoZipCode> r = (List<GeoZipCode>) q.list();
		return r;
	}

	@Override
	public int countByZipCode(String zipCode) {
		Query q = getSession().createQuery("select count(*) from " + GeoZipCode.class.getSimpleName() + " u where u.zipCode = :zipCode");
		q.setParameter("zipCode", zipCode);
		Number r = (Number)q.uniqueResult();
		return r.intValue();
	}
}
