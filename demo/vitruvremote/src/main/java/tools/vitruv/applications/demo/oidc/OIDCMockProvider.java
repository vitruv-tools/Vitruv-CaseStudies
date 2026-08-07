package tools.vitruv.applications.demo.oidc;

import java.net.URI;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimNames;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.oauth2.sdk.AuthorizationCode;
import com.nimbusds.oauth2.sdk.AuthorizationRequest;
import com.nimbusds.oauth2.sdk.GrantType;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.ResponseType;
import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.id.Issuer;
import com.nimbusds.oauth2.sdk.token.BearerAccessToken;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import com.nimbusds.openid.connect.sdk.LogoutRequest;
import com.nimbusds.openid.connect.sdk.SubjectType;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;
import com.nimbusds.openid.connect.sdk.token.OIDCTokens;

import tools.vitruv.remote.seccommon.cert.CertificateGenerator;

public class OIDCMockProvider {
    public static final String OIDC_JKWS_PATH = "/jkws";
    public static final String OIDC_AUTHORIZATION_PATH = "/auth";
    public static final String OIDC_TOKEN_PATH = "/token";
    public static final String OIDC_INFO_PATH = "/info";
    public static final String OIDC_LOGOUT_PATH = "/logout";

    public static record OIDCMockProviderConfiguration(
        String baseUri,
        Path keyStorePath,
        String keyStorePassword,
        String keyStoreAlias
    ) {}

    private OIDCProviderMetadata metadata;
    private JWKSet jwkSet;
    private OIDCTokens tokens;

    public void initialize(OIDCMockProviderConfiguration config) throws Exception {
        var scope = new Scope("openid", "profile", "email");

        metadata = new OIDCProviderMetadata(
            new Issuer(config.baseUri()),
            List.of(SubjectType.PUBLIC, SubjectType.PAIRWISE),
            URI.create(config.baseUri() + OIDC_JKWS_PATH)
        );
        metadata.applyDefaults();
        metadata.setGrantTypes(List.of(GrantType.AUTHORIZATION_CODE, GrantType.REFRESH_TOKEN));
        metadata.setResponseTypes(List.of(ResponseType.CODE));
        metadata.setScopes(scope);
        metadata.setIDTokenJWSAlgs(List.of(JWSAlgorithm.RS256));
        metadata.setAuthorizationEndpointURI(URI.create(config.baseUri() + OIDC_AUTHORIZATION_PATH));
        metadata.setTokenEndpointURI(URI.create(config.baseUri() + OIDC_TOKEN_PATH));
        metadata.setUserInfoEndpointURI(URI.create(config.baseUri() + OIDC_INFO_PATH));
        metadata.setEndSessionEndpointURI(URI.create(config.baseUri() + OIDC_LOGOUT_PATH));

        var keyStore = CertificateGenerator.openKeyStore(config.keyStorePath(), config.keyStorePassword());
        var signingKey = RSAKey.load(keyStore, config.keyStoreAlias(), config.keyStorePassword().toCharArray());
        jwkSet = new JWKSet(signingKey.toPublicJWK());
        
        var tokenExpiryTime = 60 * 60 * 12;
        var header = new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(signingKey.getKeyID()).build();
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
            .issuer(config.baseUri())
            .audience("42")
            .claim("email", "vitruv@localhost")
            .expirationTime(new Date(System.currentTimeMillis() + tokenExpiryTime * 1000))
            .build();
        SignedJWT idToken = new SignedJWT(header, claims);
        JWSSigner signer = new RSASSASigner(signingKey);
        idToken.sign(signer);
        
        tokens = new OIDCTokens(idToken, new BearerAccessToken(tokenExpiryTime, scope), new RefreshToken());
    }

    public String getProviderMetadata() {
        return metadata.toJSONObject().toJSONString();
    }

    public String getRedirectUriForAuthRequest(String httpRequestQuery) throws ParseException {
        AuthorizationRequest authRequest = AuthorizationRequest.parse(httpRequestQuery);
        AuthorizationCode authCode = new AuthorizationCode();
        return authRequest.getRedirectionURI() + "?code=" + authCode.getValue() + "&state=" + authRequest.getState();
    }

    public String getRedirectUriForLogout(String httpRequestQuery) throws ParseException {
        LogoutRequest logoutRequest = LogoutRequest.parse(httpRequestQuery);
        return logoutRequest.getPostLogoutRedirectionURI().toString();
    }

    public String getTokens() {
        return this.tokens
            .toJSONObject()
            .appendField(JWTClaimNames.EXPIRATION_TIME, this.tokens.getAccessToken().getLifetime())
            .toJSONString();
    }

    public String getJwkSet() {
        return this.jwkSet.toString();
    }
}
