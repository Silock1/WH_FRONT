package com.warehouse_accounting.services;

import lombok.extern.log4j.Log4j2;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Log4j2
public class ServiceUtils<T> {

    private final Class<T> type;

    public ServiceUtils(Class<T> type) {
        this.type = type;
    }

    public String getTypeName() {
        return type.getSimpleName();
    }

    public List<T> getAll(Call<List<T>> call) {
        List<T> list = Collections.emptyList();
        try {
            Response<List<T>> response = call.execute();
            if (response.isSuccessful()) {
                list = response.body();
                log.info("Успешно выполнен запрос на получение списка {}", getTypeName());
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка {}", response.code(), getTypeName());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка {}", getTypeName(), e);
        }
        return list;
    }

    public T getById(Call<T> call, Long id) {
        T dto = null;
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                dto = response.body();
                log.info("Успешно выполнен запрос на получение {} по id: {}", getTypeName(), id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение {} по id {}", response.code(), getTypeName(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение {} по id", getTypeName(), e);
        }
        return dto;
    }

    public void create(Call<Void> call) {
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание {}", getTypeName());
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании {}", response.code(), getTypeName());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании {}", getTypeName(), e);
        }
    }

    public void update(Call<Void> call) {
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение {}", getTypeName());
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение {}", response.code(), getTypeName());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменение {}", getTypeName(), e);
        }
    }

    public void delete(Call<Void> call) {
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление {}", getTypeName());
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление {}", response.code(), getTypeName());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление {} по id", getTypeName(), e);
        }
    }

    public Long count(Call<T> call) {
        Long dto = 0L;
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                dto = (Long) response.body();
                log.info("Успешно выполнен запрос на получение {} ", getTypeName());
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение {}", response.code(), getTypeName());
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение {}", getTypeName(), e);
        }
        return dto;
    }
    public List<T> getSlice(Call<List<T>> call, int offset, int limit, String name, String code) {
        List<T> list = Collections.emptyList();
        try {
            Response<List<T>> response = call.execute();
            if (response.isSuccessful()) {
                list = response.body();
                log.info("Успешно выполнен запрос на получение slice {} по offset: " +
                        "{}, limit: {}, name: {}, code {}", getTypeName(), offset, limit, name, code);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение slice {} по offset: " +
                        "{}, limit: {}, name: {}, code {}", getTypeName(), response.code(), offset, limit, name, code);
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение slice {} по offset: " +
                    "{}, limit: {}, name: {}, code {}", getTypeName(), offset, limit, name, code, e);
        }
        return list;
    }

    public int getCount(Call<Integer> call, String name, String code) {
        int count = 0;
        try {
            Response<Integer> response = call.execute();
            if (response.isSuccessful()) {
                Optional<Integer> opt = Optional.ofNullable(response.body());
                count = opt.orElse(0);
                log.info("Успешно выполнен запрос на получение count {} по name: " +
                        "{}, code {}", getTypeName(), name, code);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение count {} по name " +
                        "{}, code {}", getTypeName(), response.code(), name, code);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение count {} по name", getTypeName(), e);
        }
        return count;
    }

    public ResponseBody getResponseBody(Call<ResponseBody> call) {
        ResponseBody responseBody = null;
        try {
            Response<ResponseBody> response = call.execute();
            if (response.isSuccessful()) {
                responseBody = response.body();
                log.info("Успешно выполнен запрос на получение списка {} ", getTypeName());
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка {} ", response.code(), getTypeName());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка {} ", getTypeName(), e);
        }
        return responseBody;
    }

    public T getByCode(Call<T> call, String code) {
        T dto = null;
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                dto = response.body();
                log.info("Успешно выполнен запрос на получение {} по code: {}", getTypeName(), code);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение {} по code {}", getTypeName(), response.code(), code);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение {} по code", getTypeName(), e);
        }
        return dto;
    }

    public T getByFullAddress(Call<T> call, String fullAddress) {
        T dto = null;
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                dto = response.body();
                log.info("Успешно выполнен запрос на получение {} по id: {}", getTypeName(), fullAddress);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение {} по адресу {}", response.code(), getTypeName(), fullAddress);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение {} по id", getTypeName(), e);
        }
        return dto;
    }
}
