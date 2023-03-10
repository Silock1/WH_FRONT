package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;
/*
РЕАЛИЗОВАТЬ КОНСТРУКТОР и получение данных
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class GoodsToRealizeGiveDto {

    Long id;

    Long productDtoId;
    String productDtoName;
    //    int number; //это поле должно быть также в ProductDto но пока оно отсутствует
//    String article; //это поле должно быть также в ProductDto но пока оно отсутствует
    Long unitId;
    String unitName;
    int giveGoods;
    int quantity;
    int amount;
    int arrive;
    int remains;



}

