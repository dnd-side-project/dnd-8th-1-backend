package dnd.danverse.domain.review.repository;

import dnd.danverse.domain.mypage.dto.response.MyReviewDto;
import dnd.danverse.domain.review.dto.response.ReviewInfoDto;
import dnd.danverse.domain.review.dto.response.ReviewInfoWithPerformDto;
import dnd.danverse.domain.review.entity.Review;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 리뷰 데이터를 처리하기 위한 Repository.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
  @Query("select new dnd.danverse.domain.review.dto.response."
      + "ReviewInfoDto(r.id,r.content, r.createdAt, m.id,m.name, p.id,p.profileName)"
      + " from Review r join r.member m left join m.profile p where r.performance.id = :performId")
  List<ReviewInfoDto> findAllReviewsWithWriter(@Param("performId") Long performId);

  @Query("select new dnd.danverse.domain.review.dto.response."
      + "ReviewInfoWithPerformDto(r.id,r.content, r.createdAt, m.id,m.name, p.id,p.profileName, pe.id, pe.title, pe.performanceImg.imageUrl)"
      + " from Review r join r.member m left join m.profile p join r.performance pe order by r.createdAt desc")
  List<ReviewInfoWithPerformDto> findRecentReviews();

  @Query("select r from Review r join fetch r.member m left join fetch m.profile p where r.id = :reviewId")
  Optional<Review> findReviewWithMemberById(@Param("reviewId") Long reviewId);

  @Query("select new dnd.danverse.domain.mypage.dto.response."
      + "MyReviewDto(r.id, r.content, r.createdAt, pe.id, pe.title) from Review r join r.performance pe where r.member.id = :memberId "
      + "order by r.createdAt desc")
  List<MyReviewDto> findReviewsWithMemberId(@Param("memberId") Long memberId);
}
