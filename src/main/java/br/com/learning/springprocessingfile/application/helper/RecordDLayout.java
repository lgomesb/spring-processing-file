package br.com.learning.springprocessingfile.application.helper;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.learning.springprocessingfile.application.service.RecordServer;
import br.com.learning.springprocessingfile.domain.Record;
import br.com.learning.springprocessingfile.domain.RecordD;
import br.com.learning.springprocessingfile.domain.RecordFile;
import br.com.learning.springprocessingfile.domain.enums.LayoutRecordD;

@Service
public class RecordDLayout implements RecordServer {
	
//	@Autowired
//	private RecordDRepository recordDRepository;

	@Override
	public Record persistRecord(RecordFile recordFile, String strLine) {

		RecordD recordD = RecordD.builder()
				.registro(strLine.substring(LayoutRecordD.REGISTRO.getStartIndex(), LayoutRecordD.REGISTRO.getEndIndex()).trim())
				.idClienteEmpresaAnterior(strLine.substring(LayoutRecordD.ID_CLIENTE_EMPRESA_ANTERIOR.getStartIndex(), LayoutRecordD.ID_CLIENTE_EMPRESA_ANTERIOR.getEndIndex()).trim())
				.agenciaDebito(strLine.substring(LayoutRecordD.AGENCIA_DEBITO.getStartIndex(), LayoutRecordD.AGENCIA_DEBITO.getEndIndex()).trim())
				.idClienteBanco(strLine.substring(LayoutRecordD.ID_CLIENTE_BANCO.getStartIndex(), LayoutRecordD.ID_CLIENTE_BANCO.getEndIndex()).trim())
				.idClienteEmpresaAtual(strLine.substring(LayoutRecordD.ID_CLIENTE_EMPRESA_ATUAL.getStartIndex(), LayoutRecordD.ID_CLIENTE_EMPRESA_ATUAL.getEndIndex()).trim())				
				.build();
		return recordD;	
	}

	public void saveAll(List<RecordD> recordDs) {		
//		recordDRepository.saveAll(recordDs);
	}
	
}
