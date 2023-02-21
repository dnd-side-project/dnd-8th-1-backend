package dnd.danverse.domain.performance.dto.response;

import dnd.danverse.domain.performance.entity.Performance;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.Map;
import lombok.Getter;

/**
 * 예정된 공연 , 마감된 공연을 응답으로 보내주기 위한 Dto.
 */
@Getter
public class PerformListResponse {

  @ApiModelProperty(value = "예정된 공연")
  private final List<PerformInfoResponse> comming;

  @ApiModelProperty(value = "마감된 공연")
  private final List<PerformInfoResponse> ended;

  /**
   * true 는 예정된 공연, false 는 마감된 공연을 응답으로 보내주기 위한 Dto.
   * @param performMap 예정된 공연, 마감된 공연을 담은 Map
   */
  public PerformListResponse(Map<Boolean, List<Performance>> performMap) {
    this.comming = PerformInfoResponse.of(performMap.get(true));
    this.ended = PerformInfoResponse.of(performMap.get(false));
  }



}
