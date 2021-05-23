package com.example.ia_project_22;

public class Subscription extends Payments
{
    boolean isFree;
    String paymentPeriod;

    public Subscription(String id, String name, String amount, boolean isFree, String paymentPeriod)
    {
        super(id, name, amount);
        this.isFree = isFree;
        this.paymentPeriod = paymentPeriod;
    }
}
