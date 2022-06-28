package com.warehouse_accounting.components.address;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.warehouse_accounting.models.dto.AddressDto;
import com.warehouse_accounting.models.dto.BuildingDto;
import com.warehouse_accounting.models.dto.CityDto;
import com.warehouse_accounting.models.dto.CountryDto;
import com.warehouse_accounting.models.dto.RegionDto;
import com.warehouse_accounting.models.dto.StreetDto;
import com.warehouse_accounting.services.interfaces.BuildingService;
import com.warehouse_accounting.services.interfaces.CityService;
import com.warehouse_accounting.services.interfaces.CountryService;
import com.warehouse_accounting.services.interfaces.RegionService;
import com.warehouse_accounting.services.interfaces.StreetService;

import java.util.ArrayList;
import java.util.List;

public class AddressForm {
    private Long id = null;

    private final CountryService countryService;
    private final RegionService regionService;
    private final CityService cityService;
    private final StreetService streetService;
    private final BuildingService buildingService;

    private List<CityDto> cities;
    private List<StreetDto> streets;
    private List<BuildingDto> buildings;

    private final TextArea fullAddressTextArea;
    private final TextArea postCodeTextArea;
    private final ComboBox<CountryDto> countryComboBox;
    private final ComboBox<RegionDto> regionComboBox;
    private final ComboBox<CityDto> cityComboBox;
    private final ComboBox<StreetDto> streetComboBox;
    private final ComboBox<BuildingDto> buildingComboBox;
    private final TextField officeTextField;
    private final TextField otherTextField;
    private final TextField commentTextField;

    public AddressForm(
            CountryService countryService,
            RegionService regionService,
            CityService cityService,
            StreetService streetService,
            BuildingService buildingService,
            String title,
            FormLayout form
    ) {
        this.countryService = countryService;
        this.regionService = regionService;
        this.cityService = cityService;
        this.streetService = streetService;
        this.buildingService = buildingService;

        fullAddressTextArea = new TextArea();
        fullAddressTextArea.getStyle().set("resize", "both");
        fullAddressTextArea.getStyle().set("overflow", "auto");
        form.addFormItem(fullAddressTextArea, title);

        postCodeTextArea = new TextArea();
        form.addFormItem(postCodeTextArea, "Индекс");

        countryComboBox = new ComboBox<>();
        countryComboBox.setItems(this.countryService.getAll());
        countryComboBox.setItemLabelGenerator(CountryDto::getShortName);
        countryComboBox.setClearButtonVisible(true);
        form.addFormItem(countryComboBox, "Страна");

        regionComboBox = new ComboBox<>();
        regionComboBox.setItems(this.regionService.getAll());
        regionComboBox.setItemLabelGenerator(RegionDto::getNameSocr);
        regionComboBox.setClearButtonVisible(true);
        form.addFormItem(regionComboBox, "Регион");

        cityComboBox = new ComboBox<>();
        cityComboBox.setItemLabelGenerator(CityDto::getNameSocr);
        cityComboBox.setAllowCustomValue(true);
        cityComboBox.setDataProvider(getCityDataProvider());
        form.addFormItem(cityComboBox, "Город");

        streetComboBox = new ComboBox<>();
        streetComboBox.setItemLabelGenerator(StreetDto::getNameSocr);
        streetComboBox.setAllowCustomValue(true);
        form.addFormItem(streetComboBox, "Улица");

        buildingComboBox = new ComboBox<>();
        buildingComboBox.setItemLabelGenerator(BuildingDto::getName);
        buildingComboBox.setAllowCustomValue(true);
        form.addFormItem(buildingComboBox, "Дом");

        officeTextField = new TextField();
        form.addFormItem(officeTextField, "Квартира/Офис");

        otherTextField = new TextField();
        form.addFormItem(otherTextField, "Другое");

        commentTextField = new TextField();
        commentTextField.getStyle().set("resize", "both");
        commentTextField.getStyle().set("overflow", "auto");
        form.addFormItem(commentTextField, "Комментарий к адресу");

        postCodeTextArea.addValueChangeListener(e -> {
            generateFullName();
        });

        countryComboBox.addValueChangeListener(e -> {
            generateFullName();
        });

        regionComboBox.addValueChangeListener(e -> {
            if (regionComboBox.getValue() != null) {
                cityComboBox.setItems(this.cityService.getAll(regionComboBox.getValue().getCode()));
            } else {
                cityComboBox.setItems();
            }

            generateFullName();
        });

        cityComboBox.addValueChangeListener(e -> {
            if (cityComboBox.getValue() != null) {
                streetComboBox.setItems(this.streetService.getAll(cityComboBox.getValue().getCode()));
            } else {
                streetComboBox.setItems();
            }

            generateFullName();
        });

        cityComboBox.addCustomValueSetListener(e -> {
            CityDto custom = CityDto.builder().name(e.getDetail()).build();

            if (cities == null) {
                cities = new ArrayList<>();
            }
            cities.add(custom);

            cityComboBox.setItems(cities);
            cityComboBox.setValue(custom);
        });

        streetComboBox.addValueChangeListener(e -> {
            if (streetComboBox.getValue() != null) {
                buildingComboBox.setItems(this.buildingService.getAll(streetComboBox.getValue().getCode()));
            } else {
                buildingComboBox.setItems();
            }

            generateFullName();
        });

        streetComboBox.addCustomValueSetListener(e -> {
            StreetDto custom = StreetDto.builder().name(e.getDetail()).build();

            if (streets == null) {
                streets = new ArrayList<>();
            }
            streets.add(custom);

            streetComboBox.setItems(streets);
            streetComboBox.setValue(custom);
        });

        buildingComboBox.addValueChangeListener(e -> {
            generateFullName();
        });

        buildingComboBox.addCustomValueSetListener(e -> {
            BuildingDto custom = BuildingDto.builder().name(e.getDetail()).build();

            if (buildings == null) {
                buildings = new ArrayList<>();
            }
            buildings.add(custom);

            buildingComboBox.setItems(buildings);
            buildingComboBox.setValue(custom);
        });

        officeTextField.addValueChangeListener(e -> {
            generateFullName();
        });

        otherTextField.addValueChangeListener(e -> {
            generateFullName();
        });
    }

    public AddressDto getValue() {
        return AddressDto.builder()
                .id(id)
                .countryId((countryComboBox.getValue() == null) ? null : countryComboBox.getValue().getId())
                .postCode(postCodeTextArea.getValue())
                .regionId((regionComboBox.getValue() == null) ? null : regionComboBox.getValue().getId())
                .cityId((cityComboBox.getValue() == null) ? null : cityComboBox.getValue().getId())
                .cityName((cityComboBox.getValue() == null) ? null : cityComboBox.getValue().getName())
                .streetId((streetComboBox.getValue() == null) ? null : streetComboBox.getValue().getId())
                .streetName((streetComboBox.getValue() == null) ? null : streetComboBox.getValue().getName())
                .buildingId((buildingComboBox.getValue() == null) ? null : buildingComboBox.getValue().getId())
                .buildingName((buildingComboBox.getValue() == null) ? null : buildingComboBox.getValue().getName())
                .office(officeTextField.getValue())
                .fullAddress(fullAddressTextArea.getValue())
                .other(otherTextField.getValue())
                .comment(commentTextField.getValue())
                .build();
    }

    public void setValue(AddressDto model) {
        if (model == null) {
            return;
        }

        id = model.getId();

        postCodeTextArea.setValue(model.getPostCode());

        countryComboBox.setValue(this.countryService.getById(model.getCountryId()));

        RegionDto region = this.regionService.getById(model.getRegionId());
        regionComboBox.setValue(region);

        CityDto city;
        if (model.getCityId() != null) {
            city = this.cityService.getById(model.getCityId());
            cities = this.cityService.getAll(region.getCode());
        } else {
            city = CityDto.builder().name(model.getCityName()).build();
            cities = new ArrayList<>();
            cities.add(city);
        }
        cityComboBox.setItems(cities);
        cityComboBox.setValue(city);

        StreetDto street;
        if (model.getStreetId() != null) {
            street = this.streetService.getById(model.getStreetId());
            if (city.getCode() == null) {
                streets = new ArrayList<>();
            } else {
                streets = this.streetService.getAll(city.getCode());
            }
        } else {
            street = StreetDto.builder().name(model.getStreetName()).build();
            streets = new ArrayList<>();
            streets.add(street);
        }
        streetComboBox.setItems(streets);
        streetComboBox.setValue(street);

        BuildingDto building;
        if (model.getBuildingId() != null) {
            building = this.buildingService.getById(model.getBuildingId());
            if (street.getCode() == null) {
                buildings = new ArrayList<>();
            } else {
                buildings = this.buildingService.getAll(street.getCode());
            }
        } else {
            building = BuildingDto.builder().name(model.getBuildingName()).build();
            buildings = new ArrayList<>();
            buildings.add(building);
        }
        buildingComboBox.setItems(buildings);
        buildingComboBox.setValue(building);

        officeTextField.setValue(model.getOffice());

        otherTextField.setValue(model.getOther());

        commentTextField.setValue(model.getComment());
    }

    private void generateFullName() {
        StringBuilder str = new StringBuilder()
                .append((postCodeTextArea.getValue().isEmpty()) ? "" : postCodeTextArea.getValue() + ", ")
                .append((countryComboBox.getValue() == null) ? "" : countryComboBox.getValue().getShortName() + ", ")
                .append((regionComboBox.getValue() == null) ? "" : regionComboBox.getValue().getNameSocr() + ", ")
                .append((cityComboBox.getValue() == null) ? "" : cityComboBox.getValue().getNameSocr() + ", ")
                .append((streetComboBox.getValue() == null) ? "" : streetComboBox.getValue().getNameSocr() + ", ")
                .append((buildingComboBox.getValue() == null) ? "" : buildingComboBox.getValue().getName() + ", ")
                .append((officeTextField.getValue().isEmpty()) ? "" : officeTextField.getValue() + ", ")
                .append((otherTextField.getValue().isEmpty()) ? "" : otherTextField.getValue() + ", ");

        if (str.length() > 2) {
            str = str.deleteCharAt(str.length() - 1).deleteCharAt(str.length() - 1);
        }

        fullAddressTextArea.setValue(str.toString());
    }

    private DataProvider<CityDto, String> getCityDataProvider()
    {
        return DataProvider.fromFilteringCallbacks(
                query -> {
                    String filter = query.getFilter().orElse(null);
                    return cityService.getSlice(query.getOffset(), query.getLimit(), filter).stream();
                    },
                query -> {
                    String filter = query.getFilter().orElse(null);
                    return cityService.getCount(filter);
                });
    }
}
