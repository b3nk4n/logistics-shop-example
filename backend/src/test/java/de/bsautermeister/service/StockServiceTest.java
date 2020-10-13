package de.bsautermeister.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.common.collect.Lists;
import com.shopify.model.ShopifyVariant;

import de.bsautermeister.api.model.StockEntry;
import de.bsautermeister.configs.StockConfig;
import de.bsautermeister.db.StockDAO;
import io.dropwizard.util.Duration;

class StockServiceTest {

  private static final StockConfig TEST_CONFIG = new StockConfig(Duration.minutes(1));

  private Set<String> testSkuCache;
  private StockService stockService;

  @Mock
  private StockDAO stockDAO;

  @Mock
  private ShopifyProductService shopifyProductService;

  @BeforeEach
  void setup() {
    MockitoAnnotations.initMocks(this);

    testSkuCache = ConcurrentHashMap.newKeySet();
    stockService = new StockServiceImpl(TEST_CONFIG, stockDAO, shopifyProductService, testSkuCache,
        Executors.newSingleThreadScheduledExecutor());
  }

  @Test
  void refreshAtStartupForEmptyCache() {
    when(shopifyProductService.getProductVariants()).thenReturn(
        Lists.newArrayList(createVariant("sku1", 3L), createVariant("sku2", 2L)));

    assertThat(testSkuCache).isEmpty();

    stockService.refresh();

    assertThat(testSkuCache).hasSize(2);
    assertThat(testSkuCache).contains("sku1", "sku2");

    verify(stockDAO, times(2)).upsert(anyString(), anyLong());
    verify(stockDAO, never()).delete(anySet());
  }

  @Test
  void refreshForNonEmptyCache() {
    when(shopifyProductService.getProductVariants()).thenReturn(
        Lists.newArrayList(
            createVariant("sku1", 1L),
            createVariant("sku2", 2L),
            createVariant("sku3", 3L)));

    assertThat(testSkuCache).isEmpty();

    stockService.refresh();

    assertThat(testSkuCache).hasSize(3);

    when(shopifyProductService.getProductVariants()).thenReturn(
        Lists.newArrayList(
            createVariant("sku2", 2L),
            createVariant("sku3", 99L),
            createVariant("sku4", 4L)));

    stockService.refresh();

    assertThat(testSkuCache).hasSize(3);
    assertThat(testSkuCache).contains("sku2", "sku3", "sku4");

    verify(stockDAO, times(6)).upsert(anyString(), anyLong());
    verify(stockDAO, times(1)).delete(anySet());
  }

  @Test
  void findForEmptyResult() {
    when(stockDAO.find()).thenReturn(Collections.emptyList());

    List<StockEntry.WithChange> result = stockService.find();

    assertThat(result).isEmpty();

    verify(stockDAO, times(1)).find();
  }

  @Test
  void findForNonEmptyResult() {
    when(stockDAO.find()).thenReturn(
        Lists.newArrayList(
            new StockEntry.WithChange("sku1", 1L, 0L),
            new StockEntry.WithChange("sku2", 2L, 0L)));

    List<StockEntry.WithChange> result = stockService.find();

    assertThat(result)
        .isNotEmpty()
        .hasSize(2);

    verify(stockDAO, times(1)).find();
  }

  @Test
  void findBySkuForMiss() {
    final String testSku = "sku1";
    when(stockDAO.findBySku(eq(testSku))).thenReturn(Optional.empty());

    Optional<StockEntry.WithChange> result = stockService.findBySku(testSku);

    assertThat(result)
        .isNotPresent();

    verify(stockDAO, times(1)).findBySku(eq(testSku));
  }

  @Test
  void findBySkuForHit() {
    final StockEntry.WithChange testEntry = new StockEntry.WithChange("sku1", 1L, 0L);

    when(stockDAO.findBySku(eq(testEntry.getSku()))).thenReturn(Optional.of(testEntry));

    Optional<StockEntry.WithChange> result = stockService.findBySku(testEntry.getSku());

    assertThat(result)
        .isPresent()
        .hasValueSatisfying(entry -> assertThat(entry).isEqualTo(testEntry));

    verify(stockDAO, times(1)).findBySku(eq(testEntry.getSku()));
  }

  private static ShopifyVariant createVariant(String sku, long quantity) {
    ShopifyVariant variant = new ShopifyVariant();
    variant.setSku(sku);
    variant.setInventoryQuantity(quantity);
    return variant;
  }
}