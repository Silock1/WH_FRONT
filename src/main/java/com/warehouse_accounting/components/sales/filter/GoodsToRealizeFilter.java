package com.warehouse_accounting.components.sales.filter;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.models.dto.CompanyDto;
import com.warehouse_accounting.models.dto.ContractDto;
import com.warehouse_accounting.models.dto.ContractorDto;
import com.warehouse_accounting.models.dto.ProductGroupDto;
import com.warehouse_accounting.services.interfaces.CompanyService;
import com.warehouse_accounting.services.interfaces.ContractService;
import com.warehouse_accounting.services.interfaces.ContractorService;
import com.warehouse_accounting.services.interfaces.ProductGroupService;

import java.io.IOException;
import java.util.List;

@SpringComponent
@UIScope
public class GoodsToRealizeFilter extends VerticalLayout {

    private final ContractorService contractorService;
    private final ContractService contractService;
    private final CompanyService companyService;
    private final ProductGroupService productGroupService;

    public GoodsToRealizeFilter(ContractorService contractorService,
                                ContractService contractService,
                                CompanyService companyService,
                                ProductGroupService productGroupService) {
        this.contractorService = contractorService;
        this.contractService = contractService;
        this.companyService = companyService;
        this.productGroupService = productGroupService;

        HorizontalLayout layout_one = getHorizontalLayoutOne();
        HorizontalLayout layout_two = getHorizontalLayoutTwo();
        add(layout_one, layout_two);
        setSizeFull();
        actionClear();
        setVisible(false);
    }

    Button find = new Button("Найти");
    Button clear = new Button("Очистить");
    Button settingFilter = new Button(new Icon(VaadinIcon.COG));
    Button bookmarks = new Button(new Icon(VaadinIcon.BOOKMARK));
    DatePicker period_start_date = new DatePicker("На дату ");
    ComboBox<String> noReport = new ComboBox<>("Не отчитались", "Любое", "Больше нуля", "Ноль");
    ComboBox<ProductGroupDto> goodsOrGroup = new ComboBox<>("Товар или група");
    ComboBox<ContractorDto> contragent = new ComboBox<>("Контрагент");
    ComboBox<ContractDto> contract = new ComboBox<>("Договор");
    ComboBox<CompanyDto> organization = new ComboBox<>("Организация");


    private HorizontalLayout getHorizontalLayoutOne(){
        var horizontalLayout = new HorizontalLayout();
        find.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        clear.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        bookmarks.addClickListener(buttonClickEvent -> addBookmarks());

        List<ProductGroupDto> productGroupDtos = productGroupService.getAll();
        goodsOrGroup.setItems(productGroupDtos);
        goodsOrGroup.setItemLabelGenerator(ProductGroupDto::getName);

        List<ContractorDto> contractorDtos = contractorService.getAll();
        contragent.setItems(contractorDtos);
        contragent.setItemLabelGenerator(ContractorDto::getName);

        horizontalLayout.add(find, clear, bookmarks, settingFilter, period_start_date,
                noReport, goodsOrGroup, contragent);
        return horizontalLayout;
    }
    private HorizontalLayout getHorizontalLayoutTwo() {
        var horizontalLayout = new HorizontalLayout();

        List<ContractDto> contractDtos = contractService.getAll();
        contract.setItems(contractDtos);
        contract.setItemLabelGenerator(ContractDto::getNumber);

        List<CompanyDto> companyDtos = null;
        try {
            companyDtos = companyService.getAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        organization.setItems(companyDtos);
        organization.setItemLabelGenerator(CompanyDto::getName);

        horizontalLayout.add(contract, organization);
        return horizontalLayout;
    }

    private void actionClear(){
        clear.addClickListener(buttonClickEvent -> {
            period_start_date.clear();
            noReport.clear();
            goodsOrGroup.clear();
            contragent.clear();
            contract.clear();
            organization.clear();
        });
    }

    private void addBookmarks(){
        Dialog dialog = new Dialog();
        dialog.getElement().setAttribute("aria-label", "Create new employee");

        VerticalLayout dialogLayout = createDialogLayout(dialog);
        dialog.add(dialogLayout);
        bookmarks.addClickListener(buttonClickEvent -> dialog.open());
        add(dialog);
    }

    private static VerticalLayout createDialogLayout(Dialog dialog) {
        H2 headline = new H2("Закладки");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");

        TextField firstNameField = new TextField("Название");
        VerticalLayout fieldLayout = new VerticalLayout(firstNameField);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);

        Button cancelButton = new Button("Отменить", e -> dialog.close());
        Button saveButton = new Button("Сохранить закладку", e -> dialog.close());
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, cancelButton);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        VerticalLayout dialogLayout = new VerticalLayout(headline, fieldLayout, buttonLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "300px").set("max-width", "100%");

        return dialogLayout;
    }

}
