package com.gwn.xcbl.data.transformer.bill;

import org.apache.commons.collections4.Transformer;

import com.gwn.xcbl.data.hibernate.entity.Bill;
import com.gwn.xcbl.data.hibernate.entity.BillInternetOptions;
import com.gwn.xcbl.data.shared.bill.BillDTO;
import com.gwn.xcbl.data.shared.bill.BillInternetOptionsDTO;

public class BillInternetOptionsDtoTransformer implements Transformer<BillInternetOptions, BillInternetOptionsDTO> {

	private Transformer<Bill, BillDTO> billTrnsfmr;
	
	@Override
	public BillInternetOptionsDTO transform(BillInternetOptions input) {
		BillInternetOptionsDTO r = new BillInternetOptionsDTO();
		r.setId(input.getId());
		if (billTrnsfmr != null) {
			r.setBill(billTrnsfmr.transform(input.getBill()));
		}
		r.setModem(input.isModem());
		r.setDownloadSpeedMbps(input.getDownloadSpeedMbps());
		r.setDownloadSpeedMbpsRngLower(input.getDownloadSpeedMbpsRngLower());
		r.setDownloadSpeedMbpsRngUpper(input.getDownloadSpeedMbpsRngUpper());
		r.setUploadSpeedMbps(input.getUploadSpeedMbps());
		return r;
	}
}
