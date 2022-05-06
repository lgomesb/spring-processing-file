package br.com.learning.springprocessingfile.framework.in;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.learning.springprocessingfile.application.service.FileProcessingService;

@Component
@EnableScheduling
public class FileProcessingSchedule {

	private static final int TIME_SCHEDULE = 6000;

	@Autowired
	private FileProcessingService service; 
	
	@Value("${file.source.in}")
	private String FILE_SOURCE_IN;
	
	
	@Scheduled( fixedRate = TIME_SCHEDULE, initialDelay = TIME_SCHEDULE)
	public void processingFile() throws Exception { 
		
		List<String> files = listFileUsingFilesList(FILE_SOURCE_IN);
		
		for (String fileName : files) {
			System.out.println("File Processing is Done: " + FILE_SOURCE_IN + File.separator + fileName);
			service.process(new File(FILE_SOURCE_IN + File.separator + fileName));
		}
	}
	
	private List<String> listFileUsingFilesList(String dir) throws IOException { 
		
		File fileDir = new File(dir);
		if( fileDir.exists() ) {
			List<File> files = Arrays.asList(fileDir.listFiles());
			files.sort(Comparator.comparingLong(File::lastModified));
			
			files.stream().filter(f -> !f.isDirectory())
			.map(File::getName)
			.collect(Collectors.toList());
			
			try( Stream<Path> stream = Files.list(Paths.get(dir)) ) {
				return stream
						.filter(file -> !Files.isDirectory( file ) )
						.map(Path::getFileName)
						.map(Path::toString)
						.sorted()
						.collect(Collectors.toList());
			}			
		}
		
		return new ArrayList<>();
		
	}
	
}
