package de.bsautermeister;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.jdbi.v3.core.Jdbi;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

import de.bsautermeister.configs.LogisticsShopConfig;
import de.bsautermeister.configs.ShopifyConfig;
import de.bsautermeister.configs.StockConfig;
import de.bsautermeister.db.StockDAO;
import de.bsautermeister.service.ShopifyProductService;
import de.bsautermeister.service.ShopifyProductServiceImpl;
import de.bsautermeister.service.StockService;
import de.bsautermeister.service.StockServiceImpl;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Environment;

public class LogisticsShopModule extends AbstractModule {

  private final Environment environment;
  private final LogisticsShopConfig config;

  public LogisticsShopModule(Environment environment, LogisticsShopConfig config) {
    this.environment = environment;
    this.config = config;
  }

  @Override
  protected void configure() {
    super.configure();

    bind(new TypeLiteral<Set<String>>() {}).annotatedWith(Names.named("SkuCache")).toInstance(ConcurrentHashMap.newKeySet());
    bind(StockConfig.class).toInstance(config.getStockConfig());
    bind(ShopifyConfig.class).toInstance(config.getShopifyConfig());

    bind(StockService.class).to(StockServiceImpl.class);
    bind(ShopifyProductService.class).to(ShopifyProductServiceImpl.class);
  }

  @Provides
  @Singleton
  public Jdbi jdbi() {
    final JdbiFactory factory = new JdbiFactory();
    return factory.build(environment, config.getDatabase(), "postgresql");
  }

  @Provides
  @Singleton
  public StockDAO stockDAO(Jdbi jdbi) {
    return jdbi.onDemand(StockDAO.class);
  }

  @Provides
  @Singleton
  public ScheduledExecutorService scheduledExecutorService() {
    return Executors.newScheduledThreadPool(1);
  }
}
