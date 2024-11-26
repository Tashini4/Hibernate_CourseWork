package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dao.SuperDAO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserBO extends SuperBO {
    public boolean save(UserDTO dto) throws Exception;

    public boolean update(UserDTO dto) throws Exception;

    public boolean delete(String ID)throws Exception;

    public List<UserDTO> getAll() throws SQLException, ClassNotFoundException;

    User searchByIdUser(String id) throws SQLException, ClassNotFoundException;

    public String generateNextId() throws SQLException, ClassNotFoundException;
    public List<String> getUserIds();
}
