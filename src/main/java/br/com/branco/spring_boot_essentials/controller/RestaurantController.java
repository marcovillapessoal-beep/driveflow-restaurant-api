package br.com.branco.spring_boot_essentials.controller;

import br.com.branco.spring_boot_essentials.database.model.TableOrder;
import br.com.branco.spring_boot_essentials.database.model.OrderItem;
import br.com.branco.spring_boot_essentials.database.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/orders")
public class RestaurantController {

    @Autowired
    private OrderRepository repository;

    // 1. LIST ALL ORDERS
    @GetMapping
    public List<TableOrder> getAllOrders() {
        return repository.findAll();
    }

    // 2. CREATE A NEW TABLE ORDER
    @PostMapping
    public TableOrder createOrder(@RequestBody TableOrder order) {
        TableOrder newOrder = new TableOrder(order.getTableNumber(), order.getAttendantName());
        return repository.save(newOrder);
    }

    @PostMapping("/{id}/items")
    public TableOrder addItemToOrder(@PathVariable Long id, @RequestBody OrderItem newItem) {
        TableOrder order = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found!"));

        order.addItem(newItem);
        return repository.save(order);
    }

    // 4. DELETE An ORDER FROM THE SYSTEM
    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {
        TableOrder order = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found!"));

        repository.delete(order);
        return "Order for Table " + order.getTableNumber() + " has been successfully deleted!";
    }

    // Rota: PUT /api/orders/{id}/items/reduce?productName=Coca-Cola&quantity=1
    // 5. REDUCE THE QUANTITY OF A SPECIFIC ITEM OR REMOVE IT IF QUANTITY REACHES 0
    // 5. REDUCE THE QUANTITY OF A SPECIFIC ITEM OR REMOVE IT
    @PutMapping("/{id}/items/reduce")
    public TableOrder reduceItemQuantity(
            @PathVariable Long id,
            @RequestParam String productName,
            @RequestParam int quantity) {

        TableOrder order = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found!"));

        // Cria uma cópia da lista para evitar erros de concorrência ao mexer nela
        java.util.List<OrderItem> currentItems = new java.util.ArrayList<>(order.getItems());

        for (OrderItem item : currentItems) {
            if (item.getProductName().equalsIgnoreCase(productName)) {
                int newQuantity = item.getQuantity() - quantity;

                order.removeItem(item);

                if (newQuantity > 0) {
                    OrderItem updatedItem = new OrderItem(
                            item.getProductName(),
                            newQuantity,
                            item.getUnitPrice(),
                            item.getObservation()
                    );
                    order.addItem(updatedItem);
                }
                break;
            }
        }

        return repository.save(order);
    }
}
