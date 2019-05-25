package io.github.itliwei.mvcorm;


import io.github.itliwei.mvcorm.orm.opt.QueryModel;

/**
 * @author : liwei
 * @date : 2019/05/23 21:10
 * @description : ${TODO}
 */
public class UserQueryModel extends QueryModel {

    private String usernameEQ;

    public String getUsernameEQ() {
        return usernameEQ;
    }

    public void setUsernameEQ(String usernameEQ) {
        this.usernameEQ = usernameEQ;
    }
}
