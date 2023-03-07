package commerce.hosinsa.global.extension

import commerce.hosinsa.domain.dto.coupon.SaveCouponDto
import commerce.hosinsa.entity.brand.Brand
import commerce.hosinsa.entity.coupon.Coupon

fun SaveCouponDto.settingAt() {
    expiredAt = assignedAt.plusDays(14)
}

fun SaveCouponDto.toCoupon(brand: Brand?) = Coupon(
    name = name,
    discount = discount,
    brand = brand
)