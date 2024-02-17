package com.example.monefy.basic.functionality.Interface.navigation;

import java.lang.reflect.InvocationTargetException;

public interface ClickListener {
    void clickBtnClose();
    void clickBtnSetUp() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException;
}
