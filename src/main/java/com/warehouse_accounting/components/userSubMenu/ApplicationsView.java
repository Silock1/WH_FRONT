package com.warehouse_accounting.components.userSubMenu;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.models.dto.ApplicationDto;
import com.warehouse_accounting.services.interfaces.ApplicationService;

import java.util.List;

@Route(value = "applications", layout = AppView.class)
@PageTitle("Приложения")
public class ApplicationsView extends VerticalLayout {

    private final Div pageContent = new Div();
    private final ApplicationService applicationService;
    private boolean popupOpened = false;

    public ApplicationsView(ApplicationService applicationService) {
        this.applicationService = applicationService;
        pageContent.setSizeFull();
        pageContent.addClassName("screenWrapper");

        add(pageHead());
        add(pageContentFill(pageContent));
    }

    private Div pageContentFill(Div content) {
        List<ApplicationDto> allApps = applicationService.getAll();
        List<HorizontalLayout> listLayout = ApplicationsHorizontalView.horizontalDivFill(allApps);

        for(HorizontalLayout layout : listLayout) {
            content.add(layout);
        }

        return content;
    }

    private Div pageHead() {
        Div head = new Div();

        Span helpBtn = new Span();
        StreamResource res = new StreamResource("helpApp.svg",
                () -> AppView.getImageInputStream("helpApp.svg"));
        Image image = new Image(res, "HelpAppLogo");
        helpBtn.add(image);
        helpBtn.addClassName("helpButton");
        Div popup = new Div();
        popup.setClassName("popup");
        head.add(popup);
        popup.setVisible(false);
        Div popupChild = new Div();
        helpBtn.addClickListener(spanClickEvent -> { popupField(popup, popupChild); });

        Span manualName = new Span("Приложения");
        manualName.addClassName("manualName");

        head.add(helpBtn);
        head.add(manualName);
        return head;
    }

    private void popupField(Div popup, Div popupChild) {
        popupChild.setText("Это Магазин приложений — целая витрина программ для" + "\n" +
                "оптимизации и автоматизации бизнеса. Приложения расширяют" + "\n" +
                "возможности МоегоСклада и позволяют прямо в сервисе" + "\n" +
                "заказывать банковские выписки, звонить контрагентам," + "\n" +
                "настраивать скидки и бонусы и пользоваться другими решениями.");
        popupChild.addClassName("popupChild");

        StreamResource res = new StreamResource("closePopup.png",
                () -> AppView.getImageInputStream("closePopup.png"));
        Image image = new Image(res, "ClosePopup");
        image.addClickListener(imageClickEvent -> {
            popup.remove(popupChild);
            popup.setVisible(false);
            popupOpened = false;
        });
        image.addClassName("closePopup");
        popup.add(image);

        if (!popupOpened) {
            popup.add(popupChild);
            popup.setVisible(true);
            popupOpened = true;
        } else {
            popup.remove(popupChild);
            popup.setVisible(false);
            popupOpened = false;
        }
    }
}
