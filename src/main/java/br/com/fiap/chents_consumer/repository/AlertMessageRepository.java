package br.com.fiap.chents_consumer.repository;

import br.com.fiap.chents_consumer.dto.AlertMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertMessageRepository extends MongoRepository<AlertMessage, String> {
}
