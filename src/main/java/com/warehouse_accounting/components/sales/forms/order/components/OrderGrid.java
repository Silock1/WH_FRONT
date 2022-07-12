package com.warehouse_accounting.components.sales.forms.order.components;

import com.vaadin.flow.component.BlurNotifier;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.warehouse_accounting.components.sales.forms.order.types.GridSummaryReciver;
import com.warehouse_accounting.components.sales.forms.order.types.OrderSummary;
import com.warehouse_accounting.models.dto.InvoiceProductDto;
import com.warehouse_accounting.models.dto.ProductDto;
import com.warehouse_accounting.models.dto.UnitsOfMeasureDto;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// todo: валидации значений нет

// Связанные дтошки: ProductDto, UnitsOfMeasureDto, ImageDto, UnitDto, InvoiceDto

/*
 Все столбцы грида:
 1. захват (контрол для перестановки позиций),
 2. номер (номер в текущем списке, не зависит от товара. В заголовке неотмеченный чекбокс заменяющий номера
 на чекбоксы для удаления и общая кнопка удалить. В ячейке номер или отмеченный чекбокс. При наведении заменяется на чекбокс.),
 3. картинка (изображение),
 4. наименование (в заголовке: контрол сортировки по названию, коду, оно же но в группе. В ячейке: "код название"),
 5. количество (заголовок пустой, строка вида "1 шт."),
 6. отгружено (целое число),
 7. единица измерения (не имеет заголовка, строка вида "шт."),
 8. доступно (целое число, помечается красным при 0),
 9. остаток (целое число),
 10. резерв (целое число) (оно же "Неснижаемый остаток"),
 11. ожидание (целое число),
 12. вес (число с вида "50,00"),
 13. объём (число с вида "50,00"),
 14. цена (число с вида "50,00"),
 15. ндс (число в процентах),
 16. сумма ндс (число с вида "50,00"),
 17. скидка (вещественное число в процентах, отрицательное число - наценка. При наведении заменяется на поле ввода числа.),
 18. сумма (число вида "50,00". Как Итого: зависит от флага "Цена включает НДС", количества, скидки\наценки, цены.)
 19. в конце контрол выбора отображаемых столбцов (в заголовке: шестерёнка. В ячейке контрол с "удалить/дублировать/заменить")

 Варианты выбора столбцов: изображение, единица измерения, отгружено, доступно, остаток, резерв, ожидание, вес,
 объём, сумма ндс.

 Всегда отображаются: захват, позиция, наименование, количество, цена, ндс, скидка, сумма, контрол выбора столбцов

 По-умолчанию отбражаются: захват, позиция, изображение, наименование, количество, еденица измерения, отгружено,
 доступно, цена, ндс, скидка, сумма, контрол столбцов
*/

@Log4j2
public class OrderGrid extends Grid<InvoiceProductDto> {
    private boolean includeNds = false;
    private final List<Float> nds = List.of(0f, 10f, 18f, 20f);
    private List<InvoiceProductDto> orderPositions = new ArrayList<>();
    private GridSummaryReciver receiver;
    private final Binder<InvoiceProductDto> binder;

    public OrderGrid() {
        super(InvoiceProductDto.class, false);
        setHeightByRows(true);
        addThemeVariants(GridVariant.LUMO_NO_BORDER);

        Grid.Column<InvoiceProductDto> anchorColumn = addColumn(x -> "<|>").setHeader("?");
        Grid.Column<InvoiceProductDto> positionColumn = addColumn(x -> 1 + orderPositions.indexOf(x)).setHeader("№");
        Grid.Column<InvoiceProductDto> imageColumn = addColumn(x -> "image preview").setHeader(""); // todo: брать из товара
        Grid.Column<InvoiceProductDto> nameColumn = addColumn(x -> x.getProductDto().getArticul() + " " + x.getProductDto().getName())
                .setHeader("Наименование");
        Grid.Column<InvoiceProductDto> amountColumn = addColumn(x -> x.getCount().toString()).setHeader("Кол-во");
        Grid.Column<InvoiceProductDto> unitsColumn = addColumn(x -> {
            UnitsOfMeasureDto mes = x.getProductDto().getUnitsOfMeasureDto();
            return mes != null ? mes.getName() : "";
        }).setHeader("");
        Grid.Column<InvoiceProductDto> unloadColumn = addColumn(x -> "?").setHeader("Отгружено");
        Grid.Column<InvoiceProductDto> accessibleColumn = addColumn(x -> "?").setHeader("Доступно");
        Grid.Column<InvoiceProductDto> remainderColumn = addColumn(x -> "?").setHeader("Остаток");
        Grid.Column<InvoiceProductDto> reservedColumn = addColumn(x -> "?").setHeader("Резерв");
        Grid.Column<InvoiceProductDto> waitedColumn = addColumn(x -> "?").setHeader("Ожидание");
        Grid.Column<InvoiceProductDto> weightColumn = addColumn(x -> x.getProductDto().getWeight().toString()).setHeader("Вес");
        Grid.Column<InvoiceProductDto> volumeColumn = addColumn(x -> x.getProductDto().getVolume().toString()).setHeader("Объём");
        Grid.Column<InvoiceProductDto> priceColumn = addColumn(x -> x.getProductDto().getPurchasePrice().floatValue()).setHeader("Цена");
        Grid.Column<InvoiceProductDto> ndsColumn = addColumn(x -> x.getNds()).setHeader("НДС");
        Grid.Column<InvoiceProductDto> ndsSumColumn = addColumn(x ->
                x.getCount().multiply(x.getProductDto().getPurchasePrice())
                        .multiply(BigDecimal.valueOf(1 - x.getDiscount() / 100))
                        .multiply(BigDecimal.valueOf(includeNds ? x.getNds() / (100 + x.getNds()) :  x.getNds() / 100 ))
                        .round(new MathContext(2, RoundingMode.HALF_UP)).floatValue())
                .setHeader("Сумма НДС");
        Grid.Column<InvoiceProductDto> discountColumn = addColumn(x -> x.getDiscount()).setHeader("Скидка");
        Grid.Column<InvoiceProductDto> totalColumn = addColumn(x -> calculateTotal(x.getProductDto().getPurchasePrice(),
                x.getCount().intValue(), x.getNds(), x.getDiscount(), includeNds).floatValue())
                .setHeader("Сумма");

        List.of(reservedColumn, waitedColumn, weightColumn, volumeColumn, ndsSumColumn).forEach(x -> x.setVisible(false));

        Component columnSwitch = new ColumnToggleContextMenu<InvoiceProductDto>()
                .addColumn("Изображение", imageColumn)
                .addColumn("Единица измерения", unitsColumn)
                .addColumn("Отгружено", unloadColumn)
                .addColumn("Доступно", accessibleColumn)
                .addColumn("Остаток", remainderColumn)
                .addColumn("Резерв", reservedColumn)
                .addColumn("Ожидание", waitedColumn)
                .addColumn("Вес", weightColumn)
                .addColumn("Объём", volumeColumn)
                .addColumn("Сумма НДС", ndsSumColumn)
                .getTarget();

        Grid.Column<InvoiceProductDto> gearColumn = addColumn(x -> "").setHeader(columnSwitch);

        List.of(anchorColumn, positionColumn, nameColumn, amountColumn, unitsColumn, unloadColumn, accessibleColumn,
                remainderColumn, reservedColumn, waitedColumn, weightColumn, volumeColumn, priceColumn, ndsColumn,
                ndsSumColumn, discountColumn, totalColumn, gearColumn).forEach(x -> x.setAutoWidth(true));

        binder = new Binder<>(InvoiceProductDto.class);
        Editor<InvoiceProductDto> editor = getEditor().setBinder(binder);

        BigDecimalField amount = new BigDecimalField();
        binder.forField(amount)
                .bind(InvoiceProductDto::getCount, InvoiceProductDto::setCount);
        setEditableColumn(editor, amountColumn, amount);
        amount.setValue(BigDecimal.ONE);

        BigDecimalField price = new BigDecimalField();
        binder.forField(price)
                .bind(order -> order.getProductDto().getPurchasePrice(), (order, value) -> order.getProductDto().setPurchasePrice(value));
        setEditableColumn(editor, priceColumn, price);

        NumberField discount = new NumberField();
        binder.forField(discount)
                .withValidator(value -> !(value < 0), "Discount must not be less 0")
                .bind(order -> (double) order.getDiscount(), (order, value) -> order.setDiscount(value.floatValue()));
        setEditableColumn(editor, discountColumn, discount);

        NumberField nds = new NumberField();
        binder.forField(nds)
                .bind(order -> (double) order.getNds(), (order, value) -> {
                    order.setNds(value.floatValue());
                    order.setPrice(calculateTotal(order.getProductDto().getPurchasePrice(),
                            order.getCount().intValue(), order.getNds(), order.getDiscount(), includeNds));
                });
        setEditableColumn(editor, ndsColumn, nds);

        BigDecimalField total = new BigDecimalField();
        binder.forField(total).bind(
                order -> calculateTotal(order.getProductDto().getPurchasePrice(), order.getCount().intValue(),
                        order.getNds(), order.getDiscount(), includeNds),

                (order, value) -> order.getProductDto().setPurchasePrice(includeNds ?
                        value.multiply(BigDecimal.valueOf(1 / (1 - order.getDiscount() / 100)))
                                .divide(order.getCount(), new MathContext(2, RoundingMode.HALF_UP)) :
                        value.multiply(BigDecimal.valueOf(1 / (1 + order.getNds() / 100)))
                                .multiply(BigDecimal.valueOf(1 / (1 - order.getDiscount() / 100)))
                                .divide(order.getCount(), new MathContext(2, RoundingMode.HALF_UP)))
        );
        setEditableColumn(editor, totalColumn, total);

        addItemClickListener(event -> {
            Component editorComponent = event.getColumn().getEditorComponent();
            if (editorComponent instanceof Focusable && event.getItem() != null) {
                editor.editItem(event.getItem());
                ((Focusable) editorComponent).focus();
            }
        });
    }

    public OrderGrid(GridSummaryReciver receiver) {
        this();
        this.receiver = receiver;
    }

    public List<InvoiceProductDto> getOrder() {
        orderPositions.forEach(invoiceProduct -> {
            try {
                binder.writeBean(invoiceProduct);
            } catch (ValidationException err) {
                log.error("Ошибка валидации при заполнении списка заказанных товаров");
            }
        });
        return orderPositions.stream()
                .filter(invoice -> invoice.getCount().compareTo(BigDecimal.ZERO) > 0)
                .collect(Collectors.toList());
    }

    public void includeNds(boolean include) {
        includeNds = include;
        getDataProvider().refreshAll();
    }

    public OrderGrid addProduct(ProductDto product) {
        InvoiceProductDto invoice = new InvoiceProductDto();

        invoice.setProductDto(product);
        invoice.setNds(product.getNds());
        invoice.setCount(BigDecimal.ONE);
        invoice.setDiscount(0f);
        orderPositions.add(invoice);
        setItems(orderPositions);
        collectSummary();
        return this;
    }

    public OrderGrid setSummaryReceiver(GridSummaryReciver receiver) {
        this.receiver = receiver;
        return this;
    }

    private void setEditableColumn(Editor<InvoiceProductDto> editor, Grid.Column<InvoiceProductDto> column, Component field) {
        field.getElement().addEventListener("keydown", e -> editor.cancel())
                .setFilter("event.code === 'Escape'");

        ((BlurNotifier) field).addBlurListener(event -> {
            editor.closeEditor();
            deselectAll();
            getDataProvider().refreshAll();
            collectSummary();
        });

        column.setEditorComponent(field);
    }

    private BigDecimal calculateTotal(BigDecimal price, int amount, float nds, float discount, boolean priceWithNDS) {
        BigDecimal total = price.multiply(BigDecimal.valueOf(amount));

        BigDecimal totalWithNds = total.multiply(BigDecimal.valueOf(1 - discount / 100))
                .round(new MathContext(2, RoundingMode.HALF_UP));

        BigDecimal totalWithoutNds =  total.multiply(BigDecimal.valueOf(1 + nds / 100))
                .multiply(BigDecimal.valueOf(1 - discount / 100))
                .round(new MathContext(2, RoundingMode.HALF_UP));

        return priceWithNDS ? totalWithNds : totalWithoutNds;
    }

    private void collectSummary() {
        if(receiver != null) {
            OrderSummary summary = new OrderSummary();
            for(InvoiceProductDto invoice : orderPositions) {
                summary.setAmount(invoice.getCount().add(BigDecimal.valueOf(summary.getAmount())).intValue());
                summary.setWeight(invoice.getProductDto().getWeight().multiply(invoice.getCount()).add(summary.getWeight()));
                summary.setVolume(invoice.getProductDto().getVolume().multiply(invoice.getCount()).add(summary.getVolume()));

                BigDecimal price = invoice.getProductDto().getPurchasePrice();
                BigDecimal amountPrice = invoice.getCount().multiply(price != null ? price : BigDecimal.ZERO);

                summary.setPriceWithoutNds(summary.getPriceWithoutNds()
                                .add(amountPrice.multiply(BigDecimal.valueOf(1 - invoice.getDiscount()/100)))
                        );

                Float nds = invoice.getNds();

                summary.setPriceWithNds(summary.getPriceWithNds().add(amountPrice
                        .multiply(BigDecimal.valueOf(1 + nds / 100))
                        .multiply(BigDecimal.valueOf(1 - invoice.getDiscount()/100))));
            }
            receiver.allowSummary(summary);
        }
    }
}
