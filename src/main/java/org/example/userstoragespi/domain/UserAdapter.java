package org.example.userstoragespi.domain;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.*;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.adapter.AbstractUserAdapter;

import javax.ws.rs.core.MultivaluedHashMap;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserAdapter extends AbstractUserAdapter.Streams {
    private User user;
    private final RealmModel realm;

    public UserAdapter(KeycloakSession session, RealmModel realm, ComponentModel model, User user) {
        super(session, realm, model);
        this.storageId = new StorageId(storageProviderModel.getId(), user.getUsername());
        this.user = user;
        this.realm=realm;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getFirstName() {
        return user.getName();
    }

    @Override
    public String getLastName() {
        return user.getName();
    }

    @Override
    public String getEmail() {
        return  user.getName();
    }

//    @Override
//    public SubjectCredentialManager credentialManager() {
//        return new LegacyUserCredentialManager(session, realm, this);
//    }

    @Override
    public String getFirstAttribute(String name) {
        List<String> list = getAttributes().getOrDefault(name, new ArrayList<>());
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public Map<String, List<String>> getAttributes() {
        MultivaluedHashMap<String, String> attributes = new MultivaluedHashMap<>();
        attributes.add(UserModel.USERNAME, getUsername());
        attributes.add(UserModel.EMAIL, getEmail());
        attributes.add(UserModel.FIRST_NAME, getFirstName());
        attributes.add(UserModel.LAST_NAME, getLastName());
        attributes.add("birthday", "1403");
        attributes.add("gender", "man");
        attributes.add("branch",getBranchs());
        attributes.add("password",user.getPassword());
        return attributes;
    }
    private String getBranchs(){
        return user.getUserInBranches().stream()
                .map(x->x.getBranch())
                .map(y->y.getCode()).collect(Collectors.joining(","));
    }


    @Override
    public Stream<String> getAttributeStream(String name) {
        Map<String, List<String>> attributes = getAttributes();
        return (attributes.containsKey(name)) ? attributes.get(name).stream() : Stream.empty();
    }

    @Override
    protected Set<GroupModel> getGroupsInternal() {
        Set<GroupModel> vl=new HashSet<>();
        if (user.getUserGroups() != null) {
             user.getUserGroups().stream().forEach(x->{
                GroupModel gm=new UserGroupModel();
                gm.setName(x.getName());
                vl.add(gm);
            });
            return vl;
        }
        return null;
    }

//    @Override
//    protected Set<RoleModel> getRoleMappingsInternal() {
//        if (user.getRoles() != null) {
//            return user.getRoles().stream().map(roleName -> new UserRoleModel(roleName, realm)).collect(Collectors.toSet());
//        }
//        return Set.of();
//    }
    @Override
    protected Set<RoleModel> getRoleMappingsInternal() {
        Set<RoleModel> vl=new HashSet<>();
        if (user.getUserGroups() != null) {
            user.getUserGroups().stream().forEach(x->{
                RoleModel roleModel=new UserRoleModel(realm);
                roleModel.setName(x.getName());
                vl.add(roleModel);
            });
            return vl;
        }
        return null;
    }
    public String getPassword(){
        return user.getPassword();
    }
//
//    @Override
//    public Set<RoleModel> getRealmRoleMappings() {
//        return getRoleMappingsInternal();
//    }
//
//    @Override
//    public Stream<RoleModel> getRealmRoleMappingsStream() {
//        return getRoleMappingsInternal().stream();
//    }
//
//    @Override
//    public Set<RoleModel> getRoleMappings() {
//        return getRoleMappingsInternal();
//    }
//
//    @Override
//    public Stream<RoleModel> getRoleMappingsStream() {
//        return getRoleMappingsInternal().stream();
//    }
}
