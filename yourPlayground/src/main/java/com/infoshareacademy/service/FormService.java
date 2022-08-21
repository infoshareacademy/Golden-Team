package com.infoshareacademy.service;

import com.infoshareacademy.entity.Game;

import java.io.IOException;

public interface FormService {
    Game createForm();
    void closeForm(Game game) throws IOException;
}