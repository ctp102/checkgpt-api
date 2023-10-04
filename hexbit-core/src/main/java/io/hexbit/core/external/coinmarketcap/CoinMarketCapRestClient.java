package io.hexbit.core.external.coinmarketcap;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hexbit.core.common.config.properties.CoinMarketCapProperties;
import io.hexbit.core.external.coinmarketcap.domain.CmcCryptoCurrencyPlatform;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
public class CoinMarketCapRestClient {

    private final CoinMarketCapProperties coinMarketCapProperties;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public CmcCryptoCurrencyPlatform getCryptoCurrencyPlatform(String symbol) {
        log.info("getCryptoCurrencyPlatform symbol: {}", symbol);
        return null;
    }

    public CmcCryptoCurrencyPlatform getCryptoCurrencyPlatform(String symbol, PlatformType platformType) {
        log.info("getCryptoCurrencyPlatform symbol: {}, platformType: {}", symbol, platformType);
        return null;
    }

    public CmcCryptoCurrencyPlatform getCryptoCurrencyPlatform(String symbol, PlatformType platformType, SortType sortType) {
        log.info("getCryptoCurrencyPlatform symbol: {}, platformType: {}, sortType: {}", symbol, platformType, sortType);
        return null;
    }

    public CmcCryptoCurrencyPlatform getCryptoCurrencyPlatform(String symbol, PlatformType platformType, SortType sortType, int limit) {
        log.info("getCryptoCurrencyPlatform symbol: {}, platformType: {}, sortType: {}, limit: {}", symbol, platformType, sortType, limit);
        return null;
    }

    public CmcCryptoCurrencyPlatform getCryptoCurrencyPlatform(String symbol, PlatformType platformType, SortType sortType, int limit, int start) {
        log.info("getCryptoCurrencyPlatform symbol: {}, platformType: {}, sortType: {}, limit: {}, start: {}", symbol, platformType, sortType, limit, start);
        return null;
    }

    public CmcCryptoCurrencyPlatform getCryptoCurrencyPlatform(String symbol, PlatformType platformType, SortType sortType, int limit, int start, boolean convert) {
        log.info("getCryptoCurrencyPlatform symbol: {}, platformType: {}, sortType: {}, limit: {}, start: {}, convert: {}", symbol, platformType, sortType, limit, start, convert);
        return null;
    }

    public CmcCryptoCurrencyPlatform getCryptoCurrencyPlatform(String symbol, PlatformType platformType, SortType sortType, int limit, int start, boolean convert, String convertId) {
        log.info("getCryptoCurrencyPlatform symbol: {}, platformType: {}, sortType: {}, limit: {}, start: {}, convert: {}, convertId: {}", symbol, platformType, sortType, limit, start, convert, convertId);
        return null;
    }

    public CmcCryptoCurrencyPlatform getCryptoCurrencyPlatform(String symbol, PlatformType platformType, SortType sortType, int limit, int start, boolean convert, String convertId, String aux) {
        log.info("getCryptoCurrencyPlatform symbol: {}, platformType: {}, sortType: {}, limit: {}, start: {}, convert: {}, convertId: {}, aux: {}", symbol, platformType, sortType, limit, start, convert, convertId, aux);
        return null;
    }

    public enum PlatformType {
        ALL,
        DEFI,
        SMART_CONTRACT,
        BSC,
        ETHEREUM,
        POLYGON,
        SOLANA,
        TERRA,
        TRON,
        EOS,
        COSMOS,
        BINANCE_CHAIN,
        AVALANCHE,
        FANTOM,
        HECO,
        KUSAMA,
        TEZOS,
        NEO,
        WAVES,
        STELLAR,
        ALGORAND,
        CARDANO,
        XDAI,
        ARBITRUM,
        CELO,
        FLOW,
        MOONRIVER,
        MOONBEAM,
        NEAR,
        OASIS,
        POLKADOT,
        SECRET_NETWORK,
        THETA,
        THETA_FUEL,
        TON,
        VECHAIN,
        WAX,
        ZILLIQA,
        BITCOIN,
        BITCOIN_CASH,
        BITCOIN_SV,
        DASH,
        DOGECOIN,
        LITECOIN,
        MONERO,
        NANO,
        NEM,
        RAVENCOIN,
        STEEM,
        TERRAUSD,
        TRON_USDT,
        USDC,
        USDT,
        XRP,
        ZCASH,
        BAND_PROTOCOL,
        CHAINLINK,
        COMPOUND,
        CRYPTO_COM_COIN,
        DAI,
        HUOBI_TOKEN,
        LEO_TOKEN,
        MAKER,
        OKB,
        PAXOS_STANDARD,
        POLYGON_USDC,
        POLYGON_USDT,
        SHIBA_INU,
        SOLANA_USDC,
        SOLANA_USDT,
        STAKED_ETHER,
        SYNTHETIX_NETWORK_TOKEN,
        TERRAUSD_USD,
        TERRAUSD_USDT,
        UNISWAP,
        USD_COIN,
        USD_TETHER,
        VECHAIN_VTHO,
        WETH,
        XRP_THE_STANDARD,
        YFI,
        ZCASH_USD,
        ZCASH_USDT,
        ZCASH_XRP,
        ZCASH_XRPBULL,
        ZCASH_XRPBEAR,
        ZCASH_XRPDOWN,
        ZCASH_XRPUP,
        ZCASH_ZEC,
        ZCASH_ZECBULL,
        ZCASH_ZECBE
    }

    public enum SortType {
        MARKET_CAP,
        NAME,
        PRICE,
        SYMBOL,
        VOLUME_24H,
        PERCENT_CHANGE_1H,
        PERCENT_CHANGE_24H,
        PERCENT_CHANGE_7D,
        PERCENT_CHANGE_30D,
        PERCENT_CHANGE_60D,
        PERCENT_CHANGE_90D,
        PRICE_CHANGE_1H,
        PRICE_CHANGE_24H,
        PRICE_CHANGE_7D,
        PRICE_CHANGE_30D,
        PRICE_CHANGE_60D,
        PRICE_CHANGE_90D,
        CIRCULATING_SUPPLY,
        TOTAL_SUPPLY,
        MAX_SUPPLY,
        NUM_MARKETS,
        NUM_EXCHANGES,
        NUM_PAIRS,
        ATH,
        ATH_CHANGE_PERCENT,
        ATH_DATE,
        ATL,
        ATL_CHANGE_PERCENT,
        ATL_DATE,
        ROI,
        LAST_UPDATED
    }



}
