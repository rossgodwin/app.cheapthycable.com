package com.gwn.xcbl.data.hibernate.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.gwn.xcbl.data.dao.ProviderDAO;
import com.gwn.xcbl.data.hibernate.entity.Provider;
import com.gwn.xcbl.data.model.QueryComposite;
import com.gwn.xcbl.data.query.QueryOrderByBuilder;
import com.gwn.xcbl.data.query.QueryUtils;
import com.gwn.xcbl.data.query.h.ProviderHqueryUtils;
import com.gwn.xcbl.data.shared.ILongId;
import com.gwn.xcbl.data.shared.ProviderSearchCritrDTO;

public class ProviderDAOImpl extends GenericHibernateDAO<Provider, ILongId> implements ProviderDAO {

	@Override
	public Provider findByILongId(ILongId id, boolean readOnly) {
		return findById(id.getId(), readOnly);
	}

	@Override
	public Provider findById(Long id, boolean readOnly) {
		Query q = getSession().createQuery("from " + Provider.class.getSimpleName() + " u where u.id = :id");
		q.setParameter("id", id);
		Provider r = (Provider) q.uniqueResult();
		return r;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Provider> findByCritr(ProviderSearchCritrDTO critr, Integer offset, Integer limit) {
		QueryComposite qc = findBySearchCritrQuery(critr, true);
		Query q = getSession().createQuery(qc.getQueryString());
		DAOUtils.applyParameters(q, qc.getParams());
		DAOUtils.applyPaging(q, offset, limit);
		List<Provider> r = q.list();
		return r;
	}
	
	@Override
	public int countByCritr(ProviderSearchCritrDTO critr) {
		QueryComposite qc = findBySearchCritrQuery(critr, false);
		Query q = getSession().createQuery("select count(*) " + qc.getQueryString());
		DAOUtils.applyParameters(q, qc.getParams());
		Number count = (Number)q.uniqueResult();
		return count.intValue();
	}
	
	private QueryComposite findBySearchCritrQuery(ProviderSearchCritrDTO critr, boolean orderBy) {
		StringBuilder qsb = new StringBuilder();
		Map<String, Object> params = new HashMap<>();
		
		qsb.append("from " + Provider.class.getSimpleName() + " p where 1 = 1");
		ProviderHqueryUtils.appendNameLikeCritr("p", qsb, params, critr.getName());
		
		if (orderBy) {
			qsb.append(" ").append(new QueryOrderByBuilder().addKeyword().addOrderBy("p.name").addOrderBy("p.id").asString());
		}
		
		QueryComposite qc = new QueryComposite();
		qc.setQueryString(qsb.toString());
		qc.setParams(params);
		return qc;
	}

	@Override
	public Provider findByName(String name) {
		StringBuilder qsb = new StringBuilder();
		qsb.append("from " + Provider.class.getSimpleName() + " u where");
		
		String param = QueryUtils.generateRandomParam();
		qsb.append(" lower(u.name) = :").append(param);
		
		Query q = getSession().createQuery(qsb.toString());
		q.setParameter(param, name.toLowerCase());
		Provider r = (Provider) q.uniqueResult();
		return r;
	}
}
