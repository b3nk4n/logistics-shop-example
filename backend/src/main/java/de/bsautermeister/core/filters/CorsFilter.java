package de.bsautermeister.core.filters;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.io.Closeable;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.Response;

import com.google.inject.Singleton;

@Singleton
public class CorsFilter implements Filter {
  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;

    String origin = request.getHeader("Origin");

    if (isBlank(origin)) {
      // Browsers will only transmit Origin headers when sending a request from one origin to another. API clients
      // will never transmit Origin headers. Users should be able to open API resources directly in their browser
      // (in which case there will be no Origin header).
      chain.doFilter(request, response);
      return;
    }

    if (isMethod(request, HttpMethod.OPTIONS)) {
      response.setHeader("Access-Control-Allow-Origin", origin);
      response.setStatus(Response.Status.OK.getStatusCode());
      closeQuietly(response.getOutputStream());
      return;
    }

    if (isMethod(request, HttpMethod.GET)) {
      // for this example app it's fine do allow GET from any origin
      response.setHeader("Access-Control-Allow-Origin", origin);
    }

    chain.doFilter(request, response);
  }

  private static boolean isMethod(HttpServletRequest request, String method) {
    return method.equalsIgnoreCase(request.getMethod());
  }

  private static void closeQuietly(final Closeable closeable) {
    try {
      if (closeable != null) {
        closeable.close();
      }
    } catch (final IOException ioe) {
      // ignore
    }
  }
}
