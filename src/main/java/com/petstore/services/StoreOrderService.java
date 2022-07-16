package com.petstore.services;

import com.petstore.models.Order;

import java.io.IOException;

public class StoreOrderService extends PetsStoreApiService{
    PetsStoreApiService petsStoreApiService = new PetsStoreApiService();

    private final String baseUrl = "https://petstore.swagger.io/v2/store";
    String urlPlaceOrder = "%s/order";
    String urlFindOrderById = "%s/order/%s";
    String urlDelete = "%s/order/%s";
    String urlInventories = "%s/inventory";

    public Order addNewOrder(Order newOrder) throws IOException {
        String url = String.format(urlPlaceOrder, baseUrl);
        return (Order) petsStoreApiService.postEntity(newOrder, url);
    }

    public Order getOrderById(long orderId) throws IOException {
        String url = String.format(urlFindOrderById, baseUrl, orderId);
        return (Order) petsStoreApiService.getModel(url, new Order());
    }

    public boolean deleteOrderById(long orderId) throws IOException {
        String url = String.format(urlDelete, baseUrl, orderId);
        return petsStoreApiService.deleteEntity(url);
    }

    public String getPetInventory() throws IOException {
        String url = String.format(urlInventories, baseUrl);
        return petsStoreApiService.getInventory(url);
    }
}
