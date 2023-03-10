package commerce.hosinsa.domain.repository

import commerce.hosinsa.entity.coupon.CouponMember
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CouponMemberRepository: JpaRepository<CouponMember, Int>