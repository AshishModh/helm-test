package logdownload;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class OMSIButlerResponseTest {


    @Test
    public void test() {
        OMSIButlerResponse response = new OMSIButlerResponse("USER", "USER1", "DIR");

        Assert.assertEquals("USER", response.getMemberId());
        Assert.assertEquals("USER1", response.getUserId());
        Assert.assertEquals("DIR", response.getActionType());
    }

    @Test
    public void test1() {
        OMSIButlerResponse response = new OMSIButlerResponse("USER", "USER1", "DIR");
        List<LogDownload> list = response.getButlerFileEntriesList();
        Assert.assertEquals(1, list.size());
    }


}