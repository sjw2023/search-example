package com.example.searchexample.controller

import com.example.searchexample.dto.SearchRequestDto
import com.example.searchexample.dto.SearchResponseDto
import com.example.searchexample.service.SearchService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SearchController (
    val searchService: SearchService
){
    @GetMapping
    fun searchWithName( @RequestBody searchRequestDto: SearchRequestDto ) : ResponseEntity<SearchResponseDto>{
        val result = searchService.searchWithName( searchRequestDto )
        println(result)
        return ResponseEntity.ok().body(SearchResponseDto())
    }
    @PostMapping("/regex")
    fun getRegex( @RequestBody searchRequestDto: SearchRequestDto) : ResponseEntity<List<com.example.searchexample.repository.Users>>{
        val result = searchService.getRegex(searchRequestDto)
        println("result : " + result)
        return ResponseEntity.ok().body(result)
    }
}