package com.example.qi.demo_rxjava2_learning.Bean;

import java.util.List;

public class UserPlus {

    public String useName;

    //一个人可能有多个地址：工作/家庭/户籍
    public List<Address> addresses;

    @Override
    public String toString() {
        return "UserPlus{" +
                "useName='" + useName + '\'' +
                ", addresses=" + addresses.toString() +
                '}';
    }
}
