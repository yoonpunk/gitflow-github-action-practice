# gitflow-github-action-practice

## 프로젝트 소개
이 프로젝트는 GitFlow와 Github Action을 연습하기 위한 프로젝트입니다.


## 릴리즈 노트
v1.0.0 이 release 되었습니다.
* Member 생성 기능 추가  
  아래와 같은 방법으로 Member를 추가할 수 있습니다.
```java
url: http://localhost:8080/members
method: POST
body:
{
"name": "홍길동",
"phone": "01012345678"
}

curl:
curl -X 'POST' \
'http://localhost:8080/members' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{
"name": "홍길동",
"phone": "01012345678"
}'

response:
## 성공
code: 201
result: "success"

## 실패 (이미 존재하는 회원)
code: 400
result: "MEMBER_ALREADY_EXIST"
```