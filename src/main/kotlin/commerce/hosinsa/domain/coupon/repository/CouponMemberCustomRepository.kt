package commerce.hosinsa.domain.coupon.repository

import commerce.hosinsa.domain.coupon.entity.CouponMember

interface CouponMemberCustomRepository {
    fun findByExpiredCoupon(): MutableList<CouponMember>
}