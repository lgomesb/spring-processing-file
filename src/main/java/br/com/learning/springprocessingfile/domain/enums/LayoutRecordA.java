package br.com.learning.springprocessingfile.domain.enums;

public enum LayoutRecordA {

	REGISTRO(0, 1),
	REMESSA(1, 2),
	CONVENIO(2, 22),
	NOME_EMPRESA(22, 42),
	CODIGO_BANCO(42, 45),
	NOME_BANCO(45, 65);

	private int startIndex;
	private int endIndex;

	private LayoutRecordA(int startIndex, int endIndex) {
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
