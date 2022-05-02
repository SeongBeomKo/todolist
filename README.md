# todolist

할일을 기록하기 위한 todo list 개발

<br>


## 기술스택

Java11, gradle, SpringBoot, Database X

<br>


## 요구사항

> 1. 날짜, 우선순위(S,A,B,C,D), rank(0,1,2,3....), 할일제목, 할일내용, 상태(진행중, 완료, 취소)가 포함 되어야함
> 2. 우선순위는 동일날짜내에 중복 누락이 가능하나, rank는 중복 불가능 (ex. A0 다음에 B 없이 C가 있어도 되나 A0, A2는 안됨, A0 다음에는 무조건 A1이 있고 A2가 와야됨)



<br>

## API 명세서

https://futuristic-dosa-8f6.notion.site/0be9bcaa2a0e42269a5d07a9b8045fca?v=6dfab1d442fd4c82877213b054d1822d

<br>

## 테스트 방법

> 로컬 환경에서 빌드 후 실행 또는 IDE를 통해 실행 후 swagger를 통해 api 테스트 가능
> 
> http://localhost:8080/swagger-ui.html#/main-controller

<br>

## 고려 사항

**수정/삭제 시 자동 정렬**

> ex) A0,A1,B0,B1,B2 가 있을때 B0을 A1로 바꿀때
>
> B1, B2가 자동으로 B0, B1으로 맞춰지고 B0이 A1으로 삽입되면서 기존에 있던 A1 은 A2로 밀려남
>
> ex2) B0, B1, B2 가 있을때 B0 삭제 할때
>
> B1, B2 가 B0, B1으로 자동 수정 됨
