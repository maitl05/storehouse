package maitl.model.entity;

import java.sql.Date;

public class SalesList {
    private long productID;
    private Date salesDate;
    private long salesQuantity;

    public SalesList(){}

    public SalesList(long productID, Date salesDate, long salesQuantity) {
        this.productID = productID;
        this.salesDate = salesDate;
        this.salesQuantity = salesQuantity;
    }

    public long getProductID() {
        return productID;
    }

    public SalesList setProductID(long productID) {
        this.productID = productID;
        return this;
    }

    public Date getSalesDate() {
        return salesDate;
    }

    public SalesList setSalesDate(Date salesDate) {
        this.salesDate = salesDate;
        return this;
    }

    public long getSalesQuantity() {
        return salesQuantity;
    }

    public SalesList setSalesQuantity(long salesQuantity) {
        this.salesQuantity = salesQuantity;
        return this;
    }
}
