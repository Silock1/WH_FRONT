package com.warehouse_accounting.components.contragents.form;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class FormNewContragent extends VerticalLayout {

    public FormNewContragent() {
        add(getGroupButton(), getNameContragent(), groupBlockLayout());
    }

    private HorizontalLayout getGroupButton() {
        HorizontalLayout controlGroupButton = new HorizontalLayout();
        Button createButton = new Button("Сохранить");
        Button closeButton = new Button("Закрыть");
        controlGroupButton.add(createButton, closeButton);

        return controlGroupButton;
    }

    private HorizontalLayout getNameContragent() {
        HorizontalLayout nameContragentLayout = new HorizontalLayout();
        TextField nameContragent = new TextField("Наименование");
        nameContragent.setWidth("550px");
        nameContragentLayout.add(nameContragent);
        return nameContragentLayout;
    }

    private HorizontalLayout groupBlockLayout() {
        HorizontalLayout blockLayout = new HorizontalLayout();

        blockLayout.add(leftGroupButtonLayout(), rightGroupButtonLayout());
        return blockLayout;
    }

    private HorizontalLayout leftGroupButtonLayout() {
        HorizontalLayout leftLayout = new HorizontalLayout();

        VerticalLayout verticalLayout = new VerticalLayout();
        FormLayout formContragent = new FormLayout();

        Text textContragent = new Text("О контрагенте");
        ComboBox<String> statusContragent = new ComboBox<>();
        ComboBox<String> groupContragent = new ComboBox<>();
        TextField telTextField = new TextField();
        TextField faxTextField = new TextField();
        TextField emailTextField = new TextField();
        TextField actualAddress = new TextField();

        formContragent.addFormItem(statusContragent, "Статус");
        formContragent.addFormItem(groupContragent, "Группы");
        formContragent.addFormItem(telTextField,"Телефон");
        formContragent.addFormItem(faxTextField,"Факс");
        formContragent.addFormItem(emailTextField,"E-mail");
        formContragent.addFormItem(actualAddress, "Фактический адрес");
        formContragent.setWidth("400px");


        Text textContactContragent = new Text("Контактные лица");

        FormLayout formLayout = new FormLayout();
        TextField revenue = new TextField();
        formLayout.addFormItem(revenue, "Смените тариф");

        Text textRequisites = new Text("Реквизиты");
        FormLayout formRequisites = new FormLayout();
        ComboBox<String> typeEmployee = new ComboBox<>();
        TextField innEmployee = new TextField();
        TextField fullNameEmployee = new TextField();
        TextField legalAddress = new TextField();
        TextField commentToTheAddress = new TextField();
        TextField kppEmployee = new TextField();
        TextField ogrnEmployee = new TextField();
        TextField okpoEmployee = new TextField();
        Button paymentAccount = new Button(new Icon(VaadinIcon.PLUS));
        paymentAccount.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        formRequisites.addFormItem(typeEmployee, "Тип контрагента");
        formRequisites.addFormItem(innEmployee, "ИНН");
        formRequisites.addFormItem(fullNameEmployee, "Полное наименование");
        formRequisites.addFormItem(legalAddress, "Юридический адрес");
        formRequisites.addFormItem(commentToTheAddress,"Комментарий к адресу");
        formRequisites.addFormItem(kppEmployee, "КПП");
        formRequisites.addFormItem(ogrnEmployee, "ОГРН");
        formRequisites.addFormItem(okpoEmployee, "ОКПО");
        formRequisites.addFormItem(paymentAccount, "Расчетный счет");
        formRequisites.setWidth("400px");

        /*
        Скидки и цены
         */
        Text saleAndPrice = new Text("Скидки и цены");
        FormLayout formSalePrice = new FormLayout();
        ComboBox<String> price = new ComboBox<>();
        TextField discountCard = new TextField();
        formSalePrice.addFormItem(price, "Цены");
        formSalePrice.addFormItem(discountCard, "Дисконтная карта");
//        formSalePrice.setResponsiveSteps(
//                new FormLayout.ResponsiveStep("0", 1)
//        );
        formSalePrice.setWidth("400px");

        Text access = new Text("Доступ");
        FormLayout accessForm = new FormLayout();
        ComboBox<String> employee = new ComboBox<>();
        ComboBox<String> departmentEmployee = new ComboBox<>();
        Checkbox generalAccess = new Checkbox();

        accessForm.addFormItem(employee, "Сотрудник");
        accessForm.addFormItem(departmentEmployee,"Отдел");
        accessForm.addFormItem(generalAccess, "Общий доступ");
        accessForm.setWidth("400px");

        verticalLayout.add(textContragent, formContragent, textContactContragent, formLayout, textRequisites,
                formRequisites, saleAndPrice,formSalePrice, access, accessForm);


        leftLayout.add(verticalLayout);
        return leftLayout;
    }

    private VerticalLayout rightGroupButtonLayout() {
        VerticalLayout rightLayout = new VerticalLayout();

        Tab details = new Tab("События");
        Tab payment = new Tab("Задачи");
        Tab shipping = new Tab("Документы");
        Tab files = new Tab("Файлы");
        Tab indicators = new Tab("Показатели");

        Tabs tabs = new Tabs(details, payment, shipping, files, indicators);

        rightLayout.add(tabs);
        return rightLayout;
    }


}
