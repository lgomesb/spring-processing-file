package br.com.learning.springprocessingfile.application.service;

import org.springframework.stereotype.Service;

import br.com.learning.springprocessingfile.domain.Record;
import br.com.learning.springprocessingfile.domain.RecordA;
import br.com.learning.springprocessingfile.domain.RecordFile;
import br.com.learning.springprocessingfile.domain.enums.LayoutRecordA;

@Service
public class RecordAService implements RecordServer {
	
//	@Autowired 
//	private RecordARepository recordARepository;	

	@Override
	public Record persistRecord(RecordFile recordFile, String strLine) {
		
		RecordA recordA = RecordA.builder()
				.registro(strLine.substring(LayoutRecordA.REGISTRO.getStartIndex(), LayoutRecordA.REGISTRO.getEndIndex()).trim())
				.remessa(strLine.substring(LayoutRecordA.REMESSA.getStartIndex(), LayoutRecordA.REMESSA.getEndIndex()).trim())
				.convenio(strLine.substring(LayoutRecordA.CONVENIO.getStartIndex(), LayoutRecordA.CONVENIO.getEndIndex()).trim())
				.nomeEmpresa(strLine.substring(LayoutRecordA.NOME_EMPRESA.getStartIndex(), LayoutRecordA.NOME_EMPRESA.getEndIndex()).trim())
				.codigoBanco(strLine.substring(LayoutRecordA.CODIGO_BANCO.getStartIndex(), LayoutRecordA.CODIGO_BANCO.getEndIndex()).trim())
				.nomeBanco(strLine.substring(LayoutRecordA.NOME_BANCO.getStartIndex(), LayoutRecordA.NOME_BANCO.getEndIndex()).trim())
				.build();
		return recordA;	
	}

	public void save(RecordA recordA) {		
//		recordARepository.save(recordA);
	}

}
