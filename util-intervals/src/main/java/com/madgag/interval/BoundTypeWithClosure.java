package com.madgag.interval;



public enum BoundTypeWithClosure {
	MIN_OPEN("("),
	MIN_CLOSED("["),
	MAX_OPEN(")"),
	MAX_CLOSED("]");
	
	private final BoundType boundType;
	private final Closure closure;
	private final String textRepresentation;
	
	BoundTypeWithClosure(String textRepresentation) {
		this.textRepresentation = textRepresentation;
		boundType = BoundType.values()[(ordinal() & 2) >>> 1];
		closure = Closure.values()[ordinal() & 1];
	}

	public boolean isOnside(int comparison) {
		return comparison==0?closure.isSatisfiedByEquality():boundType.isWithinBound(comparison);
	}

	public static BoundTypeWithClosure get(BoundType boundType, Closure closure) {
		return values()[closure.ordinal() + (boundType.ordinal()<<1)];
	}
	
	public String getTextRepresentation() {
		return textRepresentation;
	}

	public BoundType getBoundType() {
		return boundType;
	}
}
