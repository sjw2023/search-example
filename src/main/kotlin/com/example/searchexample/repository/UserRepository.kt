package com.example.searchexample.repository

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<Users, String> {
    fun findUserByName( name : String ) : List<Users>
    @Query("SELECT * FROM users WHERE name regexp :namePattern")
    fun findUsersByNameRegex(namePattern: String): List<Users>
}