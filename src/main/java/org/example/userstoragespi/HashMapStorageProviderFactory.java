package org.example.userstoragespi;

//import org.keycloak.Config;
import org.example.userstoragespi.domain.UserGroup;
import org.example.userstoragespi.repository.UserGroupRepositoryImpl;
import org.example.userstoragespi.repository.interfaces.UserGroupRepository;
import org.keycloak.Config;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.services.resources.admin.GroupResource;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.UserStorageProviderFactory;

import java.util.List;

public class HashMapStorageProviderFactory implements  UserStorageProviderFactory<HashMapStorageProvider> {

    public static final String PROVIDER_NAME = "hashmap-user-store";


    UserGroupRepository userGroupRepository;

    @Override
    public UserStorageProvider create(KeycloakSession session) {
        return new HashMapStorageProvider(session, new ComponentModel());
    }

    @Override
    public HashMapStorageProvider create(KeycloakSession session, ComponentModel model) {
        return new HashMapStorageProvider(session, model);
    }


    @Override
    public String getId() {
        return PROVIDER_NAME;
    }
    @Override
    public void close() {
        System.out.println("close");
    }

    @Override
    public void init(Config.Scope config) {
        System.out.println("init ... ");
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        System.out.println("post init ...");
    }
    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return ProviderConfigurationBuilder.create()
                .property("myParam", "My Param", "Some Description", ProviderConfigProperty.STRING_TYPE, "some value", null)
                .build();
    }

    @Override
    public String getHelpText() {
        return "UserStorageProviderFactory.super.getHelpText();";
    }
}