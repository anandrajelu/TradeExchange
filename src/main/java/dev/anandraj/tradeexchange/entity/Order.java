package dev.anandraj.tradeexchange.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import java.io.Serializable;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {

    @Id
    private long id;
    @NonNull private String party;
    @NonNull private String stock;
    @NonNull private OrderType orderType;
    @NonNull private double price;
    private long tradeId;

    public enum OrderType {
        BUY, SELL;

        public OrderType getReverse() {
            if (this == BUY) return SELL;
            else return BUY;
        }
    }
}
