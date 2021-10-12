package com.warehouse_accounting.components.user;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.models.dto.FeedDto;
import com.warehouse_accounting.services.interfaces.FeedService;

import java.util.List;

@Route(value = "feed", layout = AppView.class)
@PageTitle("Новости")
@CssImport(value = "./css/feed.css")
public class FeedView extends HorizontalLayout {

    private final FeedService feedService;
    private final Grid<FeedDto> grid = new Grid<>();

    public FeedView(FeedService feedService) {
        this.feedService = feedService;

        setHeightFull();

        VerticalLayout leftColumn = new VerticalLayout();
        leftColumn.addClassName("leftColumn");

        VerticalLayout middleColumn = new VerticalLayout();
        Span header = new Span("Новости");
        header.addClassName("news");
        middleColumn.setWidth("2700px");
        middleColumn.add(header, new Hr(), grid);
        middleColumn.setAlignItems(Alignment.CENTER);
        middleColumn.addClassName("middleColumn");

        VerticalLayout rightColumn = new VerticalLayout();
        rightColumn.addClassName("rightColumn");

        listFeed();

        add(leftColumn, middleColumn, rightColumn);
    }

    private void listFeed() {
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT,
                              GridVariant.LUMO_NO_BORDER,
                              GridVariant.LUMO_NO_ROW_BORDERS);
        grid.addComponentColumn(feedDto -> createCard(feedDto));

        grid.setItems(feedService.getAll());
    }

    private HorizontalLayout createCard(FeedDto feedDto) {
        HorizontalLayout card = new HorizontalLayout();
        card.addClassName("card");
        card.setSpacing(false);
        card.getThemeList().add("spacing-s");

        VerticalLayout description = new VerticalLayout();
        description.addClassName("description");
        description.setSpacing(false);
        description.setPadding(false);

        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("header");
        header.setSpacing(false);
        header.getThemeList().add("spacing-s");

        Span title = new Span(feedDto.getFeedHead());
        title.addClassName("title");
        header.add(title);

        Span post = new Span(feedDto.getFeedBody());
        post.addClassName("post");

        HorizontalLayout footer = new HorizontalLayout();
        Span date = new Span(String.valueOf(feedDto.getFeedDate()));
        date.addClassName("date");
        footer.add(date);

        description.add(header, post, footer, new Hr());
        card.add(description);

        return card;
    }

}
