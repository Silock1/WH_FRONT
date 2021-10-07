package com.warehouse_accounting.components;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.models.dto.FeedDto;
import com.warehouse_accounting.services.interfaces.FeedService;

@Route(value = "feed", layout = AppView.class)
@PageTitle("Новости")
public class FeedUserSubMenuView extends VerticalLayout {

    private final FeedService feedService;
    final Grid<FeedDto> grid;

    public FeedUserSubMenuView(FeedService feedService) {
        this.feedService = feedService;
        this.grid = new Grid<>(FeedDto.class);
        add(grid);
        listFeed();
    }

    private void listFeed() {
        grid.setItems(feedService.getAll());
    }

}
