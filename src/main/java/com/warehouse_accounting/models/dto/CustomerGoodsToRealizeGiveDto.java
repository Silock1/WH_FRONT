package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;
/*
РЕАЛИЗОВАТЬ КОНСТРУКТОР и получение данных
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class CustomerGoodsToRealizeGiveDto {

    List<ProductDto> productsDto = new ArrayList<>();
    int id;
    String article;
    String unit;
    int give;
    int quantity;
    int amount;
    int arrive;
    int remains;



}

