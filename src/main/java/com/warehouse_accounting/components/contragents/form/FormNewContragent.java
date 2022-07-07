package com.warehouse_accounting.components.contragents.form;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.address.AddressForm;
import com.warehouse_accounting.components.contragents.ContragentsList;
import com.warehouse_accounting.models.dto.ContractorDto;
import com.warehouse_accounting.models.dto.ContractorGroupDto;
import com.warehouse_accounting.services.impl.ContractorServiceImpl;
import com.warehouse_accounting.services.interfaces.ContractorService;
import org.springframework.beans.factory.annotation.Autowired;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



@SpringComponent
@UIScope
public class FormNewContragent extends VerticalLayout {
    private TextField nameContragent;
    private ComboBox<String> statusContragent;
    private ComboBox<String> groupContragent;
    private TextField telTextField;
    private TextField faxTextField;
    private TextField emailTextField;
    private AddressForm actualAddressForm;
    private TextArea commentTextArea;
    private ComboBox<String> typeEmployee;
    private TextField innEmployee;
    private AddressForm legalAddressForm;
    private TextField kppEmployee;
    private TextField ogrnEmployee;
    private TextField okpoEmployee;
    private ComboBox<String> price;
    private TextField discountCard;
    private ComboBox<String> employee;
    private ComboBox<String> departmentEmployee;



    private ContragentsList contragentsList;


    @Autowired
    public FormNewContragent(
            AddressForm actualAddressForm,
            AddressForm legalAddressForm
    ) {
        this.actualAddressForm = actualAddressForm;
        this.legalAddressForm = legalAddressForm;
    }

    public void refres (){
        removeAll();
        add(getGroupButton(), getNameContragent(), groupBlockLayout());
    }
    private HorizontalLayout getGroupButton() {
        HorizontalLayout controlGroupButton = new HorizontalLayout();
        Button createButton = new Button("Сохранить", e ->{
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://localhost:4446")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ContractorService contractorService = new ContractorServiceImpl("/api/contractors", retrofit);
            ContractorDto contractorDto = new ContractorDto();
            contractorDto.setName(nameContragent.getValue());
            contractorDto.setPhone(telTextField.getValue());
            contractorDto.setFax(faxTextField.getValue());
            contractorDto.setEmail(emailTextField.getValue());
            contractorDto.setAddress(actualAddressForm.getValue());
            contractorDto.setComment(commentTextArea.getValue());
            contractorDto.setNumberDiscountCard(discountCard.getValue());
            contractorDto.setLegalDetailAddress(legalAddressForm.getValue());
            contractorDto.setLegalDetailInn(innEmployee.getValue());
            contractorDto.setLegalDetailKpp(kppEmployee.getValue());
            contractorDto.setLegalDetailTypeOfContractorName(typeEmployee.getValue());
            contractorDto.setContractorGroupName(groupContragent.getValue());
            contractorDto.setContractorGroup(ContractorGroupDto.builder().build());

            contractorService.create(contractorDto);
            removeAll();
            contragentsList.showButtonEndGrid(true);
        });
        Button closeButton = new Button("Закрыть", e -> {
            removeAll();
            contragentsList.showButtonEndGrid(false);
        });
        controlGroupButton.add(createButton, closeButton);
        return controlGroupButton;
    }
    private HorizontalLayout getNameContragent() {
        HorizontalLayout nameContragentLayout = new HorizontalLayout();
        nameContragent = new TextField("Наименование");
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
        statusContragent = new ComboBox<>();
        statusContragent.setItems("Новый", "Выслано предложение");
        groupContragent = new ComboBox<>();
        groupContragent.setItems("Основные", "Мелкий опт");
        telTextField = new TextField();
        faxTextField = new TextField();
        emailTextField = new TextField();
        commentTextArea = new TextArea();

        formContragent.addFormItem(statusContragent, "Статус");
        formContragent.addFormItem(groupContragent, "Группы");
        formContragent.addFormItem(telTextField,"Телефон");
        formContragent.addFormItem(faxTextField,"Факс");
        formContragent.addFormItem(emailTextField,"E-mail");
        actualAddressForm.addToForm("Фактический адрес", formContragent);
        formContragent.addFormItem(commentTextArea, "Комментарий");
        formContragent.setWidth("400px");
        /*
        Контактные лица
         */

        Text textContactContragent = new Text("Контактные лица");

        FormLayout formLayout = new FormLayout();
        TextField revenue = new TextField();
        formLayout.addFormItem(revenue, "Смените тариф");
        /*
        Реквизиты
         */

        Text textRequisites = new Text("Реквизиты");
        FormLayout formRequisites = new FormLayout();
        typeEmployee = new ComboBox<>();
        typeEmployee.setItems("Поставщик", "Покупатель");
        innEmployee = new TextField();
        TextField fullNameEmployee = new TextField();
        kppEmployee = new TextField();
        ogrnEmployee = new TextField();
        okpoEmployee = new TextField();
        Button paymentAccount = new Button(new Icon(VaadinIcon.PLUS));
        paymentAccount.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        formRequisites.addFormItem(typeEmployee, "Тип контрагента");
        formRequisites.addFormItem(innEmployee, "ИНН");
        formRequisites.addFormItem(fullNameEmployee, "Полное наименование");
        legalAddressForm.addToForm("Юридический адрес", formRequisites);
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
        price = new ComboBox<>();
        price.setItems("Оптовые", "Мелкооптовые");
        discountCard = new TextField();
        formSalePrice.addFormItem(price, "Цены");
        formSalePrice.addFormItem(discountCard, "Дисконтная карта");
        formSalePrice.setWidth("400px");

        Text access = new Text("Доступ");
        FormLayout accessForm = new FormLayout();
        employee = new ComboBox<>();
        employee.setItems("Иванов Петр Сергеевич","Рогов Роман Романович");
        departmentEmployee = new ComboBox<>();
        departmentEmployee.setItems("Центарльный офис","Удаленный филиал");
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

    /*
    Вкладка с Табами
     */
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
    public void setContragentsList(ContragentsList contragentsList) {
        this.contragentsList = contragentsList;
    }

}
