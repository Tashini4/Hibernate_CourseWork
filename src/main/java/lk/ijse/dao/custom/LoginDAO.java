package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Login;

public interface LoginDAO extends CrudDAO<Login> {
    Login getLastLogin();
}
