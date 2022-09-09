package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.CompanyDto;
import com.warehouse_accounting.models.dto.PriceListDto;
import com.warehouse_accounting.models.dto.RemainsDto;
import com.warehouse_accounting.models.dto.WriteOffsDto;
import com.warehouse_accounting.services.interfaces.RemainsService;
import com.warehouse_accounting.services.interfaces.WriteOffsService;
import com.warehouse_accounting.services.interfaces.api.WriteOffsApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class RemainsServiceImpl implements RemainsService {
    private final WriteOffsApi writeOffsApi;
    private final String writeOffsUrl;

    public RemainsServiceImpl(@Value("${retrofit.restServices.writeOffs_url}") String writeOffsUrl, Retrofit retrofit) {
        this.writeOffsApi = retrofit.create(WriteOffsApi.class);
        this.writeOffsUrl = writeOffsUrl;
    }

    //убрать после доработки
    private RemainsDto wfod = new RemainsDto(1L);


    @Override
    public List<RemainsDto> getAll() {
        List<RemainsDto> list = new ArrayList<RemainsDto>();
        list.add(wfod);
        return list;
    }

    @Override
    public RemainsDto getById(Long id) {
        return wfod;
    }

    @Override
    public void create(RemainsDto dto) {

    }

    @Override
    public void update(RemainsDto dto) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
