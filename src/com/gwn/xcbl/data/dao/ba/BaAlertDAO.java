package com.gwn.xcbl.data.dao.ba;

import java.util.List;

import com.gwn.xcbl.data.dao.GenericDAO;
import com.gwn.xcbl.data.hibernate.entity.ba.BaAlert;
import com.gwn.xcbl.data.shared.ILongId;

public interface BaAlertDAO extends GenericDAO<BaAlert, ILongId> {

	public List<BaAlert> findSubscribedAlertsByAccount(long accountId, Integer offset, Integer limit);
	
	public int countSubscribedAlertsByAccount(long accountId);
}
