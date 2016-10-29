package com.gwn.xcbl.data.transformer.bill;

import java.time.ZoneId;
import java.util.Date;

import org.apache.commons.collections4.Transformer;

import com.gwn.xcbl.data.hibernate.entity.Account;
import com.gwn.xcbl.data.hibernate.entity.Bill;
import com.gwn.xcbl.data.hibernate.entity.BillCableOptions;
import com.gwn.xcbl.data.hibernate.entity.BillInternetOptions;
import com.gwn.xcbl.data.hibernate.entity.GeoZipCode;
import com.gwn.xcbl.data.hibernate.entity.Provider;
import com.gwn.xcbl.data.shared.AccountDTO;
import com.gwn.xcbl.data.shared.ProviderDTO;
import com.gwn.xcbl.data.shared.bill.BillCableOptionsDTO;
import com.gwn.xcbl.data.shared.bill.BillDTO;
import com.gwn.xcbl.data.shared.bill.BillInternetOptionsDTO;
import com.gwn.xcbl.data.shared.geo.GeoZipCodeDTO;

public class BillDtoTransformer implements Transformer<Bill, BillDTO> {

	private Transformer<Account, AccountDTO> acctTrnsfmr;
	
	private Transformer<Provider, ProviderDTO> prvdrTrnsfmr;
	
	private Transformer<GeoZipCode, GeoZipCodeDTO> geoZipCodeTrnsfmr;
	
	private Transformer<BillInternetOptions, BillInternetOptionsDTO> internetOptionsTrnsfmr;
	
	private Transformer<BillCableOptions, BillCableOptionsDTO> cableOptionsTrnsfmr;
	
	@Override
	public BillDTO transform(Bill input) {
		BillDTO r = new BillDTO();
		r.setId(input.getId());
		if (acctTrnsfmr != null) {
			r.setAccount(acctTrnsfmr.transform(input.getAccount()));
		}
		if (prvdrTrnsfmr != null && input.getProvider() != null) {
			r.setProvider(prvdrTrnsfmr.transform(input.getProvider()));
		}
		r.setProviderOther(input.getProviderOther());
		if (geoZipCodeTrnsfmr != null) {
			r.setGeoZipCode(geoZipCodeTrnsfmr.transform(input.getGeoZipCode()));
		}
		r.setValidDateInMillis(Date.from(input.getValidDate().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
		r.setTotalAmount(input.getTotalAmount());
		r.setAdditionalFeesTotalAmount(input.getAdditionalFeesTotalAmount());
		r.setInternetService(input.isInternetService());
		r.setCableService(input.isCableService());
		r.setPhoneService(input.isPhoneService());
		if (internetOptionsTrnsfmr != null && input.getInternetOptions() != null) {
			r.setInternetOptions(internetOptionsTrnsfmr.transform(input.getInternetOptions()));
		}
		if (cableOptionsTrnsfmr != null && input.getCableOptions() != null) {
			r.setCableOptions(cableOptionsTrnsfmr.transform(input.getCableOptions()));
		}
		r.setComments(input.getComments());
		return r;
	}

	public void setAcctTrnsfmr(Transformer<Account, AccountDTO> acctTrnsfmr) {
		this.acctTrnsfmr = acctTrnsfmr;
	}

	public void setPrvdrTrnsfmr(Transformer<Provider, ProviderDTO> prvdrTrnsfmr) {
		this.prvdrTrnsfmr = prvdrTrnsfmr;
	}

	public void setGeoZipCodeTrnsfmr(Transformer<GeoZipCode, GeoZipCodeDTO> geoZipCodeTrnsfmr) {
		this.geoZipCodeTrnsfmr = geoZipCodeTrnsfmr;
	}

	public void setInternetOptionsTrnsfmr(Transformer<BillInternetOptions, BillInternetOptionsDTO> internetOptionsTrnsfmr) {
		this.internetOptionsTrnsfmr = internetOptionsTrnsfmr;
	}

	public void setCableOptionsTrnsfmr(Transformer<BillCableOptions, BillCableOptionsDTO> cableOptionsTrnsfmr) {
		this.cableOptionsTrnsfmr = cableOptionsTrnsfmr;
	}
}
