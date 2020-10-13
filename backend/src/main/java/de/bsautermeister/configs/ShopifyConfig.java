package de.bsautermeister.configs;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyConfig {
  @NotBlank
  private final String subDomain;

  @NotBlank
  private final String accessToken;

  public ShopifyConfig(@JsonProperty("subDomain") String subDomain,
                       @JsonProperty("accessToken") String accessToken) {
    this.subDomain = subDomain;
    this.accessToken = accessToken;
  }

  public String getSubDomain() {
    return subDomain;
  }

  public String getAccessToken() {
    return accessToken;
  }
}
