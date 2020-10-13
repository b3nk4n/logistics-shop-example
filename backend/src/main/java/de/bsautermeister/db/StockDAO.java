package de.bsautermeister.db;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindList;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import de.bsautermeister.api.model.StockEntry;
import de.bsautermeister.db.mapper.StockEntryWithMetadataMapper;

/**
 * DAO for stocks table.
 */
@RegisterRowMapper(StockEntryWithMetadataMapper.class)
public interface StockDAO {
  /**
   * Upserts an entry.
   * @param sku The SKU of the product.
   * @param amount The amount in stock.
   */
  default void upsert(String sku, long amount) {
    upsert(sku, amount, 0);
  }

  /**
   * Upserts an entry.
   * @param sku The SKU of the product.
   * @param amount The amount in stock.
   * @param change The change by default (usually 0).
   */
  @SqlUpdate("INSERT INTO stocks (sku, amount, change) VALUES (:sku, :amount, :change)"
                 + "ON CONFLICT(sku) DO UPDATE SET amount = excluded.amount, change = excluded.amount - stocks.amount")
  void upsert(@Bind("sku") String sku, @Bind("amount") long amount, @Bind("change") long change);

  /**
   * Finds all products in stock ordered by SKU.
   * @return The list of products in stock.
   */
  @SqlQuery("SELECT sku, amount, change FROM stocks ORDER BY sku")
  List<StockEntry.WithChange> find();

  /**
   * Finds a product by SKU.
   * @param sku The SKU of the product.
   * @return The entry in stock of present.
   */
  @SqlQuery("SELECT sku, amount, change FROM stocks WHERE sku = :sku")
  Optional<StockEntry.WithChange> findBySku(@Bind("sku") String sku);

  /**
   * Deletes the products given a set of SKUs
   * @param skus A set of SKUs to delete.
   */
  @SqlUpdate("DELETE FROM stocks WHERE sku IN (<skus>)")
  void delete(@BindList("skus") Collection<String> skus);
}
