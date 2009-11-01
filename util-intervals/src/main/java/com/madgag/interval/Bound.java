package com.madgag.interval;

import static com.madgag.interval.BoundType.MIN;

public class Bound<T extends Comparable<T>> {

	private final T val;
	private final BoundTypeWithClosure boundTypeWithClosure;

	public Bound(BoundTypeWithClosure boundTypeWithClosure, T val) {
		this.boundTypeWithClosure = boundTypeWithClosure;
		this.val = val;
	}

	public T getValue() {
		return val;
	}
	
	public boolean encloses(T point) {
		int comparison = val.compareTo(point);
		return boundTypeWithClosure.isOnside(comparison);
	}

	public BoundTypeWithClosure getBound() {
		return boundTypeWithClosure;
	}
	
	@Override
	public String toString() {
		if (boundTypeWithClosure.getBoundType()==MIN) {
			return boundTypeWithClosure.getTextRepresentation()+val;
		} else {
			return val + boundTypeWithClosure.getTextRepresentation();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((boundTypeWithClosure == null) ? 0 : boundTypeWithClosure
						.hashCode());
		result = prime * result + ((val == null) ? 0 : val.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bound other = (Bound) obj;
		if (boundTypeWithClosure == null) {
			if (other.boundTypeWithClosure != null)
				return false;
		} else if (!boundTypeWithClosure.equals(other.boundTypeWithClosure))
			return false;
		if (val == null) {
			if (other.val != null)
				return false;
		} else if (!val.equals(other.val))
			return false;
		return true;
	}
	
	
}
