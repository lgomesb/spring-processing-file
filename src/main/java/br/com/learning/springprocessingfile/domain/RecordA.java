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
public class RecordA extends Record implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	private String registro;
	
	private String remessa;
	
	private String convenio;
	
	private String nomeEmpresa;
	
	private String codigoBanco;
	
	private String nomeBanco;

}
