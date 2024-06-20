---
name: Issue Custom Template
about: Develop FoodDeliveryApp
title: 'FEAT : 구현할 기능'
labels: enhancement
assignees: ''

---

## Description
- 회원가입 기능
    - 성공
        - DB에 중복된 `username`이 없다면 회원을 저장한다.
        - 클라이언트에 성공 메시지와 상태코드를 반환한다.
        - 응답은 content-type application/json 형식입니다.
        - 회원 권한 부여
            - ADMIN
                - 모든 게시글, 댓글 수정과 삭제 가능
            - USER
                - 본인이 작성한 게시글, 댓글에 한하여 수정과 삭제 가능
    - ⚠️ 필수 예외처리
        - DB에 중복된 `username`이 이미 존재하는 경우
        - `username`,`password` 조건에 맞지 않는 경우

## issue
- [ ] 예시)회원가입 기능 구현
- [ ] 예시)회원가입 비밀번호 무결성 검사 기능 구현
