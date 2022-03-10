package com.warehouse_accounting.services.interfaces;


import com.warehouse_accounting.models.dto.dadata.Example;

public interface DadataService {

    Example getExample(String inn);
}
