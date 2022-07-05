package com.warehouse_accounting.components.goods.grids;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.components.goods.Posting;
import com.warehouse_accounting.components.goods.forms.PostingForm;
import com.warehouse_accounting.models.dto.PostingDto;
import com.warehouse_accounting.services.interfaces.PostingService;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;


import java.util.Comparator;
import java.util.List;


@UIScope
@Component
@Log4j2

@Route(value = "postingGridLayout", layout = AppView.class)
public class PostingGridLayout extends HorizontalLayout {

    private final PostingService postingService;
    @Getter
    private final Grid<PostingDto> postingDtoGrid = new Grid<>(PostingDto.class, false);
    private final Posting posting;
    private List<PostingDto> postingDtoList ;


    public PostingGridLayout(PostingService postingService, Posting posting) {
        this.postingService = postingService;
        this.posting = posting;


        Grid.Column<PostingDto> idColumn = postingDtoGrid.addColumn(PostingDto::getId).setHeader("Id");
        Grid.Column<PostingDto> dateColumn = postingDtoGrid.addColumn(PostingDto::getDateOfCreation).setHeader("Время");
        Grid.Column<PostingDto> warehouseToColumn = postingDtoGrid.addColumn(PostingDto::getWarehouseTo).setHeader("На склад");
        Grid.Column<PostingDto> companyColumn = postingDtoGrid.addColumn(PostingDto::getCompany).setHeader("Организация");
        Grid.Column<PostingDto> sumColumn = postingDtoGrid.addColumn(PostingDto::getSum).setHeader("Сумма");
        Grid.Column<PostingDto> movedColumn = postingDtoGrid.addColumn(PostingDto::isMoved).setHeader("Отправлено");
        Grid.Column<PostingDto> printedColumn = postingDtoGrid.addColumn(PostingDto::isPrinted).setHeader("Напечатано");
        Grid.Column<PostingDto> commentColumn = postingDtoGrid.addColumn(PostingDto::getComment).setHeader("Комментарий");


        postingDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI); //

        postingDtoList = postingService.getAll();
        postingDtoList.sort(Comparator.comparingLong(PostingDto::getId));
        postingDtoGrid.setItems(postingDtoList);

        postingDtoGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        postingDtoGrid.addItemClickListener(event -> {
            posting.getPageContent().removeAll();
            PostingForm postingForm  = new PostingForm(
                    posting,
                    new PostingDto(),
                    postingService);
            posting.getPageContent().add(postingForm);
        });

        Button menuButton = new Button(new Icon(VaadinIcon.COG));
        menuButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        PostingGridLayout.ColumnToggleContextMenu columnToggleContextMenu = new PostingGridLayout.ColumnToggleContextMenu(
                (Component) menuButton);
        columnToggleContextMenu.addColumnToggleItem("Id", idColumn);
        columnToggleContextMenu.addColumnToggleItem("Время", dateColumn);
        columnToggleContextMenu.addColumnToggleItem("На склад", warehouseToColumn);
        columnToggleContextMenu.addColumnToggleItem("Организация", companyColumn);
        columnToggleContextMenu.addColumnToggleItem("Сумма", sumColumn);
        columnToggleContextMenu.addColumnToggleItem("Отправлено", movedColumn);
        columnToggleContextMenu.addColumnToggleItem("Напечатано", printedColumn);
        columnToggleContextMenu.addColumnToggleItem("Комментарий", commentColumn);

        Span title = new Span("Документы");
        title.getStyle().set("font-weight", "bold");
        HorizontalLayout headerLayout = new HorizontalLayout(menuButton);
        headerLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        headerLayout.setFlexGrow(1);
        add(postingDtoGrid, headerLayout);
    }

    public void updateGrid() {
        postingDtoList = postingService.getAll();
        postingDtoList.sort(Comparator.comparingLong(PostingDto::getId));
        postingDtoGrid.setItems(postingDtoList);
    }

    private static class ColumnToggleContextMenu extends ContextMenu {
        public ColumnToggleContextMenu(Component target) {
            super((com.vaadin.flow.component.Component) target);
            setOpenOnClick(true);
        }
        /**/
        void addColumnToggleItem(String label, Grid.Column<PostingDto> column) {
            MenuItem menuItem = this.addItem(label, e -> {
                column.setVisible(e.getSource().isChecked());
            });
            menuItem.setCheckable(true);
            menuItem.setChecked(column.isVisible());
        }
    }

}
