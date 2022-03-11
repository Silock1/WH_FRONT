package com.warehouse_accounting.components.contragents.form;




import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.contragents.ContragentsList;
import com.warehouse_accounting.models.dto.ContractorDto;

import com.warehouse_accounting.services.interfaces.ContractorService;



@SpringComponent
@UIScope
public class FormEditCotragent extends VerticalLayout {

    private ContractorService contractorService;
    private ContragentsList parent;

    private Button edit = new Button ("Изменить");
    private Button close = new Button ("Закрыть");

    private TextField name = new TextField("Наименование");
    private ComboBox<String> group = new ComboBox<>("Группы");
    private TextField phone = new TextField("Телефон");
    private TextField fax = new TextField("Факс");
    private TextField email = new TextField("Электроннный фдрес");
    private TextArea legalDetailAddress = new TextArea("Фактический фдрес");
    private TextArea commentForAddress = new TextArea("Комментарий к Адресу");
    private TextArea comment = new TextArea("Комментарий");
    private TextField code = new TextField("Код");
    private TextField outSideCode = new TextField("Внешний код");
    private ComboBox<String>statusContragent = new ComboBox<>();
    private ComboBox<String> groupContragent = new ComboBox<>();
    private ComboBox<String> typeEmployee = new ComboBox<>();
    private TextField innEmployee = new TextField();
    private TextField fullNameEmployee = new TextField();
    private TextField legalAddress = new TextField();
    private TextField commentToTheAddress = new TextField();
    private TextField kppEmployee = new TextField();
    private TextField ogrnEmployee = new TextField();
    private TextField okpoEmployee = new TextField();
    private TextField revenue = new TextField();
    private ComboBox<String> price = new ComboBox<>();
    private TextField discountCard = new TextField();
    private ComboBox<String> employee = new ComboBox<>();
    private ComboBox<String>departmentEmployee = new ComboBox<>();


    public FormEditCotragent(ContractorService contractorService) {
        this.contractorService = contractorService;
    }
    public void bild(ContractorDto contractorDto){
        removeAll();
        add(createButton(contractorDto), getNameContragent(contractorDto),groupBlockLayout(contractorDto));
    }
    private HorizontalLayout createButton(ContractorDto contractorDto){
        HorizontalLayout button = new HorizontalLayout();
        button.setAlignItems(FlexComponent.Alignment.CENTER);
        edit = new Button("Изменить");
        edit.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        edit.addClickListener(e->{
            contractorDto.setName(name.getValue());
            contractorDto.setPhone(phone.getValue());
            contractorDto.setFax(fax.getValue());
            contractorDto.setEmail(email.getValue());
            contractorDto.setComment(commentToTheAddress.getValue());
            contractorDto.setNumberDiscountCard(discountCard.getValue());
            contractorDto.setLegalDetailAddress(legalAddress.getValue());
            contractorDto.setLegalDetailInn(innEmployee.getValue());
            contractorDto.setLegalDetailKpp(kppEmployee.getValue());
            contractorDto.setLegalDetailTypeOfContractorName(typeEmployee.getValue());
            contractorDto.setContractorGroupName(groupContragent.getValue());
            contractorService.update(contractorDto);
            removeAll();
            parent.showButtonEndGrid(true);
        });
        close = new Button("Закрыть");
        close.addClickListener(e -> {
            removeAll();
            parent.showButtonEndGrid(false);
        });
        button.add(close,edit);
        button.setAlignItems(FlexComponent.Alignment.CENTER);
        return button;
    }
    private HorizontalLayout getNameContragent(ContractorDto contractorDto) {
        HorizontalLayout nameContragentLayout = new HorizontalLayout();
        name.setWidth("550px");
        name.setValue(contractorDto.getName());
        nameContragentLayout.add(name);
        return nameContragentLayout;
    }
    private HorizontalLayout leftGroupFormLayout(ContractorDto contractorDto) {
        HorizontalLayout leftLayout = new HorizontalLayout();
        VerticalLayout verticalLayout = new VerticalLayout();
        FormLayout formContragent = new FormLayout();
        // Блок о конрагенте
        Text textContragent = new Text("О контрагенте");
        statusContragent.setItems("Новый", "Выслано предложение","Переговоры", "Сделка заключена", "Сделка не заключена");
        groupContragent.setItems("Основные", "Мелкий опт","Розница");
        if(contractorDto.getPhone() != null) phone.setValue(contractorDto.getPhone());
        if(contractorDto.getFax() != null)fax.setValue(contractorDto.getFax());
        if(contractorDto.getEmail() != null)email.setValue(contractorDto.getEmail());
        if(contractorDto.getLegalDetailAddress()!= null)legalDetailAddress.setValue(contractorDto.getLegalDetailAddress());

        formContragent.addFormItem(statusContragent, "Статус");
        formContragent.addFormItem(groupContragent, "Группы");
        formContragent.addFormItem(phone, "Телефон");
        formContragent.addFormItem(fax, "Факс");
        formContragent.addFormItem(email, "E-mail");
        formContragent.addFormItem(legalDetailAddress, "Фактический адрес");
        formContragent.setWidth("400px");
        // Блок контактные лица.
        Text textContactContragent = new Text("Контактные лица");
        FormLayout formLayout = new FormLayout();
        //revenue.setValue(contractorDto.getRevenue());
        formLayout.addFormItem(revenue, "Смените тариф");

        // Блок Реквизиты
        Text textRequisites = new Text("Реквизиты");
        FormLayout formRequisites = new FormLayout();
        typeEmployee.setItems("Поставщик", "Покупатель");
        if(contractorDto.getLegalDetailInn()!= null)innEmployee.setValue(contractorDto.getLegalDetailInn());
        // no null fullNameEmployee.setValue(contractorDto.getNameEmloyee());
        if(contractorDto.getLegalDetailAddress() != null)legalAddress.setValue(contractorDto.getLegalDetailAddress());
        if(contractorDto.getCommentToAddress() != null)commentToTheAddress.setValue(contractorDto.getCommentToAddress());
        if(contractorDto.getLegalDetailKpp() != null)kppEmployee.setValue(contractorDto.getLegalDetailKpp());
        // no null ogrnEmployee.setValue(contractorDto.getOgrn());
        //no null okpoEmployee.setValue(contractorDto.getOrpo());
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

        //Скидки и цены
        Text saleAndPrice = new Text("Скидки и цены");
        FormLayout formSalePrice = new FormLayout();
        price.setItems("Оптовые", "Мелкооптовые");;
        formSalePrice.addFormItem(price, "Цены");
        formSalePrice.addFormItem(discountCard, "Дисконтная карта");
        formSalePrice.setWidth("400px");

        //Доступ
        Text access = new Text("Доступ");
        FormLayout accessForm = new FormLayout();
        employee.setItems("Иванов Петр Сергеевич","Рогов Роман Романович");
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
    private HorizontalLayout groupBlockLayout(ContractorDto contractorDto) {
        HorizontalLayout blockLayout = new HorizontalLayout();
        blockLayout.add(leftGroupFormLayout(contractorDto), rightGroupButtonLayout());
        return blockLayout;
    }

    public void setParent(ContragentsList parent) {
        this.parent = parent;
    }
}
