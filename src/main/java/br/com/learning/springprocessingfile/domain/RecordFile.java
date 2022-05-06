package br.com.learning.springprocessingfile.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Document
public class RecordFile implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id	
	private String id;

	private String fileName;
	
	@Field
	@Indexed( name = "recordDileIndex", expireAfterSeconds = 10)
	private Date createDate;
	
	private RecordA recordA; 
	
	private List<RecordD> recordD;
	
	private List<ErrorRecordFile> errorRecordFile;

	public RecordFile(String fileName) {
		super();
		this.id = UUID.randomUUID().toString();
		this.createDate = new Date();
		this.fileName = fileName;
	}
	
	
}
