package maitl.model.entity;

import java.sql.Date;

public class Product {
    private long productID = -1;
    private String productName;
    private String productCategory;
    private long productQuantityAvailableInStore = -1;
    private Date importDate;
    private long price = -1;

    public Product(){}

    public Product(long productID, String productName, String productCategory, long productQuantityAvailableInStore, Date importDate, long price) {
        this.productID = productID;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productQuantityAvailableInStore = productQuantityAvailableInStore;
        this.importDate = importDate;
        this.price = price;
    }

    public long getProductID() {
        return productID;
    }

    public Product setProductID(long productID) {
        this.productID = productID;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public Product setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public Product setProductCategory(String productCategory) {
        this.productCategory = productCategory;
        return this;
    }

    public long getProductQuantityAvailableInStore() {
        return productQuantityAvailableInStore;
    }

    public Product setProductQuantityAvailableInStore(long productQuantityAvailableInStore) {
        this.productQuantityAvailableInStore = productQuantityAvailableInStore;
        return this;
    }

    public Date getImportDate() {
        return importDate;
    }

    public Product setImportDate(Date importDate) {
        this.importDate = importDate;
        return this;
    }

    public long getPrice() {
        return price;
    }

    public Product setPrice(long price) {
        this.price = price;
        return this;
    }
}
