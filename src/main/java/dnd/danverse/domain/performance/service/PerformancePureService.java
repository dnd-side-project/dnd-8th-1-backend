package dnd.danverse.domain.performance.service;

import static dnd.danverse.global.exception.ErrorCode.PERFORMANCE_NOT_FOUND;

import dnd.danverse.domain.performance.dto.request.PerformUpdateRequestDto;
import dnd.danverse.domain.performance.dto.response.ImminentPerformsDto;
import dnd.danverse.domain.performance.entity.Performance;
import dnd.danverse.domain.performance.exception.PerformanceNotFoundException;
import dnd.danverse.domain.performance.repository.PerformanceRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 공연 순수 서비스.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PerformancePureService {

  private final PerformanceRepository performanceRepository;

  /**
   * 임박한 공연을 4개까지 조회할 수 있다. 오늘 날짜 기준으로, 최근 공연 4개를 조회할 수 있다.
   */
  @Transactional(readOnly = true)
  public List<ImminentPerformsDto> searchImminentPerforms() {
    LocalDate now = LocalDate.now();

    log.info("임박한 공연을 4개 조회한다.");
    List<Performance> imminentPerforms = performanceRepository.findImminentPerforms(now);

    return imminentPerforms.stream()
        .map(ImminentPerformsDto::new)
        .limit(4)
        .collect(Collectors.toList());
  }

  /**
   * performId 를 통해서 주최자와 공연 정보를 함께 찾는다.
   *
   * @param performId 조회하려고 하는 performId.
   * @return Performance.
   */
  @Transactional(readOnly = true)
  public Performance getPerformanceDetail(Long performId) {
    log.info("Performance 와 주최자(profile) 를 찾기 위해 performId 인 {} 를 찾는다.", performId);
    return performanceRepository.findPerformanceWithProfile(performId)
        .orElseThrow(() -> new PerformanceNotFoundException(PERFORMANCE_NOT_FOUND));
  }

  /**
   * performance 를 저장한다.
   *
   * @param performance 저장하려고 하는 Performance.
   * @return Performance.
   */
  @Transactional
  public Performance createPerform(Performance performance) {
    log.info("Performance 를 저장한다. 제목 : {} ", performance.getTitle());
    return performanceRepository.save(performance);
  }

  /**
   * 공연 정보를 updateRequestDto 대로 수정합니다.
   *
   * @param performance 수정하려는 공연.
   * @param request 수정 요청 Dto.
   * @return 요청 Dto 대로 수정합니다.
   */
  @Transactional
  public Performance updatePerform(Performance performance, PerformUpdateRequestDto request) {
    log.info("요청 dto로 수정합니다.");
    return performance.updateInfo(request);
  }

  /**
   * Performance 만 찾기 위해 사용한다.
   *
   * @param performId Performance 의 id
   * @return Performance
   */
  @Transactional(readOnly = true)
  public Performance getPerformance(Long performId) {
    log.info("Performance 만 찾기 위해 performId 인 {} 를 찾는다.", performId);
    return performanceRepository.findById(performId)
        .orElseThrow(() -> new PerformanceNotFoundException(PERFORMANCE_NOT_FOUND));
  }

  /**
   * 공연 정보를 삭제합니다.
   *
   * @param performance 삭제하려는 공연 정보.
   */
  @Transactional
  public void deletePerform(Performance performance) {
    log.info("performId가 {}인 공연글을 삭제합니다.", performance.getId());
    performanceRepository.delete(performance);
  }

  /**
   * 내가 등록한 공연 정보 리스트를 조회합니다.
   *
   * @param profileId 조회 요청하는 사용자의 프로필 Id.
   * @return 공연 정보 리스트.
   */
  @Transactional(readOnly = true)
  public List<Performance> getMyPerforms(Long profileId) {
    log.info("profileId 인 {} 를 통해서 공연 정보들을 찾는다. ", profileId);
    return performanceRepository.findAllByProfileId(profileId);
  }

}
