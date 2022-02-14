package com.warehouse_accounting.services.impl;

    import com.warehouse_accounting.models.dto.SalesChannelDto;

    import com.warehouse_accounting.services.interfaces.SalesChannelsService;
    import com.warehouse_accounting.services.interfaces.api.SalesChannelsApi;
    import lombok.extern.log4j.Log4j2;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Service;
    import retrofit2.Call;
    import retrofit2.Response;
    import retrofit2.Retrofit;
    import retrofit2.converter.gson.GsonConverterFactory;

    import java.io.IOException;
    import java.util.Collections;
    import java.util.List;

@Log4j2
@Service
public class SalesChannelsServiceImpl implements SalesChannelsService {

    private final String channelsUrl = "/api/comissioner_reports";
    private final SalesChannelsApi channelsApi = buildRetrofit().create(SalesChannelsApi.class);

    Retrofit buildRetrofit() {
        return new Retrofit.Builder()
            .baseUrl("http://localhost:4446")
            .addConverterFactory(GsonConverterFactory.create()).build();
    }

    @Override
    public List<SalesChannelDto> getAll() {
        List<SalesChannelDto> channelsDtoList = Collections.emptyList();
        Call<List<SalesChannelDto>> channelsGetAllCall = channelsApi.getAll(channelsUrl);
        try {
            Response<List<SalesChannelDto>> response = channelsGetAllCall.execute();
            if (response.isSuccessful()) {
                channelsDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка SalesChannelDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка SalesChannelDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка SalesChannelDto", e);
        }
        return channelsDtoList;
    }

    @Override
    public SalesChannelDto getById(Long id) {
        SalesChannelDto channelDto = null;
        Call<SalesChannelDto> callSync = channelsApi.getById(channelsUrl, id);
        try {
            Response<SalesChannelDto> response = callSync.execute();
            if (response.isSuccessful()) {
                channelDto = response.body();
                log.info("Успешно выполнен запрос на получение SalesChannelDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение SalesChannelDto по id {}",response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение SalesChannelDto по id", e);
        }
        return channelDto;
    }

    @Override
    public void create(SalesChannelDto dto) {
        try {
            Call<Void> voidCall = channelsApi.create(channelsUrl, dto);
            Response<Void> response = voidCall.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание SalesChannelDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создание SalesChannelDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создание SalesChannelDto", e);
        }
    }

    @Override
    public void update(SalesChannelDto dto) {
        Call<Void> call = channelsApi.update(channelsUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение SalesChannelDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение SalesChannelDto",response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменение SalesChannelDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = channelsApi.deleteById(channelsUrl, id);
        try {
            Response<Void> response = call.execute();
            if(response.isSuccessful()){
                log.info("Успешно выполнен запрос на удаление SalesChannelDto");
            }else{
                log.error("Произошла ошибка {} при выполнении запроса на удаление SalesChannelDto по id",response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление SalesChannelDto по id", e);
        }

    }
}

