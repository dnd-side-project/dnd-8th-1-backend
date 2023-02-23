package dnd.danverse.domain.performance.service;

import dnd.danverse.domain.performance.dto.request.PerformUpdateRequestDto;
import dnd.danverse.domain.performance.dto.response.PerformDetailResponse;
import dnd.danverse.domain.performance.entity.Performance;
import dnd.danverse.domain.performgenre.entity.PerformGenre;
import dnd.danverse.domain.performgenre.service.PerformGenrePureService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 글 수정 복합 서비스.
 */
@Service
@RequiredArgsConstructor
public class PerformUpdateComplexService {

  private final PerformWriterValidationService performWriterValidationService;
  private final PerformancePureService performancePureService;
  private final PerformGenrePureService performGenrePureService;

  /**
   * 1. 글 작성자와 수정을 요청하는 사용자와 동일한지 확인한다.
   * 2. 수정 요청 dto에 따라 수정이 일어난다.
   * 3-1. 기존의 장르 데이터와 요청하는 장르 데이터의 동일하면, 그대로 반환한다.
   * 3-2. 기존의 장르 데이터와 요청하는 장르 데이터가 다르면, deleteAll과 saveAll을 한 후에 반환한다.
   *
   * @param updateRequest 수정 요청 Dto.
   * @param memberId 사용자 Id.
   * @return 공연 응답 Dto.
   */
  public PerformDetailResponse updatePerform(PerformUpdateRequestDto updateRequest, Long memberId) {

    // <리펙토링 전>
    // 공연 + 주최자 (validate)
    // 멤버 (validate)
    // 공연 + 주최자 + 장르 (장르 비교 위해)


    // <리펙토링 다양한 방안>
    // 2번
    // 공연 + 주최자 + 장르 (추후 장르 비교를 위해) (validate) => fetch join
    // 멤버 (validate)
    // 장점 : 2번 나간다.
    // 단점 : 공연 주최자랑 api 요청자가 다른 경우, 3번 fetch join 의미 없다. => 3번 조인으로 중복된 데이터 발생

    // 3번
    // 공연 -> 주최자 (프록시, 인식표)
    // 멤버 (validate)
    // 공연(더티) + 주최자(응닶) + 장르(업데이트 내역) => fetch join
    // 장점 : 공연 주최자랑 api 요청자가 다른 경우, join 최소화
    // 단점 : 쿼리 3번, 3개의 테이블 조인, 3개 조인으로 중복된 데이터 발생

    // 3번
    // 공연 + 주최자 (fetch join)
    // 멤버
    // 공연 => 장르 (lazy loading) => 조인 없이 1개의 테이블 대상으로 쿼리 날린다.
    // 특징 : 모든 상황에 대해 중간 지점 (주최자 , api 요청자 맞는지 틀렸는지 상관없이)
    // 장점 : 최대 2개 테이블 조인 (중복 된 데이터 발생 안함)
    // 장점 : 장르에 대해서 추가적인 조인 없어

    // <저울질>
    // 1. 2번 사용 + 삭제 validate method 추가 생성
    // 2. (3번 a,b 방법 중 택1) + 삭제 validate method 추가 생성
    // 3. 2번 방법 다 사용 (x)

    // 3번 b => 2번 => 3번 a


    Performance performance = performWriterValidationService.validatePerformWriter(updateRequest.getId(), memberId); //같은 작성자인지 검증 및 performance 반환.

    Performance newPerformance = performancePureService.updatePerform(performance, updateRequest); //장르를 제외한 나머지를 수정한다.
    if (newPerformance.containGenres(updateRequest.getGenres())) {
      return new PerformDetailResponse(newPerformance);
    }
    Set<PerformGenre> newGenres = updateRequest.stringToGenre(newPerformance);
    performGenrePureService.deleteAndSaveAll(updateRequest.getId(), newGenres);
    return new PerformDetailResponse(newPerformance, newGenres);
  }


}
