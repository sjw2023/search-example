package com.example.searchexample.service

import com.example.searchexample.dto.SearchRequestDto
import com.example.searchexample.repository.JpaUserRepository
import com.example.searchexample.repository.TestSpecification
import com.example.searchexample.repository.Users
import com.example.searchexample.util.GetRegExpOptions
import com.example.searchexample.util.RegexGenerator
import org.apache.catalina.User
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class SearchService(
    val userRepository: JpaUserRepository,
    val regexGenerator: RegexGenerator
) {
    private val log = LoggerFactory.getLogger(this.javaClass)

    fun searchWithName(searchRequestDto: SearchRequestDto): List<Users> {
        return userRepository.findUserByName(searchRequestDto.name)
    }

    fun getRegex(searchRequestDto: SearchRequestDto): List<Users> {
        return findByRegexSpec(regexGenerator.getRegExp(searchRequestDto.name).pattern)
    }

    fun getInitialRegex(searchRequestDto: SearchRequestDto): List<Users> {
        return findByRegexSpec(
            regexGenerator.getRegExp(
                searchRequestDto.name,
                GetRegExpOptions(
                    true,
                    false,
                    false,
                    false,
                    true,
                    false,
                    false,
                    false,
                )
            ).pattern
        )
    }

    fun findByRegexSpec( regex : String ): List<Users>{
        val specification = TestSpecification<Users>("name", regex)
        val result = userRepository.findAll(specification)
        log.debug("result {}", result)
        return userRepository.findAll(specification)
    }
}