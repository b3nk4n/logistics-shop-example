package de.bsautermeister.api.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("StockEntry")
public class StockEntry {
  @ApiModelProperty(value = "The SKU", example = "XYZ12345")
  @NotBlank
  @Length(max=255)
  @JsonProperty("sku")
  private final String sku;

  @ApiModelProperty(value = "The amount on stock", example = "99")
  @Min(0)
  @JsonProperty("amount")
  private final long amount;

  @JsonCreator
  public StockEntry(@JsonProperty("sku") String sku,
                    @JsonProperty("amount") long amount) {
    this.sku = sku;
    this.amount = amount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    StockEntry that = (StockEntry) o;

    if (amount != that.amount)
      return false;
    return sku != null ? sku.equals(that.sku) : that.sku == null;
  }

  @Override
  public int hashCode() {
    int result = sku != null ? sku.hashCode() : 0;
    result = 31 * result + (int) (amount ^ (amount >>> 32));
    return result;
  }

  @Override
  public String toString() {
    return "StockEntry{" +
        "sku='" + sku + '\'' +
        ", amount=" + amount +
        '}';
  }

  public String getSku() {
    return sku;
  }

  public long getAmount() {
    return amount;
  }

  @ApiModel("StockEntryWithChange")
  public static class WithChange extends StockEntry {
    /**
     * Change in amount since the last update.
     */
    @ApiModelProperty(value = "The change since the last update", example = "-3")
    @JsonProperty("change")
    private final long change;

    public WithChange(@JsonProperty("sku") String sku,
                      @JsonProperty("amount") long amount,
                      @JsonProperty("change") long change) {
      super(sku, amount);
      this.change = change;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o)
        return true;
      if (o == null || getClass() != o.getClass())
        return false;
      if (!super.equals(o))
        return false;

      WithChange that = (WithChange) o;

      return change == that.change;
    }

    @Override
    public int hashCode() {
      int result = super.hashCode();
      result = 31 * result + (int) (change ^ (change >>> 32));
      return result;
    }

    @Override
    public String toString() {
      return "WithChange{" +
          "change=" + change +
          "} " + super.toString();
    }

    public long getChange() {
      return change;
    }
  }
}
