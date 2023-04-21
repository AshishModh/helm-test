package logdownload;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@RunWith(MockitoJUnitRunner.class)
public class LogDownloadRequestTest {

    @Mock
    Request request;


    @Test(expected = Exception.class)
    public void invlidActionTypeTest() throws Exception {
        LogDownloadRequest test = new LogDownloadRequest(request, "xyz");
        test.validateRequestActionType();
    }

    @Test()
    public void validActionTypeTest() throws Exception {
        LogDownloadRequest test = new LogDownloadRequest(request, "DIR");
        test.validateRequestActionType();
    }

    @Test
    public void logDownloadRequestGetterTest() {
        Mockito.when(request.getMemberid()).thenReturn("USER");
        Mockito.when(request.getUserid()).thenReturn("USER1");
        Mockito.when(request.getFilename()).thenReturn("FILENAME");
        LogDownloadRequest test = new LogDownloadRequest(request, "DIR");
        Assert.assertEquals("USER", test.getMemberId());
        Assert.assertEquals("USER1", test.getUserId());
        Assert.assertEquals("FILENAME", test.getFileName());
        Assert.assertEquals("DIR", test.getActionType());
        Assert.assertEquals(false, test.getIsOMSI());
        Assert.assertEquals("USERUSER1DIR", test.getRequestKey());
        Assert.assertEquals("RemoteComponent_Traders_USER_USER1", test.getTopic());
    }

    @Test
    public void logDownloadRequestGetterIsOMSITest1() {
        Mockito.when(request.getMemberid()).thenReturn("USER");
        Mockito.when(request.getUserid()).thenReturn("");
        Mockito.when(request.getFilename()).thenReturn("FILENAME");
        LogDownloadRequest test = new LogDownloadRequest(request, "DIR");
        Assert.assertEquals("USER", test.getMemberId());
        Assert.assertEquals("", test.getUserId());
        Assert.assertEquals("FILENAME", test.getFileName());
        Assert.assertEquals("DIR", test.getActionType());
        Assert.assertEquals(true, test.getIsOMSI());
        Assert.assertEquals("USERDIR", test.getRequestKey());
        Assert.assertEquals("RemoteComponent_Butler_USER", test.getTopic());
    }

    @Test
    public void logDownloadRequestGetterIsOMSITest2() {
        Mockito.when(request.getMemberid()).thenReturn("USER");
        Mockito.when(request.getUserid()).thenReturn("OMSI");
        Mockito.when(request.getFilename()).thenReturn("FILENAME");
        LogDownloadRequest test = new LogDownloadRequest(request, "GET");
        Assert.assertEquals("USER", test.getMemberId());
        Assert.assertEquals("", test.getUserId());
        Assert.assertEquals("FILENAME", test.getFileName());
        Assert.assertEquals("GET", test.getActionType());
        Assert.assertEquals(true, test.getIsOMSI());
        Assert.assertEquals("USERGET", test.getRequestKey());
        Assert.assertEquals("RemoteComponent_Butler_USER", test.getTopic());
    }

    @Test
    public void logDownloadRequestWithSpaceGetterTest() {
        Mockito.when(request.getMemberid()).thenReturn(" U SE R ");
        Mockito.when(request.getUserid()).thenReturn("USER 1");
        Mockito.when(request.getFilename()).thenReturn("FILE NAME");
        LogDownloadRequest classToTest = new LogDownloadRequest(request, "DIR");
        Assert.assertEquals(" U SE R ", classToTest.getMemberId());
        Assert.assertEquals("USER 1", classToTest.getUserId());
        Assert.assertEquals("FILE NAME", classToTest.getFileName());
        Assert.assertEquals("DIR", classToTest.getActionType());
        Assert.assertEquals(false, classToTest.getIsOMSI());
        Assert.assertEquals("USERUSER1DIR", classToTest.getRequestKey());
        Assert.assertEquals("RemoteComponent_Traders_USER_USER1", classToTest.getTopic());
    }

    @Test
    public void logDownloadRequestWithSpaceGetterIsOMSITest1() {
        Mockito.when(request.getMemberid()).thenReturn("U SE R");
        Mockito.when(request.getUserid()).thenReturn("");
        Mockito.when(request.getFilename()).thenReturn("FILE NAME");
        LogDownloadRequest classToTest = new LogDownloadRequest(request, "DIR");
        Assert.assertEquals("U SE R", classToTest.getMemberId());
        Assert.assertEquals("", classToTest.getUserId());
        Assert.assertEquals("FILE NAME", classToTest.getFileName());
        Assert.assertEquals("DIR", classToTest.getActionType());
        Assert.assertEquals(true, classToTest.getIsOMSI());
        Assert.assertEquals("USERDIR", classToTest.getRequestKey());
        Assert.assertEquals("RemoteComponent_Butler_USER", classToTest.getTopic());
    }

    @Test
    public void logDownloadRequestWithSpaceGetterIsOMSITest2() {
        Mockito.when(request.getMemberid()).thenReturn("U SE R");
        Mockito.when(request.getUserid()).thenReturn("OMSI");
        Mockito.when(request.getFilename()).thenReturn("FILE NAME");
        LogDownloadRequest classToTest = new LogDownloadRequest(request, "DIR");
        Assert.assertEquals("U SE R", classToTest.getMemberId());
        Assert.assertEquals("", classToTest.getUserId());
        Assert.assertEquals("FILE NAME", classToTest.getFileName());
        Assert.assertEquals("DIR", classToTest.getActionType());
        Assert.assertEquals(true, classToTest.getIsOMSI());
        Assert.assertEquals("USERDIR", classToTest.getRequestKey());
        Assert.assertEquals("RemoteComponent_Butler_USER", classToTest.getTopic());
    }


}