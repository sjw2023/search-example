package com.example.searchexample

import com.example.searchexample.service.SearchService
import com.example.searchexample.util.RegexGenerator
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SearchExampleApplication

fun main(
	args: Array<String>
) {
	val regexGenerator : RegexGenerator = RegexGenerator()
	print(regexGenerator.getRegExp("ㅅ"))

	runApplication<SearchExampleApplication>(*args)
}
