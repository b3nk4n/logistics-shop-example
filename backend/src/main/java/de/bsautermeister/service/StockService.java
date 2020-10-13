package de.bsautermeister.service;

import java.util.List;
import java.util.Optional;

import de.bsautermeister.api.model.StockEntry;
import io.dropwizard.lifecycle.Managed;

/**
 * A managed service to find the current stock of products.
 */
public interface StockService extends Managed {
  /**
   * Refresh the data.
   */
  void refresh();

  /**
   * Find all products with additional change information.
   * @return The current stock of products.
   */
  List<StockEntry.WithChange> find();

  /**
   * Finds a product by a given SKU.
   * @param sku The SKU of the product.
   * @return The product with change information of found.
   */
  Optional<StockEntry.WithChange> findBySku(String sku);
}
