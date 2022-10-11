package com.warehouse_accounting.components.retail;

import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.retail.forms.bonus_transaction.BonusTransactionForm;
import com.warehouse_accounting.components.retail.grids.BonusTransactionGridLayout;
import com.warehouse_accounting.components.retail.toolbars.BonusTransactionToolBar;
import com.warehouse_accounting.models.dto.BonusProgramDto;
import com.warehouse_accounting.models.dto.BonusTransactionDto;
import com.warehouse_accounting.models.dto.ContractorDto;
import com.warehouse_accounting.services.interfaces.BonusProgramService;
import com.warehouse_accounting.services.interfaces.BonusTransactionService;
import com.warehouse_accounting.services.interfaces.ContractorService;

import java.time.LocalDate;
import java.util.List;


/**
 * Операции с баллами (Розница/Операции с баллами)
 **/
@SpringComponent
@Route("bonus_transaction")
@CssImport(value = "./css/application.css")
@UIScope
public class BonusTransaction extends VerticalLayout {

    private BonusTransactionGridLayout grid;
    private BonusTransactionToolBar toolBar;
    private BonusTransactionService service;
    private BonusProgramService programService;
    private ContractorService contractorService;
    private BonusTransactionForm earningForm = new BonusTransactionForm(BonusTransactionForm.TypeOperation.CHARGE);
    private BonusTransactionForm spendingForm = new BonusTransactionForm(BonusTransactionForm.TypeOperation.WRITE_OFF);


    public BonusTransaction(BonusTransactionGridLayout grid,

                            BonusTransactionToolBar toolBar,

                            BonusTransactionService service,
                            BonusProgramService programService,
                            ContractorService contractorService) {
        this.service = service;
        this.programService = programService;
        this.contractorService = contractorService;
        this.grid = grid;
        this.toolBar = toolBar;

        setMultiSelect();
        setVisibleChangeSubmenu();
        setSelectItem();
        setCloseButtonEarning();
        setCloseButtonSpending();
        setSubMenuEarning();
        setSubMenuSpending();
        setRefreshButton();
        loadSpendingForm();
        loadEarningForm();
        setDeleteLogic();
        setCopyLogic();
        add(toolBar, grid, earningForm, spendingForm);

    }

    private void setSelectItem() {
        grid.getPointsGrid().addItemDoubleClickListener(event -> {
                    BonusTransactionDto.TransactionType currentType = event.getItem().getTransactionType();
                    if (currentType == BonusTransactionDto.TransactionType.EARNING) {
                        setChargeFormFields(event.getItem());
                        openEarningForm();
                    } else {
                        setWriteOffFormFields(event.getItem());
                        openSpendingForm();
                    }


                }
        );

    }


    private void setCloseButtonEarning() {
        earningForm.getClosedButton().addClickListener(buttonClickEvent -> {
                    closeEarningForm();
                    updateGrid();

                }
        );
    }

    private void setCloseButtonSpending() {
        spendingForm.getClosedButton().addClickListener(buttonClickEvent -> {
                    closeSpendingForm();
                    updateGrid();
                }
        );
    }

    private void openEarningForm() {
        grid.setVisible(false);
        toolBar.setVisible(false);
        earningForm.setVisible(true);
    }

    private void openSpendingForm() {
        grid.setVisible(false);
        toolBar.setVisible(false);
        spendingForm.setVisible(true);

    }

    private void closeEarningForm() {
        grid.setVisible(true);
        toolBar.setVisible(true);
        earningForm.setVisible(false);

    }

    private void closeSpendingForm() {
        grid.setVisible(true);
        toolBar.setVisible(true);
        spendingForm.setVisible(false);
    }

    private void updateGrid() {
        grid.getPointsGrid().setItems(service.getAll());
    }

    private void setRefreshButton() {
        toolBar.getRefreshButton().addClickListener(buttonClickEvent -> {
            updateGrid();
        });
    }

    private void setSubMenuEarning() {
        toolBar.getMenuBarOperation().getItems().get(0).getSubMenu().addItem("Начислить",
                menuItemClickEvent -> {


                    setChargeFormFields(new BonusTransactionDto());
                    openEarningForm();
                }
        );
    }

    private void setVisibleChangeSubmenu() {
        MenuItem delete = toolBar.getDeleteItem();
        MenuItem copy = toolBar.getCopyItem();
        toolBar.getMenuBarChanged().getItems().get(0).addClickListener(event ->
        {
            if (toolBar.getMiniField().getValue() > 0) {

                delete.getElement().setAttribute("disabled", false);
                copy.getElement().setAttribute("disabled", false);
            } else {
                delete.getElement().setAttribute("disabled", true);
                copy.getElement().setAttribute("disabled", true);
            }
        });

    }


    private void setSubMenuSpending() {
        toolBar.getMenuBarOperation().getItems().get(0).getSubMenu().addItem("Списать",
                menuItemClickEvent -> {
                    BonusTransactionDto dto = BonusTransactionDto.builder()
                            .transactionType(BonusTransactionDto.TransactionType.SPENDING)
                            .build();

                    setWriteOffFormFields(dto);
                    openSpendingForm();
                }
        );
    }

    private void loadEarningForm() {

        earningForm.getBonusProgram().setItems(getBonusPrograms());
        earningForm.getBonusProgram().setValue(getBonusPrograms().get(0));
        earningForm.getContractor().setItems(getContractors());
        earningForm.getContractor().setValue(getContractors().get(0));
        earningForm.getContractor().setItemLabelGenerator(ContractorDto::getName);
        earningForm.getBonusProgram().setItemLabelGenerator(BonusProgramDto::getName);


        earningForm.getSaveButton().addClickListener(click -> {

            service.create(getFromChargeForm());

        });


    }

    private void loadSpendingForm() {

        spendingForm.getBonusProgram().setItems(getBonusPrograms());
        spendingForm.getBonusProgram().setValue(getBonusPrograms().get(0));
        spendingForm.getContractor().setItems(getContractors());
        spendingForm.getContractor().setValue(getContractors().get(0));
        spendingForm.getContractor().setItemLabelGenerator(ContractorDto::getName);
        spendingForm.getBonusProgram().setItemLabelGenerator(BonusProgramDto::getName);

        spendingForm.getSaveButton().addClickListener(click -> {

            service.create(getFromWriteOffForm());

        });
    }

    private BonusTransactionDto getFromWriteOffForm() {

        BonusTransactionDto dto = new BonusTransactionDto();

        dto.setId(Long.valueOf(spendingForm.getIdInput().getValue()));
        dto.setExecutionDate(spendingForm.getExecutionDate().getValue());
        dto.setCreated(spendingForm.getCreatedDate().getValue());
        dto.setComment(spendingForm.getComment().getValue());
        dto.setBonusValue(Long.valueOf(spendingForm.getBonusValueInput().getValue()));
        dto.setBonusProgramDto(spendingForm.getBonusProgram().getValue());
        dto.setContragent(spendingForm.getContractor().getValue());
        dto.setTransactionType(BonusTransactionDto.TransactionType.SPENDING);
        dto.setTransactionStatus(BonusTransactionDto.TransactionStatus.COMPLETED);

        return dto;

    }


    private void setChargeFormFields(BonusTransactionDto dto) {

        if (dto.getId() == null) {
            earningForm.getIdInput().setValue(0);
            earningForm.getComment().setValue("");
            earningForm.getBonusValueInput().setValue(0);
            earningForm.getExecutionDate().setValue(LocalDate.now());
            earningForm.getCreatedDate().setValue(LocalDate.now());
        } else {
            earningForm.getIdInput().setValue(Math.toIntExact(dto.getId()));
            earningForm.getComment().setValue(dto.getComment());
            earningForm.getBonusValueInput().setValue(Math.toIntExact(dto.getBonusValue()));
            earningForm.getExecutionDate().setValue(dto.getExecutionDate());
            earningForm.getCreatedDate().setValue(dto.getCreated());

        }
        dto.setBonusProgramDto(earningForm.getBonusProgram().getValue());
        dto.setContragent(earningForm.getContractor().getValue());

        System.out.println(dto);

    }

    private void setWriteOffFormFields(BonusTransactionDto dto) {

        if (dto.getId() == null) {
            spendingForm.getIdInput().setValue(0);
            spendingForm.getComment().setValue("");
            spendingForm.getBonusValueInput().setValue(0);
            spendingForm.getExecutionDate().setValue(LocalDate.now());
            spendingForm.getCreatedDate().setValue(LocalDate.now());
        } else {
            spendingForm.getIdInput().setValue(Math.toIntExact(dto.getId()));
            spendingForm.getComment().setValue(dto.getComment());
            spendingForm.getBonusValueInput().setValue(Math.toIntExact(dto.getBonusValue()));
            spendingForm.getExecutionDate().setValue(dto.getExecutionDate());
            spendingForm.getCreatedDate().setValue(dto.getCreated());

        }
        dto.setBonusProgramDto(spendingForm.getBonusProgram().getValue());
        dto.setContragent(spendingForm.getContractor().getValue());


    }

    private BonusTransactionDto getFromChargeForm() {
        BonusTransactionDto dto = new BonusTransactionDto();

        dto.setId(Long.valueOf(earningForm.getIdInput().getValue()));
        dto.setExecutionDate(earningForm.getExecutionDate().getValue());
        dto.setCreated(earningForm.getCreatedDate().getValue());
        dto.setComment(earningForm.getComment().getValue());
        dto.setBonusValue(Long.valueOf(earningForm.getBonusValueInput().getValue()));
        dto.setBonusProgramDto(earningForm.getBonusProgram().getValue());
        dto.setContragent(earningForm.getContractor().getValue());
        dto.setTransactionType(BonusTransactionDto.TransactionType.EARNING);
        dto.setTransactionStatus(BonusTransactionDto.TransactionStatus.COMPLETED);


        return dto;
    }

    private void setMultiSelect() {

        grid.getPointsGrid().asMultiSelect().addSelectionListener(multiSelectionEvent -> {

            toolBar.getMiniField().setValue(multiSelectionEvent.getAllSelectedItems().size());
        });

    }

    private void setDeleteLogic() {

        grid.getPointsGrid().asMultiSelect().addSelectionListener(event -> {

            toolBar.getDeleteItem().addClickListener(clickDelete -> {

                for (BonusTransactionDto dto : event.getAllSelectedItems()) {
                    service.deleteById(dto.getId());
                }
                updateGrid();
            });


        });


    }

    private void setCopyLogic() {
        toolBar.getCopyItem().addClickListener(event -> {
            Notification.show("Берем лист из выделенных и прогоняем через метод create");
        });
    }

    private List<BonusProgramDto> getBonusPrograms() {
        return programService.getAll();
    }

    private List<ContractorDto> getContractors() {
        return contractorService.getAll();
    }

}


