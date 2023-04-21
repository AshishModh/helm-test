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
public class LogDownloadTest {

    @Test
    public void test(){
        LogDownload download = new LogDownload("DIR", "ABCD/XYZ", "03/23/2023  07:26 AM", "1024" );
        Assert.assertEquals("DIR", download.getType());
        Assert.assertEquals("ABCD/XYZ", download.getFilePath());
        Assert.assertEquals("03/23/2023  07:26 AM", download.getDateTimeModified());
        Assert.assertEquals("1024", download.getSize());
    }

}