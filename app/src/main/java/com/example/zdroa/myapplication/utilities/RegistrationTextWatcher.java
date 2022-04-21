package com.example.zdroa.myapplication.utilities;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class RegistrationTextWatcher implements TextWatcher {

    private final EditText mainEt;
    private final EditText otherEt;
    private final RegistrationValidator.Validator validator;
    private final RegistrationValidator.OnTextChangedAction onTextChangedAction;

    public RegistrationTextWatcher(EditText mainEt, EditText otherEt, RegistrationValidator.Validator validator, RegistrationValidator.OnTextChangedAction onTextChangedAction) {
        this.mainEt = mainEt;
        this.otherEt = otherEt;
        this.validator = validator;
        this.onTextChangedAction = onTextChangedAction;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable mainEditable) {
        String mainEtText = mainEditable.toString().trim();
        String errorMessage;
        if (otherEt == null) {
            errorMessage = validator.getErrorMessageOrNull(mainEtText);
        } else {
            errorMessage = validator.getErrorMessageOrNull(mainEtText, otherEt.getText().toString().trim());
        }
        onTextChangedAction.doAction(mainEt, errorMessage);
    }
}
