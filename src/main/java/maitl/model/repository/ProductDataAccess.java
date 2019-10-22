package maitl.model.repository;


import maitl.model.common.ConnectionPool;
import maitl.model.entity.Product;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

public class ProductDataAccess implements AutoCloseable {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public ProductDataAccess() throws Exception{
        connection = ConnectionPool.getConnection();
        connection.setAutoCommit(false);

    }

    public void insertProduct(Product product) throws Exception{
//        preparedStatement = connection.prepareStatement("select product.nextval nid form dual");
//        ResultSet resultSet = preparedStatement.executeQuery();
//        resultSet.next();
//        product.setProductID(resultSet.getLong("nid"));
        product.setImportDate(new Date(System.currentTimeMillis()));
        preparedStatement = connection.prepareStatement("insert into product (product_id, import_date, product_name, product_category, price, product_quantity) values (?,?,?,?,?,?)");
        preparedStatement.setLong(1 , product.getProductID());
        preparedStatement.setDate(2,product.getImportDate());
        preparedStatement.setString(3,product.getProductName());
        preparedStatement.setString(4,product.getProductCategory());
        preparedStatement.setLong(5, product.getPrice());
        preparedStatement.setLong(6,product.getProductQuantityAvailableInStore());
        preparedStatement.executeUpdate();


    }

    public String selectAllProduct () throws Exception{
        preparedStatement = connection.prepareStatement("select * from product");
        ResultSet resultSet = preparedStatement.executeQuery();
        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            jsonArray.put(one(resultSet));
        }
        return jsonArray.toString();
    }

    public String selectOneProduct(long productID) throws Exception{
        preparedStatement = connection.prepareStatement("select * from product where product_id = ?");
        preparedStatement.setLong(1, productID);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return one(resultSet).toString();
        } else {
            throw new ProductNotFoundException(productID);
        }
    }

    private JSONObject one (ResultSet resultSet) throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("productID", resultSet.getString("product_id"));
        jsonObject.put("importDate", resultSet.getString("import_date"));
        jsonObject.put("productName", resultSet.getString("product_name"));
        jsonObject.put("productCategory", resultSet.getString("product_category"));
        jsonObject.put("price", resultSet.getLong("price"));
        jsonObject.put("ProductQuantityAvailableInStore",resultSet.getLong("product_quantity"));
        return jsonObject;
    }

    public void updateProduct(Product product) throws Exception{
        if (product.getPrice() != -1){
            preparedStatement  = connection.prepareStatement("update product set price = ? where product_id=?");
            preparedStatement.setLong(1, product.getPrice());
            preparedStatement.setLong(2, product.getProductID());
            if (preparedStatement.executeUpdate() == 0){
                throw new ProductNotFoundException(product.getProductID());
            }
        }
        if (product.getProductQuantityAvailableInStore() != -1){
            preparedStatement = connection.prepareStatement("update product set product_quantity = ? where product_id = ?");
            preparedStatement.setLong(1,product.getProductQuantityAvailableInStore());
            preparedStatement.setLong(2, product.getProductID());
            if (preparedStatement.executeUpdate() == 0){
                throw new ProductNotFoundException(product.getProductID());
            }
        }
    }

    public void deleteProduct(long productID) throws Exception{
        preparedStatement  = connection.prepareStatement("delete from product where product_id=?");
        preparedStatement.setLong(1,productID);
        if (preparedStatement.executeUpdate() == 0){
            throw new ProductNotFoundException(productID);
        }
    }


    @Override
    public void close() throws Exception {
        connection.commit();
        preparedStatement.close();
        connection.close();
    }
}
