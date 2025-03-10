package com.springcommerce.product_service.dto;


import java.math.BigDecimal;

public record ProductRequest(String id, String name, String description, BigDecimal price) {

}
