package de.bsautermeister.resources;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import de.bsautermeister.api.model.StockEntry;
import de.bsautermeister.service.StockService;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;

@ExtendWith(DropwizardExtensionsSupport.class)
class StockResourceTest {

  private static final StockService STOCK_SERVICE = mock(StockService.class);
  private static final ResourceExtension RESOURCE = ResourceExtension.builder()
      .addResource(new StockResource(STOCK_SERVICE))
      .build();
  private final ObjectReader stockEntryReader = new ObjectMapper()
      .readerFor(new TypeReference<StockEntry.WithChange>() {});

  @AfterEach
  void tearDown() {
    reset(STOCK_SERVICE);
  }

  @Test
  void findStock_forEmptyResult() {
    when(STOCK_SERVICE.find()).thenReturn(Collections.emptyList());

    List<StockEntry.WithChange> result = RESOURCE
        .target("/stock")
        .request()
        .get(new GenericType<List<StockEntry.WithChange>>() {});

    assertThat(result).hasSize(0);
  }

  @Test
  void findStock_forNonEmptyResult() {
    when(STOCK_SERVICE.find()).thenReturn(Lists.newArrayList(
        new StockEntry.WithChange("sku1", 1, 0),
        new StockEntry.WithChange("sku2", 2, 1),
        new StockEntry.WithChange("sku3", 3, -1)));

    List<StockEntry.WithChange> result = RESOURCE
        .target("/stock")
        .request()
        .get(new GenericType<List<StockEntry.WithChange>>() {});

    assertThat(result).hasSize(3);
  }

  @Test
  void testFindStock_forHit() throws IOException {
    final StockEntry.WithChange expected = new StockEntry.WithChange("sku1", 1, 0);

    when(STOCK_SERVICE.findBySku(eq("sku1")))
        .thenReturn(Optional.of(expected));

    Response response = RESOURCE
        .target("/stock/" + expected.getSku())
        .request()
        .get();

    assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    StockEntry.WithChange actual = stockEntryReader.readValue((ByteArrayInputStream) response.getEntity());
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void testFindStock_forMiss() {
    when(STOCK_SERVICE.findBySku(eq("sku1")))
        .thenReturn(Optional.empty());

    Response response = RESOURCE
        .target("/stock/unknownSku")
        .request()
        .get();

    assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
  }
}