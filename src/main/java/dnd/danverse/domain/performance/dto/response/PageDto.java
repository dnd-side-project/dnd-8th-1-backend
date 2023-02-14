package dnd.danverse.domain.performance.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

/**
 * Pageable 을 통해 조회한 결과를 담는 DTO
 * @param <T> 조회한 결과의 타입
 */
@Getter
@AllArgsConstructor
public class PageDto<T> {

  /**
   * 조회한 결과
   */
  private List<T> content;
  /**
   * 하나의 페이지에 담기는 요소의 수
   */
  private int numberOfElements;
  /**
   * 조회한 결과의 시작 위치
   * 0부터 시작해서 몇 개를 건너뛰었는지를 나타냄
   */
  private long offset;
  /**
   * 현재 페이지 번호
   */
  private int pageNumber;
  /**
   * 페이지의 크기 (수용 가능한 content 수)
   */
  private int pageSize;
  /**
   * 전체 요소의 수
   */
  private long totalElements;
  /**
   * 전체 페이지의 수
   */
  private int totalPages;

  public PageDto(Page<T> page) {
    this.content = page.getContent();
    this.numberOfElements = page.getNumberOfElements();
    this.offset = page.getPageable().getOffset();
    this.pageNumber = page.getPageable().getPageNumber();
    this.pageSize = page.getPageable().getPageSize();
    this.totalElements = page.getTotalElements();
    this.totalPages = page.getTotalPages();
  }


}
