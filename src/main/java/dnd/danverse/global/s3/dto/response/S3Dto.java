package dnd.danverse.global.s3.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 업로드된 이미지의 url을 전달하기 위한 DTO.
 * - S3에 저장된 이미지 url
 */
@Builder
@Getter
@AllArgsConstructor
public class S3Dto {
  @ApiModelProperty(value = "이미지 url")
  private String imgUrl;

}
