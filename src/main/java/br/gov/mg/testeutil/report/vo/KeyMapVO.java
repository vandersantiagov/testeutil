package br.gov.mg.testeutil.report.vo;

public class KeyMapVO<T1, T2> {

	private T1 key1;
	private T2 key2;

	public KeyMapVO(T1 key1, T2 key2) {
		this.key1 = key1;
		this.key2 = key2;
	}

	public T1 getKey1() {
		return key1;
	}

	public void setKey1(T1 key1) {
		this.key1 = key1;
	}

	public T2 getKey2() {
		return key2;
	}

	public void setKey2(T2 key2) {
		this.key2 = key2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key1 == null) ? 0 : key1.hashCode());
		result = prime * result + ((key2 == null) ? 0 : key2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		@SuppressWarnings("unchecked")
		KeyMapVO<T1, T2> other = (KeyMapVO<T1, T2>) obj;
		if (key1 == null) {
			if (other.key1 != null)
				return false;
		} else if (!key1.equals(other.key1))
			return false;
		if (key2 == null) {
			if (other.key2 != null)
				return false;
		} else if (!key2.equals(other.key2))
			return false;
		return true;
	}

}
