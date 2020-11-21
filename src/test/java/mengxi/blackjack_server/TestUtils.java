package mengxi.blackjack_server;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestUtils {
    public static <T> void assertListEquals(List<T> expect, List<T> actual) {
        assertEquals(expect.size(), actual.size());
        for (int i=0; i<expect.size(); i++) {
            assert(expect.get(i).equals(actual.get(i)));
        }
    }

    @SuppressWarnings("serial")
    public static void assertUniqueCards(List<String> a, List<String> b) {
        Set<String> s = new HashSet<>() {
            {
                for (String s : a)
                    this.add(s);
                for (String s : b)
                    this.add(s);
            }
        };

        assertEquals(a.size() + b.size(), s.size());
    }
}
