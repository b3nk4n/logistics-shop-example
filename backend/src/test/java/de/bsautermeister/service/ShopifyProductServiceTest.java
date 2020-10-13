package de.bsautermeister.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.shopify.exceptions.ShopifyErrorResponseException;
import com.shopify.model.ShopifyVariant;

import de.bsautermeister.configs.ShopifyConfig;

class ShopifyProductServiceTest {

  private final static String TEST_SUBDOMAIN = "logistikbroker-demo";
  private final static String TEST_ACCESS_TOKEN = "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";
  private static final ShopifyConfig CONFIG = new ShopifyConfig(TEST_SUBDOMAIN, TEST_ACCESS_TOKEN);

  private ShopifyProductService productService;

  @Test
  void getProductVariants() {
    productService = new ShopifyProductServiceImpl(CONFIG);
    List<ShopifyVariant> result = productService.getProductVariants();
    assertThat(result).isNotEmpty();
  }

  @Test
  void getProductVariants_forInvalidAccessToken() {
    final ShopifyConfig invalidConfig = new ShopifyConfig(TEST_SUBDOMAIN, "invalidToken");
    productService = new ShopifyProductServiceImpl(invalidConfig);
    assertThatThrownBy(() -> productService.getProductVariants())
        .isInstanceOf(ShopifyErrorResponseException.class)
        .hasMessageContaining("Invalid API key or access token");
  }

  @Test
  void getProductVariants_forInvalidSubDomain() {
    final ShopifyConfig invalidConfig = new ShopifyConfig("invalid-subdomain", TEST_ACCESS_TOKEN);
    productService = new ShopifyProductServiceImpl(invalidConfig);
    assertThatThrownBy(() -> productService.getProductVariants())
        .isInstanceOf(ShopifyErrorResponseException.class)
        .hasMessageContaining("Received unexpected Response Status Code of 404");
  }
}