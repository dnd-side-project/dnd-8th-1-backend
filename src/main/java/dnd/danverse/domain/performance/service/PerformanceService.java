package dnd.danverse.domain.performance.service;

import dnd.danverse.domain.performance.dto.response.ImminentPerformsDto;
import dnd.danverse.domain.performance.entity.Performance;
import dnd.danverse.domain.performance.repository.PerformanceRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 공연 순수 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PerformanceService {

  private final PerformanceRepository performanceRepository;

  /**
   * 임박한 공연을 4개까지 조회할 수 있다. 오늘 날짜 기준으로, 최근 공연 4개를 조회할 수 있다.
   */
  public List<ImminentPerformsDto> searchImminentPerforms() {
    LocalDate now = LocalDate.now();

    List<Performance> imminentPerforms = performanceRepository.findImminentPerforms(now);

    return imminentPerforms.stream()
        .map(ImminentPerformsDto::new)
        .limit(4)
        .collect(Collectors.toList());
  }
}
