package de.bsautermeister.core;

import com.google.inject.Injector;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;

public abstract class GuiceApplication<T extends Configuration> extends Application<T> {

  protected abstract Injector createInjector(T config, Environment environment);

  protected abstract void run(T config, Environment environment, Injector injector) throws Exception;

  @Override
  public final void run(T config, Environment environment) throws Exception {
    Injector injector = createInjector(config, environment);
    run(config, environment, injector);
  }
}
