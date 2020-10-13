package de.bsautermeister;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.bsautermeister.configs.LogisticsShopConfig;
import de.bsautermeister.core.GuiceApplication;
import de.bsautermeister.resources.StockResource;
import de.bsautermeister.service.StockService;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.jdbi3.bundles.JdbiExceptionsBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class LogisticsShopApp extends GuiceApplication<LogisticsShopConfig> {

    public static void main(final String[] args) throws Exception {
        new LogisticsShopApp().run(args);
    }

    @Override
    public String getName() {
        return "Logistics Shop";
    }

    @Override
    public void initialize(final Bootstrap<LogisticsShopConfig> bootstrap) {
        // bundle to automatically unwrap any thrown SQLException or JdbiException instances
        bootstrap.addBundle(new JdbiExceptionsBundle());
        // enable migrations command
        bootstrap.addBundle(new MigrationsBundle<LogisticsShopConfig>() {
            @Override
            public PooledDataSourceFactory getDataSourceFactory(LogisticsShopConfig config) {
                return config.getDatabase();
            }
        });
        // enable Swagger api docs
        bootstrap.addBundle(new SwaggerBundle<LogisticsShopConfig>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(LogisticsShopConfig config) {
                return config.getSwaggerConfig();
            }
        });
    }

    @Override
    protected Injector createInjector(LogisticsShopConfig config, Environment environment) {
        return Guice.createInjector(new LogisticsShopModule(environment, config));
    }

    @Override
    protected void run(LogisticsShopConfig config, Environment environment, Injector injector) {
        // resources
        environment.jersey().register(injector.getInstance(StockResource.class));

        // managed services
        environment.lifecycle().manage(injector.getInstance(StockService.class));
    }
}
