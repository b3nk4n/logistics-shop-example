package de.bsautermeister.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;
import com.shopify.model.ShopifyVariant;

import de.bsautermeister.api.model.StockEntry;
import de.bsautermeister.configs.StockConfig;
import de.bsautermeister.db.StockDAO;

@Singleton
public class StockServiceImpl implements StockService {

  private static final Logger LOGGER = LoggerFactory.getLogger(StockServiceImpl.class);

  private final StockConfig stockConfig;
  private final StockDAO stockDAO;
  private final ShopifyProductService shopifyProductService;

  /**
   * Cache for the SKUs in the database, to be aware which SKUs are currently know, and thus to be able to know whether
   * a DELETE or an UPSERT operation need to be executed for each entry when syncing the data in the refresh step.
   */
  private final Set<String> skuCache;

  private final ScheduledExecutorService executorService;
  private ScheduledFuture<?> refreshJob;

  @Inject
  public StockServiceImpl(StockConfig stockConfig, StockDAO stockDAO, ShopifyProductService shopifyProductService,
                          @Named("SkuCache") Set<String> skuCache, ScheduledExecutorService executorService) {
    this.stockConfig = stockConfig;
    this.stockDAO = stockDAO;
    this.shopifyProductService = shopifyProductService;
    this.skuCache = skuCache;
    this.executorService = executorService;
  }

  @Override
  public void start() throws Exception {
    LOGGER.debug("Starting...");
    refreshJob = executorService.scheduleAtFixedRate(this::refresh,
        0L, stockConfig.getRefreshInterval().toMilliseconds(), TimeUnit.MILLISECONDS);
  }

  @Override
  public void stop() throws Exception {
    LOGGER.debug("Stopping...");
    if (refreshJob != null) {
      refreshJob.cancel(false);
    }
  }

  @Override
  public List<StockEntry.WithChange> find() {
    return stockDAO.find();
  }

  @Override
  public Optional<StockEntry.WithChange> findBySku(String sku) {
    return stockDAO.findBySku(sku);
  }

  @Override
  public void refresh() {
    LOGGER.debug("Refreshing stock data");
    try {
      List<ShopifyVariant> variants = shopifyProductService.getProductVariants();
      purgeOutdatedVariants(variants);
      upsertVariants(variants);
    } catch (Exception e) {
      LOGGER.warn("Stock data refresh failed", e);
    }
  }

  private void purgeOutdatedVariants(List<ShopifyVariant> variants) {
    Set<String> newSkuSet = variants.stream()
        .map(ShopifyVariant::getSku)
        .collect(Collectors.toSet());

    Set<String> deletedSkus = Sets.difference(skuCache, newSkuSet);
    if (!deletedSkus.isEmpty()) {
      stockDAO.delete(deletedSkus);
    }

    skuCache.clear();
    skuCache.addAll(newSkuSet);
  }

  private void upsertVariants(List<ShopifyVariant> variants) {
    Set<StockEntry> upsertEntries = variants.stream()
        .map(variant -> new StockEntry(
            variant.getSku(),
            variant.getTitle(),
            variant.getInventoryQuantity() != null ? variant.getInventoryQuantity() : 0L))
        .collect(Collectors.toSet());
    upsertEntries.forEach(entry -> stockDAO.upsert(entry.getSku(), entry.getTitle(), entry.getAmount()));
  }
}
