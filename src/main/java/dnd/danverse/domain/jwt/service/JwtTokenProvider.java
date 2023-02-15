package dnd.danverse.domain.jwt.service;

import static dnd.danverse.global.exception.ErrorCode.JWT_EXPIRED_TOKEN;
import static dnd.danverse.global.exception.ErrorCode.JWT_INVALID_TOKEN;

import dnd.danverse.domain.jwt.exception.JwtException;
import dnd.danverse.domain.member.entity.Member;
import dnd.danverse.domain.member.entity.Role;
import dnd.danverse.domain.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * JWT 토큰을 생성 및 검증 모듈
 */
@Slf4j
@Component
public class JwtTokenProvider {

  public static final Long ACCESS_TOKEN_EXPIRE_LENGTH_MS = 1000L * 60 * 60; // 1hour
  public static final Long REFRESH_TOKEN_EXPIRE_LENGTH_MS = 1000L * 60 * 60 * 24 * 7;  // 1week
  private static final String AUTHORITIES_KEY = "role";
  private final SecretKey secretKey;
  private final MemberRepository memberRepository;

  public JwtTokenProvider(@Value("${jwt.secret}") String secretKey,
      MemberRepository memberRepository) {
    this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    this.memberRepository = memberRepository;
  }

  /**
   * Spring 서버에서 제공한 토큰이 유효한지 검사를 하는 과정이다.
   * JwtException 을 발생하는 경우, JwtExceptionFilter 에서 처리한다.
   * @param token Spring 서버에서 제공한 Access Token
   * @return 유효한 토큰인지 여부
   */
  public Boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
      return true;
    } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
      log.warn("잘못된 JWT 서명입니다.");
      throw new JwtException(JWT_INVALID_TOKEN);
    } catch (ExpiredJwtException e) {
      log.warn("만료된 JWT 토큰입니다.");
      throw new JwtException(JWT_EXPIRED_TOKEN);
    }
  }


  /**
   * Access Token 을 이용하여 Authentication 객체를 생성한다.
   *
   * @param accessToken Spring 서버에서 제공한 Access Token
   * @return 반환되는 Authentication 객체는 인증된 객체이며, Security Context Holder에 들어간다.
   */
  public Authentication getAuthentication(String accessToken) {
    // access token에 들어 있는 claims를 분석한다.
    Claims claims = parseClaims(accessToken);

    // claims가 가지고 있는 AUTHORITIES_KEY 를 이용하여 권한을 생성한다.
    // Access Token 생성시, "role" 이라는 이름으로 권한 정보를 넣어줬다.
    String role = claims.get(AUTHORITIES_KEY).toString();

    // claims 가 sub 으로 가지고 있는 고유한 db_id 값을 가져온다.
    // 해당 값은 controller 에서 @SecurityContext 를 이용하여 리소스 요청 사용자 정보를 가져올 때 사용된다.
    String email = claims.getSubject();

    return new UsernamePasswordAuthenticationToken(getSessionUser(email), "",
        getGrantedAuthorities(role));
  }

  /**
   * Access Token 을 생성한다.
   * 유효 시간은 1시간으로 설정한다.
   * @param email 사용자 email
   * @return 생성된 Access Token
   */
  public String createAccessToken(String email, Role role) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_LENGTH_MS);

    return Jwts.builder()
        .signWith(secretKey)
        .setSubject(email)
        .claim(AUTHORITIES_KEY, role.getAuthority())
        .setIssuer("danverse")
        .setIssuedAt(now)
        .setExpiration(validity)
        .compact();
  }

  /**
   * Refresh Token 을 생성한다.
   * 유효 시간은 7일로 설정한다.
   * @return 생성된 Refresh Token
   */
  public String createRefreshToken() {

    Date now = new Date();
    Date validity = new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_LENGTH_MS);

    return Jwts.builder()
        .signWith(secretKey)
        .setIssuer("danverse")
        .setIssuedAt(now)
        .setExpiration(validity)
        .compact();
  }

  /**
   * Spring Context Holder 에 저장될 Authentication 객체를 생성한다.
   * @param email 사용자 고유 email
   * @return 생성된 Authentication 객체
   */
  private SessionUser getSessionUser(String email) {
    Optional<Member> optionalMember = memberRepository.findByEmail(email);

    if (optionalMember.isEmpty()) {
      throw new JwtException(JWT_INVALID_TOKEN);
    }
    return new SessionUser(optionalMember.get());
  }

  /**
   * Spring Context Holder 에 저장될 Authentication 객체의 권한을 생성한다.
   * @param role 일반 String 문자열의 권한
   * @return 생성된 권한 목록
   */
  private static List<GrantedAuthority> getGrantedAuthorities(String role) {
    List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
    grantedAuthorities.add(new SimpleGrantedAuthority(role));
    return grantedAuthorities;
  }

  /**
   * Access Token 에 들어 있는 claims 를 분석한다.
   * @param accessToken Access Token
   * @return 분석된 claims
   */
  private Claims parseClaims(String accessToken) {
    try {
      return Jwts.parserBuilder().setSigningKey(secretKey).build()
          .parseClaimsJws(accessToken)
          .getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }


}