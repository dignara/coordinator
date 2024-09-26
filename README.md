## 구성
* * *
* Springboot v3.3.3
* Kotlin
* Kotest
* Jacoco
* java 17
* Graphql
* React v18
* babel
* webpack

## 코드 빌드
* * *
FE 빌드를 위한 npm install 과 Webpack 빌드 (FE 수정사항 발생하지 않을 경우 불 필요)
```bash
npm install
```
```bash
webpack --mode=development
```
BE 빌드를 위한 Gradle빌드
```bash
./gradlew clean
```
```bash
./gradlew build
```

## 실행 방법
* * *
```bash
java -jar ./build/libs/coordinator-0.0.1-SNAPSHOT.jar
```
* 웹 메인 페이지 : http://localhost:8080/

* Graphql 테스트 페이지 : http://localhost:8080/graphiql

## 테스트 방법
* * *
테스트 실행 및 커버리지 리포트 생성
```bash
./gradlew --console verbose test jacocoTestReport jacocoTestCoverageVerification
```
테스트 커버리지 확인 페이지

<img width="1086" alt="테스트 커버리지" src="https://github.com/user-attachments/assets/fa39a72a-07b6-491d-8530-76105d8c608d">

```bash
./build/jacocoHtml/index.html
```
테스트 제외 Class (@ExcludeFromJacocoGeneratedReport 사용)
  * CoordinatorApplication
  * GraphQLExceptionResolver

## 구현 내용
* * *
### 구현 1) 카테고리 별 최저가격 브랜드 상품 리스트, 총액 조회

* Get http://localhost:8080/graphql

* 요청
```graphql
query LowPriceGoodsByCategory {
  lowPriceGoodsByCategory {
    totalPrice
    goods {
      id
      brand
      category
      price
    }
  }
}
```
* 응답
```json
{
  "data": {
    "lowPriceGoodsByCategory": {
      "totalPrice": 34100,
      "goods": [
        {
          "id": "3",
          "brand": "C",
          "category": "상의",
          "price": 10000
        },
        {
          "id": "14",
          "brand": "E",
          "category": "아우터",
          "price": 5000
        },
        {
          "id": "22",
          "brand": "D",
          "category": "바지",
          "price": 3000
        },
        {
          "id": "28",
          "brand": "A",
          "category": "스니커즈",
          "price": 9000
        },
        {
          "id": "37",
          "brand": "A",
          "category": "가방",
          "price": 2000
        },
        {
          "id": "49",
          "brand": "D",
          "category": "모자",
          "price": 1500
        },
        {
          "id": "63",
          "brand": "I",
          "category": "양말",
          "price": 1700
        },
        {
          "id": "69",
          "brand": "F",
          "category": "액세서리",
          "price": 1900
        }
      ]
    }
  }
}
```
* 화면

<img width="596" alt="구현1" src="https://github.com/user-attachments/assets/afca6132-d0cb-4edc-96b5-c6c6991a1897">

### 구현 2) 단일 브랜드로 모든 카테고리 상품을 구매 할 때 최저 가격 브랜드, 상품 리스트, 총액 조회
* Get http://localhost:8080/graphql
* 요청
```graphql
query LowPriceBrandGoods {
  lowPriceBrandGoods {
    totalPrice
    brand
    goods {
      id
      category
      price
    }
  }
}
```
* 응답
```json
{
  "data": {
    "lowPriceBrandGoods": {
      "totalPrice": 36100,
      "brand": "D",
      "goods": [
        {
          "id": "4",
          "category": "상의",
          "price": 10100
        },
        {
          "id": "13",
          "category": "아우터",
          "price": 5100
        },
        {
          "id": "22",
          "category": "바지",
          "price": 3000
        },
        {
          "id": "31",
          "category": "스니커즈",
          "price": 9500
        },
        {
          "id": "40",
          "category": "가방",
          "price": 2500
        },
        {
          "id": "49",
          "category": "모자",
          "price": 1500
        },
        {
          "id": "58",
          "category": "양말",
          "price": 2400
        },
        {
          "id": "67",
          "category": "액세서리",
          "price": 2000
        }
      ]
    }
  }
}
```
* 화면

<img width="596" alt="구현2" src="https://github.com/user-attachments/assets/c72d256f-5b25-468a-b872-93ac704031b6">

### 구현 3) 카테고리 이름으로 최저, 최고 가격 브랜드, 상품 가격 조회
* Get http://localhost:8080/graphql
* 요청
```graphql
query GetCategoryHighAndLowPriceGoods {
  categoryHighAndLowPriceGoods(category: "상의") {
    category
    highPriceGoods {
      id
      brand
      price
    }
    lowPriceGoods {
      id
      brand
      price
    }
  }
}
```
* 응답
```json
{
  "data": {
    "categoryHighAndLowPriceGoods": {
      "category": "상의",
      "highPriceGoods": [
        {
          "id": "9",
          "brand": "I",
          "price": 11400
        }
      ],
      "lowPriceGoods": [
        {
          "id": "3",
          "brand": "C",
          "price": 10000
        }
      ]
    }
  }
}
```
* 화면

<img width="596" alt="구현3" src="https://github.com/user-attachments/assets/d8067565-5c8d-4d3b-a1a7-e36d537ff837">

### 상품 상세보기 (고객 상태에서 상품 리스트 클릭)
* 화면

<img width="596" alt="상품상세" src="https://github.com/user-attachments/assets/25fcd85d-db76-4e89-abd7-2d3a61dd2449">

### 운영자 기능(상품 추가, 수정, 삭제)을 위한 사용자 선택

* 화면

<img width="596" alt="사용자선택" src="https://github.com/user-attachments/assets/83587784-4cc1-42f7-b4f0-504d0bbb0f70">

운영자 화면 (우측 상단에 운영자 아이콘 변경과 상품 추가 버튼 노출)

* 화면

<img width="596" alt="운영자 메뉴" src="https://github.com/user-attachments/assets/c2b0043a-3c1b-4426-af65-b5b73999daaf">

### 구현 4) 상품 등록 (우측 상단 상품 추가 버튼 클릭)
* Get http://localhost:8080/graphql
* id가 0인 경우 등록
* 요청
```graphql
mutation SaveGoods{
    saveGoods(goodsInput: {id: 0, brand: "Z", category: "상의", price: 9999}) {
        id
        brand
    }
}
```
* 응답
```json
{
  "data": {
    "saveGoods": {
      "id": "75",
      "brand": "Z",
      "category": "상의",
      "price": 9999
    }
  }
}
```
* 화면

<img width="596" alt="상품등록" src="https://github.com/user-attachments/assets/315d1139-606f-4e16-ad1d-42858120191c">

* 조건 충족 시 등록 버튼 활성화 (입력 제한은 고려하지 않고 입력 여부만 체크)

<img width="596" alt="상품등록활성화" src="https://github.com/user-attachments/assets/35edbabc-c352-401c-b821-1d1e2250b07a">

### 구현 4) 상품 수정 / 삭제 (운영자 상태에서 상품 리스트 클릭)
* Get http://localhost:8080/graphql
* id가 0이 아닌 경우
* 존재하는 아이디가 아닌 경우 에러 발생
* 수정 요청
```graphql
mutation SaveGoods{
    saveGoods(goodsInput: {id: 10, brand: "Z", category: "상의", price: 9999}) {
        id
        brand
    }
}
```
* 응답
```json
{
  "data": {
    "saveGoods": {
      "id": "75",
      "brand": "Z",
      "category": "상의",
      "price": 9999
    }
  }
}
```
* 삭제 요청
```graphql
mutation DeleteGoods {
  deleteGoods(id: 10) {
    id
    brand
    category
    price
	}
}
```
* 응답
```json
{
  "data": {
    "deleteGoods": {
      "id": "75",
      "brand": "Z",
      "category": "상의",
      "price": 9999
    }
  }
}
```
* 상품 수정 화면
<img width="596" alt="상품수정" src="https://github.com/user-attachments/assets/fa18ebd4-7ee2-4aeb-8eb2-7a705acc922f">

* 수정 사항 발생 시 수정 버튼 활성화, 삭제 버튼은 항상 활성화

<img width="596" alt="상품수정활성화" src="https://github.com/user-attachments/assets/d793b0f2-9105-48b3-a7d6-85f0e3b54d3a">

## 에러처리

```json
{
  "errors": [
    {
      "message": "존재하지 않는 상품입니다.",
      "locations": [
        {
          "line": 4,
          "column": 3
        }
      ],
      "path": [
        "deleteGoods"
      ],
      "extensions": {
        "classification": "NOT_EXIST_GOODS"
      }
    }
  ],
  "data": {
    "deleteGoods": null
  }
}
```
