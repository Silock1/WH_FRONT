package com.warehouse_accounting.components.user;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.user.settings.SettingsView;
import com.warehouse_accounting.models.dto.TokenDto;
import com.warehouse_accounting.services.interfaces.TokenService;


@PageTitle("Токены")
@Route(value = "token", layout = SettingsView.class)
public class TokenView extends VerticalLayout {

    private final TextField textField = new TextField();
    private final TokenService tokenService;
    private final Grid<TokenDto> tokenDtoGrid = new Grid<>(TokenDto.class, false);

    public TokenView(TokenService tokenService) {
        this.tokenService = tokenService;
        HorizontalLayout groupButtons = getGroupButtons();
        add(groupButtons);
        setSizeFull();
        add(tokenDtoGrid());
    }


    private HorizontalLayout getGroupButtons() {
        var horizontalLayout = new HorizontalLayout();

        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        helpButton.addClickListener(buttonClickEvent -> Notification.show("" +
                "На этой странице можно отслеживать токены, выданные сотрудникам и приложениям.\n" +
                "\n" + "Токены доступа предоставляют альтернативный способ доступа к JSON API МоегоСклада." +
                "\n" + "Для того чтобы создать новый токен, щёлкните по кнопке Токен.\n" +
                "\n" +
                "При удалении приложения выданный этому приложению токен удаляется автоматически.", 5000, Notification.Position.TOP_START));

        Label token = new Label("Токены");

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        Button addToken = new Button("Токен", new Icon(VaadinIcon.PLUS));
        addToken.addThemeVariants(ButtonVariant.LUMO_SMALL);

        TextField searchField = new TextField();
        searchField.setWidth("200px");
        searchField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        searchField.addInputListener(inputEvent -> {


        });

        HorizontalLayout setting = getSetting();

        horizontalLayout.add(helpButton, token, refreshButton, addToken, setting);
        return horizontalLayout;
    }

    private HorizontalLayout getSetting() {
        Icon caretDownIcon = new Icon(VaadinIcon.CARET_DOWN);
        caretDownIcon.setSize("12px");
        textField.setReadOnly(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidth("30px");
        textField.setValue("0");

        MenuBar editMenuBar = new MenuBar();
        editMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        HorizontalLayout editItem = new HorizontalLayout(new Text("Изменить"), caretDownIcon);
        editItem.setSpacing(false);
        editItem.setAlignItems(Alignment.CENTER);

        MenuItem editMenu = editMenuBar.addItem(editItem);
        editMenu.getSubMenu().addItem("Удалить", menuItemClickEvent -> {
        });

        var horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(textField, editMenuBar);
        horizontalLayout.setSpacing(false);
        horizontalLayout.setAlignItems(Alignment.CENTER);
        return horizontalLayout;
    }

    private HorizontalLayout tokenDtoGrid() {
        var horizontalLayout = new HorizontalLayout();
        tokenDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        tokenDtoGrid.setItems(tokenService.getAll());
        tokenDtoGrid.addColumn(TokenDto::getId).setHeader("Создан пользователем");
        tokenDtoGrid.addColumn(TokenDto::getFirstName).setHeader("Владелец");
        add(tokenDtoGrid);
        return horizontalLayout;
    }
}
