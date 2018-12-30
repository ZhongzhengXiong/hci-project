package edu.fudan.repository;

import edu.fudan.domain.Notice;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends Neo4jRepository<Notice, Long>{
}
