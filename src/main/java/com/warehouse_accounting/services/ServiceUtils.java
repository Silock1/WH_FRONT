package com.warehouse_accounting.services;

import lombok.extern.log4j.Log4j2;
import retrofit2.Call;
import retrofit2.Response;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Log4j2
public class ServiceUtils<T> {

    private final Class<T> type;

    public ServiceUtils(Class<T> type) {
        this.type = type;
    }

    public String typeName() {
        return type.getSimpleName();
    }

    public List<T> getAll(Call<List<T>> call) {
        List<T> list = Collections.emptyList();
        try {
            Response<List<T>> response = call.execute();
            if (response.isSuccessful()) {
                list = response.body();
                log.info("Успешно выполнен запрос на получение списка {}", typeName());
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка {}", response.code(), typeName());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка {}", typeName(), e);
        }
        return list;
    }

    public T getById(Call<T> call, Long id) {
        T dto = null;
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                dto = response.body();
                log.info("Успешно выполнен запрос на получение {} по id: {}", typeName(), id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение {} по id {}", response.code(), typeName(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение {} по id", typeName(), e);
        }
        return dto;
    }

    public void create(Call<Void> call) {
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание {}", typeName());
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании {}", response.code(), typeName());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании {}", typeName(), e);
        }
    }

    public void update(Call<Void> call) {
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение {}", typeName());
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение {}", response.code(), typeName());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменение {}", typeName(), e);
        }
    }

    public void delete(Call<Void> call) {
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление {}", typeName());
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление {}", response.code(), typeName());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление {} по id", typeName(), e);
        }
    }

}
