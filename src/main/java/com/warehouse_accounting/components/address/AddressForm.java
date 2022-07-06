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

public class AddressForm {
    private final CountryService countryService;
    private final RegionService regionService;
    private final CityService cityService;
    private final StreetService streetService;
    private final BuildingService buildingService;

    private Long id = null;
    private String cityName;
    private String streetName;
    private String buildingName;

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
        streetComboBox.setDataProvider(getStreetDataProvider());
        form.addFormItem(streetComboBox, "Улица");

        buildingComboBox = new ComboBox<>();
        buildingComboBox.setItemLabelGenerator(BuildingDto::getName);
        buildingComboBox.setAllowCustomValue(true);
        buildingComboBox.setDataProvider(getBuildingDataProvider());
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
            generateFullName();
        });

        cityComboBox.addCustomValueSetListener(e -> {
            cityName = e.getDetail();
            generateFullName();
        });

        streetComboBox.addCustomValueSetListener(e -> {
            streetName = e.getDetail();
            generateFullName();
        });

        buildingComboBox.addCustomValueSetListener(e -> {
            buildingName = e.getDetail();
            generateFullName();
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
                .cityName(cityName)
                .streetId((streetComboBox.getValue() == null) ? null : streetComboBox.getValue().getId())
                .streetName(streetName)
                .buildingId((buildingComboBox.getValue() == null) ? null : buildingComboBox.getValue().getId())
                .buildingName(buildingName)
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

        if (model.getCountryId() != null) {
            countryComboBox.setValue(this.countryService.getById(model.getCountryId()));
        }

        if (model.getRegionId() != null) {
            RegionDto region = this.regionService.getById(model.getRegionId());
            regionComboBox.setValue(region);
        }

        CityDto city;
        if (model.getCityId() != null) {
            city = this.cityService.getById(model.getCityId());
        } else {
            city = CityDto.builder().name(model.getCityName()).build();
        }
        cityName = model.getCityName();
        cityComboBox.setValue(city);

        StreetDto street;
        if (model.getStreetId() != null) {
            street = this.streetService.getById(model.getStreetId());
        } else {
            street = StreetDto.builder().name(model.getStreetName()).build();
        }
        streetName = model.getStreetName();
        streetComboBox.setValue(street);

        BuildingDto building;
        if (model.getBuildingId() != null) {
            building = this.buildingService.getById(model.getBuildingId());
        } else {
            building = BuildingDto.builder().name(model.getBuildingName()).build();
        }
        buildingName = model.getBuildingName();
        buildingComboBox.setValue(building);

        officeTextField.setValue(model.getOffice());

        otherTextField.setValue(model.getOther());

        commentTextField.setValue(model.getComment());

        fullAddressTextArea.setValue(model.getFullAddress());
    }

    private void generateFullName() {
        StringBuilder str = new StringBuilder()
                .append((postCodeTextArea.getValue().isEmpty()) ? "" : postCodeTextArea.getValue() + ", ")
                .append((countryComboBox.getValue() == null) ? "" : countryComboBox.getValue().getShortName() + ", ")
                .append((regionComboBox.getValue() == null) ? "" : regionComboBox.getValue().getNameSocr() + ", ")
                .append((cityName == null) ? "" : cityName + ", ")
                .append((streetName == null) ? "" : streetName + ", ")
                .append((buildingName == null) ? "" : buildingName + ", ")
                .append((officeTextField.getValue().isEmpty()) ? "" : officeTextField.getValue() + ", ")
                .append((otherTextField.getValue().isEmpty()) ? "" : otherTextField.getValue() + ", ");

        if (str.length() > 2) {
            str = str.deleteCharAt(str.length() - 1).deleteCharAt(str.length() - 1);
        }

        fullAddressTextArea.setValue(str.toString());
    }

    private DataProvider<CityDto, String> getCityDataProvider() {
        return DataProvider.fromFilteringCallbacks(
                query -> {
                    String filter = query.getFilter().orElse(null);
                    return cityService.getSlice(
                            query.getOffset(),
                            query.getLimit(),
                            filter,
                            (regionComboBox.getValue() == null) ? "" : regionComboBox.getValue().getCode()
                    ).stream();
                    },
                query -> {
                    String filter = query.getFilter().orElse(null);
                    return cityService.getCount(
                            filter,
                            (regionComboBox.getValue() == null) ? "" : regionComboBox.getValue().getCode()
                    );
                });
    }

    private DataProvider<StreetDto, String> getStreetDataProvider() {
        return DataProvider.fromFilteringCallbacks(
                query -> {
                    String filter = query.getFilter().orElse(null);
                    return streetService.getSlice(query.getOffset(), query.getLimit(), filter, getCodeForStreetDataProvider()).stream();
                },
                query -> {
                    String filter = query.getFilter().orElse(null);
                    return streetService.getCount(filter, getCodeForStreetDataProvider());
                });
    }

    private String getCodeForStreetDataProvider() {
        if (cityComboBox.getValue() != null) {
            return cityComboBox.getValue().getCode();
        } else if (regionComboBox.getValue() != null) {
            return regionComboBox.getValue().getCode();
        } else {
            return "";
        }
    }

    private DataProvider<BuildingDto, String> getBuildingDataProvider() {
        return DataProvider.fromFilteringCallbacks(
                query -> {
                    String filter = query.getFilter().orElse(null);
                    return buildingService.getSlice(
                            query.getOffset(),
                            query.getLimit(),
                            filter,
                            getCodeForBuildingDataProvider()
                    ).stream();
                },
                query -> {
                    String filter = query.getFilter().orElse(null);
                    return buildingService.getCount(filter, getCodeForBuildingDataProvider());
                });
    }

    private String getCodeForBuildingDataProvider() {
        if (streetComboBox.getValue() != null) {
            return streetComboBox.getValue().getCode();
        } else if (cityComboBox.getValue() != null) {
            return cityComboBox.getValue().getCode();
        } else if (regionComboBox.getValue() != null) {
            return regionComboBox.getValue().getCode();
        } else {
            return "";
        }
    }
}
