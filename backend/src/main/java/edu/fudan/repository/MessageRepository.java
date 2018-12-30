package edu.fudan.repository;

import edu.fudan.domain.Message;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends Neo4jRepository<Message, Long>{
}
