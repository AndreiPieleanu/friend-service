package s6.friendservice.servicelayer.token;


import s6.friendservice.configuration.AccessToken;

public interface IAccessTokenEncoder {
    String encode(AccessToken accessToken);
}
