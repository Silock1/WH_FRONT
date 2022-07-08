package com.warehouse_accounting.components.production.forms;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.production.ProductionOrders;
import com.warehouse_accounting.models.dto.ProductionOrderDto;
import com.warehouse_accounting.services.interfaces.ProductionOrderService;
import lombok.extern.log4j.Log4j2;

import java.util.Comparator;
import java.util.List;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY_INLINE;

@UIScope
@Log4j2
public class ProductionOrdersForm extends VerticalLayout {
    private final ProductionOrders productionOrders;
    private final ProductionOrderService productionOrderService;
    private final Div returnDiv;
    private final VerticalLayout selectStage = new VerticalLayout();
    private final List<ProductionOrderDto> productionOrderDtoList;
    private Binder<ProductionOrderDto> productionOrderDtoBinder = new Binder<>(ProductionOrderDto.class);
    private ProductionOrderDto productionOrderDto;

    private Select<String> formTechMap; //Input формы тех карта
    private Select<String> formWareHouse; //Input формы склад для материалов
    private TextField formVolume;

   private TextField formNumber;
    private DatePicker dateIncomingNumber;



    public ProductionOrdersForm(
            ProductionOrders productionOrders,
            ProductionOrderDto productionOrderDto,
            ProductionOrderService productionOrderService
    ) {
        this.productionOrders = productionOrders;
        this.productionOrderDto = productionOrderDto;
        this.productionOrderService = productionOrderService;
        this.productionOrderDtoList = productionOrderService.getAll();
        this.productionOrderDtoList.sort(Comparator.comparingLong(ProductionOrderDto::getId));
        this.returnDiv = productionOrders.getPageContent();

        productionOrders.removeAll();
        add(createTopGroupElements(), createInputFieldForm());

    }

    private HorizontalLayout createTopGroupElements() {
        HorizontalLayout topPartLayout = new HorizontalLayout();
        topPartLayout.setAlignItems(Alignment.CENTER);
        topPartLayout.add(
                continueProductionOrderButton(),
                cancelProductionOrderButton()
        );
        return topPartLayout;
    }

    private Button continueProductionOrderButton() {
        Button continueButton = new Button("Продолжить");
        continueButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        continueButton.addClickListener(c -> {
            productionOrderService.create(
                    ProductionOrderDto.builder()
                            .id(productionOrderDto.getId())
                            .number(formNumber.getValue())
                            .technologicalMapName((formTechMap.getValue()))
                            .warehouseName(formWareHouse.getValue())
                            .build()
            );
            UI.getCurrent().navigate(ProductionOrders.class);

            productionOrders.removeAll();
            productionOrders.add(returnDiv);


        });
        return continueButton;

    }

    private Button cancelProductionOrderButton() {
        Button cancelButton = new Button("Отмена");
        cancelButton.addClickListener(c -> {
            productionOrders.removeAll();
            productionOrders.add(returnDiv);
        });
        return cancelButton;

    }

    private VerticalLayout createInputFieldForm() {
        VerticalLayout verticalLayout = new VerticalLayout();

//Первая строка с номером
        FormLayout formLayout1 = new FormLayout();

        formNumber = new TextField();
        formNumber.setWidth("270px");


        formLayout1.addFormItem(formNumber, "Номер");
        formLayout1.addClassName("formLayout1");

        productionOrderDtoBinder
                .forField(formNumber)
                .bind(ProductionOrderDto::getNumber, ProductionOrderDto::setNumber);


        //Вторая строка с датой
        FormLayout formLayout2 = new FormLayout();
        dateIncomingNumber = new DatePicker();

        formLayout2.addFormItem(dateIncomingNumber, "Дата");
        formLayout2.addClassName("formLayout2");

        //Третья строка с тех картой
        FormLayout formLayout3 = new FormLayout();
        formTechMap = new Select<>();
        formTechMap.setItems("Технологическая карта 1", "Технологическая карта 2", "Технологическая карта 3");
        formTechMap.setWidth("270px");

        formLayout3.addFormItem(formTechMap, "Технологическая карта");
        formLayout3.addClassName("formLayout3");

        //Четвертая строка с объемом
        FormLayout formLayout4 = new FormLayout();

        formVolume = new TextField();
        formVolume.setWidth("270px");

        formLayout4.addFormItem(formVolume, "Объем");
        formLayout4.addClassName("formLayout4");

        //Пятая строка с склад
        FormLayout formLayout5 = new FormLayout();
        formWareHouse = new Select<>();
        formWareHouse.setItems("Основной склад", "Резервный склад");
        formWareHouse.setWidth("270px");

        formLayout5.addFormItem(formWareHouse, "Склад для материалов");
        formLayout5.addClassName("formLayout5");

        verticalLayout.add(formLayout1, formLayout2, formLayout3, formLayout4, formLayout5);
        return verticalLayout;

    }


    private void showErrorNotification(String showText) {
        Notification notification = new Notification();
        notification.setPosition(Notification.Position.TOP_START);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Icon icon = VaadinIcon.WARNING.create();
        icon.getElement().getStyle().set("margin-right", "var(--lumo-space-m)");
        Button closeBtn = new Button(
                VaadinIcon.CLOSE_SMALL.create(),
                clickEvent -> notification.close());
        closeBtn.addThemeVariants(LUMO_TERTIARY_INLINE);

        HorizontalLayout layout = new HorizontalLayout();
        layout.setAlignItems(Alignment.CENTER);
        Text text = new Text(showText);
        layout.add(icon, text, closeBtn);
        layout.setMaxWidth("350px");

        notification.add(layout);
        notification.open();
    }

    private HorizontalLayout spaceGenerator(int count) {
        HorizontalLayout space = new HorizontalLayout();
        for (int i = 0; i < count; i++) {
            space.add(new HorizontalLayout());
        }
        return space;
    }
}

