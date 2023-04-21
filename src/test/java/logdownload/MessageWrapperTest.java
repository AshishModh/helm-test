package logdownload;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class MessageWrapperTest {

    @Test
    public void test() {
        Message message = new Message("USER", "USER1", "DIR");
        MessageWrapper messageWrapper = new MessageWrapper<>(message);
        Assert.assertEquals(message, messageWrapper.getMessage());
    }

}