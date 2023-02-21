package dnd.danverse.domain.performance.repository;

import dnd.danverse.domain.performance.dto.request.PerformCondDto;
import dnd.danverse.domain.performance.dto.response.PerformInfoResponse;
import dnd.danverse.domain.performance.entity.Performance;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 공연을 필터링을 통해서 조회하는 커스텀 인터페이스.
 */
public interface PerformFilterCustom {

  Page<PerformInfoResponse> searchAllPerformWithCond(PerformCondDto performCondDto, Pageable pageable);

  List<Performance> searchPerformsByTeam(String teamName);


}
