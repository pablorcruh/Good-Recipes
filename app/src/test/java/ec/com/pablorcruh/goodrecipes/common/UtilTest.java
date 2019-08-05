package ec.com.pablorcruh.goodrecipes.common;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class UtilTest {

    private Util SUT;
    private String email;
    private String password;
    private boolean result;
    private String confirmPassword;

    @Before
    public void setUp() throws Exception {
        SUT = new Util();
    }

    @Test
    public void util_emptyEmail_returnTrue(){
        email= "";
        result= SUT.isFieldEmpty(email);
        assertThat(result, is(true));
    }

    @Test
    public void util_notEmptyEmail_returnFalse(){
        email="pablo";
        result = SUT.isFieldEmpty(email);
        assertThat(result, is(false));
    }

    @Test
    public void util_validEmail_returnTrue(){
        email="pablo@test.com";
        result = SUT.isValidEmail(email);
        assertThat(result, is(true));
    }

    @Test
    public void util_emailInvalidFormatNotAt_returnFalse(){
        email="pablo.com";
        result = SUT.isValidEmail(email);
        assertThat(result, is(false));
    }

    @Test
    public void util_emailInvalidFormatNotDot_returnFalse(){
        email="pablo@test";
        result = SUT.isValidEmail(email);
        assertThat(result, is(false));
    }

    @Test
    public void util_invalidEmail_returnFalse(){
        email="prwer";
        result = SUT.isValidEmail(email);
        assertThat(result, is(false));
    }

    @Test
    public void util_passwordEmpty_returnTrue(){
        password = "";
        result = SUT.isPasswordEmpty(password);
        assertThat(result, is(true));
    }

    @Test
    public void util_shortPassword_returnFalse(){
        password="123";
        result = SUT.isPasswordLengthValid(password);
        assertThat(result,is(false));
    }

    @Test
    public void util_LongPassword_returnTrue(){
        password="12345678";
        result = SUT.isPasswordLengthValid(password);
        assertThat(result,is(true));
    }

    @Test
    public void util_confirmPasswordEqualsPassword_returnTrue(){
        confirmPassword="12345678";
        password= "12345678";
        result = SUT.isConfirmationPasswordSamePassword(password, confirmPassword);
        assertThat(result, is(true));
    }


    @Test
    public void util_confirmPasswordNotEqualsPassword_returnFalse(){
        confirmPassword="123";
        password= "12345678";
        result = SUT.isConfirmationPasswordSamePassword(password, confirmPassword);
        assertThat(result, is(false));
    }

}