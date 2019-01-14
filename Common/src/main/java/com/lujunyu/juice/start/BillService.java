package com.lujunyu.juice.start;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import lombok.ToString;

@ToString
public class BillService {
    private CreditCardProcessor processor;
    private TransactionLog transactionLog;

    @Inject
    BillService(CreditCardProcessor processor,
                   TransactionLog transactionLog) {
        this.processor = processor;
        this.transactionLog = transactionLog;
    }

    public static void main(String args[]){
        BillService service = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                super.configure();
            }
        }).getInstance(BillService.class);
        System.out.println(service);
    }
}
