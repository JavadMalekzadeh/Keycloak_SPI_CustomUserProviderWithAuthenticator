package org.example.userstoragespi.repository;


import org.example.userstoragespi.HibernateUtil;
import org.example.userstoragespi.domain.ExpiringMap;
import org.example.userstoragespi.domain.User;
import org.example.userstoragespi.repository.interfaces.UserRepository;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;

import javax.transaction.Transactional;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

//    private static List<User> users;
    private static ExpiringMap<String,User> expiringMap=new ExpiringMap<>();
    @Override
    public List<User> getAllUsers() {
//        if(users==null) {
//        return getuu();
        Statistics statistics=HibernateUtil.getSessionFactory().getStatistics();
        System.out.println("Connection Pool :"+ statistics.getConnectCount());
        Session session;
        try{
            System.out.println("getting current session");
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("open a session");
            session = HibernateUtil.getSessionFactory().openSession();
        }
        List<User> users=null;
        try {
             session.beginTransaction();
             users = session.createQuery("SELECT distinct u FROM User as u join u.userGroups").list();
             session.getTransaction().commit();
        }catch (Exception e) {
            session.getTransaction().rollback();
            throw e;

        }finally {

//            session.close();
        }
//        }
        return  users;
    }

    @Override
    public User getUserByUsername(String username) {
        User cachedUser=expiringMap.get(username);
        if(cachedUser!=null){
            return cachedUser;
        }
        Statistics statistics=HibernateUtil.getSessionFactory().getStatistics();
        System.out.println("Connection Pool :"+ statistics.getConnectCount());
        Session session;
        try{
            System.out.println("getting current session getuser");
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("open a session");
            session = HibernateUtil.getSessionFactory().openSession();
        }

        List<User> users=null;
        try {
            session.beginTransaction();
            Query query = session.createQuery("SELECT distinct u FROM User as u join fetch u.userGroups where u.username like :usrnam");
            query.setParameter("usrnam",username);
            users=query.list();
            session.getTransaction().commit();
        }catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
        if(users!=null && users.size()>0) {
            User user =  users.get(0);
            expiringMap.put(user.getUsername(),user,2000);
            return user;
        }
        return null;
    }

    @Override
    public List<User> searchUser(String username) {
        Statistics statistics=HibernateUtil.getSessionFactory().getStatistics();
        System.out.println("Connection Pool :"+ statistics.getConnectCount());
        Session session;
        try{
            System.out.println("getting current session searchuser");
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("open a session");
            session = HibernateUtil.getSessionFactory().openSession();
        }
        List<User> users=null;
        try {
            session.beginTransaction();
            Query query = session.createQuery("select distinct u FROM User as u join u.userGroups where  u.username like :usrnam");
            query.setParameter("usrnam","%"+username+"%");
            users=query.list();
            session.getTransaction().commit();
        }catch (Exception e) {
            session.getTransaction().rollback();
            throw e;

        }finally {
//            session.close();
        }
//        }
        return  users;
    }


}
