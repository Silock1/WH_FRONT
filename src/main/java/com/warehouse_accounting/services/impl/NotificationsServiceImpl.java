package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.NotificationsDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.NotificationsService;
import com.warehouse_accounting.services.interfaces.api.NotificationsApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class NotificationsServiceImpl implements NotificationsService {
    private final NotificationsApi api;
    private final String url;

    public NotificationsServiceImpl(Retrofit retrofit, @Value("${retrofit.restServices.notifications_url") String url) {
        this.api = retrofit.create(NotificationsApi.class);
        this.url = url;
    }
    

    @Override
    public List<NotificationsDto> getAll() {
        Call<List<NotificationsDto>> call = api.getAll(url);
        return new ServiceUtils<>(NotificationsDto.class).getAll(call);
    }

    @Override
    public NotificationsDto getById(Long id) {
        Call<NotificationsDto> call = api.getById(url, id);
        return new ServiceUtils<>(NotificationsDto.class).getById(call, id);
    }

    @Override
    public void create(NotificationsDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(NotificationsDto.class).create(call);
    }

    @Override
    public void update(NotificationsDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(NotificationsDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(NotificationsDto.class).delete(call);
    }
    
}
