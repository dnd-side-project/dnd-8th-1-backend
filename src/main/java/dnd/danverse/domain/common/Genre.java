package dnd.danverse.domain.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 장르 관련 Enum Class .
 * 커버 댄스, 힙합, 스트릿, 브레이킹, 팝핀, 락킹, 왁킹, 댄스홀, 크럼프, 기타
 */
@Getter
@RequiredArgsConstructor
public enum Genre {

    COVER_DANCE("커버댄스"),
    HIPHOP("힙합"),
    STREET("스트릿"),
    BREAKING("브레이킹"),
    POPPIN("팝핀"),
    LOCKING("락킹"),
    WACKING("왁킹"),
    DANCEHALL("댄스홀"),
    CRUMP("크럼프"),
    ETC("기타");

    private final String genreName;

}
