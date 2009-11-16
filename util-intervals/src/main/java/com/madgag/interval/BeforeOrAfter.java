package com.madgag.interval;

public enum BeforeOrAfter {
    BEFORE {
        public boolean isTrueFor(int comparison) { return comparison<0; }
    },
    AFTER {
        public boolean isTrueFor(int comparison) { return comparison>0; }
    };

    public abstract boolean isTrueFor(int comparison);
}
