package de.bsautermeister.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import de.bsautermeister.api.model.StockEntry;

public class StockEntryWithMetadataMapper implements RowMapper<StockEntry.WithChange> {
  @Override
  public StockEntry.WithChange map(ResultSet rs, StatementContext ctx) throws SQLException {
    return new StockEntry.WithChange(
        rs.getString("sku"),
        rs.getInt("amount"),
        rs.getInt("change")
    );
  }
}
