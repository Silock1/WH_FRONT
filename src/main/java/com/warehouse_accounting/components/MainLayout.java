package com.warehouse_accounting.components;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
//import com.warehouse_accounting.model.dto.TypeOfContractorDto;
import com.warehouse_accounting.services.impl.TypeOfContractorServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Route(value = "main", layout = AppView.class)
@RouteAlias(value = "", layout = AppView.class)
@PageTitle("Главная | CRM")
public class MainLayout extends VerticalLayout {

    public MainLayout() {


    }
}
