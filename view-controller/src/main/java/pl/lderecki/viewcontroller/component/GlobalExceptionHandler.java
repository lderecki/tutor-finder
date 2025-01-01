package pl.lderecki.viewcontroller.component;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import pl.lderecki.viewcontroller.dto.ExceptionResponse;
import pl.lderecki.viewcontroller.exception.ConstraintViolationException;
import pl.lderecki.viewcontroller.exception.NotFoundException;
import pl.lderecki.viewcontroller.exception.UnauthorizedException;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public Mono<ResponseEntity<ExceptionResponse>> handleNotFound(NotFoundException exception, ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(exception.getMessage())));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public Mono<ResponseEntity<ExceptionResponse>> handleUnauthorized(UnauthorizedException exception, ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionResponse(exception.getMessage())));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Mono<ResponseEntity<ExceptionResponse>> handleConstraintViolation(ConstraintViolationException exception, ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ExceptionResponse(exception.getMessage())));
    }
}
