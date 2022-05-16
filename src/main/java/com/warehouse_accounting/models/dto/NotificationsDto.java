package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationsDto {

    private Long id;

    // Заказы покупателей
    private SelectorDto buyerOrders = new SelectorDto();

    // Счета покупателей
    private SelectorDto buyersInvoices= new SelectorDto();

    // Остатки
    private SelectorDto remainder= new SelectorDto();

    // Розничная торговля
    private SelectorDto retail= new SelectorDto();

    // Задачи
    private SelectorDto tasks= new SelectorDto();

    // Обмен данными
    private SelectorDto dataExchange= new SelectorDto();

    // Сценарии
    private SelectorDto scripts= new SelectorDto();

    // Интернет-магазины
    private SelectorDto onlineStores= new SelectorDto();

    public NotificationsDto(Long id,
                            Long buyerOrdersId,
                            Long buyersInvoicesId,
                            Long remainderId,
                            Long retailId,
                            Long tasksId,
                            Long dataExchangeId,
                            Long scriptsId,
                            Long onlineStoresId) {
        this.id = id;
        this.buyerOrders.setId(buyerOrdersId);
        this.buyersInvoices.setId(buyersInvoicesId);
        this.remainder.setId(remainderId);
        this.retail.setId(retailId);
        this.tasks.setId(tasksId);
        this.dataExchange.setId(dataExchangeId);
        this.scripts.setId(scriptsId);
        this.onlineStores.setId(onlineStoresId);
    }

    @Override
    public String toString() {
        return "NotificationsDto{" +
                "id=" + id +
                ", buyerOrders=" + buyerOrders +
                ", buyersInvoices=" + buyersInvoices +
                ", remainder=" + remainder +
                ", retail=" + retail +
                ", tasks=" + tasks +
                ", dataExchange=" + dataExchange +
                ", scripts=" + scripts +
                ", onlineStores=" + onlineStores +
                '}';
    }
}
