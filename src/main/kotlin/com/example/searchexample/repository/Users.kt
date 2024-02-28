package com.example.searchexample.repository

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name="users")
data class Users(
    @Id
    @GeneratedValue
    var id : Int=0,
    val name : String=""
)