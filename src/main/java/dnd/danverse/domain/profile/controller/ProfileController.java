package dnd.danverse.domain.profile.controller;

import dnd.danverse.domain.jwt.service.SessionUser;
import dnd.danverse.domain.profile.dto.request.ProfileSaveRequestDto;
import dnd.danverse.domain.profile.dto.response.ProfileHomeDto;
import dnd.danverse.domain.profile.dto.response.ProfileWithGenreDto;
import dnd.danverse.domain.profile.service.ProfileDetailService;
import dnd.danverse.domain.profile.service.ProfileSaveService;
import dnd.danverse.domain.profile.service.ProfileSearchService;
import dnd.danverse.global.response.DataResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 프로필 컨트롤러.
 */
@Controller
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
@Api(tags = "ProfileController : 프로필 관련 API")
public class ProfileController {

  private final ProfileSearchService profileSearchService;
  private final ProfileDetailService profileDetailService;
  private final ProfileSaveService profileSaveService;

  /**
   * 서비스 사용자는 실제 사용자의 프로필 6개를 랜덤으로 조회할 수 있습니다.
   *
   * @return 랜덤 프로필 6개를 반환한다.
   */
  @GetMapping("/home")
  @ApiOperation(value = "프로필 6개 랜덤 조회", notes = "프로필 6개 랜덤 조회")
  public ResponseEntity<DataResponse<List<ProfileHomeDto>>> searchProfileForHome() {
    List<ProfileHomeDto> profileHomes = profileSearchService.searchProfileForHome();
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "프로필 6개 랜덤 조회 성공", profileHomes), HttpStatus.OK);
  }

  /**
   * 서비스 사용자는 다른 사용자의 프로필을 조회할 수 있습니다.
   */
  @GetMapping("/{profileId}")
  @ApiOperation(value = "프로필 상세 조회", notes = "다른 사용자의 프로필 상세 조회")
  public ResponseEntity<DataResponse<ProfileWithGenreDto>> getProfile(@PathVariable("profileId") Long profileId) {
    ProfileWithGenreDto profileWithGenreDto = profileDetailService.getProfile(profileId);
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "프로필 상세 조회 성공", profileWithGenreDto), HttpStatus.OK);
  }

  /**
   * 로그인한 사용자는 프로필을 등록할 수 있습니다.
   */
  @PostMapping()
  @ApiOperation(value = "프로필 등록", notes = "로그인한 사용자에 한하여 프로필 등록")
  @ApiImplicitParam(name = "Authorization", value = "Bearer access_token (서버에서 발급한 access_token)",
      required = true, dataType = "string", paramType = "header")
  public ResponseEntity<DataResponse<ProfileWithGenreDto>> saveProfile(@RequestBody ProfileSaveRequestDto request,
      @AuthenticationPrincipal SessionUser sessionUser) {
    ProfileWithGenreDto profileResponse = profileSaveService.saveProfile(request, sessionUser.getId());
    return new ResponseEntity<>(DataResponse.of(HttpStatus.CREATED, "프로필 등록 성공", profileResponse), HttpStatus.CREATED);
  }


}
