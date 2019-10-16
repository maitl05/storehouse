package maitl.model.repository;

import maitl.model.common.ConnectionPool;
import maitl.model.entity.Product;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductDataAccess implements AutoCloseable {
    Connection connection;
    PreparedStatement preparedStatement;

    public ProductDataAccess() throws Exception{
        connection = ConnectionPool.getConnection();
        connection.setAutoCommit(false);

    }
    //insert select delete update search
    public void insertProduct(Product product) throws Exception{
        preparedStatement = connection.prepareStatement("select product.nextval nid form dual");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        product.setProductID(resultSet.getLong("nid"));
        product.setImportDate(new Date(System.currentTimeMillis()));
        preparedStatement = connection.prepareStatement("insert into product (productID, importDate, productName, productCategory, price, productQuantityAvailableInStore) values (?,?,?,?,?,?)");
        preparedStatement.setLong(1 , product.getProductID());
        preparedStatement.setDate(2,product.getImportDate());
        preparedStatement.setString(3,product.getProductName());
        preparedStatement.setString(4,product.getProductCategory());
        preparedStatement.setLong(5, product.getPrice());
        preparedStatement.setLong(6,product.getProductQuantityAvailableInStore());
        preparedStatement.executeUpdate();


    }






    @Override
    public void close() throws Exception {
        connection.commit();
        preparedStatement.close();
        connection.close();
    }
}
