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
public class CustomerGoodsToRealizeGetDto {

    Long id;
    Long ProductDtoId;
    String ProductDtoName;
//    int number; //это поле должно быть также в ProductDto но пока оно отсутствует
//    String article; //это поле должно быть также в ProductDto но пока оно отсутствует
    UnitDto ProductDtoUnit;
    int getGoods;
    int quantity;
    int amount;
    int arrive;
    int remains;
    int quantity_report;
    int amount_report;
    int quantity_Noreport;
    int amount_Noreport;


}

