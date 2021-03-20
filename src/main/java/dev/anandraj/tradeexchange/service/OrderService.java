package dev.anandraj.tradeexchange.service;

import dev.anandraj.tradeexchange.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class OrderService {

    @Autowired
    KafkaTemplate<String, Order> orderKafkaTemplate;

    @Autowired
    RedisTemplate<String, List<Order>> redisTemplate;

    @Value("${trade.exchange.order.topic}")
    private String orderTopic;

    public void postOrder(Order order) {
        orderKafkaTemplate.send(orderTopic, order);
    }

    public List<Order> getNonMatchedOrders(String symbol, Double price) {
        List<Order> orders = new ArrayList<>();
        if (symbol != null && price != null) {
            fetchOrdersByExactMatch(symbol, price, orders);
        } else {
            fetchOrdersByPartialMatch(symbol, price, orders);
        }
        return orders;
    }

    private void fetchOrdersByPartialMatch(String symbol, Double price, List<Order> orders) {
        String key = ((symbol != null)? symbol: "*") + ((price != null)? price: "*");
        Set<String> keys = redisTemplate.keys(key);
        for (String k: keys) {
            List<Order> orders1 = redisTemplate.opsForValue().get(k);
            if (orders1 != null) {
                orders.addAll(orders1);
            }
        }
    }

    private void fetchOrdersByExactMatch(String symbol, Double price, List<Order> orders) {
        List<Order> orders1 = redisTemplate.opsForValue().get(symbol + price + Order.OrderType.BUY);
        if (orders1 != null) {
            orders.addAll(orders1);
        }
        orders1 = redisTemplate.opsForValue().get(symbol+price+Order.OrderType.SELL);
        if (orders1 != null) {
            orders.addAll(orders1);
        }
    }
}
