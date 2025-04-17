package vn.ptithcm.shopapp.service;

import vn.ptithcm.shopapp.enums.TokenTypeEnum;

public interface IEmailService {
    String send(String to, String subject, String body);
    void sendVerifyMail(String to, String subject, String name, String verifyToken, TokenTypeEnum type);
}
