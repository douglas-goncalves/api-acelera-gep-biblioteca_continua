package br.com.aceleragep.api_biblioteca.handler;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FieldsExceptionOutput {

	private String name;
	private String message;

}
