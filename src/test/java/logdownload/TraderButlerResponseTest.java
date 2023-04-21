package logdownload;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TraderButlerResponseTest {

    @Test
    public void test(){
        OMSIButlerResponse response = new OMSIButlerResponse("USER", "USER1", "DIR");

        Assert.assertEquals("USER", response.getMemberId());
        Assert.assertEquals("USER1", response.getUserId());
        Assert.assertEquals("DIR", response.getActionType());
    }

}