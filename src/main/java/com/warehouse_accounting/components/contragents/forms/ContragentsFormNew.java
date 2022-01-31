package com.warehouse_accounting.components.contragents.forms;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.models.dto.CountryDto;

@SpringComponent
@UIScope
public class ContragentsFormNew extends VerticalLayout {

//    private final Div parentLayer;
//    private final Component returnLayer;

    public ContragentsFormNew() {
//        this.parentLayer = parentLayer;
//        this.returnLayer = returnLayer;
        VerticalLayout form = initForm();
        add(form);
    }

    private VerticalLayout initForm() {
        VerticalLayout verticalLayout = new VerticalLayout();
        Button save = new Button("Сохранить", e->{

        });
        Button close = new Button("Закрыть", e-> {

        });

        HorizontalLayout groupButton = new HorizontalLayout();
        groupButton.setHeightFull();
        groupButton.add(save, close);

        VerticalLayout groupForm = new VerticalLayout();
        FormLayout formOne = getFormLayoutOne();
        FormLayout formTwo = getFormLayoutTwo();
        FormLayout formThree = getFormLayoutThree();
        groupForm.add(formOne, formTwo, formThree);
        verticalLayout.add(groupButton, groupForm);

        return verticalLayout;
    }

    private FormLayout getFormLayoutOne() {
        FormLayout form = new FormLayout(new Text("О контрагенте"));

        TextField nameField = new TextField("Наименование");

        ComboBox statusComboBox = new ComboBox("Статус");
        statusComboBox.setItems("Новый", "Выслано предложение", "Переговоры", "Сделка заключена", "Сделка не заключена", "Настроить");

        ComboBox groupComboBox= new ComboBox("Группы");
        groupComboBox.setItems("Первая группа");

        TextField telTextField= new TextField("Телефон");
        TextField faxTextField = new TextField("Факс");
        TextField emailTextField = new TextField("Email");
        TextField factAddress = new TextField("Фактический адрес"); // Добавить кнопку + со списком полей
        TextField commentAddress = new TextField("Комменатрий к адресу");
        TextField comment = new TextField("Комментарий");
        TextField code = new TextField("Код");
        TextField externalCode = new TextField("Внешний код");

        form.add(nameField, statusComboBox, groupComboBox, telTextField, faxTextField, emailTextField, factAddress, commentAddress,
                comment, code, externalCode);
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1)
        );
        return form;
    }


    private FormLayout getFormLayoutTwo() {
        FormLayout formTwo = new FormLayout(new Text("Контактные лица"));
        Button contactEmployee = new Button(("Контактное лицо"), new Icon(VaadinIcon.PLUS));

        formTwo.add(contactEmployee);

        return formTwo;
    }

    private FormLayout getFormLayoutThree() {
        FormLayout formThree = new FormLayout(new Text("Реквизиты"));
       ComboBox typeContragent = new ComboBox("Тип контрагента");
       typeContragent.setItems("Юридическое лицо", "Индивидуальный предприниматель","Физическое лицо");
       TextField innField = new TextField("ИНН");
       TextField fullNameField = new TextField("Полное наименование");
       TextField legalAddressField = new TextField("Юрридический адрес");
       TextField commentAddressField = new TextField("Комментарий к адресу");
       TextField kppField = new TextField("КПП");
       TextField ogrnField = new TextField("ОГРН");
       TextField okpoField = new TextField("ОКПО");




        formThree.add(typeContragent, innField, fullNameField, legalAddressField, commentAddressField, kppField,
                ogrnField, okpoField);
        return formThree;

    }

}
