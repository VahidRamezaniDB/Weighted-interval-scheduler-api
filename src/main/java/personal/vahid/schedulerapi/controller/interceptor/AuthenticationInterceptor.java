package personal.vahid.schedulerapi.controller.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import personal.vahid.schedulerapi.service.AuthenticationService;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class AuthenticationInterceptor extends OncePerRequestFilter {

    AuthenticationService authenticationService;
    ObjectMapper objectMapper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> token  = extractToken(request);
        if(token.isPresent()){
            if(!authenticationService.authenticate(token.get())){
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(objectMapper.writeValueAsString(Map.of("error", "Invalid authorization token.")));
                return;
            }
        }else {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(Map.of("error", "Missing authorization token.")));
            return;
        }
        filterChain.doFilter(request, response);

    }

    private Optional<String> extractToken(HttpServletRequest request) {
        final String requestTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Strings.isNotBlank(requestTokenHeader) && requestTokenHeader.startsWith("Bearer ")) {
            if (requestTokenHeader.split(" ").length < 2 || Strings.isBlank(requestTokenHeader.split(" ")[1])) {
                return Optional.empty();
            }
            return Optional.of(requestTokenHeader.split(" ")[1].trim());
        }else {
            return Optional.empty();
        }

    }
}
