package com.web.listacompra.controller.productController.formattedClassesForNDJsonResponse;

public class FormattedNDJsonAddedItemNotification extends FormattedNDJsonNotification {
    private FormattedNDJsonItem item;
    public FormattedNDJsonItem getItem() {
        return item;
    }
    public void setItem(FormattedNDJsonItem item) {
        this.item = item;
    }
    
}
