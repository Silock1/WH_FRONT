package com.warehouse_accounting.components.payments;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.components.payments.filter.PaymentsSettlementsCompanyFilter;
import com.warehouse_accounting.components.payments.filter.PaymentsSettlementsEmlpoyeeFilter;
import com.warehouse_accounting.models.dto.PaymentsDto;
import com.warehouse_accounting.services.interfaces.ContractorGroupService;
import com.warehouse_accounting.services.interfaces.ContractorService;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.PaymentsService;
import com.warehouse_accounting.services.interfaces.ProjectService;
import com.warehouse_accounting.services.interfaces.SalesChannelsService;
import org.springframework.stereotype.Component;

@Component
@UIScope
@Route(value = "payments_settlements", layout = AppView.class)
public class PaymentsSettlementsView extends VerticalLayout {

    private HorizontalLayout horizontalToolPanelLayout = new HorizontalLayout();

    private ProjectService projectService;
    private ContractorService contractorService;
    private ContractorGroupService contractorGroupService;
    private SalesChannelsService salesChannelsService;
    private EmployeeService employeeService;

    public PaymentsSettlementsCompanyFilter paymentsSettlementsCompanyFilter;
    public PaymentsSettlementsEmlpoyeeFilter paymentsSettlementsEmlpoyeeFilter;

    public PaymentsSettlementsView(ProjectService projectService, ContractorService contractorService,
                                   ContractorGroupService contractorGroupService, SalesChannelsService salesChannelsService,
                                   EmployeeService employeeService) {

        this.projectService = projectService;
        this.contractorService = contractorService;
        this.contractorGroupService = contractorGroupService;
        this.salesChannelsService = salesChannelsService;
        this.employeeService = employeeService;

        this.paymentsSettlementsCompanyFilter =  new PaymentsSettlementsCompanyFilter(projectService, contractorService,
                contractorGroupService);
        this.paymentsSettlementsEmlpoyeeFilter = new PaymentsSettlementsEmlpoyeeFilter(projectService, contractorService,
                 salesChannelsService, employeeService);
        horizontalToolPanelLayout.setAlignItems(Alignment.CENTER);

        configToolPanel();

        add(horizontalToolPanelLayout);
        add(paymentsSettlementsCompanyFilter);
        add(paymentsSettlementsEmlpoyeeFilter);
    }

    // Здесь настройка панели инструментов
    private void configToolPanel() {
        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        helpButton.addClickListener(e->{
            Notification.show("В разделе представлены данные о расчетах с контрагентами\n" +
                    "и сотрудниками в заданный период. Отчет позволяет отслеживать\n" +
                    "оборот средств, просматривать задолженность и авансы.\n" + "\n" +

                    "Для каждого контрагента или сотрудника доступна детализация.\n" +
                    "Чтобы ее просмотреть, нажмите на строку с нужным контрагентом\n" +
                    "или сотрудником. Из окна детализации можно напечатать акт\n" +
                    "сверки: нажмите на кнопку Печать и выберите Акт сверки\n" +
                    "в выпадающем списке.\n", 5000, Notification.Position.TOP_START);
        });

        Span text = new Span("Взаиморасчеты");
        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH), event -> {
            //TODO повод поработать этот функционал
        });

        Button contractorButton = new Button("С котрагентами");
        contractorButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        contractorButton.addClickListener(event ->{
            paymentsSettlementsEmlpoyeeFilter.setVisible(false);
            paymentsSettlementsCompanyFilter.setVisible(!paymentsSettlementsCompanyFilter.isVisible());
        });


        Button employeeButton = new Button("С сотрудниками");
        employeeButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        employeeButton.addClickListener(event ->{
            paymentsSettlementsCompanyFilter.setVisible(false);
            paymentsSettlementsEmlpoyeeFilter.setVisible(!paymentsSettlementsEmlpoyeeFilter.isVisible());
        });

        Button filterButton = new Button("Фильтр");
        filterButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        filterButton.addClickListener( event -> {
                    if(paymentsSettlementsEmlpoyeeFilter.isVisible() | paymentsSettlementsCompanyFilter.isVisible()){
                        paymentsSettlementsCompanyFilter.setVisible(false);
                        paymentsSettlementsEmlpoyeeFilter.setVisible(false);
                    }
                }
        );

        MenuBar menuBar = new MenuBar();

        MenuItem print = menuBar.addItem(new Icon(VaadinIcon.PRINT));
        print.add("Печать");
        print.add(new Icon(VaadinIcon.CARET_DOWN));

        SubMenu printSubMenu = print.getSubMenu();
        MenuItem printList = printSubMenu.addItem("Взаиморасчеты");
        printList.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem incomingOrdersList = printSubMenu.addItem("Настроить...");
        incomingOrdersList.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        horizontalToolPanelLayout.add(helpButton, text, refreshButton, contractorButton, employeeButton, filterButton, menuBar);
    }
}