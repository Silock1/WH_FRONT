package com.warehouse_accounting.components.retail;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.warehouse_accounting.components.retail.forms.bonus_transaction.ChargeForm;
import com.warehouse_accounting.components.retail.forms.bonus_transaction.WriteOffForm;
import com.warehouse_accounting.components.retail.grids.BonusTransactionGridLayout;
import com.warehouse_accounting.components.retail.toolbars.BonusTransactionToolBar;
import com.warehouse_accounting.services.interfaces.BonusTransactionService;


/**
 * Операции с баллами (Розница/Операции с баллами)
 **/
@SpringComponent
@Route("bonus_transaction")
@CssImport(value = "./css/application.css")
public class BonusTransaction extends VerticalLayout {


    private BonusTransactionGridLayout grid;
    private ChargeForm chargeForm;
    private WriteOffForm writeOffForm;
    private BonusTransactionToolBar toolBar;
    private BonusTransactionService service;

    public BonusTransaction(BonusTransactionGridLayout grid,
                            ChargeForm chargeForm,
                            BonusTransactionToolBar toolBar,
                            WriteOffForm writeOffForm,
                            BonusTransactionService service) {

        this.service = service;
        this.grid = grid;
        this.chargeForm = chargeForm;
        this.toolBar = toolBar;
        this.writeOffForm = writeOffForm;


        setSizeFull();
        setCloseButtonCharge();
        setCloseButtonWriteOff();
        setSubMenuCharge();
        setSubMenuWriteOff();
        setRefreshButtonLogic();

        add(toolBar, grid, chargeForm, writeOffForm);

    }

    private void setCloseButtonCharge() {
        chargeForm.getClosedButton().addClickListener(buttonClickEvent -> {
                    grid.setVisible(true);
                    toolBar.setVisible(true);
                    chargeForm.setVisible(false);
                }
        );
    }

    private void setCloseButtonWriteOff() {
        writeOffForm.getClosedButton().addClickListener(buttonClickEvent -> {
                    grid.setVisible(true);
                    toolBar.setVisible(true);
                    writeOffForm.setVisible(false);
                }
        );
    }

    private void setRefreshButtonLogic() {
        toolBar.getRefreshButton().addClickListener(buttonClickEvent -> {
            grid.getPointsGrid().setItems(service.getAll());
        });
    }

    private void setSubMenuCharge() {
        toolBar.getMenuBar().getItems().get(0).getSubMenu().addItem("Начислить",
                menuItemClickEvent -> {
                    toolBar.setVisible(false);
                    grid.setVisible(false);
                    chargeForm.setVisible(true);
                }
        );
    }

    private void setSubMenuWriteOff() {
        toolBar.getMenuBar().getItems().get(0).getSubMenu().addItem("Списать",
                menuItemClickEvent -> {
                    toolBar.setVisible(false);
                    grid.setVisible(false);
                    writeOffForm.setVisible(true);
                }
        );
    }


}


