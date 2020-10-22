package mengxi.blackjack_server;

import static org.junit.Assert.assertEquals;

import java.util.List;

public class TestUtils {
    public static <T> void assertListEquals(List<T> expect, List<T> actual) {
        assertEquals(expect.size(), actual.size());
        for (int i=0; i<expect.size(); i++) {
            assert(expect.get(i).equals(actual.get(i)));
        }
    }
}
