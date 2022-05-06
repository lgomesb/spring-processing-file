package br.com.learning.springprocessingfile.domain.enums;

public enum LayoutRecordD {

	REGISTRO(0, 1),
	ID_CLIENTE_EMPRESA_ANTERIOR(1, 26),
	AGENCIA_DEBITO(26, 30),
	ID_CLIENTE_BANCO(30, 44),
	ID_CLIENTE_EMPRESA_ATUAL(44, 69);

	private int startIndex;
	private int endIndex;

	private LayoutRecordD(int startIndex, int endIndex) {
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

}
