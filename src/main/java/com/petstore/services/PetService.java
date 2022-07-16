package com.petstore.services;

import com.petstore.models.Category;
import com.petstore.models.Pet;
import com.petstore.models.Tag;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class PetService extends PetsStoreApiService {

    PetsStoreApiService petsStoreApiService = new PetsStoreApiService();
    private final String baseUrl = "https://petstore.swagger.io/v2/pet";
    String urGetByStatus = "%s/findByStatus?status=%s";
    String urlGetById = "%s/%s";
    String urlUploadImage = "%s/%s/uploadImage";

    public List<Pet> getAllPetsByStatus(Pet.Status status) throws IOException {
        String url = String.format(urGetByStatus, baseUrl, status.toString().toLowerCase());
        List<Pet> result = petsStoreApiService.getAll(url, new Pet())
                .stream()
                .filter(model -> model instanceof Pet)
                .map(model -> (Pet) model).collect(Collectors.toList());
        return result;
    }

    public Pet getPetById(long id) throws IOException {
        String url = String.format(urlGetById, baseUrl, id);
        return (Pet) petsStoreApiService.getModel(url, new Pet());
    }

    public Pet updatePet(Pet updatePet) throws IOException {
        String url = baseUrl;
        return (Pet) petsStoreApiService.putEntity(updatePet, url);
    }

    public Pet addNewPet(Pet newPet) throws IOException {
        String url = baseUrl;
        return (Pet) petsStoreApiService.postEntity(newPet, url);
    }

    public boolean updateInStore(long id, String name, String status) throws IOException {
        String url = String.format(urlGetById, baseUrl, id);
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("status", status);
        return petsStoreApiService.postForUpdate(url, params);
    }

    public boolean deletePetById(long id) throws IOException {
        String url = String.format(urlGetById, baseUrl, id);
        return petsStoreApiService.deleteEntity(url);
    }

    public boolean uploadImage(long id, File file) throws IOException {
        String url = String.format(urlUploadImage, baseUrl, id);
        return petsStoreApiService.postForMultipart(url, file);
    }

    public Pet setPet(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter pet's Id (or it will be generated automatically for NEW Pet): ");
        long petId;
        String petIdString = scanner.nextLine();
        if(petIdString.equals(" ")){
            petId = 0L;
        }else {
            petId = Long.parseLong(petIdString);
        }
        System.out.println("Enter category Id:");
        long categoryId = 0L;
        try {
            categoryId = Long.parseLong(scanner.nextLine());
        }catch (NumberFormatException ex){
            System.out.println("Id can't be parsed to long");
        }
        System.out.println("Enter category name:");
        String categoryName = scanner.nextLine();
        System.out.println("Enter pet's name :");
        String petsName = scanner.nextLine();
        System.out.println("Enter photoUrl may be null:");
        String photoUrl = scanner.nextLine();
        System.out.println("How many tags do you want to create? :");
        int quantity = 0;
        try {
            quantity = Integer.parseInt(scanner.nextLine());
        }catch (NumberFormatException ex){
            System.out.println("Status can't be parsed to integer");
        }
        List<Tag> tags = new ArrayList<>();
        for (int i = 0; i < quantity; i++){
            System.out.println("Enter tag's Id:");
            long tagId = 0L;
            try {
                tagId = Long.parseLong(scanner.nextLine());
            }catch (NumberFormatException ex){
                System.out.println("Id can't be parsed to long");
            }
            System.out.println("Enter tag's name : ");
            String tagName = scanner.nextLine();

            Tag newTag = new Tag();
            newTag.setId(tagId);
            newTag.setName(tagName);
            tags.add(newTag);
        }
        System.out.println("Choose and enter status : available, pending, sold ");
        String status = scanner.nextLine();

        Pet newPet = new Pet();
        newPet.setId(petId);
        newPet.setCategory(new Category(categoryId));
        newPet.setCategory(new Category(categoryName));
        newPet.setName(petsName);
        newPet.setPhotoUrls(Collections.singletonList(photoUrl));
        newPet.setTags(tags);
        newPet.setStatus(convertStringToStatus(status));

        return newPet;
    }

    public Pet.Status convertStringToStatus(String statusString){
        switch (statusString){
            case "available": return Pet.Status.AVAILABLE;
            case "pending" : return Pet.Status.PENDING;
            case "sold" : return  Pet.Status.SOLD;
            default:
                System.out.println("Status incorrect");
                setPet();
                break;
        }
        return null;
    }
}
