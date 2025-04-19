package vn.ptithcm.shopapp.service;

import vn.ptithcm.shopapp.model.entity.Token;
import vn.ptithcm.shopapp.model.entity.User;

public interface IEmailService {
    String send(String to, String subject, String body);
    void sendVerifyMail(User user, Token token, String clientType);
}
