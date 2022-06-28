package com.warehouse_accounting.components.production;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.components.production.forms.ProductionOperationsForm;
import com.warehouse_accounting.components.production.grids.ProductionOperationsGridLayout;
import com.warehouse_accounting.models.dto.ProductionOperationsDto;
import com.warehouse_accounting.services.interfaces.ProductionOperationsService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@SpringComponent
@UIScope
@Log4j2
@Route (value = "ProductionOperations", layout = AppView.class)
public class ProductionOperations extends VerticalLayout {
    private ProductionOperationsService productionOperationsService;
    private TextField textField;
    private ProductionOperationsGridLayout productionOperationsGridLayout;
    private final HorizontalLayout formLayout;

    @Getter
    @Setter
    private Div pageContent;

    private HorizontalLayout createTopGroupElements () {
        HorizontalLayout topPartLayout = new HorizontalLayout();
        topPartLayout.setAlignItems(Alignment.CENTER);



        return topPartLayout;
    }

    private Button createNewOperation () {
        Image buttonIcon = new Image("icons/plus.png", "Plus");
        buttonIcon.setWidth("14px");
        Button addProductionOperationButton = new Button("Операция", buttonIcon);
        addProductionOperationButton.addClickListener(buttonClickEvent -> {
            add(new ProductionOperationsForm(pageContent));
        });
        return addProductionOperationButton;
    }


    private HorizontalLayout getEditMenuBar() {
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
        editItem.setAlignItems(Alignment.CENTER); //

        MenuItem editMenu = editMenuBar.addItem(editItem);
        editMenu.getSubMenu().addItem("Удалить", menuItemClickEvent -> {
        }).getElement().setAttribute("disabled", true);

        editMenu.getSubMenu().addItem("Массовое редактирование", menuItemClickEvent -> {

        });
        editMenu.getSubMenu().addItem("Поместить в архив", menuItemClickEvent -> {

        }).getElement().setAttribute("disabled", true);
        editMenu.getSubMenu().addItem("Извлечь из архива", menuItemClickEvent -> {

        }).getElement().setAttribute("disabled", true);

        HorizontalLayout groupEdit = new HorizontalLayout();
        groupEdit.add(textField, editMenuBar);
        groupEdit.setSpacing(false);
        groupEdit.setAlignItems(Alignment.CENTER); //
        return groupEdit;
    }
}
