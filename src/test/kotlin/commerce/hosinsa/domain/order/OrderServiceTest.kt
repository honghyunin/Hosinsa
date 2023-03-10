package commerce.hosinsa.domain.order

import commerce.hosinsa.domain.member.MEMBER_IDX
import commerce.hosinsa.domain.product.PRODUCT
import commerce.hosinsa.domain.product.PRODUCT_IDX
import commerce.hosinsa.global.exception.CustomException
import commerce.hosinsa.global.exception.ErrorCode.*
import commerce.hosinsa.global.extension.toOrder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.verify
import java.util.*

class OrderServiceTest : DescribeSpec({

    describe("orderRequest") {

        context("올바른 정보가 입력되면") {
            every { orderRepository.save(ORDER_REQUEST_DTO.toOrder(currentUserUtil.currentUser!!)) } returns ORDER
            every { productRepository.findByIdx(PRODUCT_IDX) } returns PRODUCT
            every { orderService.orderRequest(ORDER_REQUEST_DTO) } just Runs

            orderService.orderRequest(ORDER_REQUEST_DTO)

            it("새로운 주문이 생성된다") {
                verify(exactly = 1) { orderService.orderRequest(ORDER_REQUEST_DTO) }
            }

            every { orderProductRepository.save(ORDER_PRODUCT) } returns ORDER_PRODUCT

            val orderProduct = orderProductRepository.save(ORDER_PRODUCT)

            it("상품의 재고 업데이트가 성공한다") {
                ORDER_PRODUCT shouldBe orderProduct
            }

        }

        context("상품이 존재하지 않을 경우") {
            every { orderRepository.save(any()) } returns ORDER
            every { productRepository.findByIdx(PRODUCT_IDX) } returns null
            every { orderService.orderRequest((ORDER_REQUEST_DTO)) } throws CustomException(PRODUCT_NOT_FOUND)

            it("Product Not Found Exception이 발생한다") {
                shouldThrow<CustomException> { orderService.orderRequest((ORDER_REQUEST_DTO)) }
            }
        }

        context("상품의 개수가 null일 경우") {
            if (EMPTY_PRODUCT_QUANTITIES_ORDER_REQUEST_DTO.productQuantities[PRODUCT_IDX] == null)
                every { orderService.orderRequest(EMPTY_PRODUCT_QUANTITIES_ORDER_REQUEST_DTO) } throws CustomException(
                    PRODUCT_QUANTITY_NOT_FOUND
                )

            it("Product Quantity Not Found Exception이 발생한다") {
                shouldThrow<CustomException> { orderService.orderRequest(EMPTY_PRODUCT_QUANTITIES_ORDER_REQUEST_DTO) }
            }
        }
    }

    describe("getOrder") {

        context("올바른 정보가 입력되면") {
            every { memberRepository.existsById(MEMBER_IDX) } returns true
            every { orderProductCustomRepository.findGetOrderResponsesByMemberIdx(MEMBER_IDX) } returns GET_ORDER_RESPONSE_LIST
            every { orderService.getOrder(MEMBER_IDX) } returns GET_ORDER_RESPONSE_LIST

            val orders = orderService.getOrder(MEMBER_IDX)

            it("주문 조회에 성공한다") {
                GET_ORDER_RESPONSE_LIST shouldBe orders
            }
        }

        context("존재하지 않는 회원이 입력되면") {
            every { memberRepository.existsById(MEMBER_IDX) } returns false
            every { orderService.getOrder(MEMBER_IDX) } throws CustomException(MEMBER_NOT_FOUND)

            it("Member Not Found Exception이 발생한다") {
                shouldThrow<CustomException> { orderService.getOrder(MEMBER_IDX) }
            }
        }

        context("조회된 주문이 없을 경우") {
            every { orderProductCustomRepository.findGetOrderResponsesByMemberIdx(MEMBER_IDX) } returns mutableListOf()
            every { orderService.getOrder(MEMBER_IDX) } throws CustomException(ORDER_NOT_FOUND)

            it("Order Not Found Exception이 발생한다") {
                shouldThrow<CustomException> { orderService.getOrder(MEMBER_IDX) }
            }
        }
    }

    describe("cancelOrder") {

        context("올바른 정보가 입력되면") {
            every { orderRepository.findById(ORDER_IDX) } returns Optional.of(ORDER)
            every { orderService.cancelOrder(ORDER_IDX) } just Runs

            orderService.cancelOrder(ORDER_IDX)
            it("주문이 취소된다") {
                verify(exactly = 1) { orderService.cancelOrder(ORDER_IDX) }
            }
        }

        context("로그인 되어있지 않을 경우") {
            every { orderService.cancelOrder(ORDER_IDX) } throws CustomException(FORBIDDEN)

            it("Unauthorized Access Exception이 발생한다") {
                shouldThrow<CustomException> { orderService.cancelOrder(ORDER_IDX) }
            }
        }

        context("주문이 없을 경우") {
            every { orderRepository.findById(ORDER_IDX) } returns Optional.empty()
            every { orderService.cancelOrder(ORDER_IDX) } throws CustomException(ORDER_NOT_FOUND)

            it("Not Found Order Exception이 발생한다") {
                shouldThrow<CustomException> { orderService.cancelOrder(ORDER_IDX) }
            }
        }
    }
})