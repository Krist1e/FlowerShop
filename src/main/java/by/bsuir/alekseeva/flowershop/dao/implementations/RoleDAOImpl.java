package by.bsuir.alekseeva.flowershop.dao.implementations;

import by.bsuir.alekseeva.flowershop.beans.Role;
import by.bsuir.alekseeva.flowershop.dao.RoleDAO;
import by.bsuir.alekseeva.flowershop.exception.DAOException;

import java.util.Optional;

public class RoleDAOImpl implements RoleDAO {
    @Override
    public Optional<Role> getRoleById(int id) throws DAOException {
        return Optional.empty();
    }

    @Override
    public int getRoleId(Role role) throws DAOException {
        return 0;
    }
}
