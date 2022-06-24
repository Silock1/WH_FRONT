package com.warehouse_accounting.components.production.forms;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
            if (!productionOrderDtoBinder.validate().isOk()) {
                return;
            }

            if (selectStage.getComponentCount() < 1) {
                showErrorNotification("Технологическая карта не может быть пустым");
                return;
            }

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


        TextField numberField = new TextField();
        numberField.setLabel("Номер");
        numberField.setMinWidth("300px");
        productionOrderDtoBinder
                .forField(numberField)
                .asRequired("Наименование не должно быть пустым!")
                .bind(ProductionOrderDto::getNumber, ProductionOrderDto::setNumber);
        numberField.setValue(productionOrderDto.getNumber() == null ? "" : productionOrderDto.getNumber());

        //field data
        TextField dataField = new TextField();
        numberField.setLabel("Номер");
        numberField.setMinWidth("300px");

        //field tech map
        TextField techMapField = new TextField();
        numberField.setLabel("Номер");
        numberField.setMinWidth("300px");

        //field volume
        TextField volumeField = new TextField();
        numberField.setLabel("Номер");
        numberField.setMinWidth("300px");

        //field warehouse
        TextField warehouseField = new TextField();
        numberField.setLabel("Номер");
        numberField.setMinWidth("300px");

        VerticalLayout returnLayout = new VerticalLayout();
        returnLayout.setAlignItems(Alignment.START);
        returnLayout.add(numberField, dataField, techMapField, volumeField, warehouseField);
        return returnLayout;
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
}

