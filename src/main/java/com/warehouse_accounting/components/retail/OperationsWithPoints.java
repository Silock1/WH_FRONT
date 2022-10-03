package com.warehouse_accounting.components.retail;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.components.util.SilverButton;
import com.warehouse_accounting.models.dto.OperationsWithPointsDto;
import com.warehouse_accounting.models.dto.ProductDto;

/**
 * Операции с баллами (Розница/Операции с баллами)
 **/

@Route("operationsWIthPoints")
@CssImport(value = "./css/application.css")
public class OperationsWithPoints extends VerticalLayout {
    Grid<OperationsWithPointsDto> pointsGrid = new Grid<>(OperationsWithPointsDto.class, false);

    public OperationsWithPoints() {
        setSizeFull();
        add(toolBar(),
                tableContent()
        );


    }

    public HorizontalLayout tableContent() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        horizontalLayout.setSizeFull();
        pointsGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        pointsGrid.setSizeFull();
        pointsGrid.setSelectionMode(Grid.SelectionMode.MULTI);



        horizontalLayout.add(pointsGrid, settingButtonOperations());
        return horizontalLayout;
    }

    private Button settingButtonOperations() {
        SilverButton silverButton = new SilverButton();
        Grid.Column<OperationsWithPointsDto> idColumn =  pointsGrid.addColumn(OperationsWithPointsDto::getId).setHeader("№").setSortable(true);
        Grid.Column<OperationsWithPointsDto> dateCreateColumn = pointsGrid.addColumn(OperationsWithPointsDto::getDateCreate).setHeader("Дата создания").setSortable(true);
        Grid.Column<OperationsWithPointsDto> operationColumn =  pointsGrid.addColumn(OperationsWithPointsDto::getTypeOfOperation).setHeader("Тип операции").setSortable(true);
        Grid.Column<OperationsWithPointsDto> bonusColumn =  pointsGrid.addColumn(OperationsWithPointsDto::getBonusPoints).setHeader("Бонусные баллы").setSortable(true);
        Grid.Column<OperationsWithPointsDto> statusColumn =  pointsGrid.addColumn(OperationsWithPointsDto::getStatus).setHeader("Статус").setSortable(true);
        Grid.Column<OperationsWithPointsDto> dateChargeColumn =  pointsGrid.addColumn(OperationsWithPointsDto::getDateProfit).setHeader("Дата начисления").setSortable(true);
        Grid.Column<OperationsWithPointsDto> bonusProgramColumn =  pointsGrid.addColumn(OperationsWithPointsDto::getBonusProgram).setHeader("Бонусная программа").setSortable(true);
        Grid.Column<OperationsWithPointsDto> contragentColumn =  pointsGrid.addColumn(OperationsWithPointsDto::getContrAgent).setHeader("Контрагент").setSortable(true);
        Grid.Column<OperationsWithPointsDto> commentaryColumn =  pointsGrid.addColumn(OperationsWithPointsDto::getCommentary).setHeader("Комментарий").setSortable(true);

        Button settingButton = silverButton.settingButton();
        ColumnToggleContextMenu<OperationsWithPointsDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(settingButton);
        columnToggleContextMenu.addColumnToggleItem("№", idColumn);
        columnToggleContextMenu.addColumnToggleItem("Дата создания", dateCreateColumn);
        columnToggleContextMenu.addColumnToggleItem("Тип операции", operationColumn);
        columnToggleContextMenu.addColumnToggleItem("Бонусные баллы", bonusColumn);
        columnToggleContextMenu.addColumnToggleItem("Статус", statusColumn);
        columnToggleContextMenu.addColumnToggleItem("Дата начисления", dateChargeColumn);
        columnToggleContextMenu.addColumnToggleItem("Бонусная программа", bonusProgramColumn);
        columnToggleContextMenu.addColumnToggleItem("Контрагент", contragentColumn);
        columnToggleContextMenu.addColumnToggleItem("Комментарий", commentaryColumn);
        return settingButton;
    }



    private HorizontalLayout toolBar() {
        HorizontalLayout toolbarLayout = new HorizontalLayout();
        SilverButton silverButton = new SilverButton();
        //toolbar settings
        toolbarLayout.setWidthFull();
        toolbarLayout.setAlignItems(Alignment.CENTER);

        //MenuBar
        MenuBar menuOperation = menuOperationButton("Операция");
        //Filter
        Button filter = silverButton.btnBlank("Фильтр");
        //Text
        Span text = new Span("Операции с баллами");
        text.setClassName("title");
        //Help
        Button helpButton = silverButton.helpButton();

        //Help realisation
        helpButton.addClickListener(buttonClickEvent ->
                show()
        );

        //TODO Refresh: getList from rest
        Button refreshButton = silverButton.refreshButton();

        //Inputs
        Input searchField = new Input();
        searchField.setPlaceholder("Номер или комментарий");
        searchField.setWidth("200px");
        Input miniField = new Input();
        miniField.setWidth("25px");
        miniField.setValue("0");

        //MenuBar Edit
        MenuBar edit = menuEditButton("Изменить");


        toolbarLayout.add(
                helpButton,
                text,
                refreshButton,
                menuOperation,
                filter,
                searchField,
                miniField,
                edit

        );
        return toolbarLayout;
    }

    private Notification show() {
        Notification notification = new Notification();


        Div text = new Div(helpNotificationText());

        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.getElement().setAttribute("aria-label", "Close");
        closeButton.addClickListener(event -> {
            notification.close();
        });

        HorizontalLayout layout = new HorizontalLayout(text, closeButton);


        notification.add(layout);
        notification.open();

        notification.setPosition(Notification.Position.TOP_START);

        return notification;

    }

    private Text helpNotificationText() {

        return new Text(
                "Бонусные программы создаются в разделе Меню пользователя → Настройки → Скидки.\n" +
                        "\n" +
                        "Баллы начисляются и списываются из документа розничной продажи (возврата) или отдельно. Откройте розничную продажу или возврат, нажмите вверху на кнопку Создать документ и выберите в выпадающем списке Начисление баллов или Списание баллов.\n" +
                        "\n" +
                        "Баланс баллов отображается в карточке контрагента.\n" +
                        "\n" +
                        "Читать инструкцию: Бонусные программы"
        );
    }

    private void helpButtonAction() {

    }

    private MenuBar menuOperationButton(String textButton) {
        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_ICON, MenuBarVariant.LUMO_CONTRAST);

        MenuItem operation = menuBar.addItem(menuVision(textButton));

        SubMenu accureSubMenu = operation.getSubMenu();

        accureSubMenu.addItem("Начислить");//.onEnabledStateChanged(false);
        accureSubMenu.addItem("Списать");//.onEnabledStateChanged(false);


        return menuBar;
    }

    private MenuBar menuEditButton(String textButton) {
        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_ICON, MenuBarVariant.LUMO_CONTRAST);

        MenuItem operation = menuBar.addItem(menuVision(textButton));

        SubMenu accureSubMenu = operation.getSubMenu();
        accureSubMenu.addItem("Удалить")
                .getElement()
                .setAttribute("disabled", true);
        accureSubMenu.addItem("Копировать")
                .getElement()
                .setAttribute("disabled", true);
        accureSubMenu.addItem("Массовое редактирование");

        return menuBar;
    }

    private HorizontalLayout menuVision(String textButton) {
        Icon caretDownEdit = new Icon(VaadinIcon.CARET_DOWN);
        caretDownEdit.setSize("13px");
        Image image = new Image("icons/plusBlue.jpg", "Plus");


        HorizontalLayout menuVision = new HorizontalLayout(image, new Text(textButton), caretDownEdit);
        menuVision.setSpacing(false);
        return menuVision;

    }





}


