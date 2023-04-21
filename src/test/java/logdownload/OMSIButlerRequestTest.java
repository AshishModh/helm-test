package logdownload;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class OMSIButlerRequestTest {

    @Test
    public void test() {
        OMSIButlerRequest request = new OMSIButlerRequest("DIR", "FILENAME", "USER", "USER1", 1);

        Assert.assertEquals("DIR", request.getActionType());
        Assert.assertEquals("FILENAME", request.getFileName());
        Assert.assertEquals("USER", request.getMemberId());
        Assert.assertEquals("USER1", request.getUserId());
        Assert.assertEquals(1, request.getResendFromStart());
    }

    @Test
    public void test1() {
        TraderButlerResponse response = new TraderButlerResponse("USER", "USER1", "DIR");
        List<LogDownload> str = response.getButlerFileEntriesList();
        Assert.assertEquals(1, str.size());


    }

}