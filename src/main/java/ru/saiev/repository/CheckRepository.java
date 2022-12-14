package ru.saiev.repository;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class CheckRepository extends AbstractRepository {

    //
    public long newCheck(BigDecimal totalSum) {
        Long result = null;
        String sql = "INSERT INTO myshema.checks (total)" +
                "VALUES (?) RETURNING check_id";
        try (PreparedStatement prSt = dataBaseConnection.getDbConnection().prepareStatement(sql)) {
            prSt.setFloat(1, totalSum.floatValue());
            ResultSet resultQuery;
            resultQuery = prSt.executeQuery();

            if (resultQuery.next()) {
                result = resultQuery.getLong("check_id");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return Objects.requireNonNull(result);
    }
}

