/**
 * так как форма Address является исключительно дочерней, и встраивается в другие формы,
 * для независимого тестирования был создан текущий класс,
 * в работе приложения текущий класс не участвует, поэтому может быть удалён
 */

package com.warehouse_accounting.components.address;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
//import com.warehouse_accounting.components.contragents.form.FormNewContragent;
import com.warehouse_accounting.models.dto.AddressDto;
import com.warehouse_accounting.models.dto.CountryDto;
import com.warehouse_accounting.services.interfaces.AddressService;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
@Route(value = "address")
@Deprecated
public class AddressView extends VerticalLayout {
    private final AddressService addressService;
    private final AddressForm addressForm;
//    private final FormNewContragent formNewContragent;

    @Autowired
    public AddressView(
            AddressService addressService,
            AddressForm addressForm
//            , FormNewContragent formNewContragent
    ) {
        this.addressService = addressService;
        this.addressForm = addressForm;
//        this.formNewContragent = formNewContragent;
//
//        formNewContragent.refres();
//        add(formNewContragent);

        /*add(addressForm.getNewForm("Фактический адрес"));


        ComboBox<AddressDto> addressComboBox = new ComboBox<>();
        addressComboBox.setItems(addressService.getAll());
        addressComboBox.setItemLabelGenerator(item -> item.getId().toString());
        add(addressComboBox);

        Button openButton = new Button("Open");
        openButton.addClickListener(e -> {
            AddressDto address = addressService.getById(addressComboBox.getValue().getId());
            addressForm.setValue(address);
        });
        add(openButton);


        Button createButton = new Button("Save create");
        createButton.addClickListener(e -> {
            if (addressForm.getValue().getId() == null) {
                addressService.create(addressForm.getValue());
                addressComboBox.setItems(addressService.getAll());
            } else {
                System.out.println("Не удалось сохранить, так как ключ не null (объект уже существует, его нужно обновить)");
            }
        });
        add(createButton);

        Button updateButton = new Button("Save update");
        updateButton.addClickListener(e -> {
            if (addressForm.getValue().getId() != null) {
                addressService.update(addressForm.getValue());
            } else {
                System.out.println("Не удалось обновить, так как ключ null (объект нужно сохранить как new - create)");
            }
        });
        add(updateButton);

        Button deleteButton = new Button("Del");
        deleteButton.addClickListener(e -> {
            if (addressForm.getValue().getId() != null) {
                addressService.deleteById(addressForm.getValue().getId());
                addressComboBox.setItems(addressService.getAll());
            } else {
                System.out.println("Не удалось удалить, так как ключ ноль (объект нужно сохранить new)");
            }
        });
        add(deleteButton);*/
    }
}
