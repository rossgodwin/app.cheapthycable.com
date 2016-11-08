package com.gwn.xcbl.bl.ba.email;

import java.text.NumberFormat;

import org.apache.commons.collections4.Transformer;

import com.gwn.xcbl.bl.bill.BillHelper;
import com.gwn.xcbl.data.hibernate.entity.bill.Bill;

public class BaEmailBillTransformer implements Transformer<Bill, BaEmailBill> {

	@Override
	public BaEmailBill transform(Bill input) {
		BaEmailBill r = new BaEmailBill();
		r.setZipCode(input.getGeoZipCode().getZipCode());
		
		NumberFormat format = NumberFormat.getCurrencyInstance();
		r.setTotalAmount(format.format(input.getTotalAmount()));
		
		if (input.getProvider() != null) {
			r.setProviderName(input.getProvider().getName());
		} else {
			r.setProviderName(input.getProviderOther());
		}
		
		r.setServices(BillHelper.getServicesStr(input, ", "));
		r.setOptions(BillHelper.getAllOptionsStr(input, ", "));
		
		return r;
	}
}
