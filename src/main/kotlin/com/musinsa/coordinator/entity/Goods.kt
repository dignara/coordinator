package com.musinsa.coordinator.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "goods", indexes = [Index(columnList = "category"), Index(columnList = "brand")])
class Goods(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column
    var category: String,

    @Column
    var brand: String,

    @Column
    var price: BigDecimal,

    @CreatedDate
    var createdAt: LocalDateTime?,

    @LastModifiedDate
    var updatedAt: LocalDateTime?
)