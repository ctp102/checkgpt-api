package io.hexbit.core.external.coinmarketcap.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CmcCryptoCurrencyPlatform {

    private long id;
    private String name;
    private String symbol;
    private String slug;
    private String tokenAddress;

}
