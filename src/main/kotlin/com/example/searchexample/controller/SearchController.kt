package com.example.searchexample.controller

import com.example.searchexample.dto.SearchRequestDto
import com.example.searchexample.dto.SearchResponseDto
import com.example.searchexample.service.SearchService
import org.slf4j.LoggerFactory
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.regex.Pattern.matches

@RestController
class SearchController(
    val searchService: SearchService
) {
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    @GetMapping
    fun searchWithName(@RequestBody searchRequestDto: SearchRequestDto): ResponseEntity<SearchResponseDto> {
        val result = searchService.searchWithName(searchRequestDto)
        log.debug("Result {}", result)
        return ResponseEntity.ok().body(SearchResponseDto())
    }

    @PostMapping("/regex")
    fun getRegex(@RequestBody searchRequestDto: SearchRequestDto): ResponseEntity<List<com.example.searchexample.repository.Users>> {
        val result = if (!searchRequestDto.name.all { it.toString().matches(Regex("[ㄱ-ㅎ]")) }) searchService.getRegex(searchRequestDto)
                    else searchService.getInitialRegex(searchRequestDto)
        log.debug("Result {}", result)
        return ResponseEntity.ok().body(result)
    }
}