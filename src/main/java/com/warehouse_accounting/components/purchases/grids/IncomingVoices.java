package com.warehouse_accounting.components.purchases.grids;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.models.dto.IncomingVoicesDto;
import com.warehouse_accounting.models.dto.SerialNumbersDto;
import com.warehouse_accounting.services.interfaces.IncomingVoicesService;
import com.warehouse_accounting.services.interfaces.SerialNumbersService;
import org.springframework.stereotype.Component;

@Component
@UIScope
@Route(value = "incomingVoices", layout = AppView.class)
public class IncomingVoices extends VerticalLayout {
    private HorizontalLayout horizontalToolPanelLayout = new HorizontalLayout();
    private Grid<IncomingVoicesDto> grid = new Grid<>(IncomingVoicesDto.class);

    private Button settingButton = new Button(new Icon(VaadinIcon.COG));
    private IncomingVoicesService incomingVoicesService;

    public IncomingVoices(IncomingVoicesService incomingVoicesService) {
        this.incomingVoicesService = incomingVoicesService;
        horizontalToolPanelLayout.setAlignItems(Alignment.CENTER);

        configToolPanel();
        initGrid();
    }

    private void initGrid() {
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.setItems(incomingVoicesService.getAll());

        Grid.Column<IncomingVoicesDto> sn = grid.getColumnByKey("sn").setHeader("Номер накладной");
        Grid.Column<IncomingVoicesDto> date = grid.getColumnByKey("date").setHeader("Дата");
        Grid.Column<IncomingVoicesDto> otpr = grid.getColumnByKey("otpr").setHeader("Грузоотправитель");
        Grid.Column<IncomingVoicesDto> poluch = grid.getColumnByKey("poluch").setHeader("Грузополучатель");
        Grid.Column<IncomingVoicesDto> sum = grid.getColumnByKey("sum").setHeader("Сумма");
        Grid.Column<IncomingVoicesDto> stat = grid.getColumnByKey("stat").setHeader("Статус ЕГАИС");


        grid.setColumnOrder(sn, date, otpr, poluch, sum, stat);


        settingButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu<IncomingVoicesDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(settingButton);

        columnToggleContextMenu.addColumnToggleItem("Серийный номер", sn);
        columnToggleContextMenu.addColumnToggleItem("Дата", date);
        columnToggleContextMenu.addColumnToggleItem("Грузоотправитель", otpr);
        columnToggleContextMenu.addColumnToggleItem("Грузополучатель", poluch);
        columnToggleContextMenu.addColumnToggleItem("Сумма", sum);
        columnToggleContextMenu.addColumnToggleItem("Статус ЕГАИС", stat);


        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.add(grid, settingButton);
        grid.setHeightByRows(true);
        headerLayout.setWidthFull();

        add(headerLayout);
    }

    private void configToolPanel() {
        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        Dialog dialog = new Dialog();
        dialog.add(new Text("В разделе представлены товарно-транспортные накладные на алкоголь, поступившие от поставщиков.\n" +
                "\n" +
                "Входящие накладные необходимо проверить на расхождения, после чего подтвердить или отклонить."));
        helpButton.addClickListener(event -> dialog.open());

        dialog.setWidth("400px");
        dialog.setHeight("150px");

        Span text = new Span("Входящие накладные ЕГАИС");
        text.setClassName("pageTitle");


        MenuBar menuBar = new MenuBar();

        MenuItem change = menuBar.addItem("Получить документы");
        change.add(new Icon(VaadinIcon.MAGIC));


        horizontalToolPanelLayout.add(helpButton,dialog, text, menuBar);
        add(horizontalToolPanelLayout);
    }
}
//