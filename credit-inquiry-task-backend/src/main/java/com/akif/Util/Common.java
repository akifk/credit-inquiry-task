package com.akif.Util;

import com.akif.dto.CustomerDto;
import com.akif.entity.Customer;
import com.akif.enums.CreditStatusEnum;

public  class Common {
    private static double creditScore = 750;
    public static boolean checkCustomerInfo(CustomerDto customerDto) {
        if (Util.getDecimal(customerDto.getIdentityNumber()) != 11) {
            return false;
        } else if (customerDto.getName().length() > 15) {
            return false;
        } else if (customerDto.getSurName().length() > 15) {
            return false;
        } else if (Util.getDecimal(customerDto.getTelephone()) != 10) {
            return false;
        } else if (customerDto.getMonthlyIncome() < 0) {
            return false;
        }
        return true;
    }
    public static void sendSms(Customer customer, CreditStatusEnum creditStatus, double creditAmount) {
        Lggr.getInstance().info("sms sent to " + customer.getTelephone());
    }
    public static double getCreditScore(Customer customer) {
        return Common.creditScore;
    }
    public static void setCreditScore(double creditScore) {
        Common.creditScore = creditScore;
    }
    public static double getCreditLimitMultiplier() {
        return 4;
    }
}
