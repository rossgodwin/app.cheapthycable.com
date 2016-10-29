package com.gwn.xcbl.data.hibernate.dao;

import java.util.List;

import org.hibernate.Query;

import com.gwn.xcbl.data.dao.GeoZipCodeDAO;
import com.gwn.xcbl.data.entity.GeoZipCodeTableMetadata;
import com.gwn.xcbl.data.hibernate.entity.GeoZipCode;
import com.gwn.xcbl.data.model.HaversineFormulaCritr;
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
	
	public static String buildHaversineTable(HaversineFormulaCritr critr) {
		StringBuilder qsb = new StringBuilder();
		
		qsb.append("select");
		{
			qsb.append(" z.").append(GeoZipCodeTableMetadata.COL_ID).append(" as zip_code_id,");
			qsb.append(" p.radius as radius").append(",");
			qsb.append(" p.distance_unit * DEGREES(ACOS(COS(RADIANS(p.latpoint)) * COS(RADIANS(z.").append(GeoZipCodeTableMetadata.COL_LATITUDE).append(")) * COS(RADIANS(p.longpoint - z.").append(GeoZipCodeTableMetadata.COL_LONGITUDE).append(")) + SIN(RADIANS(p.latpoint)) * SIN(RADIANS(z.").append(GeoZipCodeTableMetadata.COL_LATITUDE).append(")))) AS distance");
		}
		
		qsb.append(" from ").append(GeoZipCodeTableMetadata.TABLE_NAME).append(" as z join (");
		{
			qsb.append("select ").append(critr.getLatitude()).append(" as latPoint");
			qsb.append(", ").append(critr.getLongitude()).append(" as longPoint");
			qsb.append(", ").append(critr.getRadius()).append(" as radius");
			qsb.append(", ").append(critr.getDistanceUnit()).append(" as distance_unit");
		}
		qsb.append(") as p on 1 = 1");
		
		qsb.append(" where");
		{
			qsb.append(" z.").append(GeoZipCodeTableMetadata.COL_LATITUDE).append(" between p.latPoint - (p.radius / p.distance_unit) and p.latPoint + (p.radius / p.distance_unit)");
			qsb.append(" and z.").append(GeoZipCodeTableMetadata.COL_LONGITUDE).append(" between p.longpoint - (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint)))) and p.longpoint + (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint))))");
		}
		
		return qsb.toString();
	}
}
