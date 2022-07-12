package com.warehouse_accounting.components.sales.forms.order.components;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.warehouse_accounting.models.dto.InvoiceDto;
import com.warehouse_accounting.models.dto.InvoiceProductDto;
import com.warehouse_accounting.services.interfaces.ProductService;
import lombok.extern.log4j.Log4j2;

import java.util.List;

// Связанные дтошки: ProductDto, UnitsOfMeasureDto, ImageDto, UnitDto, ContractorDto, TaxSystemDto, ProductGroupDto,
// AttributeOfCalculationObjectDto, InvoiceDto

// todo: Добавить после грида выбор товара из каталога (таб "Главная")
// todo: Добавить на таб "Связанные документы" кнопку "Привязать документ"
// todo: Добавить после табов добавление задач, файлов и итога для них

@Log4j2
public class OrderPositions extends VerticalLayout {
    OrderGrid grid = new OrderGrid();

    public OrderPositions(ProductService productService, InvoiceDto invoiceDto) {
        OrderGridFooter footer = new OrderGridFooter(invoiceDto, grid);
        SearchToolbar searchHeader = new SearchToolbar(productService, grid);
        Tab mainTab = new Tab("Главная");
        Tab linkedTab = new Tab("Связанные документы");
        Tabs tabs = new Tabs(mainTab, linkedTab);
        VerticalLayout tabContent = new VerticalLayout(grid, searchHeader, footer);

        grid.setSummaryReceiver(summary -> footer.allowSummary(summary));

        tabs.addSelectedChangeListener(event -> {
            tabContent.removeAll();
            if(event.getSelectedTab() == mainTab) {
                tabContent.add(grid, searchHeader, footer);
            } else {
                tabContent.add(new Label("Not implemented"));
            }
        });

        add(tabs, tabContent);
        tabs.setSelectedTab(mainTab);
    }

    public List<InvoiceProductDto> getOrder() {
        return grid.getOrder();
    }
}
