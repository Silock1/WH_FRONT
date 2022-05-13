package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.StatsDto;
import com.warehouse_accounting.services.interfaces.StatsService;
import com.warehouse_accounting.services.interfaces.api.StatsApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Log4j2
@Service
public class StatsServiceImpl implements StatsService {

    private final String url = "api/stats";
    private final StatsApi statsApi = buildRetrofit().create(StatsApi.class);

    Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://localhost:4446")
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    @Override
    public StatsDto getStatsDto() {
        StatsDto statsDto = null;
        Call<StatsDto> callSync = statsApi.getStats(url);
        try {
            Response<StatsDto> response = callSync.execute();
            if (response.isSuccessful()) {
                statsDto = response.body();
                log.info("Успешно выполнен запрос на получение Stats!");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение StatsDto!", response.code());
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение StatsDto!", e);
        }
        return statsDto;
    }
}
