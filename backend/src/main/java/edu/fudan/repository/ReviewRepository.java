package edu.fudan.repository;

import edu.fudan.domain.Review;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReviewRepository extends Neo4jRepository<Review, Long>{
    List<Review> findReviewsByUserId(long userId);
}
