package dnd.danverse.domain.performance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import dnd.danverse.domain.performance.dto.response.ImminentPerformsDto;
import dnd.danverse.domain.performance.entity.Performance;
import dnd.danverse.domain.performance.repository.PerformanceRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PerformancePureService.class})
@ActiveProfiles({"test"})
@ExtendWith(SpringExtension.class)
class PerformancePureServiceTest {

  @MockBean
  private PerformanceRepository performanceRepository;

  @Autowired
  private PerformancePureService performancePureService;


  @Test
  void testSearchImminentPerforms() {
    // Arrange
    ArrayList<Performance> performanceList = new ArrayList<>();
    when(performanceRepository.findImminentPerforms(any())).thenReturn(performanceList);
    // Act
    List<ImminentPerformsDto> imminentPerformsDtoList = performancePureService.searchImminentPerforms();
    // Assert
    assertEquals(0, imminentPerformsDtoList.size());
  }


}

