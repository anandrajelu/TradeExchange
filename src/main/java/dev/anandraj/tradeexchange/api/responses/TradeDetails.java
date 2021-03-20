package dev.anandraj.tradeexchange.api.responses;

import dev.anandraj.tradeexchange.entity.Trade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeDetails {
    private Status status;
    private List<Trade> trades;
    private Integer count;
}
