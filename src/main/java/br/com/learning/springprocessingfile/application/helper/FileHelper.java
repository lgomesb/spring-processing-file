package br.com.learning.springprocessingfile.application.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class FileHelper {

	@Value("${spring.async.max-pool-size}")
	private int threadNumber;
	
	@Value("${file.source.out}")
	private String FILE_SOURCE_OUT;
	
	@Value("${file.source.error}")
	private String FILE_SOURCE_ERROR;
	
    public int getLinesByFile(InputStream is) throws IOException {
        int line = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while (reader.readLine() != null) {
            line++;
        }
        reader.close();
        is.close();
        return line;
    }
    
	public Integer getLineNumber(Integer lines, int i) {
		Integer line;
		Integer var1 = lines / threadNumber;
		Integer var2 = lines % threadNumber;
		line = (i == threadNumber) ? (var2 == 0 ? var1 : var1 + var2) : var1;
		return line;
	}
	
  	
}
