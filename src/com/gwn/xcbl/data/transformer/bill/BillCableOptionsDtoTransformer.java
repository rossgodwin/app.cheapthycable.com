package com.gwn.xcbl.data.transformer.bill;

import org.apache.commons.collections4.Transformer;

import com.gwn.xcbl.data.hibernate.entity.bill.Bill;
import com.gwn.xcbl.data.hibernate.entity.bill.BillCableOptions;
import com.gwn.xcbl.data.shared.bill.BillCableOptionsDTO;
import com.gwn.xcbl.data.shared.bill.BillDTO;

public class BillCableOptionsDtoTransformer implements Transformer<BillCableOptions, BillCableOptionsDTO> {

	private Transformer<Bill, BillDTO> billTrnsfmr;
	
	@Override
	public BillCableOptionsDTO transform(BillCableOptions input) {
		BillCableOptionsDTO r = new BillCableOptionsDTO();
		r.setId(input.getId());
		if (billTrnsfmr != null) {
			r.setBill(billTrnsfmr.transform(input.getBill()));
		}
		r.setBoxCount(input.getBoxCount());
		r.setDvrCount(input.getDvrCount());
		r.setSpecialChannels(input.isSpecialChannels());
		return r;
	}
}
