package com.tolani.incorporated.models

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonManagedReference
import lombok.Data
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "citizen")
@Data
data class Citizen (
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long,
        @Column(nullable = false)
        var name: String,
        @Column(nullable = false)
        var email: String,
        @Column(nullable = false)
        var username: String,
        @Column(nullable = false)
        var password: String,
        @OneToMany(mappedBy = "citizen")
        var topic: List<Topic>? = mutableListOf(),
        @ManyToMany(cascade = [CascadeType.ALL])
        @JoinTable(name = "citizen_community",
                joinColumns = [JoinColumn(name = "citizen_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "community_id", referencedColumnName = "id")])
        @JsonIgnoreProperties
        @JsonManagedReference
        var community: List<Community>? = mutableListOf(),
        @CreationTimestamp
        @Temporal(TemporalType.TIMESTAMP)
        @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
        var createdAt: Date,
        @UpdateTimestamp
        @Temporal(TemporalType.TIMESTAMP)
        @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
        var updatedAt: Date
) {
        constructor() : this(1L, "", "", "", "",null,null, Date(), Date())
}