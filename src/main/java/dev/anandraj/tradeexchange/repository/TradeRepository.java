package dev.anandraj.tradeexchange.repository;

import dev.anandraj.tradeexchange.entity.Trade;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TradeRepository extends MongoRepository<Trade, Long> {
    public static Query getTradeQuery(String buyer, String seller, String symbol, Date date) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (buyer != null) {
            criteria = criteria.and("buyer").is(buyer);
        }
        if (seller != null) {
            criteria = criteria.and("seller").is(seller);
        }
        if (symbol != null) {
            criteria = criteria.and("symbol").is(symbol);
        }
        if (date != null) {
            criteria = criteria.and("tradeDate").is(date);
        }
        query.addCriteria(criteria);
        return query;
    }
}
