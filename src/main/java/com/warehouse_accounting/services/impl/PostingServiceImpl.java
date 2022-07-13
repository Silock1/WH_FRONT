package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.CompanyDto;
import com.warehouse_accounting.models.dto.PostingDto;
import com.warehouse_accounting.models.dto.ProductionOrderDto;
import com.warehouse_accounting.models.dto.WriteOffsDto;
import com.warehouse_accounting.services.interfaces.PostingService;
import com.warehouse_accounting.services.interfaces.WriteOffsService;
import com.warehouse_accounting.services.interfaces.api.PostingApi;
import com.warehouse_accounting.services.interfaces.api.WriteOffsApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Log4j2
public class PostingServiceImpl implements PostingService {
    private final PostingApi postingApi;
    private final String postingUrl;

    public PostingServiceImpl(@Value("${retrofit.restServices.posting_url}") String postingUrl, Retrofit retrofit) {
        this.postingApi = retrofit.create(PostingApi.class);
        this.postingUrl = postingUrl;
    }

    //убрать после доработки
    private PostingDto wfod = new PostingDto(1L, null, null, null, null, true, true, "this is comment");


    @Override
    public List<PostingDto> getAll() {
//        List<PostingDto> list = new ArrayList<PostingDto>();
//        return list;

        List<PostingDto> postingDtoList = new ArrayList<>();
        Call<List<PostingDto>> listCall = postingApi.getAll(postingUrl);
        try {
            Response<List<PostingDto>> response = listCall.execute();
            if (response.isSuccessful()) {
                postingDtoList = response.body();
                log.info("Успешно выполнен запрон на получение списка");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка ERRRRR", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка Errrrrr" + e);
        }
        return postingDtoList;
    }

    @Override
    public PostingDto getById(Long id) {
        return wfod;
    }

    @Override
    public void create(PostingDto dto) {

    }

    @Override
    public void update(PostingDto dto) {

    }

    @Override
    public void deleteById(Long id) {

    }
}