package ec.com.pablorcruh.goodrecipes.dao;

public interface FirebaseDao {

    void login(String email, String password);

    void register(String email, String password);
}
