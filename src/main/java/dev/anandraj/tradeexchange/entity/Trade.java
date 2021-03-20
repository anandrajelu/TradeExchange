package dev.anandraj.tradeexchange.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndexes({
        @CompoundIndex(name = "by_buy_sell_date", def = "{'buyer': 1, 'seller': 1, 'stock': 1}"),
        @CompoundIndex(name = "by_sell_buy_date", def = "{'seller': 1, 'buyer': 1, 'tradeDate': 1}"),
        @CompoundIndex(name = "by_stock_date", def = "{'stock': 1, 'tradeDate': 1}")
})
public class Trade {

    @Id
    private long id;
    private String buyer;
    private String seller;
    private String stock;
    private double price;
    private Date tradeDate;
}
