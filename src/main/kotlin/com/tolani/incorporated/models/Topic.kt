package com.tolani.incorporated.models

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonManagedReference
import lombok.Data
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Data
data class Topic (

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long,

        @Column(columnDefinition = "VARCHAR(1000)", nullable = false)
        var title: String,

        @Column(columnDefinition = "LONGTEXT", nullable = false)
        var content: String,

        @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
        @JoinColumn(name = "citizen_id")
        @JsonBackReference
        var citizen: Citizen?,

        @CreationTimestamp
        @Temporal(TemporalType.TIMESTAMP)
        @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
        var createdAt: Date,

        @UpdateTimestamp
        @Temporal(TemporalType.TIMESTAMP)
        @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
        var updatedAt: Date
) {
        constructor () : this(1L, "", "", null, Date(), Date())
}