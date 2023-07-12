package org.example.userstoragespi;

import jodd.crypt.BCrypt;
import org.example.userstoragespi.domain.Branch;
import org.example.userstoragespi.domain.User;
import org.example.userstoragespi.repository.UserRepositoryImpl;
import org.example.userstoragespi.repository.interfaces.UserRepository;
import org.example.userstoragespi.services.UserServiceImpl;
import org.example.userstoragespi.services.interfaces.UserService;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.authenticators.directgrant.AbstractDirectGrantAuthenticator;
import org.keycloak.authentication.authenticators.directgrant.ValidatePassword;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.models.*;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.representations.idm.OAuth2ErrorRepresentation;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TosanAuthenticator implements Authenticator  {
    private static final String PROVIDER_ID = "tosan-direct-access-authenticator";
    private final UserService userService=new UserServiceImpl();

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        String username=this.retrieveUsername(context);
        String password = this.retrievePassword(context);
        String branch=this.retrieveBranch(context);
        if(username==null || username.isEmpty() || password==null || password.isEmpty()){
            context.getEvent().user(context.getUser());
            context.getEvent().error("invalid_user_credentials: username or password is not provided");
            Response challengeResponse = this.errorResponse(Response.Status.UNAUTHORIZED.getStatusCode(), "invalid_grant", "Invalid user credentials: username or password is not provided");
            context.failure(AuthenticationFlowError.INVALID_USER, challengeResponse);
            return;
        }
        User user=userService.getUserByUsername(username);
        List<UserModel> userModels=context.getSession().users().searchForUser(username,context.getRealm());


        if(user==null || userModels.isEmpty()){
            context.getEvent().user(context.getUser());
            context.getEvent().error("invalid_user_credentials: user not found ");
            Response challengeResponse = this.errorResponse(Response.Status.UNAUTHORIZED.getStatusCode(), "invalid_grant", "Invalid user credentials: user not found");
            context.failure(AuthenticationFlowError.INVALID_USER, challengeResponse);
            return;
        }
        String[] splitBranches=user.getUserInBranches()
                .stream().map(x->x.getBranch().getCode()).toArray(String[]::new);
        if(!Arrays.stream(splitBranches).anyMatch(x->x.equals(branch))){
            context.getEvent().user(context.getUser());
            context.getEvent().error("invalid_user_credentials: branch is invalid");
            Response challengeResponse = this.errorResponse(Response.Status.UNAUTHORIZED.getStatusCode(), "invalid_grant", "invalid_user_credentials: branch is invalid");
            context.failure(AuthenticationFlowError.INVALID_USER, challengeResponse);
            return;
        }
        if(!BCrypt.checkpw( username+password,user.getPassword())){
            context.getEvent().user(context.getUser());
            context.getEvent().error("invalid_user_credentials: password is invalid");
            Response challengeResponse = this.errorResponse(Response.Status.UNAUTHORIZED.getStatusCode(), "invalid_grant", "Invalid user credentials: password is invalid");
            context.failure(AuthenticationFlowError.INVALID_USER, challengeResponse);
            return;
        }
        context.setUser(userModels.get(0));
        context.success();


    }

    private String retrievePassword(AuthenticationFlowContext context) {
        MultivaluedMap<String, String> inputData = context.getHttpRequest().getDecodedFormParameters();
        return (String)inputData.getFirst("password");
    }

    @Override
    public void action(AuthenticationFlowContext authenticationFlowContext) {
        authenticationFlowContext.success();
    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {
        return false;
    }

    @Override
    public void setRequiredActions(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {

    }

    private String retrieveUsername(AuthenticationFlowContext context){
        MultivaluedMap<String, String> inputData = context.getHttpRequest().getDecodedFormParameters();
        return (String)inputData.getFirst("username");
    }
    private String retrieveBranch(AuthenticationFlowContext context){
        MultivaluedMap<String, String> inputData = context.getHttpRequest().getDecodedFormParameters();
        return (String)inputData.getFirst("branch");
    }

    @Override
    public void close() {

    }

    private Response errorResponse(int status, String error, String errorDescription) {
        OAuth2ErrorRepresentation errorRep = new OAuth2ErrorRepresentation(error, errorDescription);
        return Response.status(status).entity(errorRep).type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
