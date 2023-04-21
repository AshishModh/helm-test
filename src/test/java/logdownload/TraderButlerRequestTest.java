package logdownload;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TraderButlerRequestTest {

    @Test
    public void test(){
        TraderButlerRequest request = new TraderButlerRequest("DIR", "FILENAME", "USER", "USER1");

        Assert.assertEquals("DIR", request.getActionType());
        Assert.assertEquals("FILENAME", request.getFileName());
        Assert.assertEquals("USER", request.getMemberId());
        Assert.assertEquals("USER1", request.getUserId());
    }

}