### ✒️ 관련 이슈번호

- Closes 생년월일 optional 하게 수정 #283

## 🔑 Key Changes

1. Onboarding 엔티티 birthday 데이터타입 LocalDate -> String
    - 생년월일을 생략하는 경우 ""로 클라에서 보내신다고 하셔서 String으로 수정했습니다. **괜찮나요?!** (기존에 nullable = false 였길래요!)
    - 기존 형태(LocalDate) 유지하고 nuallble = true로 수정하려고 했는데
        - 안드한테 물어보니까 어차피 String 형태로 주고 있고, 기존에도 LocalDate -> String 형태로 박는거였어서 String 으로 변경함!
2. 회원가입 시 birthday optional 반영 (`birthday="", isPublic=false`)
    - `birthday="", isPublic=true`이면 400 에러 던집니다.
3. 프로필 정보 수정 시 birthday optional 반영 (`birthday="", isPublic=false`)
4. 나/룸메이트 조회 시 생년월일이 없는 경우 빈 스트링 보내도록 response 수정
5. 호미의 탄생 배지 스케줄러 돌릴 때 엔티티 데이터 타입 변경으로 DateUtils 에 `오늘날짜.toString()` 하는 함수 만들어서 수정

## 📢 To Reviewers

- 나/룸메이트 조회 시 age, birthday 부분이 없는 경우
    - "" 또는 null로 전달해주는게 의진오빠가 더 편하다고 해서 호옥시 안드는 null 처리를 안했을까봐 ""로 수정했습니다!