package io.hexbit.core.external.coinmarketcap.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.hexbit.core.external.coinmarketcap.enums.Currency;
import lombok.Data;

import java.util.Map;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CmcCryptoCurrencyListing {

    private long id;
    private String name;
    private String symbol;
    private String slug;
    private int cmcRank;
    private int circulatingSupply;
    private int totalSupply;
    private int maxSupply;
    private CmcCryptoCurrencyPlatform platform;
    private Map<Currency, CmcCryptoCurrencyQuote> quote;

}
