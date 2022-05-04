package com.warehouse_accounting.components.indiCators;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.ContextMenu;
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
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.models.dto.RecycleBinDto;
import com.warehouse_accounting.services.interfaces.RecycleBinService;
import org.springframework.stereotype.Component;
import org.vaadin.olli.FileDownloadWrapper;


import java.io.FileNotFoundException;
import java.time.LocalDate;

@Component
@UIScope
@Route(value = "recycleBin", layout = AppView.class)
public class RecycleBinGridLayout extends VerticalLayout {

    private HorizontalLayout horizontalToolPanelLayout = new HorizontalLayout();
    private Grid<RecycleBinDto> grid = new Grid<>(RecycleBinDto.class, false);
    private static RecycleBinService recycleBinService;

    public RecycleBinGridLayout(RecycleBinService recycleBinService) throws FileNotFoundException {
        this.recycleBinService = recycleBinService;
        horizontalToolPanelLayout.setAlignItems(Alignment.CENTER);
        grid.setItems(recycleBinService.getAll());
        configToolPanel();

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

        grid.setSelectionMode(Grid.SelectionMode.MULTI); //

        Button menuButton = new Button(new Icon(VaadinIcon.COG));
        menuButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu columnToggleContextMenu = new ColumnToggleContextMenu(
                menuButton);

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
        horizontalToolPanelLayout.add(menuButton);
        add(horizontalToolPanelLayout);
        add(grid);

    }

    private static class ColumnToggleContextMenu extends ContextMenu {
        public ColumnToggleContextMenu(com.vaadin.flow.component.Component target) {
            super(target);
            setOpenOnClick(true);
        }

        void addColumnToggleItem(String label, Grid.Column<RecycleBinDto> column) {
            MenuItem menuItem = this.addItem(label, e -> {
                column.setVisible(e.getSource().isChecked());
            });
            menuItem.setCheckable(true);
            menuItem.setChecked(column.isVisible());
        }
    }




    /*private Grid<RecycleBinDto> grid() {
        grid.setColumns(getVisibleInvoiceColumn().keySet().toArray(String[]::new));
        grid.setSelectionMode(Grid.SelectionMode.MULTI);


        getVisibleInvoiceColumn().forEach((key, value) -> grid.getColumnByKey(key).setHeader(value));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
        return grid;
    }

    private HashMap<String, String> getVisibleInvoiceColumn() {
        HashMap<String, String> fieldNameColumnName = new LinkedHashMap<>();
        fieldNameColumnName.put("documentType", "Тип документа");
        fieldNameColumnName.put("number", "№");
        //  fieldNameColumnName.put("date","Время");  ошибка
        fieldNameColumnName.put("sum", "Сумма");
        fieldNameColumnName.put("warehouseName", "Со склада");
        fieldNameColumnName.put("warehouseFrom", "На склад");
        fieldNameColumnName.put("companyName", "Организация");
        fieldNameColumnName.put("contractorName", "Контрагент");
        fieldNameColumnName.put("status", "Статус");
        fieldNameColumnName.put("projectName", "Проект");
        fieldNameColumnName.put("shipped", "Отправлено");
        fieldNameColumnName.put("printed", "Напечатано");
        fieldNameColumnName.put("comment", "Комментарий");

        return fieldNameColumnName;

    }*/

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

            NumberField numberField = new NumberField();
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
            MenuItem recover = changeSubMenu.addItem("Востановить");
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
        private  VerticalLayout createDialogLayout(Dialog dialog) {
            H2 headline = new H2("Создание печатной формы");
            headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                    .set("font-size", "1.5em").set("font-weight", "bold");


        /*Select<String> selectForm = new Select<>("Открыть в браузере",
                "Скачать в формате EXEL","Скачать в формате PDF");
*/



            FileDownloadWrapper buttonWrapper1 = new FileDownloadWrapper(
                    new StreamResource(LocalDate.now() + " openBrowse.pdf",
                            () -> recycleBinService.getTermsConditions().byteStream()));
            buttonWrapper1.wrapComponent(new Button("Открыть в браузере"));


            FileDownloadWrapper buttonWrapper2 = new FileDownloadWrapper(
                    new StreamResource(LocalDate.now() + " someSheetExel.xlsx",
                            () -> recycleBinService.getExcel().byteStream()));
            buttonWrapper2.wrapComponent(new Button("Скачать в формате EXEL"));


            FileDownloadWrapper buttonWrapper3 = new FileDownloadWrapper(
                    new StreamResource(LocalDate.now() + " someSheetPDF.pdf",
                            () -> recycleBinService.getPDF().byteStream()));
            buttonWrapper3.wrapComponent(new Button("Скачать в формате PDF"));


            VerticalLayout fieldLayout = new VerticalLayout(buttonWrapper1, buttonWrapper2, buttonWrapper3
            );
            fieldLayout.setSpacing(false);
            fieldLayout.setPadding(false);
            fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);


            Button cancelButton = new Button("Закрыть", e -> dialog.close());


            HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton);
            buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

            VerticalLayout dialogLayout = new VerticalLayout(headline, fieldLayout, buttonLayout);
            dialogLayout.setPadding(false);
            dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
            dialogLayout.getStyle().set("width", "350px").set("max-width", "100%");

            return dialogLayout;
        }


    }
