package com.petstore.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import lombok.Data;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Data
public class Pet implements BaseModel{
    private long id;
    private Category category;
    private String name;
    private List<String> photoUrls = new ArrayList<>();
    private List<Tag> tags = new ArrayList<>();
    private Status status;

    public enum Status {
        @SerializedName("available")
        AVAILABLE,
        @SerializedName("pending")
        PENDING,
        @SerializedName("sold")
        SOLD
    }

    Gson gson = new Gson();

    @Override
    public Pet convertStringToBaseModel(String jsonObject) {
        return gson.fromJson(jsonObject, Pet.class);
    }

    @Override
    public List<BaseModel> convertStringToModelList(String jsonObject) {
        Type petListType = new TypeToken<List<Pet>>(){}.getType();
        return gson.fromJson(jsonObject, petListType);
    }

    @Override
    public String convertToJson(BaseModel model) {
        Pet pet = (Pet) model;
        return gson.toJson(pet);
    }

    @Override
    public String toString() {
        return "Pet{ \n" +
                "   id=" + id + " , \n" +
                "   category=" + category + " , \n" +
                "   name=" + name + " , \n" +
                "   photoUrls=" + photoUrls + " , \n" +
                "   tags=" + tags + " , \n" +
                "   status=" + status.name().toLowerCase() +"  \n" +
                '}';
    }
}
