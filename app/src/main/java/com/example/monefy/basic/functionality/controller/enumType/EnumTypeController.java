package com.example.monefy.basic.functionality.controller.enumType;

import com.example.monefy.basic.functionality.Interface.dialogModal.TypeSelectModal;
import com.example.monefy.basic.functionality.model.currency.TypeCurrency;
import com.example.monefy.basic.functionality.model.billings.TypeBillings;
import com.example.monefy.basic.functionality.model.billings.TypeDebtorSide;
import com.example.monefy.basic.functionality.model.income.TypeFrequency;
import com.example.monefy.basic.functionality.model.income.TypeIncomes;

import java.util.ArrayList;
import java.util.List;

public class EnumTypeController {

    private static final List<TypeSelectModal> types = new ArrayList<>();

    public static List<TypeSelectModal> getTypesBillings(){
        types.clear();
        types.add(new TypeSelectModal() {
            @Override
            public List<TypeBillings> getTypeBillings() {
                return TypeSelectModal.super.getTypeBillings();
            }
        });
        return types;
    }

    public static List<TypeSelectModal> getTypeCurrency(){
        types.clear();
        types.add(new TypeSelectModal() {
            @Override
            public List<TypeCurrency> getTypeCurrency() {
                return TypeSelectModal.super.getTypeCurrency();
            }
        });
        return types;
    }

    public static List<TypeSelectModal> getTypeDebtor(){
        types.clear();
        types.add(new TypeSelectModal() {
            @Override
            public List<TypeDebtorSide> getTypeDebtorSide() {
                return TypeSelectModal.super.getTypeDebtorSide();
            }
        });
        return types;
    }

    public static List<TypeSelectModal> getTypeFrequency(){
        types.clear();
        types.add(new TypeSelectModal() {
            @Override
            public List<TypeFrequency> getTypeFrequency() {
                return TypeSelectModal.super.getTypeFrequency();
            }
        });
        return types;
    }

    public static List<TypeSelectModal> getTypeIncomes(){
        types.clear();
        types.add(new TypeSelectModal() {
            @Override
            public List<TypeIncomes> getTypeIncomes() {
                return TypeSelectModal.super.getTypeIncomes();
            }
        });
        return types;
    }
}
