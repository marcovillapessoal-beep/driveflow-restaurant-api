package br.com.branco.spring_boot_essentials.database.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "table_orders")
public class TableOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int tableNumber;
    private String attendantName;
    private String status;
    private Instant createdAt;

    @ElementCollection
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderItem> items = new ArrayList<>();

    // Constructor
    public TableOrder() {}

    public TableOrder(int tableNumber, String attendantName) {
        this.tableNumber = tableNumber;
        this.attendantName = attendantName;
        this.status = "ABERTO";
        this.createdAt = Instant.now();
    }

    public BigDecimal getTotalValue() {
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item : items) {
            total = total.add(item.getTotalItemPrice());
        }
        return total;
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
    }

    public void removeItem(OrderItem item) {
        this.items.remove(item);
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getTableNumber() { return tableNumber; }
    public void setTableNumber(int tableNumber) { this.tableNumber = tableNumber; }

    public String getAttendantName() { return attendantName; }
    public void setAttendantName(String attendantName) { this.attendantName = attendantName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
}
