package com.musinsa.coordinator.service

import com.musinsa.coordinator.entity.Goods
import com.musinsa.coordinator.exception.MusinsaException
import com.musinsa.coordinator.model.BrandPrice
import com.musinsa.coordinator.model.GoodsInput
import com.musinsa.coordinator.repository.GoodsRepository
import com.musinsa.coordinator.service.GoodsServiceImpl.Companion.ALIAS_BRAND_PRICE
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Sort
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@SpringBootTest(classes = [ModelMapper::class])
class GoodsServiceTest(
    @Autowired
    private val modelMapper: ModelMapper,
    private val goodsRepository: GoodsRepository = mockk()
) : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    given("카테고리 별 최저가격 브랜드 상품 리스트 요청") {
        val goodsService = withContext(Dispatchers.IO) {
            GoodsServiceImpl(modelMapper, goodsRepository)
        }

        every { goodsRepository.findAllByLowPriceByCategory() } returns lowPriceByCategoryGoods
        `when`("동일 카테고리 상품이 하나 더 있을 경우") {
            val result = goodsService.getLowPriceGoodsByCategory()
            then("중복을 제거하고 상품의 개수는 8개가 되어야 한다.") {
                result.goods.count() shouldBe 8
            }
            then("중복을 제거하고 최저가격 합이 8000이 되어야 한다.") {
                result.totalPrice shouldBe BigDecimal(8000)
            }
        }
    }

    given("최저 가격 브랜드 상품 리스트 요청") {
        val goodsService = withContext(Dispatchers.IO) {
            GoodsServiceImpl(modelMapper, goodsRepository)
        }
        every { goodsRepository.findAllByBrandTotalPrice(Sort.by(ALIAS_BRAND_PRICE)) } returns brandTotalPrice
        every { goodsRepository.findAllByBrand(brandTotalPrice.first().brand) } returns brandGoods
        `when`("A브랜드가 총합이 최저 가격이고 A브랜드의 상품을 모두 요청했을 때") {
            val result = goodsService.getLowPriceBrandGoods()
            then("상품의 개수는 카테고리 개수와 동일한 8개여야 한다.") {
                result.goods.count() shouldBe 9
            }
            then("최저 가격의 합은 100 이여야 한다  ") {
                result.getTotalPrice() shouldBe BigDecimal(120)
            }
        }
    }

    given("상품 저장 요청") {
        val goodsService = withContext(Dispatchers.IO) {
            GoodsServiceImpl(modelMapper, goodsRepository)
        }

        val existGoods = GoodsInput(1, "A", "상의", BigDecimal(1000))
        every { goodsRepository.findById(existGoods.id) } returns Optional.of(goods)
        every { goodsRepository.save(any()) } returns goods
        `when`("같은 아이디의 상품이 있는 경우") {
            val result = goodsService.saveGoods(existGoods)
            then("기존 상품의 정보를 업데이트 한다.") {
                result.id shouldBe existGoods.id
                result.brand shouldBe existGoods.brand
            }
        }
        val newGoods = GoodsInput(0, "A", "상의", BigDecimal(1000))
        every { goodsRepository.findById(newGoods.id) } returns Optional.empty()
        every { goodsRepository.save(any()) } returns goods
        `when`("아이디가 0인 상품인 경우") {
            val result = goodsService.saveGoods(newGoods)
            then("새로운 상품으로 등록된다.") {
                result.id shouldNotBe newGoods.id
            }
        }
    }

    given("카테고리 이름으로 최저, 최고 가격 상품 요청") {
        val goodsService = withContext(Dispatchers.IO) {
            GoodsServiceImpl(modelMapper, goodsRepository)
        }

        val category = "상의"
        every { goodsRepository.findAllByCategoryHighPrice(category) } returns categoryHighPriceGoods
        every { goodsRepository.findAllByCategoryLowPrice(category) } returns categoryLowPriceGoods
        `when`("'상의' 카테고리로 요청할 때") {
            val result = goodsService.getCategoryHighAndLowPriceGoods(category)
            then("입력한 카테고리 상품이 나와야 한다.") {
                result.category shouldBe category
                result.highPriceGoods.count() shouldBe 1
                result.lowPriceGoods.count() shouldBe 2
            }
        }
    }

    given("상품의 아이디로 삭제 요청") {
        val goodsService = withContext(Dispatchers.IO) {
            GoodsServiceImpl(modelMapper, goodsRepository)
        }

        val id = 1L
        every { goodsRepository.findById(id) } returns Optional.empty()
        `when`("id가 1인 상품이 없는경우") {
            then("'존재하지 않는 상품입니다.' 에러가 발생해야 한다.") {
                shouldThrow<MusinsaException> {
                    goodsService.delete(id)
                }.apply {
                    message shouldNotBe "존재하지 않는 상품입니다."
                }
            }
        }

        every { goodsRepository.findById(id) } returns Optional.of(goods)
        every { goodsRepository.deleteById(id) } returns Unit
        `when`("'상의' 카테고리로 요청할 때") {
            val result = goodsService.delete(id)
            then("삭제한 상품의 정보를 반환해야 한다.") {
                result.id shouldBe id
            }
        }
    }
}) {
    companion object {
        private val now = LocalDateTime.now()

        val lowPriceByCategoryGoods = listOf(
            Goods(1, "상의", "A", BigDecimal(1000), now, now),
            Goods(2, "아우터", "B", BigDecimal(1000), now, now),
            Goods(3, "바지", "C", BigDecimal(1000), now, now),
            Goods(4, "스니커즈", "D", BigDecimal(1000), now, now),
            Goods(5, "가방", "E", BigDecimal(1000), now, now),
            Goods(6, "가방", "E", BigDecimal(1000), now, now),
            Goods(7, "모자", "F", BigDecimal(1000), now, now),
            Goods(8, "양말", "G", BigDecimal(1000), now, now),
            Goods(9, "액세서리", "H", BigDecimal(1000), now, now)
        )

        private val brandTotalPrice = listOf(
            BrandPrice("A", BigDecimal(100)),
            BrandPrice("B", BigDecimal(200)),
            BrandPrice("C", BigDecimal(300)),
            BrandPrice("D", BigDecimal(400)),
            BrandPrice("E", BigDecimal(500)),
            BrandPrice("F", BigDecimal(600)),
            BrandPrice("G", BigDecimal(700)),
            BrandPrice("H", BigDecimal(800)),
            BrandPrice("I", BigDecimal(900)),
        )

        private val brandGoods = listOf(
            Goods(1, "상의", "A", BigDecimal(10), now, now),
            Goods(2, "아우터", "A", BigDecimal(10), now, now),
            Goods(3, "바지", "A", BigDecimal(10), now, now),
            Goods(4, "스니커즈", "A", BigDecimal(10), now, now),
            Goods(5, "가방", "A", BigDecimal(20), now, now),
            Goods(6, "가방", "A", BigDecimal(10), now, now),
            Goods(7, "모자", "A", BigDecimal(10), now, now),
            Goods(8, "양말", "A", BigDecimal(20), now, now),
            Goods(9, "액세서리", "A", BigDecimal(20), now, now)
        )

        private val goods = Goods(1, "상의", "A", BigDecimal(1000), now, now)

        private val categoryLowPriceGoods = listOf(
            Goods(1, "상의", "A", BigDecimal(100), now, now),
            Goods(2, "상의", "B", BigDecimal(100), now, now)
        )

        private val categoryHighPriceGoods = listOf(
            Goods(1, "상의", "A", BigDecimal(1000), now, now)
        )
    }
}
