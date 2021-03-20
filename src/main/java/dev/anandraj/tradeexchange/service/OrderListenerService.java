package dev.anandraj.tradeexchange.service;

import dev.anandraj.tradeexchange.entity.Order;
import dev.anandraj.tradeexchange.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderListenerService {

    @Autowired
    RedisTemplate<String, List<Order>> orderRedisTemplate;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    SequenceService sequenceService;

    @Autowired
    TradeService tradeService;

    @KafkaListener(topics = "${trade.exchange.order.topic}",
            groupId = "${trade.exchange.order.group.id}",
            containerFactory = "orderEventContainerFactory")
    public void consume(Order order) {
        //check if redis cache has a matched order
        //if yes, remove entry from cache and update DB
        //if no, add entry in DB
        String keyPrefix = order.getStock()+order.getPrice();
        String matchingKey = keyPrefix+order.getOrderType().getReverse();
        ValueOperations<String, List<Order>> operations = orderRedisTemplate.opsForValue();
        log.info("Order Received - "+order);
        List<Order> matchingOrders = operations.get(matchingKey);
        if (matchingOrders != null && !matchingOrders.isEmpty()) {
            processTrade(order, matchingKey, operations, matchingOrders);
        } else {
            processOrder(order, keyPrefix, operations);
        }
    }

    private void processOrder(Order order, String keyPrefix, ValueOperations<String, List<Order>> operations) {
        Long orderId = sequenceService.getNextSequence("Order");
        order.setId(orderId);
        orderRepository.save(order);
        String key = keyPrefix+order.getOrderType();
        List<Order> orders = operations.get(key);
        if (orders == null) orders = new ArrayList<>();
        orders.add(order);
        operations.set(key, orders);
        log.info("Added order to cache");
    }

    private void processTrade(Order order, String matchingKey, ValueOperations<String, List<Order>> operations, List<Order> matchingOrders) {
        log.info("Matching orders found: "+matchingOrders);
        Order matchedOrder = matchingOrders.get(0);
        matchingOrders.remove(matchedOrder);
        long tradeId = createTradeRecord(matchedOrder, order);
        matchedOrder.setTradeId(tradeId);
        order.setTradeId(tradeId);
        Order orderById = orderRepository.findById(matchedOrder.getId()).get();
        orderById.setTradeId(tradeId);
        operations.set(matchingKey, matchingOrders);
        Long orderId = sequenceService.getNextSequence("Order");
        order.setId(orderId);
        orderRepository.save(order);
        log.info("Insert order successfull"+order);
    }

    private Long createTradeRecord(Order matchedOrder, Order order) {
        return tradeService.createTrade(matchedOrder, order);
    }
}
