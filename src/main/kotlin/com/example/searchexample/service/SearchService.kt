package com.example.searchexample.service

import com.example.searchexample.dto.SearchRequestDto
import com.example.searchexample.repository.Users
import com.example.searchexample.repository.UserRepository
import com.example.searchexample.util.RegexGenerator
import org.springframework.stereotype.Service

@Service
class SearchService (
    val userRepository: UserRepository,
    val regexGenerator: RegexGenerator
){

    fun searchWithName( searchRequestDto : SearchRequestDto ) : List<Users> {
        return userRepository.findUserByName( searchRequestDto.name )
    }
    fun getRegex(searchRequestDto: SearchRequestDto) : List<Users>{
        return userRepository.findUsersByNameRegex(regexGenerator.getRegExp(searchRequestDto.name).pattern)
    }
}