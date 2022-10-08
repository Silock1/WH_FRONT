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
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.warehouse_accounting.components.retail.grids.BonusTransactionGridLayout;
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.components.util.SilverButton;
import com.warehouse_accounting.models.dto.BonusTransactionDto;
import com.warehouse_accounting.services.interfaces.BonusTransactionService;


/**
 * Операции с баллами (Розница/Операции с баллами)
 **/
@SpringComponent
@Route("bonus_transaction")
@CssImport(value = "./css/application.css")
public class BonusTransaction extends VerticalLayout {
    private final Grid<BonusTransactionDto> pointsGrid = new Grid<>(BonusTransactionDto.class, false);
    private BonusTransactionService bonusTransactionService;
    private BonusTransactionGridLayout bonusTransactionGridLayout;

    public BonusTransaction(BonusTransactionService bonusTransactionService,
                            BonusTransactionGridLayout bonusTransactionGridLayout) {
        this.bonusTransactionService = bonusTransactionService;
        this.bonusTransactionGridLayout = bonusTransactionGridLayout;

        setSizeFull();
        add(    closeView(),
                toolBar(),
                bonusTransactionGridLayout

              //  tableContent()
        );

    }

    public HorizontalLayout tableContent() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();
        pointsGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        pointsGrid.setItems(bonusTransactionService.getAll());
        pointsGrid.setSizeFull();
        pointsGrid.setSelectionMode(Grid.SelectionMode.MULTI);


        horizontalLayout.add(pointsGrid, settingButtonOperations());
        return horizontalLayout;
    }

    public Button closeView() {
        return new Button("CloseAll", buttonClickEvent -> this.setVisible(false));
    }

    private Button settingButtonOperations() {
        SilverButton silverButton = new SilverButton();
        Grid.Column<BonusTransactionDto> idColumn = pointsGrid.addColumn(BonusTransactionDto::getId).setHeader("№").setSortable(true);
        Grid.Column<BonusTransactionDto> dateCreateColumn = pointsGrid.addColumn(BonusTransactionDto::getCreated).setHeader("Дата создания").setSortable(true);
        Grid.Column<BonusTransactionDto> operationColumn = pointsGrid.addColumn(BonusTransactionDto::getTransactionType).setHeader("Тип операции").setSortable(true);
        Grid.Column<BonusTransactionDto> bonusColumn = pointsGrid.addColumn(BonusTransactionDto::getBonusValue).setHeader("Бонусные баллы").setSortable(true);
        Grid.Column<BonusTransactionDto> statusColumn = pointsGrid.addColumn(BonusTransactionDto::getTransactionStatus).setHeader("Статус").setSortable(true);
        Grid.Column<BonusTransactionDto> dateChargeColumn = pointsGrid.addColumn(BonusTransactionDto::getExecutionDate).setHeader("Дата начисления").setSortable(true);
        Grid.Column<BonusTransactionDto> bonusProgramColumn = pointsGrid.addColumn(transaction -> transaction.getBonusProgramDto().getName()).setHeader("Бонусная программа").setSortable(true);
        Grid.Column<BonusTransactionDto> contragentColumn = pointsGrid.addColumn(transaction -> transaction.getContragent().getName()).setHeader("Контрагент").setSortable(true);
        Grid.Column<BonusTransactionDto> commentaryColumn = pointsGrid.addColumn(BonusTransactionDto::getComment).setHeader("Комментарий").setSortable(true);

        Button settingButton = silverButton.buttonSetting();
        ColumnToggleContextMenu<BonusTransactionDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(settingButton);
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
        Button filter = silverButton.buttonBlank("Фильтр");
        //Text
        Span text = new Span("Операции с баллами");
        text.getElement().getStyle().set("font-size", "20px");
        text.getElement().getStyle().set("color", "#222");
        text.getElement().getStyle().set("font-weight", "bold");


        //Help
        Button helpButton = silverButton.buttonHelp();

        //Help realisation
        helpButton.addClickListener(buttonClickEvent ->
                show()
        );

        //TODO Refresh: getList from rest
        Button refreshButton = silverButton.buttonRefresh();

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


        HorizontalLayout menuVision = new HorizontalLayout(image, new Span(textButton), caretDownEdit);
        menuVision.setSpacing(false);
        menuVision.getThemeList().add("spacing-s");
        return menuVision;

    }


}


