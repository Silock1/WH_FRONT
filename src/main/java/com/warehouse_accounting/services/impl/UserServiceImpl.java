package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.EmployeeDto;
import com.warehouse_accounting.models.dto.UserDto;
import com.warehouse_accounting.security.LoginSuccessHandler;
import com.warehouse_accounting.security.UserPrincipal;
import com.warehouse_accounting.services.interfaces.UserService;
import com.warehouse_accounting.services.interfaces.api.EmployeeApi;
import com.warehouse_accounting.services.interfaces.api.UserApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;

@Service
@Log4j2
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserApi userApi;
    private final String url;

    public UserServiceImpl(@Value("${retrofit.restServices.user_url}") String url, Retrofit retrofit) {
        this.userApi = retrofit.create(UserApi.class);
        this.url = url;
    }

    @Override
    public void getAuthUser(UserDto userDto) {
        Call<Void> voidCall = userApi.getAuthUser(url,  userDto);
        try {
            Response<Void> response = voidCall.execute();
            if (response.isSuccessful()) {
                new LoginSuccessHandler();
                log.info("{} отправлен успешно",  userDto);
            } else {
                log.error("При отправлении {} произошла ошибка: {}",  userDto, response.code());
            }
        } catch (IOException ioException) {
            log.error("Произошла ошибка при выполнении запроса на создание EmployeeDto",ioException);
        }

    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Call<EmployeeDto> call = userApi.getByUsername(url,s);
        EmployeeDto employeeDto = null;
        try {
            Response<EmployeeDto> response = call.execute();
            if (response.isSuccessful()) {
                employeeDto = response.body();
                log.info("{} получен успешно",  s);
            } else {
                log.error("При получении {} произошла ошибка: {}",  s, response.code());
            }
        } catch (IOException ioException) {
            log.error("Произошла ошибка при выполнении запроса на получение UserDetails", ioException);
        }
        UserDetails user = new UserPrincipal(employeeDto);
        log.info(user);
        return user;
    }
}
