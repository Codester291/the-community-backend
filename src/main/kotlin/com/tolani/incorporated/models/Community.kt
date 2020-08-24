package com.tolani.incorporated.models

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@Entity
@Table(name = "community")
data class Community(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long,
        @Column(nullable = false)
        var name: String,
        @Column(nullable = false)
        var cause:String,
        @ManyToMany(mappedBy = "community", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
        @JsonIgnoreProperties
        @JsonBackReference
        var citizen: List<Citizen>? = mutableListOf()
) {
        constructor() : this (1L, "", "", null)
}