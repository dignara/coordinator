package com.musinsa.coordinator.repository

import com.musinsa.coordinator.entity.Goods
import com.musinsa.coordinator.model.BrandPrice
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface GoodsRepository : JpaRepository<Goods, Long> {
    @Query("SELECT g FROM (SELECT category AS category, min(price) AS price FROM Goods GROUP BY category) AS x INNER JOIN Goods AS g ON g.category = x.category AND g.price = x.price")
    fun findAllByLowPriceByCategory(): Iterable<Goods>

    @Query("SELECT new com.musinsa.coordinator.model.BrandPrice(b.brand, b.brandPrice) FROM (SELECT brand AS brand, SUM(price) AS brandPrice FROM Goods GROUP BY brand) b")
    fun findAllByBrandTotalPrice(sort: Sort): Iterable<BrandPrice>

    fun findAllByBrand(brand: String): Iterable<Goods>

    @Query("SELECT g FROM (SELECT g.category AS category, MIN(g.price) AS minPrice FROM Goods g WHERE g.category = ?1 GROUP BY g.category) as x join Goods as g on g.category = x.category and g.price = x.minPrice")
    fun findAllByCategoryLowPrice(category: String): Iterable<Goods>

    @Query("SELECT g FROM (SELECT g.category AS category, MAX(g.price) AS maxPrice FROM Goods g WHERE g.category = ?1 GROUP BY g.category) as x join Goods as g on g.category = x.category and g.price = x.maxPrice")
    fun findAllByCategoryHighPrice(category: String): Iterable<Goods>
}