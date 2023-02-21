package dnd.danverse.domain.performance.repository;

import static dnd.danverse.domain.performance.entity.QPerformance.performance;
import static dnd.danverse.domain.performgenre.entity.QPerformGenre.performGenre;
import static dnd.danverse.domain.profile.entity.QProfile.profile;
import static org.aspectj.util.LangUtil.isEmpty;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dnd.danverse.domain.performance.dto.request.PerformCondDto;
import dnd.danverse.domain.performance.dto.response.PerformInfoResponse;
import dnd.danverse.domain.performance.entity.Performance;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

/**
 * 공연 필터링 조건을 통해 공연 전체를 조회하는 repository
 */
public class PerformanceRepositoryImpl implements PerformFilterCustom {

  private final JPAQueryFactory queryFactory;

  public PerformanceRepositoryImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
  }

  /**
   * 공연 필터링 조건을 통해 공연을 조회 (페이징 처리)
   *
   * @param performCondDto 공연 필터링 조건
   * @param pageable 페이징 처리
   * @return 페이징 처리된 공연 정보
   */
  @Override
  public Page<PerformInfoResponse> searchAllPerformWithCond(PerformCondDto performCondDto,
      Pageable pageable) {

    List<Performance> performContent = queryFactory
        .select(performance)
        .from(performance)
        .join(performance.performGenres, performGenre).fetchJoin()
        .where(
            yearEq(performCondDto.getYear()),
            monthEq(performCondDto.getMonth()),
            dayEq(performCondDto.getDay()),
            locationEq(performCondDto.getLocation()),
            genreIn(performCondDto.getGenre())
        )
        .orderBy(performance.createdAt.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    // performContent to performInfoResponse
    List<PerformInfoResponse> performInfoResponse = PerformInfoResponse.of(performContent);

    return PageableExecutionUtils.getPage(performInfoResponse, pageable,
        () -> getTotalCount(performCondDto));
  }

  /**
   * 필터링 조건이 performance 와 performGenre 에서 모두 사용되므로
   * countQuery 에서는 join 을 통해 두 테이블을 조인하여 사용 (성능 향상)
   * profileHost 는 필요없으므로 join 을 사용 안함
   *
   * @param performCondDto 공연 필터링 조건
   * @return 공연 전체 개수
   */
  private long getTotalCount(PerformCondDto performCondDto) {

    JPAQuery<Performance> countQuery = queryFactory
        .select(performance).distinct()
        .from(performance)
        .join(performance.performGenres, performGenre)
        .where(
            monthEq(performCondDto.getMonth()),
            dayEq(performCondDto.getDay()),
            locationEq(performCondDto.getLocation()),
            genreIn(performCondDto.getGenre())
        );

    return countQuery.fetch().size();
  }

  /**
   * team name 을 통해서 예정된 공연, 마감된 공연 조회
   * @param teamName 팀 이름
   */
  @Override
  public List<Performance> searchPerformsByTeam(String teamName) {

    return queryFactory
        .select(performance)
        .from(performance)
        .join(performance.profileHost, profile).fetchJoin()
        .where(teamNameEq(teamName))
        .orderBy(performance.startDate.desc())
        .fetch();
  }

  /**
   * teamName 이 일치하는 경우 where 절에 추가
   * teamName 이 null 인 경우 where 절에 추가하지 않음
   * @param teamName 팀 이름
   * @return teamName 이 일치하는 경우 where 절에 추가
   */
  private BooleanExpression teamNameEq(String teamName) {
    return isEmpty(teamName) ? null : profile.profileName.eq(teamName);
  }


  /**
   * year 가 일치하는 경우 where 절에 추가
   * year 가 null 인 경우 where 절에 추가하지 않음
   * @param year 공연 년도
   * @return year 가 일치하는 경우 where 절에 추가
   */
  private BooleanExpression yearEq(Integer year) {
    return year == null ? null : performance.startDate.year().eq(year);
  }

  /**
   * month 가 일치하는 경우 where 절에 추가
   * month 가 null 인 경우 where 절에 추가하지 않음
   * @param month 공연 월
   * @return month 가 일치하는 경우 where 절에 추가
   */
  private BooleanExpression monthEq(Integer month) {
    return month == null ? null : performance.startDate.month().eq(month);
  }

  /**
   * day 가 일치하는 경우 where 절에 추가
   * day 가 null 인 경우 where 절에 추가하지 않음
   * @param day 공연 일
   * @return day 가 일치하는 경우 where 절에 추가
   */
  private BooleanExpression dayEq(Integer day) {
    return day == null ? null : performance.startDate.dayOfMonth().eq(day);
  }

  /**
   * location 이 일치하는 경우 where 절에 추가
   * location 이 null 인 경우 where 절에 추가하지 않음
   * @param location 공연 장소
   * @return location 이 일치하는 경우 where 절에 추가
   */
  private BooleanExpression locationEq(String location) {
    return isEmpty(location) ? null : performance.location.eq(location);
  }

  /**
   * genre 가 하나라도 존재 하는 경우 where 절에 추가
   * genre 가 null 인 경우 where 절에 추가하지 않음
   * @param genre 공연 장르
   * @return genre 가 일치하는 경우 where 절에 추가
   */
  private BooleanExpression genreIn(String genre) {
    return isEmpty(genre) ? null : performGenre.genre.contains(genre);
  }


}
