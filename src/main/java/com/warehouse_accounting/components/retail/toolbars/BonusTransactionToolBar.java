package com.warehouse_accounting.components.retail.toolbars;


import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.warehouse_accounting.components.util.SilverButton;
import com.warehouse_accounting.services.interfaces.BonusTransactionService;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BonusTransactionToolBar extends HorizontalLayout {
    private MenuBar menuBarOperation;
    private BonusTransactionService service;
    private Button refreshButton;
    private IntegerField miniField;
    private MenuBar menuBarChanged;
    private MenuItem deleteItem;
    private MenuItem copyItem;
    private Button filterButton;
    private MenuItem massEdit;
    private TextField searchField;

    public BonusTransactionToolBar(BonusTransactionService service) {
        this.service = service;

        add(
                toolBar()
        );
    }


    private HorizontalLayout toolBar() {
        HorizontalLayout toolbarLayout = new HorizontalLayout();
        SilverButton silverButton = new SilverButton();
        //toolbar settings
        toolbarLayout.setWidthFull();
        toolbarLayout.setAlignItems(Alignment.CENTER);

        //MenuBar
        menuBarOperation = new MenuBar();
        menuBarOperation.addThemeVariants(MenuBarVariant.LUMO_ICON, MenuBarVariant.LUMO_CONTRAST);
        menuBarOperation.addItem(menuVision("????????????????"));
        //Filter
        filterButton = silverButton.buttonBlank("????????????");
        //Text
        Span text = new Span("???????????????? ?? ??????????????");
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
        refreshButton = silverButton.buttonRefresh();


        //Inputs
        searchField = new TextField();
        searchField.setPlaceholder("?????????? ?????? ??????????????????????");
        searchField.setWidth("200px");
        miniField = new IntegerField();
        miniField.setWidth("25px");
        miniField.setValue(0);
        miniField.setReadOnly(true);

        //MenuBar Edit
        menuBarChanged = new MenuBar();
        menuBarChanged.addThemeVariants(MenuBarVariant.LUMO_ICON, MenuBarVariant.LUMO_CONTRAST);

        MenuItem operation = menuBarChanged.addItem(menuVision("????????????????"));

        SubMenu accureSubMenu = operation.getSubMenu();
        deleteItem = accureSubMenu.addItem("??????????????");
        deleteItem
                .getElement()
                .setAttribute("disabled", true);

        copyItem = accureSubMenu.addItem("????????????????????");
        copyItem.getElement()
                .setAttribute("disabled", true);
        massEdit = accureSubMenu.addItem("???????????????? ????????????????????????????");


        toolbarLayout.add(
                helpButton,
                text,
                refreshButton,
                menuBarOperation,
                filterButton,
                searchField,
                miniField,
                menuBarChanged

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

        HorizontalLayout message = new HorizontalLayout(text, closeButton);
        notification.add(message);
        notification.open();
        notification.setPosition(Notification.Position.TOP_START);

        return notification;

    }

    private Text helpNotificationText() {

        return new Text(
                "???????????????? ?????????????????? ?????????????????? ?? ?????????????? ???????? ???????????????????????? ??? ?????????????????? ??? ????????????.\n" +
                        "\n" +
                        "?????????? ?????????????????????? ?? ?????????????????????? ???? ?????????????????? ?????????????????? ?????????????? (????????????????) ?????? ????????????????. ???????????????? ?????????????????? ?????????????? ?????? ??????????????, ?????????????? ???????????? ???? ???????????? ?????????????? ???????????????? ?? ???????????????? ?? ???????????????????? ???????????? ???????????????????? ???????????? ?????? ???????????????? ????????????.\n" +
                        "\n" +
                        "???????????? ???????????? ???????????????????????? ?? ???????????????? ??????????????????????.\n" +
                        "\n" +
                        "???????????? ????????????????????: ???????????????? ??????????????????"
        );

    }

    private MenuBar menuEditButton() {
        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_ICON, MenuBarVariant.LUMO_CONTRAST);

        MenuItem operation = menuBar.addItem(menuVision("????????????????"));

        SubMenu accureSubMenu = operation.getSubMenu();
        accureSubMenu.addItem("??????????????")
                .getElement()
                .setAttribute("disabled", true);
        accureSubMenu.addItem("????????????????????")
                .getElement()
                .setAttribute("disabled", true);
        accureSubMenu.addItem("???????????????? ????????????????????????????");

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
