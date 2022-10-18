package com.warehouse_accounting.components.retail.grids;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.models.dto.BonusTransactionDto;
import com.warehouse_accounting.models.dto.EmployeeDto;
import com.warehouse_accounting.models.dto.FileDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Route("filegrid")
public class FileGridLayOut extends HorizontalLayout {
    private Grid<FileDto> fileGrid = new Grid<>(FileDto.class, false);


    public FileGridLayOut() {

        gridCreate();
        add(fileGrid);
    }

    private void gridCreate() {
        fileGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        fileGrid.addColumn(FileDto::getName).setHeader("Наименование").setSortable(true).setResizable(true);
        fileGrid.addColumn(FileDto::getSize).setHeader("Размер, МБ").setSortable(true).setResizable(true);
        fileGrid.addColumn(FileDto::getCreatedDate).setHeader("Дата добавления").setSortable(true).setWidth("100px").setResizable(true);
        fileGrid.addColumn(file -> file.getEmployeeDto().getFirstName()).setHeader("Сотрудник").setResizable(true);
        fileGrid.setHeightByRows(true);

    }

}
