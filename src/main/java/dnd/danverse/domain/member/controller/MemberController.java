package dnd.danverse.domain.member.controller;


import dnd.danverse.domain.jwt.service.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 회원 관련 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {



  @GetMapping("resource")
  public ResponseEntity<String> resource(@AuthenticationPrincipal SessionUser user) {

    String email = user.getEmail();

    return ResponseEntity.ok(email);

  }



}
