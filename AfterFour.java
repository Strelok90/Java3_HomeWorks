import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AfterFour {

    ArrayMethod arrayMethod;

    @Before
    public void init() {
        arrayMethod = new ArrayMethod();
    }

    @Test
    public void test1() {
        Assert.assertArrayEquals(new int[]{1, 7}, arrayMethod.returnNewArray(new int[]{1, 2, 4, 4, 2, 3 ,4 ,1, 7}));
    }

    @Test
    public void test2() {
        Assert.assertArrayEquals(new int[]{2}, arrayMethod.returnNewArray(new int[]{4, 2}));
    }

    @Test
    public void test3() {
        Assert.assertArrayEquals(new int[0], arrayMethod.returnNewArray(new int[]{1, 4}));
    }

    @Test
    public void test4() {
        Assert.assertArrayEquals(new int[]{44, 2, 8}, arrayMethod.returnNewArray(new int[]{1, 2, 4, 4, 2, 3 ,4 ,1, 7, 4, 4, 4 , 4 ,4 ,4 ,4 , 44, 2, 8}));
    }

    @Test(expected = RuntimeException.class)
    public void test5() {
        Assert.assertArrayEquals(new int[]{1, 7}, arrayMethod.returnNewArray(new int[]{1, 2}));
    }

}