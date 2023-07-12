package org.example.userstoragespi.services;

import org.example.userstoragespi.domain.User;
import org.example.userstoragespi.domain.UserAdapter;
import org.example.userstoragespi.repository.UserRepositoryImpl;
import org.example.userstoragespi.repository.interfaces.UserRepository;
import org.example.userstoragespi.services.interfaces.UserService;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    public UserServiceImpl() {
        this.userRepository = new UserRepositoryImpl();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public List<UserModel> getAllUserModels(KeycloakSession session, RealmModel realm, ComponentModel model) {
        List<User> users=this.getAllUsers();
        List<UserModel> userModels=new ArrayList<>();
        for(User user: users){
            UserModel userModel=new UserAdapter(session,realm,model,user);
            userModels.add(userModel);
        }
        return userModels;
    }

    @Override
    public List<User> getAllUsers(int firstResult, int maxResult) {
        List<User> users=this.getAllUsers();
        return this.getAllUsers().subList(firstResult,
                Math.min((users.size() ), (firstResult + maxResult)));
    }

    @Override
    public List<UserModel> getAllUserModels(KeycloakSession session, RealmModel realm, ComponentModel model,int firstResult, int maxResult) {
        List<UserModel> userModels=this.getAllUserModels( session,  realm,  model);
        return userModels.subList(firstResult,Math.min((userModels.size() ), (firstResult + maxResult)));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Override
    public UserModel getUserModelByUsername(KeycloakSession session, RealmModel realm, ComponentModel model,String username) {
        User user= this.getUserByUsername(username);
        return  new UserAdapter(session,realm,model,user);

    }

    @Override
    public List<UserModel> searchForUsername(KeycloakSession session, RealmModel realm, ComponentModel model, String username,int firstResult,int maxResult) {
        List<User> users=userRepository.searchUser(username);
        List<UserModel> userModels=new ArrayList<>();
        for(User user: users){
            UserModel userModel=new UserAdapter(session,realm,model,user);
            userModels.add(userModel);
        }
        return userModels.subList(firstResult,Math.min((userModels.size()), (firstResult + maxResult)));
    }
}
