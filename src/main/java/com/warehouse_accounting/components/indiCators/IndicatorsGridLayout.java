package com.warehouse_accounting.components.indiCators;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.models.dto.StatsDto;
import com.warehouse_accounting.services.interfaces.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
@UIScope
public class IndicatorsGridLayout extends VerticalLayout {

    @Autowired
    public IndicatorsGridLayout(StatsService statsService) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        StatsDto statsDto = statsService.getStatsDto();

        VerticalLayout payments = new VerticalLayout();
        Label titlePaymentsCount = new Label("Количество продаж:");
        Label countPayments = new Label(Integer.toString(statsDto.getPaymentCount()));
        Label titleBalanceOfPayments = new Label("Сумма продаж:");
        Label sumOfAllPayments = new Label(statsDto.getPaymentAmount().toString());
        payments.add(titlePaymentsCount, countPayments, titleBalanceOfPayments, sumOfAllPayments);

        VerticalLayout expiredOrders = new VerticalLayout();
        Label titleOrdersCount = new Label("Просроченные заказы:");
        Label countExpiredOrders = new Label(Integer.toString(statsDto.getExpiredOrdersCount()));
        Label titleOrdersSum = new Label("Сумма просроченных заказов:");
        Label ordersSum = new Label(statsDto.getExpiredOrdersAmount().toString());
        expiredOrders.add(titleOrdersCount, countExpiredOrders, titleOrdersSum, ordersSum);

        VerticalLayout companyLayout = new VerticalLayout();
        Label nameCompany = new Label("Название компании:");
        Label company = new Label(statsDto.getCompany());
        Label balanceCompany = new Label("Остаток на счете:");
        Label companyBalance = new Label(statsDto.getCompanyBalance().toString());
        companyLayout.add(nameCompany, company, balanceCompany, companyBalance);


        horizontalLayout.add(payments, expiredOrders, companyLayout);
        add(horizontalLayout);
    }
}
