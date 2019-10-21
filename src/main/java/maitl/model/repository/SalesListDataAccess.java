package maitl.model.repository;

import maitl.model.common.ConnectionPool;
import maitl.model.entity.SalesList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SalesListDataAccess implements AutoCloseable {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public SalesListDataAccess() throws Exception {
        connection = ConnectionPool.getConnection();
        connection.setAutoCommit(false);
    }

    public void insertSalesList(SalesList salesList) throws Exception{
        salesList.setSalesDate(new Date(System.currentTimeMillis()));
        preparedStatement = connection.prepareStatement("insert into sales_list (product_id,sales_date,sales_quantity) values (?,?,?)");
        preparedStatement.setLong(1 , salesList.getProductID());
        preparedStatement.setDate(2 , salesList.getSalesDate());
        preparedStatement.setLong(3 , salesList.getSalesQuantity());
        preparedStatement.executeUpdate();
    }

    public String selectAllSalesList () throws Exception{
        preparedStatement = connection.prepareStatement("select * from sales_list");
        ResultSet resultSet = preparedStatement.executeQuery();
        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            jsonArray.put(one(resultSet));
        }
        return jsonArray.toString();
    }

    public String selectOneProductFromSalesList (long productID) throws Exception{
        preparedStatement = connection.prepareStatement("select * from sales_list where product_id=?");
        preparedStatement.setLong(1,productID);
        ResultSet resultSet = preparedStatement.executeQuery();
        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            jsonArray.put(one(resultSet));
        }
        return jsonArray.toString();
    }

    private JSONObject one (ResultSet resultSet) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("productID", resultSet.getString("product_id"));
        jsonObject.put("salesDate", resultSet.getString("sales_date"));
        jsonObject.put("salesQuantity", resultSet.getString("sales_quantity"));
        return jsonObject;
    }

    @Override
    public void close() throws Exception {
        connection.commit();
        preparedStatement.close();
        connection.close();
    }

}
