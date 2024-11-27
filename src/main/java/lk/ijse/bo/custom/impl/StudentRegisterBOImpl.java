package lk.ijse.bo.custom.impl;

import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.PaymentBO;
import lk.ijse.bo.custom.Student_CourseBO;
import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dto.StudentRegisterPlaceDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class StudentRegisterBOImpl {
    public static boolean StudentRegisterPlace(StudentRegisterPlaceDTO SR) {
        PaymentBO paymentBO = (PaymentBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.PAYMENT);
        Student_CourseBO studentCourseBO = (Student_CourseBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.STUDENT_COURSE);

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            boolean isStudentRegister_SC = studentCourseBO.save(SR.getStudent_course());
            if (isStudentRegister_SC) {
                boolean isPay = paymentBO.save(SR.getStudent_payment());
                if (isPay) {
                    transaction.commit();
                    return true;
                }
            }
            if (transaction != null) transaction.rollback();
            return false;

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }
}
