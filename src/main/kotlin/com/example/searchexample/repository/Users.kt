package com.example.searchexample.repository

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("USERS")
data class Users(@Id var id : Int, val name : String )
