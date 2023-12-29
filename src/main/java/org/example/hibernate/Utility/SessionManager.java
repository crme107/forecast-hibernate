package org.example.hibernate.Utility;

import lombok.Getter;
import org.hibernate.Session;

public class SessionManager {
    @Getter
    private static Session session;

    public static void openSession() {
        session = SessionFactoryManager.getSessionFactory().openSession();
    }

    public static void closeSession() {
        session.close();
    }
}