package dev.anandraj.tradeexchange.api.responses;

import dev.anandraj.tradeexchange.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NonMatchedOrders {
    private Status status;
    private Integer count;
    private List<Order> orders;
}
