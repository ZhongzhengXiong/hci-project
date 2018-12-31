package edu.fudan.repository;

import edu.fudan.domain.ActivityPhoto;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityPhotoRepository extends Neo4jRepository<ActivityPhoto, Long>{
}
