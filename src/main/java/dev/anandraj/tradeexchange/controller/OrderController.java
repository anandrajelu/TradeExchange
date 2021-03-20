package dev.anandraj.tradeexchange.controller;

import dev.anandraj.tradeexchange.api.responses.NonMatchedOrders;
import dev.anandraj.tradeexchange.api.responses.Status;
import dev.anandraj.tradeexchange.entity.Order;
import dev.anandraj.tradeexchange.service.OrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping
    public ResponseEntity<NonMatchedOrders> getNonMatchedOrders(
            @ApiParam(required = false) @Nullable @RequestParam String symbol,
            @ApiParam(required = false) @Nullable @RequestParam Double price) {
        NonMatchedOrders nonMatchedOrders =  new NonMatchedOrders();
        Status status = new Status(HttpStatus.OK.toString(), "SUCCESS", "Non Matched Orders Fetched");
        if (symbol == null && price == null) {
            status = new Status(HttpStatus.BAD_REQUEST.toString(), "FAILURE", "Symbol/Price cannot be null");
            nonMatchedOrders.setStatus(status);
            return ResponseEntity.badRequest().body(nonMatchedOrders);
        }
        List<Order> orders = orderService.getNonMatchedOrders(symbol, price);
        if (orders == null) {
            return ResponseEntity.notFound().build();
        }
        nonMatchedOrders.setOrders(orders);
        nonMatchedOrders.setCount(orders.size());
        nonMatchedOrders.setStatus(status);
        return ResponseEntity.ok(nonMatchedOrders);
    }

    @PostMapping
    @ApiOperation(value = "A test method to post Order details to Kafka Topic")
    public ResponseEntity<Object> postOrders(@RequestBody Order order) {
        orderService.postOrder(order);
        return ResponseEntity.ok().build();
    }
}
