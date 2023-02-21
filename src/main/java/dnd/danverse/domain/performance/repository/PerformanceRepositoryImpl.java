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
   * content 를 가져오는 경우에는 일대다 컬렉션 fetch join 이더라도 데이터 뻥튀기에 상관 없이 원하는
   * 결과 값을 얻을 수 있다.
   * 데이터 뻥튀기의 가장 큰 문제는 count 쿼리가 발생할 때 발생한다.
   *
   * 페이징에 필요한 content 를 가져오기 위해 시도 했던 방향
   * 1. 공연(1) + 주최자(1) + 장르(N) 모두 join 을 하고자 했다. (하지만, table 조인 시 공연, 주최자 데이터에 대해서는 장르 데이터 개수에 만큼 데이터 중복 현상이 발생한다.)
   * 2. 공연(1) + 주최자(1) fetch join 을 하고, 장르는 Lazy Loading 을 하고자 했다. 하지만 이렇게 하면 필터링 조건에 장르가 있기 때문에 Lazy Loading의 결과 값으로 장르에 대한 필터링 로직이 더 추가되어야 한다.
   * 3. 공연(1) + 장르(N) fetch join 을 하고, 주최자는 Lazy Loading 을 하고자 했다. Lazy Loading 발생 문제는 Batch Size 로 해결 가능.
   *
   * 따라서, 1번 방식에서 데이터 중복의 문제점을 최소한으로 줄이기 위해 3번 방식을 선택하였다.
   * Entity 를 Dto 로 변환하는 과정에서 공연 주최자 데이터를 얻기 위해 profile 객체를 가져오는데 N + 1 문제를 해결하기 위해
   * Batch Size 100 을 활용하였다. select * from profile where profile_id in ( ?, ?, ? ~ 100개) 로 한번에 가져온다.
   * ? 에 들어가는 값은 공연 주최자의 id 값이다.
   *
   * count query 연산 시 문제점
   * 1,2,3번 모두 페이지에 필요한 content 를 가져오는데는 아무런 문제가 없다.
   * 하지만, count 쿼리를 날릴 때,
   * 1번, 2번, 3번 모두 count query 를 계산할때 , 일대다 컬렉션 조인이 이루어지고 있다.
   * 이러한 일대다 컬렉션 조인은 데이터 뻥튀기 현상이 발생한다. count query 연산에 있어서 데이터 정합성이 깨진다.
   *
   * count Query 를 위해서는 일단 무조건 공연(1) + 장르(N)는 같이 join 이 이루어져야 한다.
   * 왜냐하면, 필터링 조건이 공연과 장르에 모두 존재하기 때문이다.
   * 그러면 어떻게 하면 공연 + 장르 join 을 유지하면서 데이터 뻥튀기 현상을 방지할 수 있을까?
   * 바로 select 의 결과 값을 distinct 를 통해 조회된 공연(1)을 기준으로 disctinct 를 해주면 된다.
   * 우리가 최종적으로 원한 건, 공연 장르에 대한 데이터 개수가 궁금한게 아니라
   * 공연 데이터 개수가 궁금한 것이다.
   *
   * https://joont92.github.io/jpa/JPA-%EC%84%B1%EB%8A%A5-%EC%B5%9C%EC%A0%81%ED%99%94/
   * https://wangtak.tistory.com/30
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
   * 현재 collection을 기준으로 일대다 조인이 발생하기 때문에, 데이터 뻥튀기 현상이 발생하고 있다.
   *
   * 공연(1) + 장르(N) join 을 하여 count Query 연산을 하려고 한다.
   * 그 이유는 필터링 조건에 공연 + 장르 조건이 모두 존재하기 때문이다.
   * 하지만, 데이터 중복 현상 (데이터 뻥튀기 현상) 이 발생하고 있다.
   * 이를 해결하기 위해 select 에서 가져온 공연(1)을 기준으로 distinct 를 해주면 된다.
   * 물론 distinct 를 한다고 table 조인 시, 데이터 중복 현상이 막아지는건 아니다.
   * 단순히, 데이터 중복 현상이 발생하고 있는 결과 table 에 distinct 함수를 통해
   * 공연 size 에 대한 count 를 해주는 것이다. (핵심, 데이터 중복 현상이 해결되는건 아니다, 데이터 개수 카운팅에만 중복을 제거해준다.)
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

      List<Performance> performances = queryFactory
          .select(performance)
          .from(performance)
          .join(performance.profileHost, profile).fetchJoin()
          .where(teamNameEq(teamName))
          .fetch();

    return performances;
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
