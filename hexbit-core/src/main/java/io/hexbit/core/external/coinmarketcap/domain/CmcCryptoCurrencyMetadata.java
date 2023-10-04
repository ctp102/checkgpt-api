package io.hexbit.core.external.coinmarketcap.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CmcCryptoCurrencyMetadata {

    private long id;
    private String name;
    private String symbol;
    private String category;
    private String slug;
    private String description;
    private String logo; // url
    private CmcCryptoCurrencyUrl urls;
    private CmcCryptoCurrencyPlatform platform;


}
