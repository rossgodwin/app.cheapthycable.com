package com.gwn.xcbl.data.shared;

import java.util.List;

public class PagingResultDTO<O> {

	private int offset;
	
	private int limit;
	
	private int total;
	
	private List<O> records;

	public PagingResultDTO() {
	}
	
	public PagingResultDTO(int offset, int limit, int total, List<O> records) {
		this.offset = offset;
		this.limit = limit;
		this.total = total;
		this.records = records;
	}
	
	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<O> getRecords() {
		return records;
	}

	public void setRecords(List<O> records) {
		this.records = records;
	}
}
