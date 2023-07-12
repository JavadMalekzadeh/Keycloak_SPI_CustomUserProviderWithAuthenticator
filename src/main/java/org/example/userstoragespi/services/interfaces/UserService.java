package org.example.userstoragespi.services.interfaces;

import org.example.userstoragespi.domain.User;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    List<UserModel> getAllUserModels(KeycloakSession session, RealmModel realm, ComponentModel model);
    List<User> getAllUsers(int firstResult,int maxResult);
    List<UserModel> getAllUserModels(KeycloakSession session, RealmModel realm, ComponentModel model,int firstResult,int maxResult);
    User getUserByUsername(String username);
    UserModel getUserModelByUsername(KeycloakSession session, RealmModel realm, ComponentModel model,String username);
    List<UserModel> searchForUsername(KeycloakSession session, RealmModel realm, ComponentModel model,String username,int firstResult,int maxResult);
}
