package org.example.userstoragespi.repository;

import org.example.userstoragespi.HibernateUtil;
import org.example.userstoragespi.domain.User;
import org.example.userstoragespi.domain.UserGroup;
import org.example.userstoragespi.repository.interfaces.UserGroupRepository;
import org.hibernate.Session;

import javax.transaction.Transactional;
import java.util.List;

public class UserGroupRepositoryImpl implements UserGroupRepository {
//    private static List<UserGroup> userGroups;
    @Override
    public List<UserGroup> getAllUserGroups() {
//        if(userGroups==null) {
        Session session = null;
        List<UserGroup> userGroups=null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            userGroups = session.createQuery("FROM UserGroup").list();
        }catch(Exception e){
            throw e;
        }finally {
            session.close();
        }
//        }
        return userGroups;
    }

}
