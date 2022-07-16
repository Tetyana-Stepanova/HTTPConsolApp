package com.petstore.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Data
public class User implements BaseModel{
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private int userStatus;

    Gson gson = new Gson();
    @Override
    public User convertStringToBaseModel(String jsonObject) {
        return gson.fromJson(jsonObject, User.class);
    }

    @Override
    public List<BaseModel> convertStringToModelList(String jsonObject) {
        Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
        return gson.fromJson(jsonObject, userListType);
    }

    @Override
    public String convertToJson(BaseModel model) {
        User user = (User) model;
        return gson.toJson(user);
    }

    @Override
    public String toString() {
        return "User{ \n" +
                "   id=" + id + " , \n" +
                "   username=" + username + " , \n" +
                "   firstName=" + firstName + " , \n" +
                "   lastName=" + lastName + " , \n" +
                "   email=" + email + " , \n" +
                "   password=" + password + " , \n" +
                "   phone=" + phone + " , \n" +
                "   userStatus=" + userStatus + " , \n" +
                '}';
    }
}
