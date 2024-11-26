package lk.ijse.dao;


import lk.ijse.dao.custom.impl.*;



public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){

    }
    public static DAOFactory getDaoFactory(){
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }
    public enum DAOType{
        USER,STUDENT,PAYMENT,COURSE,STUDENT_COURSE,LOGIN
    }
    public SuperDAO getDAO(DAOType daoType){
        switch (daoType){
            case USER :
                return new UserDAOImpl();
            case STUDENT:
                return new StudentDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case COURSE:
                return new CourseDAOImpl();
            case STUDENT_COURSE:
                return new Student_CourseDAOImpl();
            case LOGIN:
                return new LoginDAOImpl();
            default:
                return null;
        }
    }
}
