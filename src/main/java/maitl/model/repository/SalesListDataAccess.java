package maitl.model.repository;

import maitl.model.common.ConnectionPool;
import maitl.model.entity.SalesList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;

public class SalesListDataAccess implements AutoCloseable {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public SalesListDataAccess() throws SQLException {
        connection = ConnectionPool.getConnection();
        connection.setAutoCommit(false);
    }

    public void insertSalesList(SalesList salesList) throws SQLException{
        salesList.setSalesDate(new Date(System.currentTimeMillis()));
        preparedStatement = connection.prepareStatement("insert into sales_list (product_id,sales_date,sales_quantity) values (?,?,?)");
        preparedStatement.setLong(1 , salesList.getProductID());
        preparedStatement.setDate(2 , salesList.getSalesDate());
        preparedStatement.setLong(3 , salesList.getSalesQuantity());
        preparedStatement.executeUpdate();
    }

    public String selectAllSalesList () throws SQLException, JSONException {
        preparedStatement = connection.prepareStatement("select * from sales_list");
        ResultSet resultSet = preparedStatement.executeQuery();
        return salesListResultSetToJSONString(resultSet);
    }

    public String selectOneProductFromSalesList (long productID) throws SQLException, JSONException{
        preparedStatement = connection.prepareStatement("select * from sales_list where product_id=?");
        preparedStatement.setLong(1,productID);
        ResultSet resultSet = preparedStatement.executeQuery();
        return salesListResultSetToJSONString(resultSet);
    }

    private String salesListResultSetToJSONString(ResultSet resultSet) throws SQLException, JSONException {
        if (resultSet.next()){
            JSONArray jsonArray = new JSONArray();
            do{
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("productID", resultSet.getString("product_id"));
                jsonObject.put("salesDate", resultSet.getString("sales_date"));
                jsonObject.put("salesQuantity", resultSet.getString("sales_quantity"));
                jsonArray.put(jsonObject);
            } while (resultSet.next());
            return jsonArray.toString();
        } else {
            return null;
        }
    }

    @Override
    public void close() throws SQLException {
        connection.commit();
        preparedStatement.close();
        connection.close();
    }

}
