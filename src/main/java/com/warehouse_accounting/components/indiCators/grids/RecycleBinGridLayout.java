package com.warehouse_accounting.components.indiCators.grids;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
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
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.models.dto.RecycleBinDto;
import com.warehouse_accounting.services.interfaces.RecycleBinService;
import org.springframework.stereotype.Component;
import org.vaadin.alejandro.PdfBrowserViewer;
import org.vaadin.olli.FileDownloadWrapper;

import java.time.LocalDate;

@Component
@UIScope
@Route(value = "recycleBin", layout = AppView.class)
public class RecycleBinGridLayout extends VerticalLayout {

    private final RecycleBinService recycleBinService;
    private Button menuButton = new Button(new Icon(VaadinIcon.COG));
    private Grid<RecycleBinDto> recycleBinDtoGrid;
    private HorizontalLayout horizontalToolPanelLayout = new HorizontalLayout();
    private NumberField numberField;

    public RecycleBinGridLayout(RecycleBinService recycleBinService) {
        this.recycleBinService = recycleBinService;
        horizontalToolPanelLayout.setAlignItems(Alignment.CENTER);
        configToolPanel();
        add(horizontalToolPanelLayout);
        recycleBinDtoGrid = initGrid();
        recycleBinDtoGrid.setItems(recycleBinService.getAll());
    }

    private Grid<RecycleBinDto> initGrid() {
        Grid<RecycleBinDto> grid = new Grid<>(RecycleBinDto.class, false);

        Grid.Column<RecycleBinDto> documentType = grid.addColumn(RecycleBinDto::getDocumentType).setHeader("Тип документа");
        Grid.Column<RecycleBinDto> number = grid.addColumn(RecycleBinDto::getNumber).setHeader("№");
        // Grid.Column<RecycleBinDto> date = grid.addColumn(RecycleBinDto::getDate).setHeader("Время");
        Grid.Column<RecycleBinDto> sum = grid.addColumn(RecycleBinDto::getSum).setHeader("Сумма");
        Grid.Column<RecycleBinDto> warehouseName = grid.addColumn(RecycleBinDto::getWarehouseName).setHeader("Со склада");
        Grid.Column<RecycleBinDto> warehouseFrom = grid.addColumn(RecycleBinDto::getWarehouseFrom).setHeader("На склад");
        Grid.Column<RecycleBinDto> companyName = grid.addColumn(RecycleBinDto::getCompanyName).setHeader("Организация");
        Grid.Column<RecycleBinDto> contractorName = grid.addColumn(RecycleBinDto::getContractorName).setHeader("Контрагент");
        Grid.Column<RecycleBinDto> status = grid.addColumn(RecycleBinDto::getStatus).setHeader("Статус");
        Grid.Column<RecycleBinDto> projectName = grid.addColumn(RecycleBinDto::getProjectName).setHeader("Проект");
        Grid.Column<RecycleBinDto> shipped = grid.addColumn(RecycleBinDto::getShipped).setHeader("Отправлено");
        Grid.Column<RecycleBinDto> printed = grid.addColumn(RecycleBinDto::getPrinted).setHeader("Напечатано");
        Grid.Column<RecycleBinDto> comment = grid.addColumn(RecycleBinDto::getComment).setHeader("Комментарий");

        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        menuButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        ColumnToggleContextMenu<RecycleBinDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(menuButton);

        columnToggleContextMenu.addColumnToggleItem("Тип документа", documentType);
        columnToggleContextMenu.addColumnToggleItem("№", number);
        // columnToggleContextMenu.addColumnToggleItem("Время", date);
        columnToggleContextMenu.addColumnToggleItem("Сумма", sum);
        columnToggleContextMenu.addColumnToggleItem("Со склада", warehouseName);
        columnToggleContextMenu.addColumnToggleItem("На склад", warehouseFrom);
        columnToggleContextMenu.addColumnToggleItem("Организация", companyName);
        columnToggleContextMenu.addColumnToggleItem("Контрагент", contractorName);
        columnToggleContextMenu.addColumnToggleItem("Статус", status);
        columnToggleContextMenu.addColumnToggleItem("Проект", projectName);
        columnToggleContextMenu.addColumnToggleItem("Отправлено", shipped);
        columnToggleContextMenu.addColumnToggleItem("Напечатано", printed);
        columnToggleContextMenu.addColumnToggleItem("Комментарий", comment);

        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.add(grid, menuButton);
        grid.setHeightByRows(true);
        headerLayout.setWidthFull();

        add(headerLayout);
        return grid;
    }

    // Здесь настройка панели инструментов
    private void configToolPanel() {

        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        helpButton.addClickListener(e -> {
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

        numberField = new NumberField();
        //todo SelectionListener

        //  grid.addSelectionListener(event -> numberField.setValue((double) (grid.getSelectedItems().size())));
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
        MenuItem recover = changeSubMenu.addItem("Воcстановить");
        recover.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });

        //модальное окно
        Dialog dialog = new Dialog();
        VerticalLayout dialogLayout = createDialogLayout(dialog);
        dialog.add(dialogLayout);
        //

        SubMenu printSubMenu = print.getSubMenu();
        MenuItem recycleBinList = printSubMenu.addItem("Список документов");
        recycleBinList.addClickListener(event -> {

            dialog.open(); //

            //TODO повод поработать этот функционал
        });


        MenuItem configurePrint = printSubMenu.addItem("Настроить...");
        configurePrint.addClickListener(event -> {

            //TODO повод поработать этот функционал
        });


        horizontalToolPanelLayout.add(helpButton, textProducts, refreshButton, addOrderButton, addFilterButton, searchField, numberField, menuBar);
    }
    //модальное окно
    private VerticalLayout createDialogLayout(Dialog dialog) {
        H2 headline = new H2("Создание печатной формы");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");
        Label label = new Label("Создать печатную форму по шаблону 'Список документов'?");
        Button accept1 = new Button("Да");
        Button accept2 = new Button("Да");
        Button buttonWrapper1 = new Button("Да", buttonClickEvent -> dialog.close());
        buttonWrapper1.addClickListener(clickEvent -> {
            PdfBrowserViewer viewer = new PdfBrowserViewer(
                    new StreamResource(LocalDate.now() + " openBrowse.pdf",
                            () -> recycleBinService.getTermsConditions().byteStream()));
            viewer.setHeight("100%");
            viewer.setWidth("100%");
            Dialog dialogPdf = new Dialog(viewer);
            dialogPdf.setHeight("80%");
            dialogPdf.setWidth("80%");
            dialogPdf.open();
        });
        FileDownloadWrapper buttonWrapper2 = new FileDownloadWrapper(
                new StreamResource(LocalDate.now() + " someSheetExel.xlsx",
                        () -> recycleBinService.getExcel().byteStream()));
        buttonWrapper2.wrapComponent(accept1);
        FileDownloadWrapper buttonWrapper3 = new FileDownloadWrapper(
                new StreamResource(LocalDate.now() + " someSheetPDF.pdf",
                        () -> recycleBinService.getPDF().byteStream()));
        buttonWrapper3.wrapComponent(accept2);
        buttonWrapper2.setVisible(false);
        buttonWrapper3.setVisible(false);
        Select<String> selectForm = new Select<>("Открыть в браузере",
                "Скачать в формате EXEL", "Скачать в формате PDF");
        selectForm.setValue("Открыть в браузере");
        selectForm.addValueChangeListener(selectStringComponentValueChangeEvent -> {
            switch (selectStringComponentValueChangeEvent.getValue()) {
                case "Открыть в браузере":
                    buttonWrapper1.setVisible(true);
                    buttonWrapper2.setVisible(false);
                    buttonWrapper3.setVisible(false);
                    break;
                case "Скачать в формате EXEL":
                    buttonWrapper1.setVisible(false);
                    buttonWrapper2.setVisible(true);
                    buttonWrapper3.setVisible(false);
                    break;
                case "Скачать в формате PDF":
                    buttonWrapper1.setVisible(false);
                    buttonWrapper2.setVisible(false);
                    buttonWrapper3.setVisible(true);
                    break;
            }
        });
        accept1.addClickListener(buttonClickEvent -> {
            selectForm.setValue("Открыть в браузере");
            dialog.close();
        });
        accept2.addClickListener(buttonClickEvent -> {
            selectForm.setValue("Открыть в браузере");
            dialog.close();
        });
        Button cancelButton = new Button("Закрыть", e -> dialog.close());
        HorizontalLayout buttonLayout = new HorizontalLayout(buttonWrapper1, buttonWrapper2, buttonWrapper3, cancelButton);

        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        VerticalLayout dialogLayout = new VerticalLayout(headline, label, selectForm, buttonLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "350px").set("max-width", "100%");
        return dialogLayout;
    }
}
