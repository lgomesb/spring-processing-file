package br.com.learning.springprocessingfile.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
public class RecordD extends Record implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;

	private String registro;
	
	private String idClienteEmpresaAnterior;
	
	private String agenciaDebito;
	
	private String idClienteBanco;
	
	private String idClienteEmpresaAtual;
	
	
	
}