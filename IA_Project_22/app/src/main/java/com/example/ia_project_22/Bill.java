package com.example.ia_project_22;

public class Bill extends Payments
{
    int paymentPeriod;


    public Bill(String id, String name, String amount, int paymentPeriod)
    {
        super(id, name, amount);
        this.paymentPeriod = paymentPeriod;
    }
}
