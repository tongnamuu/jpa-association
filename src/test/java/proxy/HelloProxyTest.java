package proxy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HelloProxyTest {
    private HelloTarget helloTarget;

    @BeforeEach
    void setUp() {
        this.helloTarget = HelloProxy.create();
    }

    @Test
    @DisplayName("sayHello uppercase test")
    void test_hello() {
        String result = helloTarget.sayHello("gg");
        assertThat(result).isEqualTo("HELLO GG");
    }

    @Test
    @DisplayName("sayHi uppercase test")
    void test_hi() {
        String result = helloTarget.sayHi("gg");
        assertThat(result).isEqualTo("HI GG");
    }

    @Test
    @DisplayName("sayThankYou uppercase test")
    void test_thank_you() {
        String result = helloTarget.sayThankYou("gg");
        assertThat(result).isEqualTo("THANK YOU GG");
    }
}
