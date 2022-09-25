package com.warehouse_accounting.components.sales.forms.order;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.warehouse_accounting.components.address.AddressForm;
import com.warehouse_accounting.components.sales.forms.order.components.DocumentOperationsToolbar;
import com.warehouse_accounting.components.sales.forms.order.components.OrderDetails;
import com.warehouse_accounting.components.sales.forms.order.components.OrderHeader;
import com.warehouse_accounting.components.sales.forms.order.components.OrderPositions;
import com.warehouse_accounting.components.sales.forms.order.types.DocumentCloseHandler;
import com.warehouse_accounting.models.dto.AddressDto;
import com.warehouse_accounting.models.dto.CustomerOrderDto;
import com.warehouse_accounting.models.dto.InvoiceProductDto;
import com.warehouse_accounting.models.dto.ProductDto;
import com.warehouse_accounting.services.interfaces.*;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.time.LocalDateTime;
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
    private final DocumentOperationsToolbar documentToolbar = new DocumentOperationsToolbar();
    private final CustomerOrderService customerOrderService;
    private final CustomerOrderDto customerOrder = new CustomerOrderDto();
    private final SalesChannelsService channelsService;
    private final AddressForm addressForm;
    private final AddressService addressService;

    public OrderPanel(CompanyService companyService, ContractorService contractorService, ProductService productService,
                      WarehouseService warehouseService, ContractService contractService, ProjectService projectService,
                      CustomerOrderService customerOrderService, SalesChannelsService channelsService, AddressForm addressForm,
                      AddressService addressService) {
        this.companyService = companyService;
        this.contractorService = contractorService;
        this.productService = productService;
        this.warehouseService = warehouseService;
        this.contractService = contractService;
        this.projectService = projectService;
        this.customerOrderService = customerOrderService;
        this.channelsService = channelsService;
        this.addressForm = addressForm;
        this.addressService = addressService;

        try {
            OrderDetails orderDetails = new OrderDetails(companyService, contractorService, warehouseService, contractService, projectService, customerOrder, channelsService, addressForm);
            OrderPositions orderPositions = new OrderPositions(productService, customerOrder);
            OrderHeader orderHeader = new OrderHeader(customerOrder);

            documentToolbar.setSaveHandler(() -> {
                // save invoice and orders
                List<InvoiceProductDto> order = orderPositions.getOrder();

                if(order.isEmpty()) { // todo: remove this
                    Notification.show("Список продуктов незаполнен или недостаточное количество продукта.");
                    return;
                }

                customerOrder.setProductDtos(order);
//                invoice.setEdits(List.of(InvoiceEditDto.builder().id(1L).build())); // todo: получать текущего пользователя?
                customerOrder.setType("Заказ покупателя");
//                customerOrder.setUpdatedAt(LocalDateTime.now());
//                customerOrder.setInvoiceAuthorId(1L);
//                customerOrder.setInvoiceAuthorFirstName("Test_author_FistName");
//                customerOrder.setInvoiceAuthorLastName("Test_author_LastName");
                // проверка адреса доставки на уникальность
                List<AddressDto> addressDtoList = addressService.getAll();
                AddressDto deliveryAddressDto = addressForm.getValue();
                boolean addressMatch = false;
                for (AddressDto a : addressDtoList) {
                    if (a.getFullAddress() == null) continue;
                    if (a.getFullAddress().equals(deliveryAddressDto.getFullAddress())) {
                        addressMatch = true;
                        break;
                    }
                }
                // если адрес новый, то сохраняем его в б/д и добавляем в ордер
                if (!addressMatch) {
                    deliveryAddressDto.setId(null);
                    addressService.create(deliveryAddressDto);
                    deliveryAddressDto = addressService.getByFullAddress(deliveryAddressDto.getFullAddress());
                }
                customerOrder.setDeliveryAddressId(deliveryAddressDto.getId());
                customerOrder.setDeliveryAddressFull(deliveryAddressDto.getFullAddress());
                Notification.show("Сохранение заказа");
                customerOrderService.create(customerOrder);
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
