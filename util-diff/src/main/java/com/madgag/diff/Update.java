package com.madgag.diff;


public class Update {
    public final static Update EMPTY=new Update("");

	private final String oldText,newText;

    public Update(String equalText) {
		this(equalText,equalText);
	}

	public Update(String oldText,String newText) {
		this.oldText=oldText;
		this.newText=newText;
	}	
	
	public boolean isChange() {
		return !newText.equals(oldText);
	}
	
	public Update appendToOld(String moreOld) {
		return new Update( oldText + moreOld, newText);
	}

    public Update appendToNew(String moreNew) {
        return new Update( oldText , newText+moreNew);
    }
	
	public String toString() {
		return isChange()?("{"+oldText+"→"+newText+"}"):oldText;
	}

    public boolean isEmpty() {
        return oldText.length()==0 && newText.length()==0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Update update = (Update) o;

        if (newText != null ? !newText.equals(update.newText) : update.newText != null) return false;
        if (oldText != null ? !oldText.equals(update.oldText) : update.oldText != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = oldText != null ? oldText.hashCode() : 0;
        result = 31 * result + (newText != null ? newText.hashCode() : 0);
        return result;
    }

    public Update appendToBoth(String text) {

        return new Update( oldText +text, newText+text);
    }

    public Update append(Update update) {
        return new Update(oldText+update.oldText,newText+update.newText);
    }


}
