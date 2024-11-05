package s6.friendservice.servicelayer.token;


import s6.friendservice.configuration.AccessToken;

public interface IAccessTokenDecoder {
    AccessToken decode(String accessTokenEncoded);
}
