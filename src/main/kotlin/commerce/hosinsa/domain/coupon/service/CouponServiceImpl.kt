package commerce.hosinsa.domain.coupon.service

import commerce.hosinsa.domain.brand.repository.BrandRepository
import commerce.hosinsa.domain.coupon.dto.SaveCouponDto
import commerce.hosinsa.domain.coupon.repository.CouponRepository
import commerce.hosinsa.global.config.utils.settingAt
import commerce.hosinsa.global.config.utils.toCoupon
import commerce.hosinsa.global.exception.CustomException
import commerce.hosinsa.global.exception.ErrorCode.BRAND_NOT_FOUND
import org.springframework.stereotype.Service

@Service
class CouponServiceImpl(
    private val brandRepository: BrandRepository,
    private val couponRepository: CouponRepository
) : CouponService {
    override fun saveCoupon(saveCouponDto: SaveCouponDto) {
        saveCouponDto.also {
            it.settingAt()

            if (saveCouponDto.brandName != null)
                couponRepository.save(saveCouponDto.toCoupon(findBrandByName(saveCouponDto.brandName)))

            couponRepository.save(saveCouponDto.toCoupon(null))
        }
    }

    private fun findBrandByName(brandName: String) = brandRepository.findByName(brandName)
        ?: throw CustomException(BRAND_NOT_FOUND)
}