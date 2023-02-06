package commerce.hosinsa.domain.product.service

import commerce.hosinsa.domain.product.dto.RegistrationProductDto
import commerce.hosinsa.domain.product.dto.UpdateProductDto

interface ProductService {
    fun registrationProduct(registrationProductDto: RegistrationProductDto)
    fun updateProduct(updateProductDto: UpdateProductDto)
    fun updateIsSoldOut(productName: String)
}