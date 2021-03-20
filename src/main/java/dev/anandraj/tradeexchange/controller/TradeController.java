package dev.anandraj.tradeexchange.controller;

import dev.anandraj.tradeexchange.api.responses.Status;
import dev.anandraj.tradeexchange.api.responses.TradeDetails;
import dev.anandraj.tradeexchange.entity.Trade;
import dev.anandraj.tradeexchange.service.TradeService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("trades")
public class TradeController {

    @Autowired
    TradeService tradeService;

    @GetMapping
    public ResponseEntity<TradeDetails> getTradeDetails(@ApiParam(required = false) @Nullable @RequestParam String buyer,
                                                        @ApiParam(required = false) @Nullable @RequestParam String seller,
                                                        @ApiParam(required = false) @Nullable @RequestParam String symbol,
                                                        @ApiParam(required = false) @Nullable @RequestParam Date date) {
        TradeDetails tradeDetails = new TradeDetails();
        Status status = new Status(HttpStatus.OK.toString(), "SUCCESS", "Trade Details Fetched");
        if (buyer == null && seller == null && symbol == null && date == null) {
            status = new Status(HttpStatus.BAD_REQUEST.toString(), "FAILURE", "filter information is required");
            tradeDetails.setStatus(status);
            return ResponseEntity.badRequest().body(tradeDetails);
        }
        List<Trade> trades = tradeService.getTradeDetails(buyer, seller, symbol, date);
        if (trades == null) {
            return ResponseEntity.notFound().build();
        }
        tradeDetails.setCount(trades.size());
        tradeDetails.setTrades(trades);
        tradeDetails.setStatus(status);
        return ResponseEntity.ok(tradeDetails);
    }
}
