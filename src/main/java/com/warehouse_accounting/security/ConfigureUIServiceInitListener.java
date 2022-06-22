package com.warehouse_accounting.security;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.warehouse_accounting.components.LoginView;
import org.springframework.stereotype.Component;

@Component
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {
    @Override
    public void serviceInit(ServiceInitEvent serviceInitEvent) {
        serviceInitEvent.getSource().addUIInitListener(uiInitEvent -> {
            final UI ui = uiInitEvent.getUI();
            ui.addBeforeEnterListener(this::authenticateNavigation);
        });
    }

    private void authenticateNavigation (BeforeEnterEvent enterEvent) {
        if (!LoginView.class.equals(enterEvent.getNavigationTarget()) && !SecurityUtils.isUserLoggedIn()) {
            enterEvent.rerouteTo(LoginView.class);
        }
    }
}
