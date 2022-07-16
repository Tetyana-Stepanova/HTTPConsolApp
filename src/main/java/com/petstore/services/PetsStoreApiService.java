package com.petstore.services;

import com.google.gson.Gson;
import com.petstore.models.BaseModel;
import com.petstore.models.UserResponse;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PetsStoreApiService {

    public BaseModel postEntity(BaseModel model, String url) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        StringEntity requestEntity = new StringEntity(
                model.convertToJson(model),
                ContentType.APPLICATION_JSON);
        HttpPost post = new HttpPost(url);
        post.setEntity(requestEntity);

        CloseableHttpResponse response = client.execute(post);
        int respStatus = response.getStatusLine().getStatusCode();
        if (respStatus >= 200 && respStatus < 300) {
            BaseModel result = model.convertStringToBaseModel(EntityUtils.toString(response.getEntity()));
            client.close();
            return result;
        } else {
            System.out.println(response.getStatusLine().toString());
        }
        return null;
    }

    public boolean postForUpdate(String url, Map<String, String> mapParams) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        mapParams.forEach((k, v) -> {
            params.add(new BasicNameValuePair(k, v));
        });
        HttpPost post = new HttpPost(url);
        post.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse response = client.execute(post);
        int respStatus = response.getStatusLine().getStatusCode();
        if (respStatus >= 200 && respStatus < 300) {
            client.close();
            return true;
        }else {
            System.out.println(response.getStatusLine().toString());
        }
        return false;
    }

    public boolean postForMultipart(String url, File file) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);
        StringBody stringBody = new StringBody("Message 1", ContentType.MULTIPART_FORM_DATA);
        builder.addPart("file", fileBody);
        builder.addPart("additionalMetadata", stringBody);
        HttpEntity multipart = builder.build();
        httpPost.setEntity(multipart);

        CloseableHttpResponse response = client.execute(httpPost);
        int respStatus = response.getStatusLine().getStatusCode();
        if (respStatus >= 200 && respStatus < 300) {
            client.close();
            return true;
        } else {
            System.out.println(response.getStatusLine().toString());
        }
        return false;
    }

    public BaseModel putEntity(BaseModel model, String url) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        StringEntity requestEntity = new StringEntity(
                model.convertToJson(model),
                ContentType.APPLICATION_JSON);
        httpPut.setEntity(requestEntity);
        CloseableHttpResponse response = client.execute(httpPut);
        int respStatus = response.getStatusLine().getStatusCode();
        if (respStatus >= 200 && respStatus < 300) {
            BaseModel result = model.convertStringToBaseModel(EntityUtils.toString(response.getEntity()));
            client.close();
            return result;
        } else {
            System.out.println(response.getStatusLine().toString());
        }
        return null;
    }

    public boolean deleteEntity(String url) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpDelete delete = new HttpDelete(url);
        delete.addHeader("Content-Type", "application/json");
        CloseableHttpResponse response = client.execute(delete);
        int respStatus = response.getStatusLine().getStatusCode();
        if (respStatus >= 200 && respStatus < 300) {
            client.close();
            return true;
        } else {
            System.out.println(response.getStatusLine().toString());
        }
        return false;
    }

    public List<BaseModel> getAll(String url, BaseModel model) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        get.addHeader("Content-Type", "application/json");
        CloseableHttpResponse response = client.execute(get);
        int respStatus = response.getStatusLine().getStatusCode();
        if (respStatus >= 200 && respStatus < 300) {
            List<BaseModel> resultList = model.convertStringToModelList(EntityUtils.toString(response.getEntity()));
            client.close();
            return resultList;
        } else {
            System.out.println(response.getStatusLine().toString());
        }
        return null;
    }

    public BaseModel getModel(String url, BaseModel model) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        get.addHeader("Content-Type", "application/json");
        CloseableHttpResponse response = client.execute(get);
        int respStatus = response.getStatusLine().getStatusCode();
        if (respStatus >= 200 && respStatus < 300) {
            BaseModel result = model.convertStringToBaseModel(EntityUtils.toString(response.getEntity()));
            client.close();
            return result;
        }else {
            System.out.println(response.getStatusLine().toString());
        }
        return null;
    }

    public String getInventory(String url) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        get.addHeader("Content-Type", "application/json");
        CloseableHttpResponse response = client.execute(get);
        int respStatus = response.getStatusLine().getStatusCode();
        if (respStatus >= 200 && respStatus < 300){
            String result = EntityUtils.toString(response.getEntity()).replaceAll(",", ",\n");
            client.close();
            return result;
        }else {
            System.out.println(response.getStatusLine().toString());
        }
        return null;
    }

    public UserResponse postForUser(String json, String url) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        StringEntity requestEntity = new StringEntity(
                json,
                ContentType.APPLICATION_JSON);
        HttpPost post = new HttpPost(url);
        post.setEntity(requestEntity);

        CloseableHttpResponse response = client.execute(post);
        int respStatus = response.getStatusLine().getStatusCode();
        if (respStatus >= 200 && respStatus < 300) {
            UserResponse result = new Gson().fromJson(EntityUtils.toString(response.getEntity()), UserResponse.class);
            client.close();
            return result;
        } else {
            System.out.println(response.getStatusLine().toString());
        }
        return null;
    }

    public UserResponse putUser(String json, String url) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        StringEntity requestEntity = new StringEntity(
                json,
                ContentType.APPLICATION_JSON);
        httpPut.setEntity(requestEntity);
        CloseableHttpResponse response = client.execute(httpPut);
        int respStatus = response.getStatusLine().getStatusCode();
        if (respStatus >= 200 && respStatus < 300) {
            UserResponse result = new Gson().fromJson(EntityUtils.toString(response.getEntity()), UserResponse.class);
            client.close();
            return result;
        } else {
            System.out.println(response.getStatusLine().toString());
        }
        return null;
    }
}
