package com.warehouse_accounting.components.user;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.models.dto.FeedDto;
import com.warehouse_accounting.services.interfaces.FeedService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

/*
Класс для теста CRUD операция над новостями
Удалить его и все с ним связанное в классе FeedView после корректного вывода новостей
 */
@SpringComponent
@UIScope
public class FeedEditor extends VerticalLayout implements KeyNotifier {

    private final FeedService feedService;
    private FeedDto feedDto;

    private TextField feedHead = new TextField("Заголовок");
    private TextField feedBody = new TextField("Тело новости");

    private Button save = new Button("Save", VaadinIcon.CHECK.create());
    private Button cancel = new Button("Cancel", VaadinIcon.BOMB.create());
    private Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    private HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<FeedDto> binder = new Binder<>(FeedDto.class);
    @Setter
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public FeedEditor(FeedService feedService) {
        this.feedService = feedService;
        add(feedHead, feedBody, actions);

        binder.bindInstanceFields(this);
        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editFeed(feedDto));
        setVisible(false);
    }

    void delete() {
        feedService.deleteById(feedDto.getId());
        changeHandler.onChange();
    }

    void save() {
        feedService.create(feedDto);
        changeHandler.onChange();
    }

    public final void editFeed(FeedDto newFeed) {
        if (newFeed == null) {
            setVisible(false);
            return;
        }

        if (newFeed.getId() != null) {
            feedDto = feedService.getById(newFeed.getId());
        } else {
            feedDto = newFeed;
        }

        binder.setBean(feedDto);

        setVisible(true);
        feedHead.focus();
    }


}
