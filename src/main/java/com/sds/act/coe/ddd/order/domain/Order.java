package com.sds.act.coe.ddd.order.domain;

import java.util.List;
import java.util.Objects;

public class Order {

    private String orderNumber;

    private List<OrderLine> orderLines;

    private ShippingInfo shippingInfo;

    private int totalAmounts;

    private OrderState state;

    public Order(List<OrderLine> orderLines, ShippingInfo shippingInfo) {
        setOrderLines(orderLines);
        setShippingInfo(shippingInfo);
    }

    private void setOrderLines(List<OrderLine> orderLines) {
        if(orderLines == null || orderLines.isEmpty()) {
            throw new IllegalArgumentException("no OrderLine");
        }
        this.orderLines = orderLines;
        calculateTotalAmounts();
    }

    private void setShippingInfo(ShippingInfo shippingInfo) {
        if(shippingInfo == null) {
            throw new IllegalArgumentException("no Shipping Info");
        }
        this.shippingInfo = shippingInfo;
    }

    private void calculateTotalAmounts() {
        this.totalAmounts = orderLines.stream().mapToInt(OrderLine::getAmounts).sum();
    }

    public void changeShippingInfo(ShippingInfo newShipping) {
        verifyNotYetShipped();
        this.shippingInfo = newShipping;
    }

    public void cancel() {
        verifyNotYetShipped();
        this.state = OrderState.CANCELED;
    }

    public void changedShipped() {

    }

    public void completePayment() {

    }

    private void verifyNotYetShipped() {
        if (state != OrderState.PAYMENT_WATTING && state != OrderState.PREPARING) {
            throw new IllegalStateException("already shipped");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderNumber, order.orderNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber);
    }
}
