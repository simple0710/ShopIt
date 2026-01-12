package com.shopit.shopit.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_option_id")
    private ProductOption productOption;

    @Column(
            name = "updated_at",
            nullable = false,
            columnDefinition = "TIMESTAMP(0)"
    )
    private LocalDateTime updatedAt;

    public static Inventory create(Integer quantity, ProductOption productOption) {
        Inventory inventory = new Inventory();
        inventory.quantity = quantity;
        inventory.productOption = productOption;
        return inventory;
    }

    @PrePersist
    private void prePersist() {
        this.updatedAt = LocalDateTime.now();
    }
}
