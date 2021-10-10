package com.warehouse_accounting.components.userSubMenu;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.models.dto.FeedDto;
import com.warehouse_accounting.services.interfaces.FeedService;

@Route(value = "feed", layout = AppView.class)
@PageTitle("Новости")
public class FeedView extends VerticalLayout {

    private final FeedService feedService;
    final Grid<FeedDto> grid;

    private final Button addNewBtn = new Button("Add new");
    private final FeedEditor editor;
    private final HorizontalLayout toolBar = new HorizontalLayout(addNewBtn);

    public FeedView(FeedService feedService, FeedEditor editor) {
        this.feedService = feedService;
        this.editor = editor;
        this.grid = new Grid<>(FeedDto.class);

        //Следующие элементы должны быть установлены по вертикали. Не работает!
        VerticalLayout layout = new VerticalLayout();
        layout.add("Item 1");
        layout.add("Item 2");
        layout.add("Item 3");
        layout.add("Item 4");

        add(layout);
        add(addNewBtn, grid, editor);

        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editFeed(e.getValue());
        });

        addNewBtn.addClickListener(e -> editor.editFeed(new FeedDto()));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listFeed();
        });

        listFeed();
    }

    private void listFeed() {
        grid.setItems(feedService.getAll());
    }

}
