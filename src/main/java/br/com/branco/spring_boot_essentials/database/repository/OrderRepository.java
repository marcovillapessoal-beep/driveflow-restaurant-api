package br.com.branco.spring_boot_essentials.database.repository;

import br.com.branco.spring_boot_essentials.database.model.TableOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<TableOrder, Long> {
}
