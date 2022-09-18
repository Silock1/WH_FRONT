package com.warehouse_accounting.components.user.legalDetail.forms;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.address.AddressForm;
import com.warehouse_accounting.components.user.legalDetail.LegalDetailSettingsView;
import com.warehouse_accounting.components.user.settings.SettingsView;
import com.warehouse_accounting.models.dto.LegalDetailDto;
import com.warehouse_accounting.services.interfaces.LegalDetailService;
import lombok.extern.log4j.Log4j2;

@PageTitle("Добавить юр. лицо")
@Route(value = "addLegalDetailForm", layout = SettingsView.class)
@Log4j2
public class LegalDetailForm extends VerticalLayout {

    private LegalDetailService legalDetailService;
    private LegalDetailDto legalDetailDto;
    private Binder<LegalDetailDto> legalDetailDtoBinder;
    private AddressForm addressForm;

    public LegalDetailForm(LegalDetailService legalDetailService) {
        this.legalDetailService = legalDetailService;
        this.legalDetailDto = new LegalDetailDto();
        this.legalDetailDtoBinder = new Binder<>(LegalDetailDto.class);
        this.addressForm = addressForm;
        initForm();
        VerticalLayout form = initForm();
        add(form);
    }

    private VerticalLayout initForm() {
        VerticalLayout verticalLayout = new VerticalLayout();

        Button save = new Button("Сохранить", event -> {
            try {
                legalDetailDtoBinder.writeBean(legalDetailDto);
                legalDetailService.create(legalDetailDto);
            } catch (ValidationException ex) {
                log.error("Ошибка валидации при создании нового юр. лица", ex);
            }
        });

        Button close = new Button("Закрыть", event -> {
            UI.getCurrent().navigate(LegalDetailSettingsView.class);
        });

        HorizontalLayout groupButton = new HorizontalLayout();
        groupButton.setHeightFull();
        groupButton.add(save,close);

        FormLayout form = getFormLayout();
        HorizontalLayout groupForm = new HorizontalLayout();
        groupForm.add(form);
        verticalLayout.add(groupButton, groupForm);
        return verticalLayout;
    }

    private FormLayout getFormLayout() {
        FormLayout form = new FormLayout();

        TextField lastNameField = new TextField("Фамилия");
        lastNameField.setRequired(true);
        legalDetailDtoBinder.forField(lastNameField).asRequired().bind(LegalDetailDto::getLastName, LegalDetailDto::setLastName);
        form.add(lastNameField);

        TextField firstNameField = new TextField("Имя");
        legalDetailDtoBinder.forField(firstNameField).asRequired().bind(LegalDetailDto::getFirstName, LegalDetailDto::setFirstName);
        form.add(firstNameField);

        TextField middleNameField = new TextField("Отчество");
        legalDetailDtoBinder.forField(middleNameField).asRequired().bind(LegalDetailDto::getMiddleName, LegalDetailDto::setMiddleName);
        form.add(middleNameField);

        addressForm.addToForm("Комментарий", form);

        TextField inn = new TextField("ИНН");
        legalDetailDtoBinder.forField(inn).asRequired().bind(LegalDetailDto::getInn, LegalDetailDto::setInn);
        form.add(inn);

        TextField okpo = new TextField("ОКПО");
        legalDetailDtoBinder.forField(okpo).asRequired().bind(LegalDetailDto::getOkpo, LegalDetailDto::setOkpo);
        form.add(okpo);

        TextField ogrnip = new TextField("ОГРНИП");
        legalDetailDtoBinder.forField(ogrnip).asRequired().bind(LegalDetailDto::getOgrn, LegalDetailDto::setOgrn);
        form.add(ogrnip);

        TextField kpp = new TextField("КПП");
        legalDetailDtoBinder.forField(kpp).asRequired().bind(LegalDetailDto::getKpp, LegalDetailDto::setKpp);
        form.add(kpp);

        TextField numberOfSertificate = new TextField("Номер сертификата");
        legalDetailDtoBinder.forField(numberOfSertificate).asRequired().bind(LegalDetailDto::getNumberOfTheCertificate, LegalDetailDto::setNumberOfTheCertificate);
        form.add(numberOfSertificate);

//        TextField typeOfContract = new TextField("Тип контракта");
//        legalDetailDtoBinder.forField(typeOfContract).asRequired().bind(LegalDetailDto::getTypeOfContractorName, LegalDetailDto::setTypeOfContractorName);
//        form.add(typeOfContract);

        form.setWidthFull();
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        return form;
    }
}
