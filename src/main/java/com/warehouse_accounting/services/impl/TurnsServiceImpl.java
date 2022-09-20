package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.CompanyDto;
import com.warehouse_accounting.models.dto.PriceListDto;
import com.warehouse_accounting.models.dto.RemainsDto;
import com.warehouse_accounting.models.dto.TurnsDto;
import com.warehouse_accounting.models.dto.WriteOffsDto;
import com.warehouse_accounting.services.interfaces.RemainsService;
import com.warehouse_accounting.services.interfaces.TurnsService;
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
public class TurnsServiceImpl implements TurnsService {
    private final WriteOffsApi writeOffsApi;
    private final String writeOffsUrl;

    public TurnsServiceImpl(@Value("${retrofit.restServices.writeOffs_url}") String writeOffsUrl, Retrofit retrofit) {
        this.writeOffsApi = retrofit.create(WriteOffsApi.class);
        this.writeOffsUrl = writeOffsUrl;
    }

    //убрать после доработки
    private TurnsDto wfod = new TurnsDto(1L, "Cola", 00.0, 1l,
            00.0, "Литры",00.0, 00.0, 00.0,
            00.0, 00.0, 00.0,
            00.0);


    @Override
    public List<TurnsDto> getAll() {
        List<TurnsDto> list = new ArrayList<TurnsDto>();
        list.add(wfod);
        return list;
    }

    @Override
    public TurnsDto getById(Long id) {
        return wfod;
    }

    @Override
    public void create(TurnsDto dto) {

    }

    @Override
    public void update(TurnsDto dto) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
