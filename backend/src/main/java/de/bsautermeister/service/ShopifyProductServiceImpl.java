package de.bsautermeister.service;

import java.util.List;

import javax.inject.Inject;

import com.shopify.ShopifySdk;
import com.shopify.model.ShopifyVariant;

import de.bsautermeister.configs.ShopifyConfig;

public class ShopifyProductServiceImpl implements ShopifyProductService {

  private final ShopifySdk shopify;

  @Inject
  public ShopifyProductServiceImpl(ShopifyConfig shopifyConfig) {
    this.shopify = ShopifySdk.newBuilder()
        .withSubdomain(shopifyConfig.getSubDomain())
        .withAccessToken(shopifyConfig.getAccessToken())
        .build();
  }

  public List<ShopifyVariant> getProductVariants() {
    return shopify.getProducts().getVariants();
  }
}
