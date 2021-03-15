package com.warehouse_accounting.components.goods.util;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.warehouse_accounting.models.dto.ProductDto;
import com.warehouse_accounting.models.dto.ProductGroupDto;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class VaadinComponents {

    @Bean(name = "mainLayer")
    public Div getParentLayout() {
        return new Div();
    }

    @Bean(name = "goodsLayer")
    public Div getGoodsLayout() {
        return new Div();
    }

    @Bean(name = "goodsSelectedTextField")
    public TextField getGoodsSelectedTextField() {
        return new TextField();
    }

    @Bean(name = "goodsGrid")
    public Grid<ProductDto> getGoodsGrid() {
        return new Grid<>(ProductDto.class, false);
    }

    @Bean(name = "goodsTreeGrid")
    public TreeGrid<ProductGroupDto> getGoodsTreeGrid() {
        return new TreeGrid<>();
    }
}
