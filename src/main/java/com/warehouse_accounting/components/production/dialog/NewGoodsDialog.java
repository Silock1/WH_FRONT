package com.warehouse_accounting.components.production.dialog;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.warehouse_accounting.components.goods.forms.GoodsForm;
import com.warehouse_accounting.services.interfaces.AttributeOfCalculationObjectService;
import com.warehouse_accounting.services.interfaces.ContractorService;
import com.warehouse_accounting.services.interfaces.CountryService;
import com.warehouse_accounting.services.interfaces.ImageService;
import com.warehouse_accounting.services.interfaces.ProductGroupService;
import com.warehouse_accounting.services.interfaces.ProductService;
import com.warehouse_accounting.services.interfaces.TaxSystemService;
import com.warehouse_accounting.services.interfaces.UnitService;
import com.warehouse_accounting.services.interfaces.UnitsOfMeasureService;

@SpringComponent
public class NewGoodsDialog extends GoodsForm {

    private Dialog dialog;

    public NewGoodsDialog(ProductService productService, CountryService countryService, UnitsOfMeasureService unitsOfMeasureService, ContractorService contractorService, TaxSystemService taxSystemService, ImageService imageService, ProductGroupService productGroupService, AttributeOfCalculationObjectService attributeService, UnitService unitService) {
        super(productService, countryService, unitsOfMeasureService, contractorService, taxSystemService, imageService, productGroupService, attributeService, unitService);
        setDialog();
    }

    private void setDialog(){
        this.dialog = new Dialog();
    }
}
