 <div align="center">  <h2> 영화 퀴즈 만들고 푸는 사이트 </h2></div>

```
해당 프로젝트는 영보나를 통해 영감을 얻어 만든 프로젝트입니다!
```

<hr>
 <div align="center">
FRONTEND - 김완영 (React, styled-components, HTML, JavaScript ES6) <br>
BACKEND - 김재우 (Springboot2.7.5,  JPA(JPQL),  MySQL, Spring Security(JWT,Session))<br>
DESIGN - 최아리 (Adobe Photoshop, Adobe Illustrator, Adobe XD, Figma )<br>
DEVOPS - 조우형 (Naver Cloud Platform, Docker, Jenkins, ArgoCD, Kubernetes)<br>
DATA - 박현<br>
</div>
<hr>

  <div align="center">DESCRIPTION<div>

<hr>

>  영화를 보기 전 광고로 봤었던 영보나 서비스를 보고 박스오피스 영화들에 퀴즈를 내고 퀴즈를 푸는 식으로 구성하였고, 퀴즈는 누구나 낼 수 있는 형태가 아닌 퀴즈를 전에 한 번이라도 푼 적이
  있는 사람이라면 퀴즈를 만들 수 있다. 다른 영화 사이트처럼 영화에 대한 리뷰도 달 수 있고 리뷰에는 스포일러가 있는 글인지 아닌지를 체크하여 블라인드 처리가 되도록 했다. 리뷰들에 욕설이
  있는 경우가 있을 수 있어 신고하기 버튼을 만들어 다른 사용자들이 해당 리뷰를 신고 버튼을 여러 사용자가 누를 경우 해당 리뷰는 블라인드 처리된다. 박스오피스 10위까지에 대한 영화와
  2021년~2022년에 나온 모든 영화에 정보들을 모두 가져와서 사용자에게 보여준다.


<hr>

  <div align="center">ERD<div>

<hr>

![image](https://user-images.githubusercontent.com/79129475/209801017-026d4e41-c211-4b09-b7fa-5a5e02b9c4b2.png)


<hr>

  <div align="center">VIEW<div>

<hr>


![image](https://user-images.githubusercontent.com/79129475/209801784-2ab40584-e151-4d94-aa7e-acb964a1da01.png)

![image](https://user-images.githubusercontent.com/79129475/209801819-eeb9a3e2-f3b9-4375-913f-855964282db4.png)

![image](https://user-images.githubusercontent.com/79129475/209801876-5b4b2632-60fc-47bd-b1cd-22b8adf9bcc2.png)

![image](https://user-images.githubusercontent.com/79129475/209801906-cf9ddac0-a5fa-4950-9d50-d0eb4f50dd05.png)

>  마이페이지

![image](https://user-images.githubusercontent.com/79129475/209802059-236bfd81-02e1-4950-b3b0-882d30643f17.png)
![image](https://user-images.githubusercontent.com/79129475/209802078-5bda5ebe-08c1-45d9-9f6d-db39f30ac3c5.png)
![image](https://user-images.githubusercontent.com/79129475/209802102-c3489cd9-6b7b-4604-8443-737c6343ba34.png)


>  검색화면

![image](https://user-images.githubusercontent.com/79129475/209802224-24054c88-48e0-4bf4-bcca-53fc9f4b8f4c.png)
![image](https://user-images.githubusercontent.com/79129475/209802317-b8385ae8-af72-4256-a966-b23559610e40.png)


> 퀴즈풀기

![image](https://user-images.githubusercontent.com/79129475/209802457-85702b96-417f-4c40-ae8b-abfba9258e82.png)
![image](https://user-images.githubusercontent.com/79129475/209802475-bc4a142b-dd56-44d4-9c37-8a38193eebef.png)





<hr>

  <div align="center">프로젝트 진행 중 가장 큰 문제<div>

<hr>



원래 기존에는 OpenAPI(NAVER 검색, 영화진흥원 박스오피스, KMDB) 를 사용하여 영화 평점, 박스오피스, 시놉시스 정보들을 가져와 사용할 예정이었는데 박스오피스를 영화진흥원 API를
통해 박스오피스 정보를 받아 NAVER 검색에 그 값을 넣어 포스터와 각 종 정보들을 불러오려고 했었다. 하지만 검색 데이터에서 제대로 된 데이터가 나오지 않는 경우가 10개 중 2개정도가 계속해서
나왔었다.(영화 자백이 박스오피스 순위에 있었는데 한국 자백이 일본 자백이 반환되었었다 

(검색어를 정확하게 하거나 국가별 지정할 수 있지 않나? - 이 모든 의문이 드는 방법은 모두 해봤다
국가를 지정하게 되면 다른 박스오피스 정보도 이상한 결과가 나왔었고 모두 정상 동작상태이지만 다른 데이터가 들어올 뿐이어서 이 부분을 구분하지 못했었다.))


그래서 KMDB에서 기존에는 시놉시스만 받고 다른 정보들은 네이버로 받으려 했었으나 네이버에서 이런 데이터가 넘어와서 KMDB에서 모든 정보를 받으려 했었는데 KMDB에서도
문제 없는 데이터가 넘어왔었지만 포스터와 스틸이미지 링크들이 전부 404가 나와서 OPEN API을 사용하는 부분에 대해 문제가 있었다. 그러다 크롤링 하는 분이 프로젝트에 참여하여
영화 데이터를 불러올 수 있도록 도와주었다. jsoup를 사용하여 영화 데이터를 크롤링하여 기존에 있던 문제들을 해결할 수 있었다.



