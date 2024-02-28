package com.example.searchexample.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface JpaUserRepository : JpaRepository<Users, String>, JpaSpecificationExecutor<Users> {
    fun findUserByName( name : String ) : List<Users>
//    @Query("SELECT name FROM users WHERE name regexp :namePattern")
//    fun findUsersByNameRegex(namePattern: String): List<Users>
}