package de.bsautermeister.configs;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.util.Duration;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StockConfig {

  @NotNull
  private final Duration refreshInterval;

  @JsonCreator
  public StockConfig(@JsonProperty("refreshInterval") Duration refreshInterval) {
    this.refreshInterval = refreshInterval;
  }

  public Duration getRefreshInterval() {
    return refreshInterval;
  }
}
