package com.example.zdroa.myapplication.activities.accountmanagement;

import android.text.Editable;
import android.text.TextWatcher;

public class LoginTextWatcher implements TextWatcher {

    private final AfterTextChangedAction afterTextChangedAction;

    public LoginTextWatcher(AfterTextChangedAction afterTextChangedAction) {
        this.afterTextChangedAction = afterTextChangedAction;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        afterTextChangedAction.doAction();
    }

    public interface AfterTextChangedAction {
        void doAction();
    }
}