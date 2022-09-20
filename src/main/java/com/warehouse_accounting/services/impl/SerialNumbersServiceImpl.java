package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.CompanyDto;
import com.warehouse_accounting.models.dto.PriceListDto;
import com.warehouse_accounting.models.dto.RemainsDto;
import com.warehouse_accounting.models.dto.SerialNumbersDto;

import com.warehouse_accounting.services.interfaces.SerialNumbersService;

import com.warehouse_accounting.services.interfaces.api.SerialNumbersApi;
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
public class SerialNumbersServiceImpl implements SerialNumbersService {
    private final WriteOffsApi writeOffsApi;
    private final String writeOffsUrl;

    public SerialNumbersServiceImpl(@Value("${retrofit.restServices.writeOffs_url}") String writeOffsUrl, Retrofit retrofit) {
        this.writeOffsApi = retrofit.create(WriteOffsApi.class);
        this.writeOffsUrl = writeOffsUrl;
    }

    //убрать после доработки
    private SerialNumbersDto wfod = new SerialNumbersDto(1l, 007l, 12441.23,
            "Рентгеноэлектрокардиографический аппарат",
    "НТЦ-Эксперт", "Накладная", 777l, "Какое слово, такое и описание...");


    @Override
    public List<SerialNumbersDto> getAll() {
        List<SerialNumbersDto> list = new ArrayList<SerialNumbersDto>();
        list.add(wfod);
        return list;
    }

    @Override
    public SerialNumbersDto getById(Long id) {
        return wfod;
    }

    @Override
    public void create(SerialNumbersDto dto) {

    }

    @Override
    public void update(SerialNumbersDto dto) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
