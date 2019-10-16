package maitl.model.repository;

import maitl.model.common.ConnectionPool;
import maitl.model.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ProductDataAccess implements AutoCloseable {
    Connection connection;
    PreparedStatement preparedStatement;

    public ProductDataAccess() throws Exception{
        connection = ConnectionPool.getConnection();
        connection.setAutoCommit(false);

    }
    //insert select delete update search
    public void insertProduct(Product product){


    }






    @Override
    public void close() throws Exception {
        connection.commit();
        preparedStatement.close();
        connection.close();
    }
}
