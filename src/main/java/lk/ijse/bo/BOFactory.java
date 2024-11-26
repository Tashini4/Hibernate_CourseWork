package lk.ijse.bo;

import lk.ijse.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){

    }
    public static BOFactory getBoFactory(){
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }
    public enum BOType{
        USER,STUDENT,PAYMENT,COURSE,STUDENT_COURSE,LOGIN
    }
    public SuperBO getBO(BOType boType){
        switch (boType){
            case USER :
                return new UserBOImpl();
            case STUDENT:
                return new StudentBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case COURSE:
                return new CourseBOImpl();
            case STUDENT_COURSE:
                return new StudentCourseBOImpl();
            case LOGIN:
                return new  LoginBOImpl();
        }
    }
}
