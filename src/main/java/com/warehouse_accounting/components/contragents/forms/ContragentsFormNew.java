package com.warehouse_accounting.components.contragents.forms;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
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
        HorizontalLayout horizontalLayoutGroup = new HorizontalLayout();

        HorizontalLayout groupButton = new HorizontalLayout(); // Группа кнопок записать
        Button save = new Button("Сохранить", e->{

        });
        Button close = new Button("Закрыть", e-> {

        });
        groupButton.setHeightFull();
        groupButton.add(save, close);

        VerticalLayout groupLeftForm = new VerticalLayout();
        FormLayout formOne = getFormLayoutOne();
        FormLayout formTwo = getFormLayoutTwo();
        FormLayout formThree = getFormLayoutThree();
        FormLayout formFour = getFormLayoutFour();
        FormLayout formFive = getFormLayoutFive();
        FormLayout formLayoutRight = getFormLayoutRight();

        VerticalLayout groupRightForm = new VerticalLayout();



        groupLeftForm.add(formOne, formTwo, formThree, formFour, formFive);
        horizontalLayoutGroup.add(groupLeftForm, formLayoutRight);
        verticalLayout.add(groupButton, horizontalLayoutGroup);






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
        formTwo.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1)
        );
        return formTwo;
    }

    private FormLayout getFormLayoutThree() {
        FormLayout formThree = new FormLayout(new Text("Реквизиты"));

       ComboBox typeContragent = new ComboBox("Тип контрагента");
       typeContragent.setItems("Юридическое лицо", "Индивидуальный предприниматель","Физическое лицо");
       TextField innField = new TextField("ИНН");
       TextField fullNameField = new TextField("Полное наименование");
       TextField legalAddressField = new TextField("Юридический адрес");
       TextField commentAddressField = new TextField("Комментарий к адресу");
       TextField kppField = new TextField("КПП");
       TextField ogrnField = new TextField("ОГРН");
       TextField okpoField = new TextField("ОКПО");
       Button paymentAccount = new Button(("Расчетный счёт"), new Icon(VaadinIcon.PLUS));

        formThree.add(typeContragent, innField, fullNameField, legalAddressField, commentAddressField, kppField,
                ogrnField, okpoField, paymentAccount);

        formThree.setResponsiveSteps(
          new FormLayout.ResponsiveStep("0", 1)
        );
        return formThree;
    }

    private FormLayout getFormLayoutFour() {
        FormLayout formFour = new FormLayout(new Text("Скидки и цены"));
        ComboBox priceCombo = new ComboBox("Цены");
        priceCombo.setItems("Цена продажи", "Еще цена");
        TextField numberDiscountCard = new TextField("Номер диск карты");

        formFour.add(priceCombo, numberDiscountCard);
        formFour.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1)
        );
        return formFour;
    }

    private FormLayout getFormLayoutFive() {
        FormLayout formFive = new FormLayout(new Text("Доступ"));
        ComboBox employeeCombo = new ComboBox("Сотрудник");
        employeeCombo.setItems("Петр", "Даша");
        ComboBox departmentCombo = new ComboBox("Отдел");
        departmentCombo.setItems("Основной", "Удаленный");
        Checkbox generalAccess = new Checkbox("Общий доступ");


        formFive.add(employeeCombo, departmentCombo, generalAccess);
        formFive.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1)
        );
        return formFive;
    }

    private FormLayout getFormLayoutRight() {
        FormLayout formRight = new FormLayout();
        Tab tabEvents = new Tab("События");
        Tab tabTasks = new Tab("Задачи");
        Tab tabDocuments = new Tab("Документы");
        Tab tabFiles = new Tab("Файлы");

        Tabs allTab = new Tabs(tabEvents, tabTasks, tabDocuments, tabFiles);
        allTab.setSelectedTab(tabDocuments);
        formRight.add(allTab);

        return formRight;

    }


}
