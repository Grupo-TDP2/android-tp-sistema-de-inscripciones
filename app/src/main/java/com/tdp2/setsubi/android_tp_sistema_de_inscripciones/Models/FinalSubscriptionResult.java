package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models;

public class FinalSubscriptionResult {
    private int subscriptionId;
    private boolean subscribedFree;

    public FinalSubscriptionResult(int subscriptionId, boolean subscribedFree) {
        this.subscriptionId = subscriptionId;
        this.subscribedFree = subscribedFree;
    }

    public int getSubscriptionId()
    {
        return subscriptionId;
    }

    public boolean getSubscribedFree()
    {
        return subscribedFree;
    }


}
