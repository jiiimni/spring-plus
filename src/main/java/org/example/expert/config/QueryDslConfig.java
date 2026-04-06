package org.example.expert.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 수정: QueryDSL 쿼리를 작성할 때 사용할 JPAQueryFactory를 스프링 빈으로 등록
 * 개념 정리: JPAQueryFactory는 QueryDSL의 쿼리 작성 도구이고, 내부적으로 EntityManager를 사용해 DB 조회를 수행함
 */
@Configuration
public class QueryDslConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
