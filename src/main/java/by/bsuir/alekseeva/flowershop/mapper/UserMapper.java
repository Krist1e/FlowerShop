package by.bsuir.alekseeva.flowershop.mapper;

import by.bsuir.alekseeva.flowershop.beans.User;
import by.bsuir.alekseeva.flowershop.dao.RoleDAO;

import java.sql.ResultSet;

public final class UserMapper {
    private UserMapper() {
    }

    public static User toEntity(ResultSet resultSet, RoleDAO roleDAO) {
        return null;
    }
}
