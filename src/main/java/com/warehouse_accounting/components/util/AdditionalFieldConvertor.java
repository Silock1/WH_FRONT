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

    public static String convertFromType(String type) {
        String result;
        switch (type) {
            case "String": result = "Строка"; break;
            case "Integer": result = "Число целое"; break;
            default: result = "";
        }
        return result;
    }
}
