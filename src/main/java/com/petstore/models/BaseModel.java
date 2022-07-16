package com.petstore.models;

import java.util.List;

public interface BaseModel {

    BaseModel convertStringToBaseModel(String jsonObject);

    List<BaseModel> convertStringToModelList(String jsonObject);

    String convertToJson(BaseModel model);
}
