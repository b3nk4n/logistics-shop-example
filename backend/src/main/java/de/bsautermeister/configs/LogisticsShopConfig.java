package de.bsautermeister.configs;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.model.ShopifyShop;

import javax.validation.Valid;
import javax.validation.constraints.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LogisticsShopConfig extends Configuration {
  @Valid
  @NotNull
  private final DataSourceFactory database;

  @Valid
  @NotNull
  private final StockConfig stockConfig;

  @Valid
  @NotNull
  private final ShopifyConfig shopifyConfig;

  @Valid
  @NotNull
  private final SwaggerBundleConfiguration swaggerConfig;

  public LogisticsShopConfig(@JsonProperty("database") DataSourceFactory database,
                             @JsonProperty("stock") StockConfig stockConfig,
                             @JsonProperty("shopify") ShopifyConfig shopifyConfig,
                             @JsonProperty("swagger") SwaggerBundleConfiguration swaggerConfig) {
    this.database = database;
    this.stockConfig = stockConfig;
    this.shopifyConfig = shopifyConfig;
    this.swaggerConfig = swaggerConfig;
  }

  public DataSourceFactory getDatabase() {
    return database;
  }

  public StockConfig getStockConfig() {
    return stockConfig;
  }

  public ShopifyConfig getShopifyConfig() {
    return shopifyConfig;
  }

  public SwaggerBundleConfiguration getSwaggerConfig() {
    return swaggerConfig;
  }
}
