package com.example.searchexample.repository

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.slf4j.LoggerFactory
import org.springframework.data.jpa.domain.Specification

class TestSpecification<T>(
    private val attribute: String,
    private val regex: String
) : Specification<T>{
    private val log = LoggerFactory.getLogger(this.javaClass)
     override fun toPredicate(
        root: Root<T>,
        query: CriteriaQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        val expression = criteriaBuilder.function(
            "REGEXP_LIKE",
            Boolean::class.java,
            root.get<String>(attribute),
            criteriaBuilder.literal(regex)
        )
        return criteriaBuilder.equal(expression, true)
    }
}