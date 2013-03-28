package ru.spbstu.telematics.annotations;

public enum EnumWithConstructor {
	
	A("a"), B("b");

	private final String val;
	private EnumWithConstructor(String val) {
		this.val = val;
	}

	public String getVal() {
		return val;
	}
	
	public EnumWithConstructor getByVal(String str) {
		for (EnumWithConstructor v : values()) {
			if (v.val.equals(str)) {
				return v;
			}
		}
		return null;
	}
}
