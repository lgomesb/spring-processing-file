package br.com.learning.springprocessingfile.application.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.learning.springprocessingfile.application.helper.FileHelper;
import br.com.learning.springprocessingfile.application.helper.RecordALayout;
import br.com.learning.springprocessingfile.application.helper.RecordDLayout;
import br.com.learning.springprocessingfile.domain.ErrorRecordFile;
import br.com.learning.springprocessingfile.domain.RecordA;
import br.com.learning.springprocessingfile.domain.RecordD;
import br.com.learning.springprocessingfile.domain.RecordFile;
import br.com.learning.springprocessingfile.framework.out.file.FileDirectoryFeature;
import br.com.learning.springprocessingfile.framework.out.persistence.RecordFileRepository;

@Service
public class FileProcessingService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileProcessingService.class);
	
	@Autowired
	private RecordFileRepository repository;
	
	@Autowired
	private RecordALayout recordAService;
	
	@Autowired
	private RecordDLayout recordDService;
	
	
	@Autowired 
	private FileHelper fileHelper;
	
	@Autowired
	private FileDirectoryFeature fileDirectory;
	
	@Value("${spring.async.max-pool-size}")
	private int threadNumber;

	public void process(File file) throws Exception {
		
		if (!file.exists()) {
            return;
        }
		
        Integer lines = fileHelper.getLinesByFile(new FileInputStream(file));     //Total number of document rows
        Integer line, startLine, endLine;  //Number of rows allocated per thread | Number of lines from which the thread reads the file | Number of end lines of file read by thread
        CompletableFuture<List<ErrorRecordFile>> future = null;
        List<ErrorRecordFile> errorRecordFiles = new ArrayList<>();
        RecordFile recordFile = new RecordFile(file.getName());
        repository.save(recordFile);
        
        if( lines < threadNumber ) {        	
        	for( int i = 1; i <= lines; i++ ) { 
        		startLine = endLine = i;
        		future = processFile(new FileInputStream(file), startLine, endLine, recordFile, errorRecordFiles);        		
        	}        	
        } else {        	
        	for (int i = 1, tempLine = 0; i <= threadNumber; i++, tempLine = ++endLine) {        		
        		line = fileHelper.getLineNumber(lines, i);
        		startLine = (i == 1) ? 1 : tempLine;
        		endLine = (i == threadNumber) ? lines : startLine + line - 1;
        		future = processFile(new FileInputStream(file), startLine, endLine, recordFile, errorRecordFiles);
        	}
        }
                
        if( future != null ) {
        	if( future.isDone() ) {
        		List<ErrorRecordFile> futureErrorRecordFile = future.get();        	
            	if( futureErrorRecordFile.size() > 0 ) {
            		recordFile.setErrorRecordFile(futureErrorRecordFile);
            		fileDirectory.moveFileToError(file);
            	} else { 
            		fileDirectory.moveFileToOut(file);
            	}
        	}
        } else { 
        	ErrorRecordFile errorRecordFile = ErrorRecordFile.builder()
							.erroDetail("Arquivo nao possui registros.")
							.errorRecordType("FILE_IS_NULL")
							.numberLinError(0)
							.build();
        	recordFile.setErrorRecordFile(Arrays.asList(errorRecordFile));
        	
        	fileDirectory.moveFileToError(file);
        }
        
        
        
	}
	
	@Async
	@Transactional
	public CompletableFuture<List<ErrorRecordFile>> processFile(InputStream is, Integer startIndex, Integer endIndex,
			RecordFile recordFile, List<ErrorRecordFile> errorsRecordFile) {
			
		Integer interator = 1;
		
		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
			List<RecordD> recordDs = new ArrayList<>();
			
			for (; interator < startIndex; interator++) {
				reader.readLine();
			}

			for (; interator <= endIndex; interator++) {
				String strLine = reader.readLine();

				if (150 == strLine.length()) {
					switch (String.valueOf(strLine.toUpperCase().charAt(0))) {
					case "A": {
						RecordA recordA = (RecordA) recordAService.persistRecord(recordFile, strLine);
						recordFile.setRecordA(recordA);
//						recordAService.save(recordA);
						repository.save(recordFile);
						break;
					}
					case "B":
						break;
					case "C":
						break;
					case "D": {
						RecordD recordD = (RecordD) recordDService.persistRecord(recordFile, strLine);
						recordDs.add(recordD);
						break;
					}
					default:
						break;
					}

				} else {
					errorsRecordFile.add(ErrorRecordFile.builder()
							.erroDetail("Linha com mais de 150 bytes.")
							.errorRecordType("Line_Validation")
							.numberLinError(interator)
							.build());
				}
			}

			recordFile.setRecordD(recordDs);
			repository.save(recordFile);

		} catch (Exception e) {
			errorsRecordFile.add(ErrorRecordFile.builder()
					.erroDetail(e.getMessage())
					.errorRecordType("Generic_error")
					.numberLinError(interator)
					.build());
		}

//		LOGGER.info("Position Line -> startIndex: {} endIndex: {} || Content Line: {}", startIndex, endIndex, strLine); 

		recordFile.setErrorRecordFile(errorsRecordFile);		
		
		return CompletableFuture.completedFuture(errorsRecordFile);
	}
	

	
}
