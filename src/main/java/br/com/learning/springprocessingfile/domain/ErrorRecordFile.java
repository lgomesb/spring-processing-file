package br.com.learning.springprocessingfile.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorRecordFile extends Record implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id	
	private Long id;
	
	private String errorRecordType;
	
	private String erroDetail;
	
	private Integer numberLinError;
	
}
