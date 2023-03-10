package commerce.hosinsa.domain.dto.coupon

import java.time.LocalDateTime

class SaveCouponDto(
    val name: String,
    val discount: Byte,
    val status: Char,
    val brandName: String? = null
) {
    var assignedAt: LocalDateTime = LocalDateTime.now()
    var expiredAt: LocalDateTime? = null
}