package pl.piotrFigura.ToDoApp.util;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
class LoggerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
      if(servletRequest instanceof HttpServletRequest){
          var httpRequest = (HttpServletRequest) servletRequest;
          log.info("[doFilter] " + httpRequest.getMethod() + " " + httpRequest.getRequestURI());
      }
      filterChain.doFilter(servletRequest, servletResponse);
      log.info("[doFilter 2]");
    }
}
