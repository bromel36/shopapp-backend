package vn.ptithcm.shopapp.service;

import vn.ptithcm.shopapp.model.response.ChatResponse;

import java.util.Map;

public interface IChatService {

    ChatResponse handleMessage(String message);
}
