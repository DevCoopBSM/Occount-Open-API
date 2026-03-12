# occount-open-api

데브쿠프팀 내부용 배치 Open API 서버입니다.  
DB에 있는 데이터를 JSON 형태로 조회하며, 현재는 상품 마스터와 키오스크 영수증 데이터를 제공합니다.

## 개요

- 인증 방식: `X-API-Key` 헤더
- 문서 경로: `/swagger-ui.html`, `/v3/api-docs`
- 조회 방식:
  `page`가 있으면 페이지 조회
  `page`가 없으면 전체 조회

## 기술 스택

- Java 21
- Spring Boot 4
- Spring MVC
- Spring Data JPA
- QueryDSL
- MySQL
- springdoc-openapi

## 인증

데이터 API 호출 시 `X-API-Key` 헤더가 필요합니다.

## 응답 형식

모든 조회 API는 객체 래퍼 없이 JSON 배열을 바로 반환합니다.

```json
[
  {
    "itemCode": "880001",
    "itemName": "Milk",
    "itemCategory": "DRINK",
    "itemPrice": 2500
  }
]
```

동작 규칙:

- `page`를 보내면 해당 페이지 범위만 조회합니다.
- `page`를 보내지 않으면 전체 데이터를 조회합니다.

## Swagger

브라우저에서 아래 경로로 접속하면 문서를 확인할 수 있습니다.

- `/swagger-ui.html`
- `/v3/api-docs`

Swagger는 문서 확인용이며, 실제 호출은 `X-API-Key`를 포함한 클라이언트에서 사용하는 것을 기준으로 합니다.
