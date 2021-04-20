package nl.hiddevanesch.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Balance {
    @SerializedName("symbol")
    @Expose
    private String symbol;

    @SerializedName("available")
    @Expose
    private float amount;

    public Balance(String symbol, float amount) {
        this.symbol = symbol;
        this.amount = amount;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public float getAmount() {
        return this.amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}