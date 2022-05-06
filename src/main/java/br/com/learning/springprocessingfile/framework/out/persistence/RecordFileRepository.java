package br.com.learning.springprocessingfile.framework.out.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.learning.springprocessingfile.domain.RecordFile;


@Repository
public interface RecordFileRepository extends MongoRepository<RecordFile, String> {

}
