package com.warehouse_accounting.components.goods.forms;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.warehouse_accounting.components.goods.GoodsAndServiceView;
import com.warehouse_accounting.models.dto.ProductGroupDto;
import com.warehouse_accounting.services.interfaces.ProductGroupService;

import java.util.List;
import java.util.Objects;

public class GroupForm extends VerticalLayout {
    private final ProductGroupService productGroupService;
    private final GoodsAndServiceView returnLayer;
    private final ProductGroupDto productGroupDto;
    private final Div parentLayer;
    private final boolean edited;

    public GroupForm(Div parentLayer, GoodsAndServiceView returnLayer, ProductGroupService productGroupService,
                     ProductGroupDto productGroupDto, Boolean edited) {
        this.parentLayer = parentLayer;
        this.returnLayer = returnLayer;
        this.productGroupService = productGroupService;
        this.productGroupDto = productGroupDto;
        this.edited = edited;
        add(initForm());
    }

    private VerticalLayout initForm() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(new HorizontalLayout(getFormLayout()));
        return verticalLayout;
    }

    private FormLayout getFormLayout() {
        FormLayout form = new FormLayout();

        TextField groupId = new TextField();
        groupId.setEnabled(false);

        TextField groupName = new TextField();

        ComboBox<ProductGroupDto> group = new ComboBox<>();
        group.setItemLabelGenerator(ProductGroupDto::getName);
        List<ProductGroupDto> allByParentGroup = productGroupService.getAllByParentGroupId(returnLayer.getRootGroupId());
        ProductGroupDto rootGroup = productGroupService.getById(returnLayer.getRootGroupId());
        allByParentGroup.add(rootGroup);
        if (edited) {
            allByParentGroup.remove(productGroupDto);
        }
        group.setItems(allByParentGroup);

        HorizontalLayout actions = new HorizontalLayout();
        NativeButton save = new NativeButton("Сохранить");
        NativeButton close = new NativeButton("Закрыть");
        NativeButton delete = new NativeButton("Удалить");
        actions.add(save, close);

        if (edited) {
            groupId.setValue(productGroupDto.getId().toString());
            groupName.setValue(productGroupDto.getName());
            group.setValue(productGroupService.getById(productGroupDto.getProductGroupDto().getId()));
            actions.add(delete);
        } else {
            group.setValue(Objects.isNull(productGroupDto) ? rootGroup : productGroupDto);
        }

        form.addFormItem(groupId, "Id");
        form.addFormItem(groupName, "Наименование");
        form.addFormItem(group, "Группа");

        save.addClickListener(event -> {
            if (edited) {
                productGroupDto.setName(groupName.getValue());
                productGroupDto.setProductGroupDto(group.getValue());
                productGroupService.update(productGroupDto);
            } else {
                ProductGroupDto newProductGroup = new ProductGroupDto();
                newProductGroup.setName(groupName.getValue());
                newProductGroup.setProductGroupDto(group.getValue());
                productGroupService.create(newProductGroup);
            }
            parentLayer.removeAll();
            returnLayer.getGoodsGridLayout().initThreeGrid(rootGroup.getId());
            parentLayer.add(returnLayer);
        });

        close.addClickListener(event -> {
            parentLayer.removeAll();
            parentLayer.add(returnLayer);
        });

        delete.addClickListener(event -> {
            productGroupService.deleteById(productGroupDto.getId());
            parentLayer.removeAll();
            returnLayer.getGoodsGridLayout().initThreeGrid(rootGroup.getId());
            parentLayer.add(returnLayer);
        });
        add(actions);
        return form;
    }
}