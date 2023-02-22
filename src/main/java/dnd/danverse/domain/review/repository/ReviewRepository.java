package dnd.danverse.domain.review.repository;

import dnd.danverse.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 리뷰 데이터를 처리하기 위한 Repository.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

}
