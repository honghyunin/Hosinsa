package commerce.hosinsa.entity.cart

import commerce.hosinsa.entity.BaseTimeEntity
import commerce.hosinsa.entity.member.Member
import commerce.hosinsa.entity.product.Product
import javax.persistence.*

@Entity
class Cart(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member.id", nullable = false)
    val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product.id", nullable = false)
    val product: Product

): BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null

    @Column(name = "is_delete", nullable = false)
    val isDelete: Boolean = false
}