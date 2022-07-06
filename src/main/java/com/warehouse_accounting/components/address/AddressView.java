package com.warehouse_accounting.components.address;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.models.dto.AddressDto;
import com.warehouse_accounting.services.interfaces.AddressService;
import com.warehouse_accounting.services.interfaces.BuildingService;
import com.warehouse_accounting.services.interfaces.CityService;
import com.warehouse_accounting.services.interfaces.CountryService;
import com.warehouse_accounting.services.interfaces.RegionService;
import com.warehouse_accounting.services.interfaces.StreetService;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
@Route(value = "address")
public class AddressView extends VerticalLayout {
    private final AddressService addressService;
    private final CountryService countryService;
    private final RegionService regionService;
    private final CityService cityService;
    private final StreetService streetService;

    private final BuildingService buildingService;

    @Autowired
    public AddressView(
            AddressService addressService,
            CountryService countryService,
            RegionService regionService,
            CityService cityService,
            StreetService streetService,
            BuildingService buildingService
    ) {
        this.addressService = addressService;
        this.countryService = countryService;
        this.regionService = regionService;
        this.cityService = cityService;
        this.streetService = streetService;
        this.buildingService = buildingService;

        FormLayout form = new FormLayout();

        AddressForm addressForm = new AddressForm(
                countryService,
                regionService,
                cityService,
                streetService,
                buildingService,
                "Фактический адрес",
                form
        );

        add(form);


        TextField t = new TextField("ID");
        Button open = new Button("Open");
        open.addClickListener(e -> {
            AddressDto address = addressService.getById(Long.parseLong(t.getValue()));
            addressForm.setValue(address);
        });
        add(t);
        add(open);


        Button submit = new Button("Save new");
        submit.addClickListener(e -> {
            if (addressForm.getValue().getId() == null) {
                addressService.create(addressForm.getValue());
            } else {
                System.out.println("Не удалось сохранить new, так как ключ не ноль (объект нужно обновить)");
            }
        });
        add(submit);

        Button update = new Button("Save update");
        update.addClickListener(e -> {
            if (addressForm.getValue().getId() != null) {
                addressService.update(addressForm.getValue());
            } else {
                System.out.println("Не удалось обновить, так как ключ ноль (объект нужно сохранить new)");
            }
        });
        add(update);

        Button delete = new Button("Del");
        delete.addClickListener(e -> {
            if (addressForm.getValue().getId() != null) {
                addressService.deleteById(addressForm.getValue().getId());
            } else {
                System.out.println("Не удалось удалить, так как ключ ноль (объект нужно сохранить new)");
            }
        });
        add(delete);

    }
}
