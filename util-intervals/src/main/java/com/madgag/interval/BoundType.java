package com.madgag.interval;

public enum BoundType {
	MIN {
		public boolean isWithinBound(int comparison) {
			return comparison<0;
		}
	}, MAX {
		public boolean isWithinBound(int comparison) {
			return comparison>0;
		}
	};

	public abstract boolean isWithinBound(int comparison);
}
