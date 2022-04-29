package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.CompanyDto;
import com.warehouse_accounting.models.dto.PriceListDto;
import com.warehouse_accounting.services.interfaces.PriceListService;
import com.warehouse_accounting.services.interfaces.api.PriceListApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class PriceListServiceImpl implements PriceListService {
    private final PriceListApi priceListApi;
    private final String priceListUrl;

    public PriceListServiceImpl(@Value("${retrofit.restServices.priceList_url}") String priceListUrl, Retrofit retrofit) {
        this.priceListUrl = priceListUrl;
        this.priceListApi = retrofit.create(PriceListApi.class);
    }

    //убрать после доработки
    private PriceListDto pld = new PriceListDto(1L, LocalDateTime.now(),new CompanyDto()
            , true, true, "comment");

    @Override
    public List<PriceListDto> getAll() {
        List<PriceListDto> list = new ArrayList<PriceListDto>();
        list.add(pld);
        return list;
    }

    @Override
    public PriceListDto getById(Long id) {
        return pld;
    }

    @Override
    public void create(PriceListDto dto) {

    }

    @Override
    public void update(PriceListDto dto) {

    }

    @Override
    public void deleteById(Long id) {

    }
}

