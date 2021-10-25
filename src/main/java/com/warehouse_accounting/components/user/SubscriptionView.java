package com.warehouse_accounting.components.user;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.models.dto.TariffDto;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.SubscriptionService;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Route(value = "subscription", layout = AppView.class)
@PageTitle("Подписка")
@CssImport(value = "./css/subscription.css")
public class SubscriptionView extends VerticalLayout {
    private final EmployeeService employeeService;
    private final SubscriptionService subscriptionService;
    private final List<String> setPaymentPeriod = Arrays.asList(
            "1 месяц", "2 месяца", "3 месяца", "4 месяца", "5 месяцев", "6 месяцев",
            "7 месяцев", "8 месяцев", "9 месяцев","10 месяцев","11 месяцев", "12 месяцев");

    public SubscriptionView(EmployeeService employeeService, SubscriptionService subscriptionService) {
        this.employeeService = employeeService;
        this.subscriptionService = subscriptionService;

        H2 header = new H2("Подписка");
        header.addClassName("header");

        HorizontalLayout columnLayout = new HorizontalLayout();
        columnLayout.setPadding(true);
        columnLayout.add(leftColumn());
        columnLayout.add(rightColumn());

        Span span = new Span("Подписка продлевается автоматически при наличии достаточных средств на лицевом счету");
        span.addClassName("text3");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addClassName("text3");
        Anchor anchor = new Anchor("operationHistory", "История Операций"); //TODO Operation History Window
        anchor.addClassName("anchor");
        horizontalLayout.add(new Span("На вашем счету " + null + " ₽"), anchor);//TODO получить остаток на счете

        Button button = new Button("Пополнить счет");
        button.addThemeVariants(ButtonVariant.LUMO_LARGE, ButtonVariant.LUMO_CONTRAST);
        button.addClassName("leftColumn");

        add(header, columnLayout, span, horizontalLayout, button);
    }

    private VerticalLayout leftColumn() {

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMaxWidth("320px");

        Span caption = new Span("МойСклад");
        caption.addClassName("text1");

        Set<TariffDto> set= employeeService.getById(1L).getTariff();
        String tariffName = null;
        String tariffInfo = null;
        if (set.isEmpty()) {
            tariffName = "Бесплатный";
        } else {
            tariffName = set.stream().findFirst().get().getTariffName();
        }

        switch (tariffName) {
                case ("Старт"):
                tariffInfo =
                            "Доступно\n" +
                                    "- 1 пользователь\n" +
                                    "- 1 точка продаж\n" +
                                    "- 1 юр. лицо\n" +
                                    "- 100 МБ данных\n" +
                                    "- Ограничение в 1000 контрагентов\n" +
                                    "- Ограничение в 1000 товаров\n" +
                                    "- Ограничение в 1000 документов\n";break;
                case ("Базовый"):
                    break;
                case ("Профессиональный"):
                    break;
                case ("Корпоративный"):
                    break;
                default:
                    tariffInfo = "Доступно\n" +
                            "- 1 пользователь\n" +
                            "- 1 точка продаж\n" +
                            "- 1 юр. лицо\n" +
                            "- 50 МБ данных\n" +
                            "- Ограничение в 1000 контрагентов\n" +
                            "- Ограничение в 1000 товаров\n" +
                            "- Ограничение в 1000 документов";
                    break;
            }

        TextArea textArea = new TextArea();
        textArea.setWidth("250px");
        textArea.setValue(tariffInfo);
        textArea.setReadOnly(true);

        Label label = getLabel("Используется");
        Anchor data = new Anchor("indicators", "Данные: 0 МБ из 50 МБ"); //
        data.addClassName("anchor");
        Span span0 = new Span("Документы: 0 из 1000"); //динамически
        Span span1 = new Span("Компании: 1 из 1"); // динамически?
        span1.getElement().getStyle().set("color", "red");
        Span span2 = new Span("Контрагенты: 3 из 1000"); // динамически?
        Span span3 = new Span("Пользователи: 1 из 1");
        span3.getElement().getStyle().set("color", "red");
        Span span4 = new Span("Товары: 0 из 1000"); //динамически?
        Span span5 = new Span("Точки продаж: 1 из 1");
        span5.getElement().getStyle().set("color", "red");

        VerticalLayout verticalLayout1 = new VerticalLayout();
        verticalLayout1.setSpacing(false);
        verticalLayout1.setPadding(false);
        verticalLayout1.add(label, data, span0, span1, span2, span3, span4, span5);
        verticalLayout1.addClassName("paragraph");

        Span span = new Span("Чтобы увеличить доступный объём ресурса, нужно изменить тариф");
        span.addClassName("text1");
        span.setMaxWidth("300px");

        Button chooseTariffButton = new Button("Выбрать тариф");
        chooseTariffButton.addThemeVariants(ButtonVariant.LUMO_LARGE);
        chooseTariffButton.setAutofocus(true);
        chooseTariffButton.addClassName("button");
        chooseTariffButton.addClickListener(event -> chooseTariffDialog().open());

        verticalLayout.add(caption, new H3(tariffName), textArea, verticalLayout1, span, chooseTariffButton);
        verticalLayout.addClassName("leftColumn");

        return verticalLayout;
    }

    private  VerticalLayout rightColumn() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMaxWidth("320px");

        H3 responsible = new H3("Ответственный сотрудник");
        responsible.addClassName("text1");

        Span span = new Span("На электронную почту сотрудника будут приходить\n" +
                " уведомления об окончании подписки");
        span.setMaxWidth("300px");

        Select<String> valueSelect = new Select<>();
        valueSelect.setPlaceholder("Пользователь"); //TODO <add Modal Window Edit Responsible Employee>
        valueSelect.setWidth("300px");

        H3 closingDocuments = new H3("Закрывающие документы");
        closingDocuments.addClassName("text1");

        Paragraph paragraph = new Paragraph();
        paragraph.addClassName("span");
        paragraph.setEnabled(false);
        paragraph.setText("Если вы оплатили подписку и хотите получить\n документы для бухгалтерии (акт),\n");
        Anchor anchor = new Anchor("details", "заполните реквизиты");
        anchor.addClassName("anchor");
        paragraph.add(anchor);
        paragraph.addClickListener(e-> UI.getCurrent().navigate(anchor.getHref()));

        Span closingDocument = new Span("Закрывающие документы выставляются на всю сумму на 5-й день после активации подписки");
        closingDocument.setMaxWidth("300px");
        closingDocument.addClassName("span");

        Paragraph span1 = getParagraph("Обращаем внимание, что с 1 июля 2018 г. наша\n" +
                "компания не является плательщиком НДС согласно\n" +
                "п. 1 ст. 145.1 НК РФ (свидетельство о внесении ООО «Логнекс» в реестр участников проекта «Сколково»\n" +
                "№ 1122193 от 18.06.2018).");

        verticalLayout.add(responsible, span, valueSelect, closingDocuments, paragraph, closingDocument, span1);

        return  verticalLayout;
    }

    public Dialog chooseTariffDialog() {

        Dialog dialog = new Dialog();
        dialog.setWidth("33%");
        dialog.setHeight("100%");
        dialog.setCloseOnOutsideClick(false);

        int totalToPay = 0;

        H2 caption = new H2("Изменить подписку");
        caption.addClassName("header1");
        Button closeButton = new Button();
        closeButton.setIcon(new Icon(VaadinIcon.CLOSE));
        closeButton.addClickListener(buttonClickEvent -> dialog.close());
        closeButton.getElement().getStyle().set("margin-left", "auto");
        dialog.add(new HorizontalLayout(caption, closeButton));

        Select<String> select = new Select<>();
        select.setId("select");
        List<String> tariffNameList = subscriptionService.getById(1L)//tariffService.getAll
                .getTariff()
                .stream()
                .map(TariffDto::getTariffName)
                .collect(Collectors.toList());
        select.setItems(tariffNameList);
        select.setValue(tariffNameList.get(0));
        select.getElement().getStyle().set("margin-left", "30px");
        select.setAutofocus(true);

        dialog.add(new HorizontalLayout(getLabel("Taриф:"), select));
        dialog.add(new HorizontalLayout(getLabel("Пользователи"), getNumberField()), getParagraph(
                "Количество сотрудников, которые могут одновременно работать в МоемСкладе"
        ));
        dialog.add(new HorizontalLayout(getLabel("Данные"), getNumberField()),
                getParagraph("Дополнительное место для хранения ваших файлов. 1 пакет = 1000 МБ"));
        dialog.add(new HorizontalLayout(getLabel("Точки продаж"), getNumberField()),
                getParagraph("Количество одновременно работающих точек продаж"));
        dialog.add(new HorizontalLayout(getLabel("Интернет-магазины"), getNumberField()),
                getParagraph("Количество одновременно работающих интернет-магазинов"));
        dialog.add(new HorizontalLayout(getLabel("Опции платных приложений"), getNumberField()),
                getParagraph("Количество одновременно подключенных опций платных приложений"));
        dialog.add(new HorizontalLayout(getLabel("CRM(управление клиентами)"), getCheckBox()),
                getParagraph("Задачи, события, контактные лица, звонки и рассылки."));
        dialog.add(new HorizontalLayout(getLabel("Сценарии"), getCheckBox()),
                getParagraph("Задачи, события, контактные лица, звонки и рассылки.")); // нужна ссылка
        dialog.add(new HorizontalLayout(getLabel("Расширенная бонусная программа"), getCheckBox()),
                getParagraph("Отложенное начисление, приветственные баллы"));

        Select<String> paymentPeriodSelect = new Select<>();
        paymentPeriodSelect.setItems(setPaymentPeriod);
        paymentPeriodSelect.setPlaceholder("12 месяцев");
        paymentPeriodSelect.getElement().getStyle().set("margin-left", "60");
        paymentPeriodSelect.setAutofocus(true);
        dialog.add(new HorizontalLayout(getLabel("Период оплаты"), paymentPeriodSelect),
                getParagraph("Скидка 5% при оплате за 3 месяца\n" +
                "Скидка 10% при оплате за 6 месяцев\n" +
                "Скидка 15% при оплате за 12 месяцев"));
        Hr hr = new Hr();
        hr.setHeight("3px");
        dialog.add(hr);
        Span total = new Span(totalToPay + " ₽"); // к оплате
        total.getElement().getStyle().set("margin-left", "auto");
        total.addClassName("label");
        dialog.add(new HorizontalLayout(getLabel("К оплате"), total));

        Button button = new Button("Выбрать способ оплаты");
        button.addThemeVariants(ButtonVariant.LUMO_LARGE);
        button.setAutofocus(true);
        button.addClassName("button");
        button.getElement().getStyle().set("margin", "15px 0 0 260px");
        dialog.add(button);

        return dialog;
    }

    public Checkbox getCheckBox() {
        Checkbox checkbox = new Checkbox();
        checkbox.getElement().getStyle().set("margin-left", "auto");
        checkbox.setValue(false);
        checkbox.setWidth("18px");
        checkbox.setHeight("18px");
        return checkbox;
    }

    public Label getLabel(String text) {
        Label label = new Label(text);
        label.addClassName("label");
        return label;
    }

    public NumberField getNumberField() {
        NumberField numberField = new NumberField();
        numberField.getElement().getStyle().set("margin-left", "auto");
        numberField.setWidth("80px");
        numberField.setValue(0d);
        numberField.setHasControls(true);
        numberField.setMin(0);
        return numberField;
    }

    public Paragraph getParagraph (String text) {
        Paragraph paragraph = new Paragraph(text);
        paragraph.setWidth("300px");
        paragraph.addClassName("paragraph");
        return paragraph;
    }
}
