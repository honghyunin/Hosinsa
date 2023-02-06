package commerce.hosinsa.domain.product.dto

class RegistrationProductDto(
    val name: String,
    val price: Int,
    val category: String,
    val gender: Char,
    val stock: Int,
    val brandName: String
)
