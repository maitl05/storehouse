package maitl.model.repository;


import maitl.model.common.ConnectionPool;
import maitl.model.entity.Product;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
    //delete update
    public void insertProduct(Product product) throws Exception{
        preparedStatement = connection.prepareStatement("select product.nextval nid form dual");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        product.setProductID(resultSet.getLong("nid"));
        product.setImportDate(new Date(System.currentTimeMillis()));
        preparedStatement = connection.prepareStatement("insert into product (product_id, import_date, product_name, product_category, price, product_quantity_available_in_store) values (?,?,?,?,?,?)");
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
            jsonArray.add(one(resultSet));
        }
        return jsonArray.toJSONString();
    }

    public String selectOneProduct(Long productID) throws Exception{
        preparedStatement = connection.prepareStatement("select * from product where product_id = ?");
        preparedStatement.setLong(1, productID);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return one(resultSet).toJSONString();

    }

    private JSONObject one (ResultSet resultSet) throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("productID", resultSet.getString("product_id"));
        jsonObject.put("importDate", resultSet.getString("import_date"));
        jsonObject.put("productName", resultSet.getString("product_name"));
        jsonObject.put("productCategory", resultSet.getString("product_category"));
        jsonObject.put("price", resultSet.getLong("price"));
        jsonObject.put("ProductQuantityAvailableInStore",resultSet.getLong("product_quantity_available_in_store"));
        return jsonObject;
    }

    public void updateProduct(Product product) throws Exception{
        preparedStatement  = connection.prepareStatement("update product set price = ?, product_quantity_available_in_store = ? where product_id=?");
        preparedStatement.setLong(1, product.getPrice());
        preparedStatement.setLong(2,product.getProductQuantityAvailableInStore());
        preparedStatement.setLong(3,product.getProductID());
        preparedStatement.executeUpdate();

    }

    public void deleteProduct(long productID)throws Exception{
        preparedStatement  = connection.prepareStatement("delete from product where product_id=?");
        preparedStatement.setLong(1,productID);
        preparedStatement.executeUpdate();
    }


    @Override
    public void close() throws Exception {
        connection.commit();
        preparedStatement.close();
        connection.close();
    }
}
