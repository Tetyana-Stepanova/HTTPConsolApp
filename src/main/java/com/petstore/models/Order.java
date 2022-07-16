package com.petstore.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import lombok.Data;

import java.lang.reflect.Type;
import java.util.List;

@Data
public class Order implements BaseModel{
    private long id;
    private long petId;
    private long quantity;
    private String shipDate;
    private Status status;
    private boolean complete;

    public enum Status {
        @SerializedName("placed")
        PLACED,
        @SerializedName("approved")
        APPROVED,
        @SerializedName("delivered")
        DELIVERED
    }

    Gson gson = new Gson();
    @Override
    public Order convertStringToBaseModel(String jsonObject) {
        return gson.fromJson(jsonObject, Order.class);
    }

    @Override
    public List<BaseModel> convertStringToModelList(String jsonObject) {
        Type orderListType = new TypeToken<List<Order>>(){}.getType();
        return gson.fromJson(jsonObject, orderListType);
    }

    @Override
    public String convertToJson(BaseModel model) {
        Order order = (Order) model;
        return gson.toJson(order);
    }

    @Override
    public String toString() {
        return "Order{ \n" +
                "   id=" + id + " , \n" +
                "   petId=" + petId + " , \n" +
                "   quantity=" + quantity + " , \n" +
                "   shipDate='" + shipDate + '\'' + " , \n" +
                "   status=" + status.name().toLowerCase() + " , \n" +
                "   complete=" + complete + "  \n" +
                '}';
    }
}
