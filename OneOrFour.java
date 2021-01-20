import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class OneOrFour {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new int[]{1, 4, 1, 4, 1, 4 ,1 ,4, 1}},
                {new int[]{4, 1}},
                {new int[]{2, 4}},
                {new int[]{1, 2, 4, 4, 2, 3 ,4 ,1, 7, 8}}
        });
    }
    private final int[] a;

    public OneOrFour(int[] a) {
        this.a = a;
    }

    ArrayMethod arrayMethod;

    @Before
    public void init() {
        arrayMethod = new ArrayMethod();
    }

    @Test
    public void test1() {
        Assert.assertTrue(arrayMethod.isOneOrFour(a));
    }

}
