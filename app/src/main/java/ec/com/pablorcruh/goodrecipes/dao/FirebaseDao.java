package ec.com.pablorcruh.goodrecipes.dao;

import ec.com.pablorcruh.goodrecipes.model.User;

public interface FirebaseDao {

    void login(User user);

    void registerNewUser(User user);

}
