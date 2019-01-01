package edu.fudan.repository;

import edu.fudan.domain.Activity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivityRepository extends Neo4jRepository<Activity, Long>{
    Optional<Activity> findActivityByInvitingCode(String invitingCode);
}
