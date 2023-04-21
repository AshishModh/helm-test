package logdownload;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class MessageTest {

    @Test
    public void test(){
        Message message = new Message("USER", "USER1", "DIR");

        Assert.assertEquals("USER", message.getMemberId());
        Assert.assertEquals("USER1", message.getUserId());
        Assert.assertEquals("DIR", message.getActionType());

    }

}