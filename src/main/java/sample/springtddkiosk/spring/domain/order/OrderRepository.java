package sample.springtddkiosk.spring.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.registeredDate >= :startDate" +
        " and o.registeredDate < :endDate" +
        " and o.orderStatus = :orderStatus")
    List<Order> findOrdersBy(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate,
        @Param("orderStatus") OrderStatus orderStatus
    );
}
