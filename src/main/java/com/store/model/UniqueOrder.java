package com.store.model;

import lombok.*;
import java.io.Serializable;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UniqueOrder implements Serializable {
    public long id;
    public String orderId;
    public String customerId;
    public String productId;
}
