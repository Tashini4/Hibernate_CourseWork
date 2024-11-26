package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.User;

import java.util.List;

public interface UserDAO extends CrudDAO<User> {
    public User searchByUsername(String username);

    List<String> getUserIds();
}
