package com.petstore.models;

import com.google.gson.Gson;
import lombok.Data;

import java.util.List;

@Data
public class UserResponse implements BaseModel{
    private int code;
    private String type;
    private String message;

    Gson gson = new Gson();
    @Override
    public UserResponse convertStringToBaseModel(String jsonObject) {
        return gson.fromJson(jsonObject, UserResponse.class);
    }

    @Override
    public List<BaseModel> convertStringToModelList(String jsonObject) {
        return null;
    }

    @Override
    public String convertToJson(BaseModel model) {
        return null;
    }

    @Override
    public String toString() {
        return "UserResponse{\n" +
                "   code=" + code + "\n " +
                "  type=" + type + "\n " +
                "  message=" + message + "\n " +
                '}';
    }
}
