package com.warehouse_accounting.components.indiCators.grids;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.models.dto.PointOfSalesDto;
import com.warehouse_accounting.models.dto.RecycleBinDto;
import com.warehouse_accounting.services.interfaces.RecycleBinService;
import org.springframework.stereotype.Component;
import org.vaadin.olli.FileDownloadWrapper;


import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Component
@UIScope
@Route(value = "recycleBin", layout = AppView.class)
public class RecycleBinGridLayout extends VerticalLayout {

    private HorizontalLayout horizontalToolPanelLayout = new HorizontalLayout();
    private Grid<RecycleBinDto> grid = new Grid<>(RecycleBinDto.class);
    private RecycleBinService recycleBinService;

    public RecycleBinGridLayout(RecycleBinService recycleBinService) {
        this.recycleBinService = recycleBinService;
        horizontalToolPanelLayout.setAlignItems(Alignment.CENTER);
        grid.setItems(recycleBinService.getAll());
        add(horizontalToolPanelLayout);
        configToolPanel();
        add(grid());
    }

    private Grid<RecycleBinDto> grid(){
        grid.setColumns(getVisibleInvoiceColumn().keySet().toArray(String[]::new));
        grid.setSelectionMode(Grid.SelectionMode.MULTI);

        getVisibleInvoiceColumn().forEach((key, value)-> grid.getColumnByKey(key).setHeader(value));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
        return grid;
    }

    private HashMap<String, String> getVisibleInvoiceColumn(){
        HashMap<String, String> fieldNameColumnName = new LinkedHashMap<>();
        fieldNameColumnName.put("documentType","Тип документа");
        fieldNameColumnName.put("number","№");
      //  fieldNameColumnName.put("date","Время");
        fieldNameColumnName.put("sum","Сумма");
        fieldNameColumnName.put("warehouse","Со склада");
      //  fieldNameColumnName.put("warehouse","На склад");
        fieldNameColumnName.put("companyName","Организация");
        fieldNameColumnName.put("contractorName","Контрагент");
        fieldNameColumnName.put("status","Статус");
        fieldNameColumnName.put("project","Проект");
        fieldNameColumnName.put("shipped","Отправлено");
        fieldNameColumnName.put("printed","Напечатано");
        fieldNameColumnName.put("comment","Комментарий");

        return fieldNameColumnName;

    }

    // Здесь настройка панели инструментов
    private void configToolPanel() {

        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        helpButton.addClickListener(e->{
            Notification.show("В разделе хранятся удаленные документы — их можно\n" +
                    "\n" +
                    "восстановить в течение 7 дней после удаления. По истечении этого\n" +
                    "\n" +
                    "срока документы окончательно стираются.\n" +
                    "\n" +
                    "Читать инструкцию: Корзина", 5000, Notification.Position.TOP_START);
        });

        Label textProducts = new Label();
        textProducts.setText("Корзина");

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        Button addOrderButton = new Button("Очистить корзину", new Icon(VaadinIcon.PLUS));
        addOrderButton.addThemeVariants(ButtonVariant.LUMO_SMALL);

        Button addFilterButton = new Button("Фильтр");
        addFilterButton.addThemeVariants(ButtonVariant.LUMO_SMALL);

        TextField searchField = new TextField();
        searchField.setPlaceholder("Номер или комментарий");
        searchField.setMinWidth("170px");

        NumberField numberField = new NumberField();
        grid.addSelectionListener(event -> numberField.setValue((double) (grid.getSelectedItems().size())));
        numberField.setValue(0d);
        numberField.setWidth("40px");

        MenuBar menuBar = new MenuBar();

        MenuItem change = menuBar.addItem("Изменить");
        change.add(new Icon(VaadinIcon.CARET_DOWN));

        MenuItem print = menuBar.addItem(new Icon(VaadinIcon.PRINT));
        print.add("Печать");
        print.add(new Icon(VaadinIcon.CARET_DOWN));

        SubMenu changeSubMenu = change.getSubMenu();
        MenuItem delete = changeSubMenu.addItem("Удалить");
        delete.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem recover = changeSubMenu.addItem("Востановить");
        recover.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });

        //модальное окно
        Dialog dialog = new Dialog();
        dialog.getElement().setAttribute("aria-label", "Создание печатной формы");
        VerticalLayout dialogLayout = createDialogLayout(dialog);
        dialog.add(dialogLayout);

        SubMenu printSubMenu = print.getSubMenu();
        MenuItem recycleBinList = printSubMenu.addItem("Список документов");
        recycleBinList.addClickListener(event -> {

            dialog.open(); //

            //TODO повод поработать этот функционал
        });

        MenuItem configurePrint = printSubMenu.addItem("Настроить...");
        configurePrint.addClickListener(event -> {
           // recycleBinService.getExcel();
            //TODO повод поработать этот функционал
        });

        PointOfSalesDto pointOfSalesDto = new PointOfSalesDto();

        //кнопка для скачивания, стоит отдельно
        Button button = new Button("Click to download");
        FileDownloadWrapper buttonWrapper = new FileDownloadWrapper(
                new StreamResource("foo.txt", () -> new ByteArrayInputStream("hellou".getBytes())));
        buttonWrapper.wrapComponent(button);
        add(buttonWrapper);
        TextField textField = new TextField("Enter file contents");
        FileDownloadWrapper link = new FileDownloadWrapper("textfield.txt", () -> textField.getValue().getBytes());
        link.setText("Download textfield.txt that has contents of the TextField");



        horizontalToolPanelLayout.add(helpButton,textProducts, refreshButton, addOrderButton, addFilterButton, searchField, numberField, menuBar);
    }

    //модальное окно
    private static VerticalLayout createDialogLayout(Dialog dialog) {
        H2 headline = new H2("Создание печатной формы");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");


        Select<String> selectForm = new Select<String>("Открыть в браузере",
                "Скачать в формате EXEL","Скачать в формате PDF", "Скачать в формате Open Office Calc");
        Checkbox checkbox = new Checkbox("Запомнить выбор");
        VerticalLayout fieldLayout = new VerticalLayout(selectForm,
                checkbox);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);



        Button cancelButton = new Button("Нет", e -> dialog.close());
        Button saveButton = new Button("Да", e -> dialog.close());


        FileDownloadWrapper buttonWrapper = new FileDownloadWrapper(
                new StreamResource("привееет.txt", () -> new ByteArrayInputStream("привеееет".getBytes())));

        buttonWrapper.wrapComponent(saveButton);
        //add(buttonWrapper);

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton,
                buttonWrapper);
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        VerticalLayout dialogLayout = new VerticalLayout(headline, fieldLayout,
                buttonLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "350px").set("max-width", "100%");

        return dialogLayout;
    }
}
