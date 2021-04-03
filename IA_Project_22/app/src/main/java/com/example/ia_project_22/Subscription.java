package com.example.ia_project_22;

public class Subscription extends Payments
{
    boolean isFree;
    int paymentPeriod;

    public Subscription(String id, String name, int amount, boolean isFree, int paymentPeriod)
    {
        super(id, name, amount);
        this.isFree = isFree;
        this.paymentPeriod = paymentPeriod;
    }
}
