package logdownload;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class RequestTest {

    @Test
    public void test(){
        Request test = new Request("USER", "USER1", "FILENAME", "ABC");

        Assert.assertEquals("USER", test.getMemberid());
        Assert.assertEquals("USER1", test.getUserid());
        Assert.assertEquals("FILENAME", test.getFilename());
        Assert.assertEquals("ABC", test.getLoghutUrl());
    }
}