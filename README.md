# 클론 코딩

- 대상 : 카카오 아지트

## 실제 구현 목표
- 회원가입
- 로그인
- 아지트 개설
- 아지트 리스트 확인
- 아지트 멤버 
  - 초대
  - 리스트 조회
- 게시글
  - CRUD
  - 좋아요 / 싫어요 기능
    - 좋아요 싫어요 숫자 보여주기
    - 좋아요 싫어요 2가지 버튼 동시 구현
  - 댓글 갯수 보여주기
  - 최신순 정렬
- 댓글
  - CRUD
  - 수정 여부
  - 생성일
  - 최신순 정렬
- 메뉴 모달

## 와이어 프레임
![Untitled](https://user-images.githubusercontent.com/37091532/209952073-ac7ecaba-e92c-4c7c-b787-c92848b79bd1.png)


## API 설계
|역할분담|기능|METHOD|URL|request|response|
| - | - | - | - | - | - |
|||||||
|지훈 백|회원가입|POST|/user/signup|{     “username”:String,     “password”:String,     “checkPassword”:String,     “nickname”:String }|{     “msg” : “회원가입 성공     “statusCode”: 200     “data” : null  } OR {     “msg”: 회원 가입 실패     “statusCode”:400     “data” : null }|
|지훈 백|아이디 중복확인|GET|/user/idcheck?username=test1||{     “msg” : “사용가능한 아이디입니다.”     “statusCode”: 200     “data” : null  } OR {     “msg”: “중복되는 회원 존재”     “statusCode”:400     “data” : null }|
|지훈 백|로그인|POST|/user/login|{     “username”:String,     “password”:String }|{     “msg” ; “로그인 성공,     “statusCode”: 200,     “data” : null } OR {     “msg”: 로그인 실패(변경 가능)     “statusCode”:400     “data” : null }|
|지훈 백|유저 정보 요청|GET|/user||{     “msg” ; “조회 성공”     “statusCode”: 200     “data” : {         {             “username” :” test1”,             “nickName”:”범준”         } } OR {     “msg”: “로그인이 필요합니다”     “statusCode”:400     “data” : null }|
|JB Park|아지트 리스트|GET|/agit||{     “msg” ; “조회 성공”     “statusCode”: 200,     “data” : {         [             {                 “id”:1,                 “agitname”:”그럴싸한 아지트”             },    . . .              {                 “id”:2,                 “agitname”:”그으으럴싸한 아지트”             },         ]     }|메인페이지||
|JB Park|아지트 생성|POST|/agit|{      “agitname” : String }|{     “msg” ; “생성 성공”     “statusCode”: 200     “data” : null } OR {     “msg”: “로그인이 필요합니다”     “statusCode”:400     “data” : null }|
|JB Park|아지트 초대|POST|/agit/{agitId}/join|{     “username”:String }|{     “msg” ; “초대 성공”     “statusCode”: 200     “data” : null } OR {     “msg”: “로그인이 필요합니다”     “statusCode”:400     “data” : null }|
|BeomJoon Kim|아지트 게시물 조회|GET|/agit/{agitId}||{     “msg” : “조회 성공”     “statusCode”: 200,     “data” : {         “postList” : [             {                 “id” : 1                 “username”:”test1”,                 “nickname”: “범준”,                 “content” : “내용”,                 “likeCount” : 3,                 “hateCount” : 2,                 “postLike”:null,                 “createdAt”: “2022-12-16-13:55”                 “IsModified”: false,                 “commentList”:                     [                         {                             “id”:”1”,                             “username”:”test1”,                             “nickname”:”범준”,                             “IsModified”:true,                             “content”:”그럴싸한 내용”                             “createdAt”: “2022-12-16-13:55”                         }                     ]             },         . . .              {                 “id” : 22,                 “username”:”test1”,                 “nickname”: “범준”,                 “content” : “내용”,                 “likeCount” : 3,                 “hateCount” : 2,                 “postLike”:null,                 “createdAt”: “2022-12-16-13:55”                 “IsModified”: false,                 “commentList”:                     [                         {                             “id”:”2”,                             “username”:”test1”,                             “nickname”:”범준”,                             “IsModified”:true,                             “content”:”그럴싸한 내용”                             “createdAt”: “2022-12-16-13:55”                         }                     ]             }         ], }|메인 페이지||
|JB Park|게시글 생성|POST|/agit/{agitId}/post|{     “content” : String,     “picturePath”:String }|{     “msg” ; “생성 성공”     “statusCode”: 200,     “data” : {         “id” : 1         “username”:”test1”         “nickname”: “범준”,         “content” : “내용”,         “likeCount” : 0,         “hateCount”:0,         “postLike”:null,         “createdAt”: “2022-12-16-13:55”         “IsModified”:false         “picturePath”:”/static/img”       }|메인페이지||
|JB Park|게시글 수정|PUT|/agit/post/{postId}|{     “content” : String,     “picturePath”:String }|{     “msg” : “수정 성공,     “statusCode”: 200,     “data”: {         “id” : 1         “username”:”test1”         “nickname”: “범준”,         “content” : “내용”,         “likeCount” : 0,         “hateCount”:0,         “postLike”:null,         “createdAt”: “2022-12-16-13:55”         “IsModified”:true         “picturePath”:”/static/img”     } } OR {     “msg”: “수정 실패”,     “statusCode”:400,     “data”:null }|
|JB Park|게시글 삭제|DELETE|/agit/post/{postId}||{     “msg” : “삭제 성공”,     “statusCode”: 200 } OR {     “msg”: “삭제 실패”,     “statusCode”:400 }|
|전재경|댓글 작성|POST|/post/{postId}/comment|{     “content” : “내용” }|{     “msg” : “생성 성공”,     “statusCode”: 200,     “data” :          {             “id”:1,             “content”:”댓글 내용”,             “nickname”:”범준”,             “username”:”test1”,             “createdAt”:“2022-12-16-13:55”,             “IsModified”: false         } }|
|전재경|댓글 수정|PUT|/post/comment/{commentId}|{     “content”:”내용” }|{     “msg” : “수정 성공”,     “statusCode”: 200,     “data” :          {             “id”:1,             “content”:”댓글 내용”,             “nickname”:”범준”,             “username”:”test1”,             “createdAt”:“2022-12-16-13:55”,             “IsModified”: true         } }|
|전재경|댓글 삭제|DELETE|/post/comment/{commentId}||{     “msg” : “댓글 삭제 성공”,     “statusCode”: 200 } OR {     “msg”: “댓글 삭제 실패”,     “statusCode”:400 }|
|전재경|게시물 좋아요|POST|/like/post/{postId}||{     “msg” : “게시물 좋아요 성공”,     “statusCode”: 200,     “data”:         {             “postLike”:true             “likeCount”:3         } } OR {     “msg”: “좋아요 실패”,     “statusCode”:400 }|
|전재경|게시물 싫어요|POST|/hate/post/{postId}||{     “msg” : “게시물 싫어요 성공”,     “statusCode”: 200,     “data”:         {             “postLike”:false,             “hateCount”:3         } } OR {     “msg”: “싫어요 실패”,     “statusCode”:400 }|
|JB Park|아지트 멤버 조회|GET|/agit/{agit}/member||{     “msg” : “게시물 싫어요 성공”,     “statusCode”: 200,     “data”:         {            [                 {              } } OR {     “msg”: “싫어요 실패”,     “statusCode”:400 }|
||댓글 좋아요|POST|/like/comment{commentId}||{     “msg” : “댓글 좋아요 성공”,     “statusCode”: 200,     “data”:         {             “thisLike”true”         } } OR {     “msg”: “좋아요 실패”,     “statusCode”:400 }|
||댓글 싫어요|POST|/hate/comment{commentId}|

## ERD

![Untitled (1)](https://user-images.githubusercontent.com/37091532/209954909-3285ad32-6e3b-49be-8bb6-538b81496ad3.png)


## 역할 분담
<지훈>

- [x]  로그인
- [x]  회원가입
- [x]  JWT + 시큐리티
- [ ]  소켓(실시간 채팅)  → 프로젝트 진행 상황 적용 보류

<범준>

- [x]  게시글 전체 조회
- [x]  Swagger를 사용한 API 테스트 환경 구축
- [x]  MYSQL DB 연결
- [ ]  CI/CD → 도커를 활용해서 구현

<종범>

- [x]  게시글 CUD
- [x]  아지트 생성, 멤버 초대, 멤버 조회, 아지트 조회


<재경>

- [x]  댓글 CUD
- [x]  게시글 좋아요, 싫어요.


## 시간적 여유가 있었다면, 기능하고 싶었던 기능
- FE
  - 무한스크롤
  - 이미지 첨부
  - 실시간 채팅 BE와 연결

- BE
  - 실시간 채팅
  - 검색 기능
  - 북마크 기능
  - 수정 시 시간 계산( ex)1시간 전 수정됨)
  - CI/CD → 도커를 활용해서 구현
