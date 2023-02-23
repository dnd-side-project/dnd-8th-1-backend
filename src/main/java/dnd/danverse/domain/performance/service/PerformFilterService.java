package dnd.danverse.domain.performance.service;

import dnd.danverse.domain.performance.dto.request.PerformCondDto;
import dnd.danverse.domain.performance.dto.response.PageDto;
import dnd.danverse.domain.performance.dto.response.PerformInfoResponse;
import dnd.danverse.domain.performance.dto.response.PerformListResponse;
import dnd.danverse.domain.performance.entity.Performance;
import dnd.danverse.domain.performance.repository.PerformanceRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 공연 필터링 조건을 통해 공연 전체를 조회하는 복합 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PerformFilterService {

  private final PerformanceRepository performanceRepository;


  /**
   * 공연 필터링 조건을 통해 공연 전체를 조회한다.
   * @param performCondDto 공연 필터링 조건
   * @return 페이징 처리된 공연 목록
   */
  public PageDto<PerformInfoResponse> searchAllPerformWithCond(PerformCondDto performCondDto) {
    Page<PerformInfoResponse> performInfoResponses = performanceRepository.searchAllPerformWithCond(
        performCondDto);

    return new PageDto<>(performInfoResponses);
  }

  /**
   * 팀 이름을 통해 예정된 , 마감된 공연을 조회한다.
   * @param teamName 팀 이름
   */
  public PerformListResponse searchPerformsByTeam(String teamName) {
    List<Performance> performances = performanceRepository.searchPerformsByTeam(teamName);

    LocalDate today = LocalDate.now();
    LocalDateTime now = LocalDateTime.now();

    Map<Boolean, List<Performance>> performList = performances.stream()
        .collect(Collectors.partitioningBy(
            performance -> performance.getStartDate().isAfter(today) ||
                (performance.getStartDate().isEqual(today) && performance.getStartTime().isAfter(now))));

    return new PerformListResponse(performList);
  }
}
