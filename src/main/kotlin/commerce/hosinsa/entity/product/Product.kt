package commerce.hosinsa.entity.product

import commerce.hosinsa.entity.BaseTimeEntity
import commerce.hosinsa.entity.brand.Brand
import javax.persistence.*

@Entity
class Product(
    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "price", nullable = false)
    var price: Int,

    @Column(name = "category", nullable = false)
    var category: String,

    @Column(name = "gender", nullable = false)
    var gender: Char,

    @ElementCollection(targetClass = ProductSize::class) // Use a set to store available sizes
    @Enumerated(EnumType.STRING)
    var size: MutableSet<ProductSize>,

    @ElementCollection // Use a set to store available sizes
    var color: MutableSet<String>,

    @Column(name = "stock", nullable = false)
    var stock: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand.idx")
    val brand: Brand
) : BaseTimeEntity() {
    @Column(name = "idx")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idx: Int? = null

    @Column(name = "is_solud_out")
    var isSoldOut: Boolean = false

    @Column(name = "is_delete", nullable = false)
    var isDelete: Boolean = false
}