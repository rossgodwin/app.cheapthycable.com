package com.gwn.xcbl.data.dao.ba;

import com.gwn.xcbl.data.dao.GenericDAO;
import com.gwn.xcbl.data.hibernate.entity.ba.BaAlertSentLog;
import com.gwn.xcbl.data.shared.ILongId;

public interface BaAlertSentLogDAO extends GenericDAO<BaAlertSentLog, ILongId> {

	public BaAlertSentLog findLastSentByAlert(long alertId);
}
