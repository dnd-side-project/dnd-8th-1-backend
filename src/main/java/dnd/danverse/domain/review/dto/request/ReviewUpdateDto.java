package dnd.danverse.domain.review.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 후기 수정을 위한 요청 Dto
 */
@Getter
@NoArgsConstructor
public class ReviewUpdateDto {

  /**
   * 후기 ID
   */
  @ApiModelProperty(value = "후기 고유 ID", example = "1")
  private Long id;

  /**
   * 후기 내용
   */
  @ApiModelProperty(value = "수정하고자 하는 후기 내용")
  private String review;

}
