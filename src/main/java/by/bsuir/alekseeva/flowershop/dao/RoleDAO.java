package by.bsuir.alekseeva.flowershop.dao;

import by.bsuir.alekseeva.flowershop.beans.Role;
import by.bsuir.alekseeva.flowershop.exception.DAOException;

import java.util.Optional;


public interface RoleDAO {
    Optional<Role> getRoleById(int id) throws DAOException;
    int getRoleId(Role role) throws DAOException;
}
