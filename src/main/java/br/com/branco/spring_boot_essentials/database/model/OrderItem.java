package br.com.branco.spring_boot_essentials.database.model;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class OrderItem {

    private String productName;
    private int quantity;

    private BigDecimal unitPrice;
    private String observation;

    public OrderItem() {}

    public OrderItem(String productName, int quantity, BigDecimal unitPrice, String observation) {
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.observation = observation;
    }

    public BigDecimal getTotalItemPrice() {
        if (unitPrice == null) return BigDecimal.ZERO;
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

    public String getObservation() { return observation; }
    public void setObservation(String observation) { this.observation = observation; }
}
