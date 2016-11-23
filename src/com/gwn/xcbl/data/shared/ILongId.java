package com.gwn.xcbl.data.shared;

import java.util.ArrayList;
import java.util.Collection;

public interface ILongId {

	public static class Utils {
		
		public static <M extends ILongId> boolean isPersistent(M obj) {
			boolean r = !obj.getId().equals(-1L);
			return r;
		}
		
		public static <M extends ILongId> Collection<Long> getIds(Collection<M> objs) {
			Collection<Long> r = new ArrayList<Long>();
			for (M obj : objs) {
				r.add(obj.getId());
			}
			return r;
		}
		
		public static <M extends ILongId> boolean doesExists(Collection<M> objs, M obj) {
			for (M o : objs) {
				if (o.getId().equals(obj.getId())) {
					return true;
				}
			}
			return false;
		}
	}
	
	public Long getId();
	
	public void setId(Long id);
}
