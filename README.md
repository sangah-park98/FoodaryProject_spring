# :grapes:Project_PORTFOLIO:green_apple:

## Index

- 💛 [소개](#소개)
- 🚀 [기능 구현](#기능-구현)
  + [메인페이지](#메인페이지)
  + [회원가입](#회원가입)
  + [마이페이지](#마이페이지)
  + [자유게시판](#자유게시판)
  + [식단 기록](#식단-기록)
  + [식단 수정 및 삭제](#식단-수정-및-삭제)
  + [식단 목록](#식단-목록)
- 🛠 [사용한 기술](#사용한-기술)
- 🎞 [시연 영상](#시연-영상)
- ✏ [교육 수료](#교육-수료)
- ➕ [추가 기능 구현](#추가-기능-구현)
- 💡 [느낀 점](#느낀-점)
- 📞 [문의](#문의)
 

<br/>


## 💛소개
> - 프로젝트명: Foodary
>  +  사용자의 편의에 따라 자유롭게 식단을 수정 및 기록할 수 있는 식단 일기장
> - 분류: 팀 프로젝트
> - 제작 기간: 2023.06 ~ 08.
> - 주요 기능: 회원가입, 마이페이지, 자유게시판, 식단 기록 및 수정, 목록 보기

<br/>

## 🚀기능 구현
## 메인페이지
![메인페이지](https://github.com/sangah-park98/project_foodary_spring/assets/133108195/97c18665-1f9b-407d-98f8-d5df13d11d9a)
저희 프로젝트는 로그인을 한 후에 이용할 수 있는 기능들이 대부분이기 때문에 로그인을 하기 전의 페이지는 최소한의 요소들만 남긴 형태로 작업했습니다.  
회원가입 페이지, 로그인 페이지로 이동할 수 있고, 로그인을 하지 않아도 간단하게 이용할 수 있는 식단계산기를 추가했습니다.  
로그인 후에는 Foodary를 방문했을 때 이용할 수 있는 기능들을 축약해두었습니다.  
각각의 버튼을 통해 기능들에 접속할 수 있고, 아래 쪽에는 간단한 재미요소로 메뉴추천, 영상 광고란 등을 만들어 두었습니다.

## 회원가입
![회원가입](https://github.com/sangah-park98/project_foodary_spring/assets/133108195/b6a24701-e8d9-4751-9ed6-053967ef6b90)
회원가입에서는 나만의 영양소 계산을 위해 기본정보 외에도 키와 나이, 체중에 대해 입력을 받고 있습니다.  
주요 기능 중 하나인 아이디 중복 검사에서는 아작스를 활용하여 모달을 띄워 입체감있게 표현했습니다.  
마이페이지는 메인페이지 드롭다운 메뉴를 통해 접속할 수 있습니다.  
회원가입 때 입력한 정보를 한눈에 볼 수 있고, 정보 수정 및 회원 탈퇴까지 가능하게 구현하였습니다.  

## 마이페이지
![마이페이지](https://github.com/sangah-park98/project_foodary_spring/assets/133108195/49bd2021-6189-47a6-8ae0-a1270ade8224)
메인페이지를 거쳐 회원가입을 진행하거나, 이미 등록된 회원이라면 로그인을 하거나, 아이디 찾기 및 비밀번호 찾기 등을 할 수 있습니다.  
로그인을 한 후에는 foodaryMainPageAfter의 뷰페이지가 나오고 마이페이지로 들어갈 수 있습니다.  
마이페이지에서는 자신의 정보 수정 및 회원탈퇴 기능까지 이용할 수 있습니다.  

## 식단 기록
![식단쓰기](https://github.com/sangah-park98/project_foodary_spring/assets/133108195/713f2435-6931-45ed-800a-202bee0bf0ff)
쓰기 메뉴에서는 음식 검색을 통해 food테이블에 들어있는 공공데이터 정보를 가져와 기록할 수 있습니다.  
가져온 음식 데이터를 자유롭게 수정, 삭제할 수 있고, 세션에 담겨있는 이용자의 정보와 음식 데이터 정보를 비교해서 그래프로 영양소 섭취량을 볼 수 있게 작업했습니다.  
식단 일기라는 컨셉에 맞게 일기의 내용과 사진도 함께 등록할 수 있습니다.  

## 식단 수정 및 삭제
![식단 수정](https://github.com/sangah-park98/project_foodary_spring/assets/133108195/c0fbf4fc-d5fc-48ba-88e0-f1b3d99e17b7)
식단 수정 및 삭제는 음식 검색을 통해 나만의 식단 기록을 한 후 자유롭게 수정 및 삭제를 할 수 있습니다.  

## 식단 목록
![식단목록 보기](https://github.com/sangah-park98/project_foodary_spring/assets/133108195/b81bb879-0151-4b53-86c1-5ee0a0b666b1)
푸드어리 보기는 달력으로 해당 날짜의 식단 목록을 확인 할 수 있습니다.  
전체 보기에서는 해당 날짜에 기록한 식단의 영양소 섭취량을 한눈에 볼 수 있고, 각 식단의 내용도 확인할 수 있습니다.  
각 식단의 시간을 클릭하면 개별 식단을 확인할 수 있고 식단 수정과 삭제가 가능합니다.  

## 자유게시판
![자유게시판](https://github.com/sangah-park98/project_foodary_spring/assets/133108195/70eedc48-feea-43cf-833d-408336f2a398)
컨트롤러와 DAO클래스는 동일하게 가져가고, freeboard.xml 과 freeboardcomment.xml 로 구분지어 명령문을 실행했습니다.  
뷰페이지는 목록, 입력창, 확인창, 수정창으로 최대한 간결하게 사용했습니다.  
자유게시판은 보통의 게시판들과 동일한 형태로 글과 사진을 남기고 댓글을 통해 소통할 수 있습니다.  
게시글과 댓글을 작성할 수 있고, 세션에 담긴 로그인 정보와 게시글, 댓글의 정보를 비교하여 정보가 일치하는 계정에서만 수정 및 삭제가 가능하게 작업했습니다.  


<br/>

## 🛠사용한 기술    
BackEnd:![js](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![js](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)  
FrontEnd:![js](https://img.shields.io/badge/CSS-239120?&style=for-the-badge&logo=css3&logoColor=white)
![js](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white)
![js](https://img.shields.io/badge/jQuery-0769AD?style=for-the-badge&logo=jquery&logoColor=white)
![js](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)  
DB: ![js](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)
![js](https://img.shields.io/badge/Oracle-F80000?style=for-the-badge&logo=Oracle&logoColor=white)

<br/>

## 🎞시연 영상
[보러가기] https://youtu.be/P89qSZqFK5Q


<br/>

## ✏교육 수료
|기간|교육과정|
|------|---|
|2023.04 ~ 2023.11|빅데이터분석(with 파이썬)과 엘라스틱서치를 활용한 자바 웹개발자양성|
|2021.08 ~ 2021.09|컴퓨터활용능력 1급|
|2022.02 ~ 2022.03|청소년지도사 2급 자격연수|

<br/>

## ➕추가 기능 구현
- Naver 로그인 API  
  따로 저장된 NaverLoginAPI DB 파일은 네이버로 로그인 하기 위해 필요한 기능들만 올린 파일이다.  
  DAO의 메소드는 자동 생성으로 넣어줘야 한다.  
  NaverLoginAPI: DB에 name, id, gender, email만 저장되는 코드
- 관리자 기능
- 자유게시판에 회원들끼리 서로 사진 공유
- 채팅 기능


<br/>

## 💡느낀 점
### 배운 점  
처음 진행한 팀 프로젝트로 Notion을 통해 서로 협업하며 각자 만든 부분들을 공유했습니다.  
sqldeveloper을 통해 DB에 사용자가 입력한 정보에 대해 저장하고, Ajax를 사용하여 기존 사용자의 아이디와 중복이 될 경우 다른 아이디를 사용하게끔 구현하였습니다.  
또한 UX에 기반하여 네이버 로그인 API를 통해 사이트 내에서 회원가입을 하지 않아도 로그인을 할 수 있도록 하였습니다.  
처음 진행하는 프로젝트인만큼 팀원들과의 협업의 중요성에 대해 배울 수 있는 시간이었습니다.
### 아쉬운 점
기능 구현에만 집중한 나머지 코드의 가독성이 떨어지는 점이 아쉬웠습니다.  
기획 단계에서 전체적인 흐름에 대해 더 깊이 있게 고민했다면 추후 기능 추가를 할 때 더 빠르게 진행이 되었을 것 같습니다.  
개발과 이론을 함께 병행하며 공부했다면 더 좋았을 것 같습니다.  
기간이 정해져 있고, 첫 프로젝트다보니 부족한 점이 많아 더 많은 기능을 추가하지 못한 것이 아쉽습니다.  

<br/>

## 📞문의
- Email: sangab018@gmail.com
- phone: 010-5284-5147
- Git: https://github.com/sangah-park98
-------------
