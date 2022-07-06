package com.warehouse_accounting.components.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AdditionalFieldConvertor {

    public static String convertToType(String nameOftype) {
        String result;
        switch (nameOftype) {
            case "Строка": result = "String"; break;
            case "Число целое": result = "Integer"; break;
            default: result = "";
        }
        return result;
    }
}
