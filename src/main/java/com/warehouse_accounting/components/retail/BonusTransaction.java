package com.warehouse_accounting.components.retail;

import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dependency.CssImport;
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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


/**
 * Операции с баллами (Розница/Операции с баллами)
 **/
@SpringComponent
@Route("bonus_transaction")
@CssImport(value = "./css/application.css")
@UIScope
public class BonusTransaction extends VerticalLayout {

    private final BonusTransactionGridLayout grid;
    private final BonusTransactionToolBar toolBar;
    private final BonusTransactionService transactionService;
    private final BonusProgramService programService;
    private final ContractorService contractorService;
    private final BonusTransactionForm earningForm = new BonusTransactionForm(BonusTransactionForm.TypeOperation.EARNING);
    private final BonusTransactionForm spendingForm = new BonusTransactionForm(BonusTransactionForm.TypeOperation.SPENDING);
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Set<BonusTransactionDto> selectedItems;

    public BonusTransaction(BonusTransactionGridLayout grid,

                            BonusTransactionToolBar toolBar,

                            BonusTransactionService transactionService,
                            BonusProgramService programService,
                            ContractorService contractorService) {
        this.transactionService = transactionService;
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
        saveButtonSpendingRealise();
        saveButtonEarningRealise();
        setDeleteLogic();
        setCopyLogic();
        add(toolBar, grid, earningForm, spendingForm);

    }

    private void setSelectItem() {
        grid.getPointsGrid().addItemDoubleClickListener(event -> {
                    BonusTransactionDto.TransactionType currentType = event.getItem().getTransactionType();
                    if (currentType == BonusTransactionDto.TransactionType.EARNING) {
                        setForm(event.getItem(), earningForm);
                        openForm(earningForm);
                    } else {
                        setForm(event.getItem(), spendingForm);
                        openForm(spendingForm);
                    }
                }
        );

    }


    private void setCloseButtonEarning() {
        earningForm.getClosedButton().addClickListener(buttonClickEvent -> {
                    closeForm(earningForm);
                    updateGrid();
                }
        );
    }

    private void setCloseButtonSpending() {
        spendingForm.getClosedButton().addClickListener(buttonClickEvent -> {
                    closeForm(spendingForm);
                    updateGrid();
                }
        );
    }

    private void openForm(BonusTransactionForm form) {
        grid.setVisible(false);
        toolBar.setVisible(false);
        form.setVisible(true);
    }

    private void closeForm(BonusTransactionForm form) {
        grid.setVisible(true);
        toolBar.setVisible(true);
        form.setVisible(false);
    }

    private void updateGrid() {

        grid.getPointsGrid().setItems(transactionService.getAll());
    }

    private void setRefreshButton() {
        toolBar.getRefreshButton().addClickListener(buttonClickEvent -> {
            updateGrid();
        });
    }

    private void setSubMenuEarning() {
        toolBar.getMenuBarOperation().getItems().get(0).getSubMenu().addItem("Начислить",
                menuItemClickEvent -> {
                    setForm(new BonusTransactionDto(), earningForm);
                    openForm(earningForm);
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

                    setForm(new BonusTransactionDto(), spendingForm);
                    openForm(spendingForm);
                }
        );
    }

    private void saveButtonEarningRealise() {
        earningForm.getSaveButton().addClickListener(click -> {
            transactionService.create(getDataFromForm(earningForm));


        });
    }

    private void saveButtonSpendingRealise() {
        spendingForm.getSaveButton().addClickListener(click -> {
            transactionService.create(getDataFromForm(spendingForm));

        });
    }


    private void setForm(BonusTransactionDto dto, BonusTransactionForm form) {
        dto.setBonusProgramDto(form.getBonusProgram().getValue());
        dto.setContragent(form.getContractor().getValue());

        if (dto.getId() == null) {
            form.getIdInput().setValue(0);
            form.getComment().setValue("");
            form.getBonusValueInput().setValue(0);
            form.getExecutionDate().setValue(LocalDate.now());
            form.getCreatedDate().setValue(LocalDate.now());
        } else {
            form.getIdInput().setValue(Math.toIntExact(dto.getId()));
            form.getComment().setValue(dto.getComment());
            form.getBonusValueInput().setValue(Math.toIntExact(dto.getBonusValue()));
            form.getExecutionDate().setValue(dto.getExecutionDate());
            form.getCreatedDate().setValue(dto.getCreated());

        }

        form.getBonusProgram().setItems(getBonusPrograms());
        form.getBonusProgram().setValue(getBonusPrograms().get(0));
        form.getContractor().setItems(getContractors());
        form.getContractor().setValue(getContractors().get(0));
        form.getContractor().setItemLabelGenerator(ContractorDto::getName);
        form.getBonusProgram().setItemLabelGenerator(BonusProgramDto::getName);
    }

    private BonusTransactionDto getDataFromForm(BonusTransactionForm form) {
        BonusTransactionDto dto = new BonusTransactionDto();
        dto.setId(Long.valueOf(form.getIdInput().getValue()));
        dto.setExecutionDate(form.getExecutionDate().getValue());
        dto.setCreated(form.getCreatedDate().getValue());
        dto.setComment(form.getComment().getValue());
        dto.setBonusValue(Long.valueOf(form.getBonusValueInput().getValue()));
        dto.setBonusProgramDto(form.getBonusProgram().getValue());
        dto.setContragent(form.getContractor().getValue());

        if (form.equals(earningForm)) {
            dto.setTransactionType(BonusTransactionDto.TransactionType.EARNING);
        } else {
            dto.setTransactionType(BonusTransactionDto.TransactionType.SPENDING);
        }

        dto.setTransactionStatus(BonusTransactionDto.TransactionStatus.COMPLETED);
        return dto;
    }


    private void setMultiSelect() {

        grid.getPointsGrid().asMultiSelect().addSelectionListener(multiSelectionEvent -> {
            toolBar.getMiniField().setValue(multiSelectionEvent.getAllSelectedItems().size());
        });


    }

    private void setDeleteLogic() {

        setSelectedItems();
        toolBar.getDeleteItem().addClickListener(eventDelete -> {
            for (BonusTransactionDto dto : selectedItems) {
                transactionService.deleteById(dto.getId());
            }
            updateGrid();
        });


    }

    private void setSelectedItems() {
        grid.getPointsGrid().asMultiSelect().addSelectionListener(event -> {
            selectedItems = event.getAllSelectedItems();

        });
    }


    private void setCopyLogic() {
        setSelectedItems();

        toolBar.getCopyItem().addClickListener(eventCopy -> {
            for (BonusTransactionDto dto : selectedItems) {
                //set id 0 чтобы он создал новый при create, иначе с тем же id не создает
                dto.setId(0L);
                //заглушка
                dto.setOwner(null);
                transactionService.create(dto);
            }
            updateGrid();
        });


    }

    private List<BonusProgramDto> getBonusPrograms() {
        return programService.getAll();
    }

    private List<ContractorDto> getContractors() {
        return contractorService.getAll();
    }

}


