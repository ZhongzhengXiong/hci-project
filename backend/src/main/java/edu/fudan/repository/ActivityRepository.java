package edu.fudan.repository;

import edu.fudan.domain.Activity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends Neo4jRepository<Activity, Long>{
}
