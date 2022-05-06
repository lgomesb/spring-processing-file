package br.com.learning.springprocessingfile.framework.out.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileDirectoryFeature {

	@Value("${file.source.out}")
	private String FILE_SOURCE_OUT;
	
	@Value("${file.source.error}")
	private String FILE_SOURCE_ERROR;
	
	public void moveFileToError(File file) throws IOException {
		this.moveFile(file, FILE_SOURCE_ERROR);		
	}
	
	public void moveFileToOut(File file) throws IOException {
		this.moveFile(file, FILE_SOURCE_OUT );		
	}

	private void moveFile(File originFile, String destinationPath ) throws IOException {		
		File fileToMove = new File(destinationPath + File.separator + originFile.getName()  );
		
		if( Files.notExists(fileToMove.getParentFile().toPath()) ) {
			Files.createDirectories(fileToMove.getParentFile().toPath());
		}
		
		if( Files.notExists(fileToMove.toPath()) )
			Files.move(originFile.toPath(), fileToMove.toPath());
		
	}
	
}
