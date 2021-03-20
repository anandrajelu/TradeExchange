package dev.anandraj.tradeexchange.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sequences")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sequence {
    @Id
    private String id;
    private Long seq;
}
