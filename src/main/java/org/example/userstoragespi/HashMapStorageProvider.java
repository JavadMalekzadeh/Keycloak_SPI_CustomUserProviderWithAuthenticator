package org.example.userstoragespi;


import jodd.crypt.BCrypt;
import org.example.userstoragespi.domain.User;
import org.example.userstoragespi.domain.UserAdapter;
import org.example.userstoragespi.services.UserServiceImpl;
import org.example.userstoragespi.services.interfaces.UserService;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.group.GroupLookupProvider;
import org.keycloak.storage.group.GroupStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;


import java.util.*;
import java.util.stream.Stream;

public class HashMapStorageProvider implements
        UserStorageProvider, GroupStorageProvider, GroupLookupProvider,
        UserLookupProvider,
        UserQueryProvider,
        CredentialInputValidator {
    private static List<org.example.userstoragespi.domain.User> constUsers=new ArrayList<>();
    KeycloakSession keycloakSession;
    ComponentModel componentModel;
    private static  Map<String, User> loadedUsers ;
    private UserService userService;

    public HashMapStorageProvider(KeycloakSession session, ComponentModel model) {
        this.keycloakSession = session;
        this.componentModel = model;
        this.userService=new UserServiceImpl();
        loadedUsers= new HashMap<>();
    }

    @Override
    public void close() {

    }


    @Override
    public UserModel getUserById(String id, RealmModel realm) {
        StorageId storageId = new StorageId(id);
        String username = storageId.getExternalId();
        return getUserByUsername(username, realm);
    }

    @Override
    public UserModel getUserByUsername(String username, RealmModel realm) {
        return this.userService.getUserModelByUsername(keycloakSession,realm,componentModel,username);

    }

    @Override
    public UserModel getUserByEmail(String email, RealmModel realm) {
        return null;
    }

    @Override
    public boolean supportsCredentialType(String credentialType) {
        return credentialType.equals(PasswordCredentialModel.TYPE);
    }

    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {

        try {
            String password=userService.getUserByUsername(user.getUsername()).getPassword();
            return credentialType.equals(PasswordCredentialModel.TYPE) && password != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean isValid(RealmModel realm, UserModel user, CredentialInput credentialInput) {
        if (!supportsCredentialType(credentialInput.getType())) return false;
        try {
            String inputPass= credentialInput.getChallengeResponse().split("::")[0];
            String inputBranch= credentialInput.getChallengeResponse().split("::")[1];

            String password = user.getFirstAttribute("password");
            String branch = user.getFirstAttribute("branch");
            System.out.println("#-->"+branch);
            String[] splitBranches=branch.split(",");


            if (password == null) return false;
            if(!Arrays.stream(splitBranches).anyMatch(x->x.equals(inputBranch))) return false;
            return BCrypt.checkpw( user.getUsername()+inputPass,password);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    //------------------------
    @Override
    public int getUsersCount(RealmModel realm) {
        return loadedUsers.size();
    }

    @Override
    public List<UserModel> getUsers(RealmModel realm) {
        return getUsers(realm, 0, Integer.MAX_VALUE);
    }

    @Override
    public List<UserModel> getUsers(RealmModel realm, int firstResult, int maxResults) {
        return userService.getAllUserModels(keycloakSession,realm,componentModel,firstResult,maxResults);
    }

    @Override
    public List<UserModel> searchForUser(String search, RealmModel realm) {
        return searchForUser(search, realm, 0, Integer.MAX_VALUE);
    }

    @Override
    public List<UserModel> searchForUser(String search, RealmModel realm, int firstResult, int maxResults) {
        return userService.searchForUsername(keycloakSession,realm,componentModel,search,firstResult,maxResults);

    }
    @Override
    public List<UserModel> searchForUser(Map<String, String> params, RealmModel realm) {
        return params.size()>0 ? searchForUser(params, realm, 0, Integer.MAX_VALUE)
                :getUsers(realm,0,Integer.MAX_VALUE);
    }

    @Override
    public List<UserModel> searchForUser(Map<String, String> params, RealmModel realm, int firstResult, int maxResults) {
        // only support searching by username
        String usernameSearchString = params.get("username");
        if (usernameSearchString == null) return Collections.EMPTY_LIST;
        return searchForUser(usernameSearchString, realm, firstResult, maxResults);
    }

    @Override
    public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group, int firstResult, int maxResults) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<UserModel> searchForUserByUserAttribute(String attrName, String attrValue, RealmModel realm) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public GroupModel getGroupById(RealmModel realmModel, String s) {
        System.out.println("get group By ID");
        return null;
    }

    @Override
    public Stream<GroupModel> searchForGroupByNameStream(RealmModel realmModel, String s, Integer integer, Integer integer1) {
        System.out.println("search group By ID");
        return null;
    }

}
