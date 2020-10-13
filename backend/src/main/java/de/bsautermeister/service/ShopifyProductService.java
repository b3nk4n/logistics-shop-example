package de.bsautermeister.service;

import java.util.List;

import com.shopify.model.ShopifyVariant;

/**
 * A simple shopify service to retrieve product information.
 */
public interface ShopifyProductService {
  /**
   * Gets all product variants.
   * @return A list of shopify product variants.
   */
  List<ShopifyVariant> getProductVariants();
}
