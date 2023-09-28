package com.vmoscalciuc.orderservice.controller;

import com.vmoscalciuc.orderservice.dto.*;
import com.vmoscalciuc.orderservice.service.*;
import io.github.resilience4j.circuitbreaker.annotation.*;
import io.github.resilience4j.retry.annotation.*;
import io.github.resilience4j.timelimiter.annotation.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name="inventory",fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest){
        return CompletableFuture.supplyAsync(()->orderService.placeOrder(orderRequest));
    }
    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest,RuntimeException runtimeException){
        return CompletableFuture.supplyAsync(()->"Oops, something went wrong, please try again after some time!");
    }
}
