package dev.anandraj.tradeexchange.repository;

import dev.anandraj.tradeexchange.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, Long> {
}
