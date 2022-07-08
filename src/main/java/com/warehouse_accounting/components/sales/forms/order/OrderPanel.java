package com.warehouse_accounting.components.sales.forms.order;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.warehouse_accounting.components.sales.forms.order.components.DocumentOperationsToolbar;
import com.warehouse_accounting.components.sales.forms.order.components.OrderDetails;
import com.warehouse_accounting.components.sales.forms.order.components.OrderHeader;
import com.warehouse_accounting.components.sales.forms.order.components.OrderPositions;
import com.warehouse_accounting.components.sales.forms.order.types.DocumentCloseHandler;
import com.warehouse_accounting.models.dto.InvoiceDto;
import com.warehouse_accounting.models.dto.InvoiceProductDto;
import com.warehouse_accounting.services.interfaces.*;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.List;

// Всё, что связано с InvoiceDto, InvoiceProductDto
// То же самое что и Закупки --> Заказы поставщикам --> Заказ
// Оно же Продажи --> Счета покупателям --> Счёт

// todo: Перенести в правильные места заполнение полей setInvoiceAuthorId setInvoiceAuthorFirstName setInvoiceAuthorLastName setComment
//  setEdits setType

@Log4j2
@Tag("div")
public class OrderPanel extends VerticalLayout {
    private final CompanyService companyService;
    private final ContractorService contractorService;
    private final ProductService productService;
    private final WarehouseService warehouseService;
    private final ContractService contractService;
    private final ProjectService projectService;
    private final InvoiceService invoiceService;
    private final DocumentOperationsToolbar documentToolbar = new DocumentOperationsToolbar();
    private final InvoiceDto invoiceDto = new InvoiceDto();

    public OrderPanel(CompanyService companyService, ContractorService contractorService, ProductService productService,
                      WarehouseService warehouseService, ContractService contractService, ProjectService projectService,
                      InvoiceService invoiceService) {
        this.companyService = companyService;
        this.contractorService = contractorService;
        this.productService = productService;
        this.warehouseService = warehouseService;
        this.contractService = contractService;
        this.projectService = projectService;
        this.invoiceService = invoiceService;

        try {
            OrderDetails orderDetails = new OrderDetails(companyService, contractorService, warehouseService, contractService, projectService, invoiceDto);
            OrderPositions orderPositions = new OrderPositions(productService, invoiceDto);
            OrderHeader orderHeader = new OrderHeader(invoiceDto);

            documentToolbar.setSaveHandler(() -> {
                // save invoice and orders
                List<InvoiceProductDto> order = orderPositions.getOrder();

                if(order.isEmpty()) { // todo: remove this
                    Notification.show("Список продуктов незаполнен или недостаточное количество продукта.");
                    return;
                }

                invoiceDto.setProductDtos(order);
//                invoice.setEdits(List.of(InvoiceEditDto.builder().id(1L).build())); // todo: получать текущего пользователя?
                invoiceDto.setType("RECEIPT");
                invoiceDto.setInvoiceAuthorId(1L);
                invoiceDto.setInvoiceAuthorFirstName("Test_author_FistName");
                invoiceDto.setInvoiceAuthorLastName("Test_author_LastName");

                Notification.show("Сохранение заказа");
                invoiceService.create(invoiceDto);
            });

            add(documentToolbar, orderHeader, orderDetails, orderPositions);
        } catch (RuntimeException | IOException err) {
            log.error("Не удалось создать форму заказа");
            err.printStackTrace();
        }
    }

    public void setOnCloseHandler(DocumentCloseHandler closeHandler) {
        documentToolbar.setCloseHandler(closeHandler);
    }
}
