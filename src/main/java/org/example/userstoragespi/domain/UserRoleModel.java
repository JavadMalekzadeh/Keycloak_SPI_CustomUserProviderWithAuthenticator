package org.example.userstoragespi.domain;

import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleContainerModel;
import org.keycloak.models.RoleModel;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class UserRoleModel implements RoleModel {
    private String name="default";
    private final RealmModel realm;
    public UserRoleModel(RealmModel realm) {
        this.realm=realm;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void setDescription(String s) {

    }

    @Override
    public String getId() {
        return getName();
    }

    @Override
    public void setName(String s) {
        name=s;
    }

    @Override
    public boolean isComposite() {
        return false;
    }

    @Override
    public void addCompositeRole(RoleModel roleModel) {

    }

    @Override
    public void removeCompositeRole(RoleModel roleModel) {

    }

    @Override
    public Stream<RoleModel> getCompositesStream() {
        return null;
    }

    @Override
    public boolean isClientRole() {
        return true;
    }

    @Override
    public String getContainerId() {
        return null;
    }

    @Override
    public RoleContainerModel getContainer() {
        return realm;
    }

    @Override
    public boolean hasRole(RoleModel roleModel) {
        return true;
    }

    @Override
    public void setSingleAttribute(String s, String s1) {

    }

    @Override
    public void setAttribute(String s, List<String> list) {

    }

    @Override
    public void removeAttribute(String s) {

    }

    @Override
    public Stream<String> getAttributeStream(String s) {
        return null;
    }

    @Override
    public Map<String, List<String>> getAttributes() {
        return null;
    }
}
