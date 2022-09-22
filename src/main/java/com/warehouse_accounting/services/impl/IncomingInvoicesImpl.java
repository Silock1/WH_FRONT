package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.CompanyDto;
import com.warehouse_accounting.models.dto.IncomingVoicesDto;
import com.warehouse_accounting.models.dto.PriceListDto;
import com.warehouse_accounting.models.dto.RemainsDto;
import com.warehouse_accounting.models.dto.SerialNumbersDto;

import com.warehouse_accounting.services.interfaces.IncomingVoicesService;
import com.warehouse_accounting.services.interfaces.SerialNumbersService;

import com.warehouse_accounting.services.interfaces.api.SerialNumbersApi;
import com.warehouse_accounting.services.interfaces.api.WriteOffsApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class IncomingInvoicesImpl implements IncomingVoicesService {
    private final WriteOffsApi writeOffsApi;
    private final String writeOffsUrl;

    public IncomingInvoicesImpl(@Value("${retrofit.restServices.writeOffs_url}") String writeOffsUrl, Retrofit retrofit) {
        this.writeOffsApi = retrofit.create(WriteOffsApi.class);
        this.writeOffsUrl = writeOffsUrl;
    }

    //убрать после доработки
    private IncomingVoicesDto wfod = new IncomingVoicesDto(0l, LocalDate.now(), "Ot", "To", 1l,
            "Ok");


    @Override
    public List<IncomingVoicesDto> getAll() {
        List<IncomingVoicesDto> list = new ArrayList<IncomingVoicesDto>();
        list.add(wfod);
        return list;
    }

    @Override
    public IncomingVoicesDto getById(Long id) {
        return wfod;
    }

    @Override
    public void create(IncomingVoicesDto dto) {

    }

    @Override
    public void update(IncomingVoicesDto dto) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
