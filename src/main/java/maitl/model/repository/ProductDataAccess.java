package maitl.model.repository;


import maitl.model.common.ConnectionPool;
import maitl.model.entity.Product;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;

public class ProductDataAccess implements AutoCloseable {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public ProductDataAccess() throws SQLException{
        connection = ConnectionPool.getConnection();
        connection.setAutoCommit(false);

    }

    public void insertProduct(Product product) throws SQLException{
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

    public String selectAllProduct () throws SQLException, JSONException{
        preparedStatement = connection.prepareStatement("select * from product");
        ResultSet resultSet = preparedStatement.executeQuery();
        JSONArray jsonArray = new JSONArray();
        if (resultSet.next()){
            do{
                jsonArray.put(productResultSetToJSON(resultSet));
            }while (resultSet.next());
            return jsonArray.toString();
        } else {
            return null;
        }

    }

    public String selectOneProduct(long productID) throws SQLException, JSONException{
        preparedStatement = connection.prepareStatement("select * from product where product_id = ?");
        preparedStatement.setLong(1, productID);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return productResultSetToJSON(resultSet).toString();
        } else {
            return null;
        }
    }

    private JSONObject productResultSetToJSON(ResultSet resultSet) throws SQLException, JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("productID", resultSet.getString("product_id"));
        jsonObject.put("importDate", resultSet.getString("import_date"));
        jsonObject.put("productName", resultSet.getString("product_name"));
        jsonObject.put("productCategory", resultSet.getString("product_category"));
        jsonObject.put("price", resultSet.getLong("price"));
        jsonObject.put("ProductQuantityAvailableInStore",resultSet.getLong("product_quantity"));
        return jsonObject;
    }

    public int updateProduct(Product product) throws SQLException{
        int rowsAffected = 0;
        if (product.getPrice() != -1){
            preparedStatement  = connection.prepareStatement("update product set price = ? where product_id=?");
            preparedStatement.setLong(1, product.getPrice());
            preparedStatement.setLong(2, product.getProductID());
            rowsAffected += preparedStatement.executeUpdate();
        }
        if (product.getProductQuantityAvailableInStore() != -1){
            preparedStatement = connection.prepareStatement("update product set product_quantity = ? where product_id = ?");
            preparedStatement.setLong(1,product.getProductQuantityAvailableInStore());
            preparedStatement.setLong(2, product.getProductID());
            rowsAffected += preparedStatement.executeUpdate();
        }
        return rowsAffected;
    }

    public int deleteProduct(long productID) throws SQLException{
        preparedStatement  = connection.prepareStatement("delete from product where product_id=?");
        preparedStatement.setLong(1,productID);
        return preparedStatement.executeUpdate();
    }


    @Override
    public void close() throws SQLException {
        connection.commit();
        preparedStatement.close();
        connection.close();
    }
}
