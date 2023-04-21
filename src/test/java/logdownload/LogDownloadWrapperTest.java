package logdownload;

import com.google.common.collect.ImmutableList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class LogDownloadWrapperTest {

    @Test
    public void test(){
        LogDownload download = new LogDownload("DIR", "ABCD/XYZ", "03/23/2023  07:26 AM", "1234");
        Collection<LogDownload> list = new ArrayList();
        list.add(download);

       LogDownloadWrapper wrapper = new LogDownloadWrapper(list, "message", "loghuturl");

        Assert.assertEquals(list, wrapper.getButlerEntries());
        Assert.assertEquals("message", wrapper.getMessage());
        Assert.assertEquals("loghuturl", wrapper.getLogHutUrl());
    }

}