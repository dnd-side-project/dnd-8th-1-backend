package dnd.danverse.domain.profile.controller;

import dnd.danverse.domain.profile.dto.response.ProfileHomeDto;
import dnd.danverse.domain.profile.service.ProfileSearchService;
import dnd.danverse.global.response.DataResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 프로필 컨트롤러
 */
@Controller
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
@Api(tags = "ProfileController : 프로필 관련 API")
public class ProfileController {

  private final ProfileSearchService profileSearchService;



  @GetMapping("/home")
  @ApiOperation(value = "프로필 6개 랜덤 조회", notes = "프로필 6개 랜덤 조회")
  public ResponseEntity<DataResponse<List<ProfileHomeDto>>> searchProfileForHome() {
    List<ProfileHomeDto> profileHomes = profileSearchService.searchProfileForHome();
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "프로필 6개 랜덤 조회 성공" ,profileHomes), HttpStatus.OK);
  }


}
