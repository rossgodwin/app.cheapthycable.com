package com.gwn.xcbl.bl.bill;

import java.util.ArrayList;
import java.util.Collection;

import com.gwn.xcbl.data.hibernate.entity.Account;
import com.gwn.xcbl.data.hibernate.entity.bill.Bill;
import com.gwn.xcbl.data.shared.bill.create.BillCreateDTO;

public class BillCreateObservable {

	private Collection<BillCreateObserver> observers = new ArrayList<BillCreateObserver>();
	
	public BillCreateObservable() {
	}
	
	public BillCreateObservable(BillCreateObserver observer) {
		observers.add(observer);
	}
	
	public Bill createBill(Account account, BillCreateDTO billDto) {
		Bill bill = BillHelper.saveBill(account, billDto);
		notifyObservers(bill);
		return bill;
	}
	
	public void addObserver(BillCreateObserver o) {
		observers.add(o);
	}
	
	private void notifyObservers(Bill bill) {
		for (BillCreateObserver observer : observers) {
			observer.create(bill);
		}
	}
}
