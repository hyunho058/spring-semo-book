

![image-20210924141404889](https://user-images.githubusercontent.com/58923731/134832007-3daa093b-dfe9-4779-a6cb-6c15e96a2b34.png)

# SEMO BOOK

- 세모책은 도서 추천 서비스 입니다,
- 사용자가 도서에 별점, 보고싶어요 또는 보기 싫어요 를 입력하여 사용자 성향에 맞는 도서를 추천해줍니다.



## 사용한 기술 스택

<div align="center">
  <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=Spring Boot&logoColor=white"/>
  <img src="https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java&logoColor=white"/> 
	<img src="https://img.shields.io/badge/MariaDB-003545?style=flat-square&logo=MariaDB&logoColor=white"/>
  <img src="https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=Redis&logoColor=white"/> 
<br/>
  <img src="https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=Docker&logoColor=white"/>
  <img src="https://img.shields.io/badge/Jenkins-D24939?style=flat-square&logo=Jenkins&logoColor=white"/>
<br/>
  <img src="https://img.shields.io/badge/Git-F05032?style=flat-square&logo=Git&logoColor=white"/>
	<img src="https://img.shields.io/badge/Slack-4A154B?style=flat-square&logo=Slack&logoColor=white"/> 
	<img src="https://img.shields.io/badge/Jira-0052CC?style=flat-square&logo=Jira&logoColor=white"/> 
</div>

## 개발환경

* java 11
* Spring boot 2.4
* Redis
  * Spring Data Redis
* Junit5
* Mariadb
* JPA
  * Spring Data JAP
* QueryDSL

#### 기타

* 협업 : JIRA, Slack
*  Devops : Docker, Jenkins 

---

# 주요 기능

##  User

* SEMO BOOK 서비스를 이용하기 위해서는 회원가입을 해야한다.
* 사용자는 도서정보에 별점, 보고싶처요, 보기싫어요를 입력 또는 리뷰 작성이 가능하다.
* 사용자는 메인 페이지에서 추천 도서 리스트와 사용자가 선호하는 도서 카테고리를 볼 수 있다.
* 비밀번호를 찾기/변경할 때 기입한 메일을 통해 인증을 받아야 한다  

## QA

* 유저가 문의글을 남기면 이것을 관리자페이지에서 답변을 작성할 수 있다.
* 문의 글을 작성하면 사용 화면에서 답변이 되었는지 상태를 확인할수 있다.

## Book want

* 사용자가 특정 도서를 보고 싶어요를 선택 하면 해당 정보가 DB에 저장되어 '사용자가 읽고싶어한 책' 에 나타난다.
* 사용자가 특정 도서를 보기 싫어요를 선택 하면 해당 정보가 DB에 저장되어 추천에 제외된다

## Book Review

* 사용자는 도서정보에 최소 1점부터 최대5점 까지 별점을 입력할 수 있다.
* 사용자는 읽은 도서에 간단한 리뷰를 작성하고, 이미 작성한 리뷰는 수정을 할 수 있다.

## Book

* 사용자가 도서를 검색하면 해당하는 도서 정보를 노출한다.
  * Spring Batch 를 이용해  Best Seller, Steady Seller정보를 저장
  * 사용자가 검색한 도서 정보가 DB에 없으면 Kakao도서 검색 API를 통해 해당 도서 정보를 사용자에게 보여준다. 

## Recom

* 이용자 정보를 기반으로 관련 책을 추천해준다.

## Notice

* 관리자 페이지를 통해 공지사항을 작성할 수 있다.
* 사용자는 설정에 공지사항 화면에서 공지사항을 볼 수 있다.

# 서비스 화면

![semo_book](https://user-images.githubusercontent.com/58923731/134832638-d2b8532c-007e-414e-99a4-0af809de5fc5.png)

# ERD

![Untitled](https://user-images.githubusercontent.com/58923731/134832848-dd168719-310e-4ccd-8e9b-9ca24937cfe1.png)

# 프로젝트 관리 - jira

![jira](https://user-images.githubusercontent.com/58923731/134832864-7c3014e2-33cb-4eae-89dd-5312457f6502.png)

# 추천 흐름

![추천](https://user-images.githubusercontent.com/58923731/134832894-4557f05c-1c6d-470a-b1ed-2eca7db77e05.png)



## 사용자 기반의 랜덤평가

- 목적 : 사용자에게 랜덤으로 책을 보여줘서 사용자가 읽은 책을 간편히 평가한다.
- 흐름
  1. 유저 성향데이터 얻기
     - 유저가 리뷰를 쓸때마다 REDIS에 평가한 책의 장르를 INSERT한다
     - REDIS에 있는 장르데이터를 가지고 최대 5순위까지의 유저 성향을 만든다
  2. 랜덤평가 보여주기
     - 유저 성향을 기반으로 20개의 책 리스트를 보여준다.
     - 1순위 5건, 2순위 3건 , 3순위 2건, 4순위 1건, 5순위 1건, 종합 베스트셀러 8건 합쳐 보여주기
  3. 기타 필터작업
     - 유저가 이미 평가한책, 유저가 더이상 추천받기 싫어하는 책은 리스트에서 제외한다.
     - 추가로 필요한 평가 데이터는 종합 베스트셀러 리스트에서 가져온다

## 사용자 기반의 추천

- 목적 : 사용자가 읽은 책을 바탕으로 유사도가 높은 책을 추천한다.
- 흐름
  1. 관리자 추천
     - 관리자가 직접 선정한 책을 추천한다.
  2. 최신 리뷰 기반 추천
     - 유저가 가장 최근에 작성한 리뷰과 비슷한 유형의 책을 추천한다
     - 유저가 남긴 평점이 3점 이상일 때만 해당된다.
  3. 유저 성향별 추천
  4. 유저 성향데이터 얻기
     - 유저 성향데이터 얻기
     - 유저가 리뷰를 쓸때마다 REDIS에 평가한 책의 장르를 INSERT한다
     - REDIS에 있는 장르데이터를 가지고 최대 5순위까지의 유저 성향을 만든다
  5. 책 추천하기
     - 유저 성향 1위와 유사한 도서를 추천한다.
  6. 유저가 보고싶어한 책 보여주기
     - 유저가 나중에 읽고 싶다고 선택한 책을 보여준다
  7. 베스트셀러, 스테디셀러
     - 추천이 3개 이상 형성되지 않았을 때 추가한다.
     - REDIS에 저장되어 있는 종합 베스트셀러, 스테디셀러를 보여준다
  8. 기타 필터 작업
     - 각각의 추천에서 유저가 이미 평가한책, 유저가 더이상 추천받기 싫어하는 책은 리스트에서 제외한다.
