package nl.hiddevanesch.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Market {
    @SerializedName("market")
    @Expose
    private String market;

    @SerializedName("price")
    @Expose
    private float price;

    public Market(String market, float price) {
        this.market = market;
        this.price = price;
    }

    public String getMarket() {
        return this.market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public float getPrice() {
        return this.price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
