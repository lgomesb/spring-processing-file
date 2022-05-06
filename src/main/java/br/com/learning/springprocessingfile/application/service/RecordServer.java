package br.com.learning.springprocessingfile.application.service;

import br.com.learning.springprocessingfile.domain.Record;
import br.com.learning.springprocessingfile.domain.RecordFile;

public interface RecordServer {

	public Record persistRecord(RecordFile recordFile, String strLine);
}
