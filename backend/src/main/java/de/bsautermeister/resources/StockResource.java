package de.bsautermeister.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;

import de.bsautermeister.api.model.StockEntry;
import de.bsautermeister.service.StockService;
import io.swagger.annotations.Api;

@Api(value = "/stock")
@Path("/api/stock")
@Produces(MediaType.APPLICATION_JSON)
public class StockResource {

  private final StockService stockService;

  @Inject
  public StockResource(StockService stockService) {
    this.stockService = stockService;
  }

  @GET
  @Timed
  public List<StockEntry.WithChange> findStock() {
    return stockService.find();
  }

  @GET
  @Path("/{sku}")
  @Timed
  public StockEntry.WithChange findStock(@PathParam("sku") String sku) {
    return stockService.findBySku(sku)
        .orElseThrow(() -> new WebApplicationException(
            String.format("Entry with the given SKU '%s' not found.", sku), Response.Status.NOT_FOUND));
  }
}
