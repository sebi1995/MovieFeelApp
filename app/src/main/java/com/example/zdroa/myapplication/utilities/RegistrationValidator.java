package com.example.zdroa.myapplication.utilities;

import static com.example.zdroa.myapplication.utils.AppSettings.MAX_REGISTRATION_PASSWORD_LENGTH;
import static com.example.zdroa.myapplication.utils.AppSettings.MIN_REGISTRATION_PASSWORD_LENGTH;

import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.zdroa.myapplication.utils.MovieUtils;
import com.google.common.collect.ImmutableMap;

import java.time.OffsetDateTime;
import java.util.regex.Pattern;

public class RegistrationValidator {

    private static boolean isFirstnameValid = false;
    private static boolean isSurnameValid = false;
    private static boolean isEmailAddressValid = false;
    private static boolean isPasswordValid = false;
    private static boolean isDateOfBirthValid = false;

    private final OnTextChangedAction onTextChangedAction;

    public RegistrationValidator(OnTextChangedAction onTextChangedAction) {
        this.onTextChangedAction = onTextChangedAction;
    }

    private interface TextWatcherGetter<T> {
        T get(EditText editText1, EditText editText2);
    }

    private final ImmutableMap<Validator, TextWatcherGetter<TextWatcher>> WATCHERS_MAP = ImmutableMap.<Validator, TextWatcherGetter<TextWatcher>>builder()
            .put(Validator.FIRSTNAME, (firstnameEt, ignored) -> newTextWatcher(firstnameEt, Validator.FIRSTNAME))
            .put(Validator.SURNAME, (surnameEt, ignored) -> newTextWatcher(surnameEt, Validator.SURNAME))
            .put(Validator.DATE_OF_BIRTH, (dateOfBirthEt, ignored) -> newTextWatcher(dateOfBirthEt, Validator.DATE_OF_BIRTH))
            .put(Validator.EMAIL_ADDRESS, (emailAddressEt, ignored) -> newTextWatcher(emailAddressEt, Validator.EMAIL_ADDRESS))
            .put(Validator.PASSWORD, (passwordEt, emailAddressEt) -> newTextWatcher(passwordEt, emailAddressEt, Validator.PASSWORD))
            .build();

    public TextWatcher createTextWatcher(Validator validator, EditText... editTexts) {
        TextWatcherGetter<TextWatcher> textWatcherTextWatcherGetter = WATCHERS_MAP.get(validator);
        if (textWatcherTextWatcherGetter == null) {
            return null;
        }
        if (editTexts.length == 1) {
            return textWatcherTextWatcherGetter.get(editTexts[0], null);
        }
        if (editTexts.length == 2) {
            return textWatcherTextWatcherGetter.get(editTexts[0], editTexts[1]);
        }
        return null;
    }

    private TextWatcher newTextWatcher(EditText editText, Validator validator) {
        return newTextWatcher(editText, null, validator);
    }

    private TextWatcher newTextWatcher(EditText mainEt, EditText otherEt, Validator validator) {
        return new RegistrationTextWatcher(mainEt, otherEt, validator, onTextChangedAction);
    }

    public boolean isFirstnameValid() {
        return isFirstnameValid;
    }

    public boolean isSurnameValid() {
        return isSurnameValid;
    }

    public boolean isDateOfBirthValid() {
        return isDateOfBirthValid;
    }

    public boolean isEmailAddressValid() {
        return isEmailAddressValid;
    }

    public boolean isPasswordValid() {
        return isPasswordValid;
    }

    public enum Validator {
        FIRSTNAME {
            @Override
            public String getErrorMessageOrNull(String firstname) {
                if (firstname.isEmpty()) {
                    isFirstnameValid = false;
                    return "Please enter your surname.";
                }
                if (firstname.length() < 3) {
                    isFirstnameValid = false;
                    return "Firstname cannot be less than 3 characters.";
                }
                if (firstname.length() > 40) {
                    isFirstnameValid = false;
                    return "Firstname cannot be longer than 40 characters.";
                }
                isFirstnameValid = true;
                return null;
            }

            @Override
            public String getErrorMessageOrNull(String ignored, String otherIgnored) {
                isFirstnameValid = true;
                return null;
            }
        },
        SURNAME {
            @Override
            public String getErrorMessageOrNull(String surname) {
                if (surname.isEmpty()) {
                    isSurnameValid = false;
                    return "Please enter your surname.";
                }
                if (surname.length() < 3) {
                    isSurnameValid = false;
                    return "Surname cannot be less than 3 characters.";
                }
                if (surname.length() > 15) {
                    isSurnameValid = false;
                    return "Surname cannot be longer than 15 characters.";
                }
                isSurnameValid = true;
                return null;
            }

            @Override
            public String getErrorMessageOrNull(String ignored, String otherIgnored) {
                isSurnameValid = true;
                return null;
            }
        },
        DATE_OF_BIRTH {
            @Override
            public String getErrorMessageOrNull(String dateOfBirth) {
                if (dateOfBirth.isEmpty()) {
                    isDateOfBirthValid = false;
                    return "Please enter your date of birth.";
                }
                if (!MovieUtils.isUserOver18(OffsetDateTime.parse(dateOfBirth))) {
                    isDateOfBirthValid = false;
                    return "You must be over 18.";
                }
                isDateOfBirthValid = true;
                return null;
            }

            @Override
            public String getErrorMessageOrNull(String ignored, String otherIgnored) {
                isDateOfBirthValid = true;
                return null;
            }
        },
        EMAIL_ADDRESS {
            @Override
            public String getErrorMessageOrNull(String emailAddress) {
                if (emailAddress.length() < 6) {
                    isEmailAddressValid = false;
                    return "Email cannot be less than 6 characters.";
                }
                if (emailAddress.length() > 40) {
                    isEmailAddressValid = false;
                    return "Email cannot be more than 40 characters.";
                }
                if (!MovieUtils.isValidEmail(emailAddress)) {
                    isEmailAddressValid = false;
                    return "Please enter a valid email";
                }
                isEmailAddressValid = true;
                return null;
            }

            @Override
            public String getErrorMessageOrNull(String emailAddress, String otherEtStringValue) {
                isEmailAddressValid = true;
                return null;
            }
        },
        PASSWORD {
            @Override
            public String getErrorMessageOrNull(String password) {
                isPasswordValid = true;
                return null;
            }

            @Override
            public String getErrorMessageOrNull(String password, String emailAddress) {
                if (TextUtils.isEmpty(password)) {
                    isPasswordValid = false;
                    return "Password cannot be empty.";
                }

                boolean containsNumber = Pattern.compile("[0-9]").matcher(password).find();
                if (!containsNumber) {
                    isPasswordValid = false;
                    return "Password must contain at least 1 number.";
                }
                boolean containsSymbols = Pattern.compile("[^a-z0-9 ]").matcher(password).find();
                if (!containsSymbols) {
                    isPasswordValid = false;
                    return "Password must contain at least 1 symbol.";
                }
                if (password.length() < MIN_REGISTRATION_PASSWORD_LENGTH) {
                    isPasswordValid = false;
                    return "Password cannot be less than " + MIN_REGISTRATION_PASSWORD_LENGTH + " characters.";
                }
                if (password.length() > MAX_REGISTRATION_PASSWORD_LENGTH) {
                    isPasswordValid = false;
                    return "Password cannot be more than " + MAX_REGISTRATION_PASSWORD_LENGTH + " characters.";
                }
                if (emailAddress.startsWith(password) || emailAddress.contains(password) || emailAddress.endsWith(password)) {
                    isPasswordValid = false;
                    return "Password cannot be similar to email.";
                }
                isPasswordValid = true;
                return null;
            }
        };

        public abstract String getErrorMessageOrNull(String editTextValue);

        public abstract String getErrorMessageOrNull(String mainEtStringValue, String otherEtStringValue);
    }

    public interface OnTextChangedAction {
        void doAction(EditText editText, String errorMessage);
    }
}
