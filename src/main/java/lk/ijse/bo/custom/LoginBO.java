package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.LoginDTO;

import java.sql.SQLException;

public interface LoginBO extends SuperBO {
    public boolean save(LoginDTO dto) throws Exception;

    String generateNextId() throws SQLException, ClassNotFoundException;
}
