package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    public static ObservableList<Part> AllParts = FXCollections.observableArrayList();
    public static ObservableList<Product> AllProducts = FXCollections.observableArrayList();

    /*********Get All Parts Observable list ********/
    public static ObservableList<Part> getAllParts() {
        return AllParts;
    }

    public static int partListSize() {
        return AllParts.size();
    }

    /*********AddParts*****/
    public static void addParts(Part newPart) {
        AllParts.add(newPart);
    }



    /****Lookup Parts  *****/
    public static Part lookupPart(int partId) {
        Part partFound = null;

        for (Part part : AllParts) {
            if (part.getId() == partId) {
                partFound = part;
            }
        }

        return partFound;
    }

    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partsFound = FXCollections.observableArrayList();

        for  (int i = 0; i < AllParts.size(); i++) {
            if (AllParts.get(i).getName().toLowerCase().contains(partName.toLowerCase())) {
                partsFound.add(AllParts.get(i));
            }
        }

        return partsFound;
    }


    /*********Search Parts By ID******/
    public static Part lookupPartID(int id) {
        Part num = null;
        for (Part part : AllParts) {
            if (id == part.getId()) {
                num = part;
            }
        }
        return num;
    }

    /******Update a selected part present in the Inventory****/

    public static boolean updatePart(int id, Part part)
    {
        int index = -1;

        for (Part product : Inventory.getAllParts())
        {
            index++;
            if (product.getId() == id)
            {
                Inventory.getAllParts().set(index, part);
                return true;
            }
        }
        return false;
    }
    /******Update a selected product present in the Inventory*/
    public static boolean updateProduct(int id, Product product)
    {
        int index = -1;

        for (Product part : Inventory.getAllProducts())
        {
            index++;
            if (part.getId() == id)
            {
                Inventory.getAllProducts().set(index, product);
                return true;
            }
        }
        return false;
    }
    /******Delete a selected part present in the Inventory****/
    public static boolean deletePart(Part partSelected)

    {
        return AllParts.remove(partSelected);
    }

    /*********Get All Products Observable list ********/
    public static ObservableList<Product> getAllProducts() {
        return AllProducts;
    }

    public static int productListSize() {
        return AllProducts.size();
    }


    /**********Lookup Products*********/
    public static ObservableList lookupProduct(String searchProductName) {
        ObservableList<Product> foundProduct = FXCollections.observableArrayList();

        if (searchProductName.length() == 0) {
            foundProduct = AllProducts;
        } else {
            for (int i = 0; i < AllProducts.size(); i++) {
                if (AllProducts.get(i).getName().toLowerCase().contains(searchProductName.toLowerCase())) {
                    foundProduct.add(AllProducts.get(i));
                }
            }
        }

        return foundProduct;
    }

    /*********AddProducts*****/
    public static void addProduct(Product newProduct) {
        AllProducts.add(newProduct);
    }


    /******Delete a selected product present in the Inventory****/
    public static boolean deleteProduct(Product selectedProduct)

    {
        return AllProducts.remove(selectedProduct);
    }

    }



