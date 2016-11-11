package com.gwn.xcbl.data.transformer.ba;

import org.apache.commons.collections4.Transformer;

import com.gwn.xcbl.data.hibernate.entity.Account;
import com.gwn.xcbl.data.hibernate.entity.ba.BaAlert;
import com.gwn.xcbl.data.shared.AccountDTO;
import com.gwn.xcbl.data.shared.ba.BaAlertDTO;

public class BaAlertDtoTransformer implements Transformer<BaAlert, BaAlertDTO> {

	private Transformer<Account, AccountDTO> accountTrnsfmr;
	
	@Override
	public BaAlertDTO transform(BaAlert input) {
		BaAlertDTO r = new BaAlertDTO();
		r.setId(input.getId());
		if (accountTrnsfmr != null) {
			r.setAccount(accountTrnsfmr.transform(input.getAccount()));
		}
		r.setReceiveEmail(input.isReceiveEmail());
		r.setReceiveEmailFrequencyDays(input.getReceiveEmailFrequencyDays());
		r.setCritrMileRadius(input.getCritrMileRadius());
		r.setCritrAmountBelow(input.getCritrAmountBelow());
		return r;
	}
}
