# 🛒 E-Commerce System (portfolio_ecommerce)

> 상품, 주문, 환불 등 **이커머스 핵심 도메인 로직**을 중심으로  
> 실무 수준의 **RESTful API 설계**, **트랜잭션 관리**, **QueryDSL 기반 검색**,  
> **테스트 코드 작성**을 경험한 백엔드 프로젝트입니다.

---

## 📆 프로젝트 개요

- **역할:** 백엔드 개발 (단독 설계 및 구현)  
- **목표:**  
  - 커머스 서비스의 핵심 기능(상품/주문/환불)을 도메인 중심으로 설계  
  - QueryDSL을 활용한 동적 조회 및 통계 API 구현  
  - 트랜잭션 기반의 안전한 주문·환불 처리 로직 구축  
  - 예외 처리 및 로깅 시스템을 통한 안정적인 서버 운영 구조 설계  

---

## ⚙️ 기술 스택

| 구분 | 기술 |
|------|------|
| **Language** | Java 17 |
| **Framework** | Spring Boot 3.4.1, Spring Data JPA |
| **ORM/Query** | Hibernate, QueryDSL |
| **Database** | MySQL |
| **Build Tool** | Gradle |
| **Test** | JUnit5, MockMVC |
| **Tool** | IntelliJ IDEA, Postman |

---

## 🧩 주요 기능

| 도메인 | 주요 기능 |
|---------|------------|
| **상품(Product)** | 상품 등록, 수정, 삭제, 상세/목록 조회 (QueryDSL 검색 지원) |
| **카테고리(Category)** | 카테고리 CRUD, 연관 상품 검증 및 트랜잭션 삭제 처리 |
| **주문(Order)** | 주문 생성, 상태 변경, 취소, 트랜잭션 기반 재고 감소 처리 |
| **환불(Refund)** | 환불 요청/승인/거절, 부분 환불, 재고 복원 트랜잭션 |
| **할인(Discount)** | 정액/정률 할인 기능, 관리자 전용 할인 설정 API |
| **통계(Dashboard)** | QueryDSL 기반 주문/환불 통계 조회 (선택 구현) |
| **로깅/예외처리** | Spring AOP 기반 API 요청/응답 로깅 및 전역 예외 처리 |




