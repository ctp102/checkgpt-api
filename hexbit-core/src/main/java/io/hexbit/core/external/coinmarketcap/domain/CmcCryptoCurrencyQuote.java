package io.hexbit.core.external.coinmarketcap.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CmcCryptoCurrencyQuote {
    private double price;
    private long volume24h;
    private double volumeChange24h;
    private double percentChange1h;
    private double percentChange24h;
    private double percentChange7d;
    private double marketCap;
    private int marketCapDominance;
    private double fullyDilutedMarketCap;
    private String lastUpdated;
}
