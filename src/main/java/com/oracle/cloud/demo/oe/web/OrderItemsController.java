package com.oracle.cloud.demo.oe.web;

import com.oracle.cloud.demo.oe.sessions.OrderItemsFacade;
import com.oracle.cloud.demo.oe.entities.OrderItem;
import com.oracle.cloud.demo.oe.entities.OrderItemsPK;
import com.oracle.cloud.demo.oe.web.util.JsfUtil;
import com.oracle.cloud.demo.oe.web.util.PaginationHelper;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@Named("orderItemsController")
@SessionScoped
public class OrderItemsController implements Serializable {

    private OrderItem current;
    private DataModel items = null;
    @EJB
    private com.oracle.cloud.demo.oe.sessions.OrderItemsFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public OrderItemsController() {
    }

    public OrderItem getSelected() {
        if (current == null) {
            current = new OrderItem();
            current.setOrderItemsPK(new OrderItemsPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private OrderItemsFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (OrderItem) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new OrderItem();
        current.setOrderItemsPK(new OrderItemsPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getOrderItemsPK().setOrderId(current.getOrder().getOrderId());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("OrderItemsCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (OrderItem) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getOrderItemsPK().setOrderId(current.getOrder().getOrderId());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("OrderItemsUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (OrderItem) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("OrderItemsDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public OrderItem getOrderItems(OrderItemsPK id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = OrderItem.class)
    public static class OrderItemsControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            OrderItemsController controller = (OrderItemsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "orderItemsController");
            return controller.getOrderItems(getKey(value));
        }

        OrderItemsPK getKey(String value) {
            OrderItemsPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new OrderItemsPK();
            key.setOrderId(Long.parseLong(values[0]));
            key.setLineItemId(Short.parseShort(values[1]));
            return key;
        }

        String getStringKey(OrderItemsPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getOrderId());
            sb.append(SEPARATOR);
            sb.append(value.getLineItemId());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof OrderItem) {
                OrderItem o = (OrderItem) object;
                return getStringKey(o.getOrderItemsPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + OrderItem.class.getName());
            }
        }

    }

}
