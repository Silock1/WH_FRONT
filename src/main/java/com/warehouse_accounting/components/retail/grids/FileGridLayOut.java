package com.warehouse_accounting.components.retail.grids;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.models.dto.FileDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@Route("filegrid")
@SpringComponent
@UIScope
public class FileGridLayOut extends HorizontalLayout {
    private Grid<FileDto> fileGrid = new Grid<>(FileDto.class, false);


    public FileGridLayOut() {

        gridCreate();
        add(fileGrid);
    }

    private void gridCreate() {
        fileGrid.setColumns("id", "name", "size", "location", "createdDate");
        fileGrid.setHeightByRows(true);


        //TODO: Нужен сервис для файла.
        List<FileDto> fileList = new ArrayList<>();
        fileList.add(FileDto.builder().id(1L).createdDate(new Date()).build());
        fileGrid.setItems(fileList);

    }

}
