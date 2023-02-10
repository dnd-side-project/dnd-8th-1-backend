package dnd.danverse.domain.performance.controller;

import dnd.danverse.domain.performance.dto.response.ImminentPerformsDto;
import dnd.danverse.domain.performance.service.PerformanceService;
import dnd.danverse.global.response.DataResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 공연 관련 데이터를 처리하기 위한 Contorller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/performances")
public class PerformanceController {

  private final PerformanceService performanceService;

  /**
   * 공연이 임박한 공연을 조회할 수 있다. 오늘 날짜 기준으로, 최근 공연 4개를 조회할 수 있다.
   *
   * @return 임박한 공연 목록
   */
  @GetMapping("/imminent")
  public ResponseEntity<DataResponse<List<ImminentPerformsDto>>> searchImminentPerformance() {

    List<ImminentPerformsDto> performs = performanceService.searchImminentPerforms();
    return ResponseEntity.ok(DataResponse.of(HttpStatus.OK, "임박한 공연 조회 성공", performs));
  }


}
