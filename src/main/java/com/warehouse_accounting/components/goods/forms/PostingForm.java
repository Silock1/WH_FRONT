package com.warehouse_accounting.components.goods.forms;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.goods.Posting;
import com.warehouse_accounting.models.dto.PostingDto;
import com.warehouse_accounting.services.interfaces.PostingService;
import lombok.extern.log4j.Log4j2;

import java.util.Comparator;
import java.util.List;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY_INLINE;
@UIScope
@Log4j2
public class PostingForm extends VerticalLayout {
    private final Posting posting;
    private final PostingService postingService;
    private final Div returnDiv;
    private final VerticalLayout selectStage = new VerticalLayout();
    private final List<PostingDto> postingDtoList;
    private Binder<PostingDto> postingDtoBinder = new Binder<>(PostingDto.class);
    private PostingDto postingDto;


    public PostingForm(
            Posting posting,
            PostingDto postingDto,
            PostingService postingService
    ) {
        this.posting = posting;
        this.postingDto = postingDto;
        this.postingService = postingService;
        this.postingDtoList = postingService.getAll();
        this.postingDtoList.sort(Comparator.comparingLong(PostingDto::getId));
        this.returnDiv = posting.getPageContent();

        posting.removeAll();
        add(createTopGroupElements(), createInputFieldForm());

    }

    private HorizontalLayout createTopGroupElements() {
        HorizontalLayout topPartLayout = new HorizontalLayout();
        topPartLayout.setAlignItems(Alignment.CENTER);

        HorizontalLayout editMenuBar = getEditMenuBar();

        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_ICON, MenuBarVariant.LUMO_CONTRAST);

        Icon caretDownPrint = new Icon(VaadinIcon.CARET_DOWN);
        caretDownPrint.setSize("13px");
        Icon caretDownStatus = new Icon(VaadinIcon.CARET_DOWN);
        caretDownStatus.setSize("13px");

        HorizontalLayout statusVision = new HorizontalLayout(new Text("Отправить"), caretDownStatus);
        statusVision.setSpacing(false);

        MenuItem status = menuBar.addItem(statusVision);
        SubMenu statusSubMenu = status.getSubMenu();
        statusSubMenu.addItem("Настроить");

        Icon printIcon = new Icon(VaadinIcon.PRINT);
        printIcon.setSize("13px");
        HorizontalLayout printVision = new HorizontalLayout(new Text("Печать"), printIcon, caretDownPrint);
        printVision.setSpacing(false);

        MenuItem print = menuBar.addItem(printVision);
        SubMenu printSubMenu = print.getSubMenu();
        printSubMenu.addItem("Список производственных заданий");
        printSubMenu.addItem("Производственное задание").onEnabledStateChanged(false);
        printSubMenu.addItem("Комплект").onEnabledStateChanged(false);
        printSubMenu.addItem("Настроить");

        topPartLayout.add(
                savePostingButton(),
                closePostingButton(),
                editMenuBar,
                print,
                status
        );
        return topPartLayout;
    }

    private HorizontalLayout getEditMenuBar() {
        Icon caretDownIcon = new Icon(VaadinIcon.CARET_DOWN);
        caretDownIcon.setSize("12px");
        TextField textField = new TextField();
        textField.setReadOnly(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidth("30px");
        textField.setValue("0");

        MenuBar editMenuBar = new MenuBar();
        editMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        HorizontalLayout editItem = new HorizontalLayout(new Text("Изменить"), caretDownIcon);
        editItem.setSpacing(false);
        editItem.setAlignItems(Alignment.CENTER); //

        MenuItem editMenu = editMenuBar.addItem(editItem);
        editMenu.getSubMenu().addItem("Удалить", menuItemClickEvent -> {
        }).getElement().setAttribute("disabled", true);

        editMenu.getSubMenu().addItem("Копировать", menuItemClickEvent -> {

        });

        HorizontalLayout groupEdit = new HorizontalLayout();
        groupEdit.add(textField, editMenuBar);
        groupEdit.setSpacing(false);
        groupEdit.setAlignItems(Alignment.CENTER); //
        return groupEdit;
    }
    private Button savePostingButton() {
        Button saveButton = new Button("Сохранить");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        saveButton.addClickListener(c -> {
            if (!postingDtoBinder.validate().isOk()) {
                return;
            }

            if (selectStage.getComponentCount() < 1) {
                showErrorNotification("Организация не может быть пустой");
                return;
            }

        });
        return saveButton;

    }

    private Button closePostingButton() {
        Button closeButton = new Button("Закрыть");
        closeButton.addClickListener(c -> {
            posting.removeAll();
            posting.add(returnDiv);
        });
        return closeButton;

    }

    private VerticalLayout createInputFieldForm() {


        TextField numberField = new TextField();
        numberField.setLabel("Номер");
        numberField.setMinWidth("300px");


        TextField projectField = new TextField();
        projectField.setLabel("Номер");
        projectField.setMinWidth("300px");

        TextField valutaField = new TextField();
        valutaField.setLabel("Номер");
        valutaField.setMinWidth("300px");

        //field volume
        TextField volumeField = new TextField();
        numberField.setLabel("Номер");
        numberField.setMinWidth("300px");

        //field warehouse
        TextField warehouseField = new TextField();
        numberField.setLabel("Номер");
        numberField.setMinWidth("300px");

        VerticalLayout returnLayout = new VerticalLayout();
        returnLayout.setAlignItems(Alignment.START);
        returnLayout.add(numberField, projectField, valutaField, volumeField, warehouseField);
        return returnLayout;
    }


    private void showErrorNotification(String showText) {
        Notification notification = new Notification();
        notification.setPosition(Notification.Position.TOP_START);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Icon icon = VaadinIcon.WARNING.create();
        icon.getElement().getStyle().set("margin-right", "var(--lumo-space-m)");
        Button closeBtn = new Button(
                VaadinIcon.CLOSE_SMALL.create(),
                clickEvent -> notification.close());
        closeBtn.addThemeVariants(LUMO_TERTIARY_INLINE);

        HorizontalLayout layout = new HorizontalLayout();
        layout.setAlignItems(Alignment.CENTER);
        Text text = new Text(showText);
        layout.add(icon, text, closeBtn);
        layout.setMaxWidth("350px");

        notification.add(layout);
        notification.open();
    }
}
