package com.warehouse_accounting.components.userSubMenu;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
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
public class FeedView extends VerticalLayout {

    private final FeedService feedService;
    private final Grid<FeedDto> grid = new Grid<>();

    private final Button addNewBtn = new Button("Add new");
    private final FeedEditor editor;
    private final HorizontalLayout toolBar = new HorizontalLayout(addNewBtn);

    public FeedView(FeedService feedService, FeedEditor editor) {
        this.feedService = feedService;
        this.editor = editor;

        VerticalLayout column = new VerticalLayout();
        Span header = new Span("Новости");
        column.setWidth("40%");
        column.add(header, grid);

        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editFeed(e.getValue());
        });

        addNewBtn.addClickListener(e -> editor.editFeed(new FeedDto()));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            grid.setItems(feedService.getAll());
        });

        listFeed();

        add(column, addNewBtn, editor);
    }

    private void listFeed() {
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT,
                              GridVariant.LUMO_NO_BORDER,
                              GridVariant.LUMO_NO_ROW_BORDERS);
        grid.addComponentColumn(feedDto -> createCard(feedDto));

        List<FeedDto> news = feedService.getAll();
        grid.setItems(news);
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

        description.add(header, post, footer);
        card.add(description);

        return card;
    }

}
