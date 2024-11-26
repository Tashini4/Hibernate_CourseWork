package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.LoginBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.LoginDAO;
import lk.ijse.dto.LoginDTO;
import lk.ijse.entity.Login;

import java.sql.SQLException;

public class LoginBOImpl implements LoginBO {
    LoginDAO loginDAO = (LoginDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.LOGIN);
    @Override
    public boolean save(LoginDTO dto) throws Exception {
        return loginDAO.save(new Login(dto.getLogin(),dto.getUserID(),dto.getDate()));
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return loginDAO.generateNextId();
    }
}
