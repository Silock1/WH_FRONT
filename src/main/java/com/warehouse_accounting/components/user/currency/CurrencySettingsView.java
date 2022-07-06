package com.warehouse_accounting.components.user.currency;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.user.currency.forms.CurrencySettingsAddView;
import com.warehouse_accounting.components.user.settings.SettingsView;
import com.warehouse_accounting.components.util.QuestionButton;
import com.warehouse_accounting.models.dto.CurrencyDto;
import com.warehouse_accounting.services.interfaces.CurrencyService;

import java.util.List;

@PageTitle("Настройки")
@Route(value = "currencies", layout = SettingsView.class)
public class CurrencySettingsView extends VerticalLayout {

    private final CurrencyService currencyService;
    private Grid<CurrencyDto> grid;
    private final HorizontalLayout filterCurrencyLayout;

    public CurrencySettingsView(CurrencyService currencyService) {
        this.currencyService = currencyService;
        this.filterCurrencyLayout = filterCurrencyLayout();

        HorizontalLayout header = new HorizontalLayout();

        H2 tableName = new H2("Валюты");
        tableName.getStyle().set("margin-top","11px");

        Button delete = new Button("Удалить");
        delete.setEnabled(false);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.getStyle().set("margin-inline-start", "auto");

        grid = new Grid<>(CurrencyDto.class, false);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addColumn(CurrencyDto::getCharcode).setHeader("Короткое наименование");
        grid.addColumn(CurrencyDto::getName).setHeader("Полное наименование");
        grid.addColumn(CurrencyDto::getNumcode).setHeader("Цифровой код");
        grid.addColumn(CurrencyDto::getNominal).setHeader("Номинал");
        grid.addColumn(CurrencyDto::getValue).setHeader("Курс");
        grid.addSelectionListener(selection -> delete.setEnabled(selection.getAllSelectedItems().size() != 0));

        delete.addClickListener(buttonClickEvent -> {
            grid.getSelectedItems().forEach(selected -> currencyService.deleteById(selected.getId()));
            grid.setItems(currencyService.getAll());
        });

        List<CurrencyDto> channels = currencyService.getAll();
        grid.setItems(channels);

        Label textChannels = new Label();
        textChannels.setText("Валюты");

        Button addCurrencies = new Button("Добавить валюту", new Icon(VaadinIcon.PLUS));
        addCurrencies.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addCurrencies.addClickListener(e-> UI.getCurrent().navigate(CurrencySettingsAddView.class));

        Button addFilterButton = new Button("Фильтр");
        addFilterButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addFilterButton.addClickListener(buttonClickEvent -> filterCurrencyLayout.setVisible(!filterCurrencyLayout.isVisible()));

        TextField searchField = new TextField();
        searchField.setPlaceholder("Краткое наименование");
        searchField.setWidth("200px");
        searchField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        searchField.addInputListener(inputEvent -> {
        });


        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        refreshButton.addClickListener(buttonClickEvent -> grid.setItems(currencyService.getAll()));

        setPadding(false);
        setAlignItems(Alignment.STRETCH);


        header.setAlignItems(FlexComponent.Alignment.CENTER);

        header.add(buttonQuestion("МойСклад позволяет работать с контрагентами в разных валютах. Валюта учета — это обычно валюта страны, где зарегистрирована ваша компания. В валюте учета рассчитывается себестоимость товаров, прибыль и взаиморасчеты с контрагентами. По умолчанию в МоемСкладе за валюту учета принят российский рубль. Вы можете изменить валюту учета в справочнике валют. Если вы хотите использовать несколько валют, то решите, какая валюта будет у вас валютой учета. Откройте настройки валюты учета и выберите нужную валюту. После этого добавьте другие валюты."),
                tableName, refreshButton, addCurrencies, addFilterButton, searchField, delete);
        add(header, grid);
    }

    private Button buttonQuestion(String text) {

        return QuestionButton.buttonQuestion(text);
    }

    private HorizontalLayout filterCurrencyLayout() {
        HorizontalLayout firstLine = new HorizontalLayout();

        Button find = new Button("Найти");
        find.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        Button clear = new Button("Очистить");
        clear.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        Button bookmarks = new Button(new Icon(VaadinIcon.BOOKMARK));
        Button settingButton = new Button(new Icon(VaadinIcon.COG));

        TextField shortCurrency = new TextField("Краткое наименование");
        TextField fullCurrency = new TextField("Полное наименование");
        TextField digitalCurrency = new TextField("Цифровой код");

        firstLine.add(find, clear, bookmarks, settingButton);

        ComboBox<String> show = new ComboBox<>("Показывать", "Только обычные", "Только архивные", "Все");

        FormLayout formLayout = new FormLayout();
        formLayout.add(shortCurrency, fullCurrency, digitalCurrency, show);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 4));

        HorizontalLayout filterForm = new HorizontalLayout();
        filterForm.add(firstLine, formLayout);
        filterForm.setVisible(false);
        return filterForm;
    }
}
