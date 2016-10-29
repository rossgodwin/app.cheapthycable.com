package com.gwn.xcbl.data.transformer;

import org.apache.commons.collections4.Transformer;

import com.gwn.xcbl.data.shared.ILongId;

public class IdOnlyTransformer<I extends ILongId, O extends ILongId> implements Transformer<I, O> {

	private Class<O> clazz;
	
	public IdOnlyTransformer(Class<O> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public O transform(I input) {
		try {
			O r = clazz.newInstance();
			r.setId(input.getId());
			return r;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException();
		}
	}
}
