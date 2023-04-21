package logdownload;

import org.apache.spark.internal.config.R;
import org.awaitility.Duration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Clock;
import java.util.concurrent.*;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class LogDownloadServiceTest {

    String requestKey = "USERUSER1DIR";
    ConcurrentMap<String, BlockingQueue<Message>> requestIdToResponseQueueMap = new ConcurrentHashMap<>();

    @Mock
    IProtoService protoService;


    @Test
    public void butlerProcessDirTest() {
        LogDownloadService service = new LogDownloadService(requestIdToResponseQueueMap, protoService, 1000, "", Clock.systemDefaultZone());
        Request request = new Request("USER", "USER1", "FILENAME", "");
        LogDownloadRequest downloadRequest = new LogDownloadRequest(request, "DIR");
        LogDownloadWrapper wrapper = service.butlerProcess(downloadRequest);
        Assert.assertEquals(0, wrapper.getButlerEntries().size());
        Assert.assertEquals(LogDownloadService.TIMEOUT_MESSAGE_ERROR, wrapper.getMessage());
    }

    @Test
    public void butlerProcessGetTest() {
        LogDownloadService service = new LogDownloadService(requestIdToResponseQueueMap, protoService, 1000, "", Clock.systemDefaultZone());
        Request request = new Request("USER", "USER1", "FILENAME", "");
        LogDownloadRequest downloadRequest = new LogDownloadRequest(request, "GET");
        LogDownloadWrapper wrapper = service.butlerProcess(downloadRequest);
        Assert.assertEquals(0, wrapper.getButlerEntries().size());
        Assert.assertEquals(LogDownloadService.TIMEOUT_MESSAGE_ERROR, wrapper.getMessage());
    }

    @Test
    public void butlerProcessIsOMSIDirTest() {
        LogDownloadService service = new LogDownloadService(requestIdToResponseQueueMap, protoService, 1000, "", Clock.systemDefaultZone());
        Request request = new Request("USER", "", "FILENAME", "");
        LogDownloadRequest downloadRequest = new LogDownloadRequest(request, "DIR");
        LogDownloadWrapper wrapper = service.butlerProcess(downloadRequest);
        Assert.assertEquals(0, wrapper.getButlerEntries().size());
        Assert.assertEquals(LogDownloadService.TIMEOUT_MESSAGE_ERROR, wrapper.getMessage());
    }

    @Test
    public void butlerProcessIsOMSIGetTest() {
        LogDownloadService service = new LogDownloadService(requestIdToResponseQueueMap, protoService, 1000, "", Clock.systemDefaultZone());
        Request request = new Request("USER", "OMSI", "FILENAME", "");
        LogDownloadRequest downloadRequest = new LogDownloadRequest(request, "GET");
        LogDownloadWrapper wrapper = service.butlerProcess(downloadRequest);
        Assert.assertEquals(0, wrapper.getButlerEntries().size());
        Assert.assertEquals(LogDownloadService.TIMEOUT_MESSAGE_ERROR, wrapper.getMessage());
    }

    @Test
    public void butlerProcessDirRequestKeyTest() {
        requestIdToResponseQueueMap.put(requestKey, new LinkedBlockingQueue<>());
        LogDownloadService service = new LogDownloadService(requestIdToResponseQueueMap, protoService, 1000, "", Clock.systemDefaultZone());
        Request request = new Request("USER", "USER1", "FILENAME", "");
        LogDownloadRequest downloadRequest = new LogDownloadRequest(request, "DIR");
        LogDownloadWrapper wrapper = service.butlerProcess(downloadRequest);
        Assert.assertEquals(0, wrapper.getButlerEntries().size());
        Assert.assertTrue(requestIdToResponseQueueMap.containsKey(downloadRequest.getRequestKey()));
        Assert.assertEquals(LogDownloadService.YOUR_REQUEST_CANNOT_BE_FULFILLED_MESSAGE_ERROR, wrapper.getMessage());

    }

    @Test()
    public void InvalidActionTypeTest() {
        requestIdToResponseQueueMap.put(requestKey, new LinkedBlockingQueue<>());
        LogDownloadService service = new LogDownloadService(requestIdToResponseQueueMap, protoService, 1000, "", Clock.systemDefaultZone());
        Request request = new Request("USER", "USER1", "FILENAME", "");
        LogDownloadRequest downloadRequest = new LogDownloadRequest(request, "Invalid");
        LogDownloadWrapper wrapper = service.butlerProcess(downloadRequest);
        Assert.assertEquals(0, wrapper.getButlerEntries().size());
        Assert.assertFalse(requestIdToResponseQueueMap.containsKey(downloadRequest.getRequestKey()));
        Assert.assertEquals(LogDownloadService.UNABLE_TO_STORE_LOG_FILE_IN_DIRECTORY_MESSAGE_ERROR, wrapper.getMessage());
    }


    @Test
    public void traderCallBackDirTest() {
        LogDownloadService service = new LogDownloadService(requestIdToResponseQueueMap, protoService, 1000, "", Clock.systemDefaultZone());
        Request request1 = new Request("USER", "USER1", "FILENAME", "");
        LogDownloadRequest downloadRequest = new LogDownloadRequest(request1, "DIR");
        OnMessageTraderResponse onMessageTraderResponse = new OnMessageTraderResponse(new LogDownloadCallbacksService(service), downloadRequest);
        onMessageTraderResponse.start();
        LogDownloadWrapper wrapper = service.butlerProcess(downloadRequest);
        Assert.assertEquals(1, wrapper.getButlerEntries().size());
        Assert.assertEquals("", wrapper.getMessage());
        Assert.assertEquals("1024", wrapper.getButlerEntries().get(0).getSize());
        Assert.assertEquals("03/23/2023  07:26 AM", wrapper.getButlerEntries().get(0).getDateTimeModified());
        Assert.assertEquals("ABCD/BCD", wrapper.getButlerEntries().get(0).getFilePath());
        Assert.assertEquals("FILE", wrapper.getButlerEntries().get(0).getType());
        Assert.assertFalse(requestIdToResponseQueueMap.containsKey(downloadRequest.getRequestKey()));
    }

    @Test
    public void OmsiCallBackDirTest() {
        LogDownloadService service = new LogDownloadService(requestIdToResponseQueueMap, protoService, 5000, "", Clock.systemDefaultZone());
        Request request1 = new Request("USER", "OMSI", "FILENAME", "");
        LogDownloadRequest downloadRequest = new LogDownloadRequest(request1, "DIR");
        OnMessageOmsiResponse onMessageOmsiResponse = new OnMessageOmsiResponse(new LogDownloadCallbacksService(service), downloadRequest);
        onMessageOmsiResponse.start();
        LogDownloadWrapper wrapper = service.butlerProcess(downloadRequest);
        Assert.assertEquals(1, wrapper.getButlerEntries().size());
        Assert.assertEquals("", wrapper.getMessage());
        Assert.assertEquals("1234", wrapper.getButlerEntries().get(0).getSize());
        Assert.assertEquals("03/23/2023  07:26 AM", wrapper.getButlerEntries().get(0).getDateTimeModified());
        Assert.assertEquals("ABCD/XYZ", wrapper.getButlerEntries().get(0).getFilePath());
        Assert.assertEquals("FILE", wrapper.getButlerEntries().get(0).getType());
        Assert.assertFalse(requestIdToResponseQueueMap.containsKey(downloadRequest.getRequestKey()));
    }

    @Test
    public void exceptionThrownWhenTraderButlerRequestSend() {
        LogDownloadService service = new LogDownloadService(requestIdToResponseQueueMap, protoService, 1000, "", Clock.systemDefaultZone());
        Request request = new Request("USER", "USER1", "FILENAME", "");
        LogDownloadRequest logDownloadRequest = new LogDownloadRequest(request, "DIR");
        doThrow(RuntimeException.class).when(protoService).sendMessage(Mockito.any(Message.class), Mockito.anyString());
        LogDownloadWrapper wrapper = service.butlerProcess(logDownloadRequest);
        Assert.assertEquals(0, wrapper.getButlerEntries().size());
        Assert.assertEquals(LogDownloadService.GENERIC_TRADER_BUTLER_MESSAGE_ERROR, wrapper.getMessage());
        Assert.assertFalse(requestIdToResponseQueueMap.containsKey(logDownloadRequest.getRequestKey()));
    }

    @Test
    public void exceptionThrownWhenDirectoryListingResponse() throws GpssApplicationException {
        LogDownloadService service = new LogDownloadService(requestIdToResponseQueueMap, protoService, 1000, "", Clock.systemDefaultZone());
        service = spy(service);
        Request request = new Request("USER", "USER1", "FILENAME", "");
        LogDownloadRequest logDownloadRequest = new LogDownloadRequest(request, "DIR");
        Mockito.doThrow(NullPointerException.class).when(service).butlerEntriesRequest(any(), anyInt());
        LogDownloadWrapper wrapper = service.butlerProcess(logDownloadRequest);
        Assert.assertEquals(0, wrapper.getButlerEntries().size());
        Assert.assertEquals(LogDownloadService.WHILE_SHOWING_DIRECTORY_LIST_MESSAGE_ERROR, wrapper.getMessage());
        Assert.assertFalse(requestIdToResponseQueueMap.containsKey(logDownloadRequest.getRequestKey()));
    }

    @Test
    public void exceptionThrownWhenStoreLogfileInLogDirectory() throws GpssApplicationException {
        LogDownloadService service = new LogDownloadService(requestIdToResponseQueueMap, protoService, 1000, "", Clock.systemDefaultZone());
        service = spy(service);
        Request request = new Request("USER", "USER1", "FILENAME", "");
        LogDownloadRequest logDownloadRequest = new LogDownloadRequest(request, "GET");
        Mockito.doThrow(NullPointerException.class).when(service).butlerEntriesRequest(any(), anyInt());
        LogDownloadWrapper wrapper = service.butlerProcess(logDownloadRequest);
        Assert.assertEquals(0, wrapper.getButlerEntries().size());
        Assert.assertEquals(LogDownloadService.UNABLE_TO_STORE_LOG_FILE_IN_DIRECTORY_MESSAGE_ERROR, wrapper.getMessage());
        Assert.assertFalse(requestIdToResponseQueueMap.containsKey(logDownloadRequest.getRequestKey()));

    }

    @Test
    public void traderCallBackTestGet() {
        LogDownloadService service = new LogDownloadService(requestIdToResponseQueueMap, protoService, 1000, "", Clock.systemDefaultZone());
        Request request1 = new Request("USER", "USER1", "FILENAME", "");
        LogDownloadRequest downloadRequest = new LogDownloadRequest(request1, "GET");
        OnMessageTraderGetResponse onMessageTraderGetResponse = new OnMessageTraderGetResponse(new LogDownloadCallbacksService(service), downloadRequest);
        onMessageTraderGetResponse.start();
        LogDownloadWrapper wrapper = service.butlerProcess(downloadRequest);
    }

    @Test
    public void OmsiCallBackTestGet() {
        LogDownloadService service = new LogDownloadService(requestIdToResponseQueueMap, protoService, 1000, "", Clock.systemDefaultZone());
        Request request1 = new Request("USER", "OMSI", "FILENAME", "");
        LogDownloadRequest downloadRequest = new LogDownloadRequest(request1, "GET");
        OnMessageOmsiGetResponse onMessageOmsiGetResponse = new OnMessageOmsiGetResponse(new LogDownloadCallbacksService(service), downloadRequest);
        onMessageOmsiGetResponse.start();
        LogDownloadWrapper wrapper = service.butlerProcess(downloadRequest);
    }

    @Test
    public void getDirectoryListFromEntriesInterruptedException() {
        LogDownloadService service = new LogDownloadService(requestIdToResponseQueueMap, protoService, 1000, "", Clock.systemDefaultZone());
        Request request = new Request("USER", "USER1", "FILENAME", "");
        LogDownloadRequest logDownloadRequest = new LogDownloadRequest(request, "DIR");
        InterruptThread interruptThread = new InterruptThread(service, logDownloadRequest);
        interruptThread.start();
        interruptThread.interrupt();
        await().atMost(Duration.FIVE_HUNDRED_MILLISECONDS).until(() -> requestIdToResponseQueueMap.get("USERUSER1DIR") == null);
        Assert.assertEquals(0, interruptThread.getWrapper().getButlerEntries().size());
        Assert.assertEquals(LogDownloadService.INTERRUPTED_LISTING_MESSAGE_ERROR, interruptThread.getWrapper().getMessage());
        Assert.assertFalse(requestIdToResponseQueueMap.containsKey(logDownloadRequest.getRequestKey()));

    }


    private class OnMessageTraderResponse extends Thread {

        private LogDownloadRequest logDownloadRequest;

        public OnMessageTraderResponse(LogDownloadCallbacksService logDownloadCallbacksService, LogDownloadRequest logDownloadRequest) {
            this.logDownloadRequest = logDownloadRequest;
        }
        public void run() {
            LogDownloadService logDownloadService = new LogDownloadService(requestIdToResponseQueueMap, protoService, 1000, "", Clock.systemDefaultZone());
            LogDownloadCallbacksService logDownloadCallbacksService = new LogDownloadCallbacksService(logDownloadService);
            Message message = new TraderButlerResponse("USER", "USER1", "DIR");
            TraderButlerRequest request = TraderButlerRequest.builder().ActionType("DIR").FileName("FILENAME").MemberId("USER").UserId("USER1").build();
            ((TraderButlerResponse) message).setRequest(request);
            MessageWrapper messageWrapper = new MessageWrapper<>(message);
            messageWrapper.setMessage(message);
            await().atMost(Duration.FIVE_HUNDRED_MILLISECONDS).until(() -> requestIdToResponseQueueMap.containsKey(logDownloadRequest.getRequestKey()));
            logDownloadCallbacksService.onMessage(messageWrapper);
        }
    }

    private class OnMessageOmsiResponse extends Thread {

        private LogDownloadRequest logDownloadRequest;

        public OnMessageOmsiResponse(LogDownloadCallbacksService logDownloadCallbacksService, LogDownloadRequest logDownloadRequest) {
            this.logDownloadRequest = logDownloadRequest;
        }
        public void run() {
            LogDownloadService logDownloadService = new LogDownloadService(requestIdToResponseQueueMap, protoService, 1000, "", Clock.systemDefaultZone());
            LogDownloadCallbacksService logDownloadCallbacksService = new LogDownloadCallbacksService(logDownloadService);
            Message message = new OMSIButlerResponse("USER", "OMSI", "DIR");
            OMSIButlerRequest request = OMSIButlerRequest.builder().ActionType("DIR").FileName("FILENAME").MemberId("USER").UserId("").ResendFromStart(1).build();
            ((OMSIButlerResponse) message).setRequest(request);
            MessageWrapper messageWrapper = new MessageWrapper<>(message);
            messageWrapper.setMessage(message);
            await().atMost(Duration.FIVE_HUNDRED_MILLISECONDS).until(() -> requestIdToResponseQueueMap.containsKey(logDownloadRequest.getRequestKey()));
            logDownloadCallbacksService.onMessage(messageWrapper);
        }
    }

    class InterruptThread extends Thread {
        private LogDownloadWrapper wrapper;
        private LogDownloadService service;
        private LogDownloadRequest downloadRequest;

        public LogDownloadWrapper getWrapper(){
            return wrapper;
        }

        public InterruptThread(LogDownloadService service, LogDownloadRequest downloadRequest) {
            this.service = service;
            this.downloadRequest = downloadRequest;
        }
        public void run() {
            wrapper = service.butlerProcess(downloadRequest);
        }
    }

    private class OnMessageTraderGetResponse extends Thread {

        private LogDownloadRequest logDownloadRequest;

        public OnMessageTraderGetResponse(LogDownloadCallbacksService logDownloadCallbacksService, LogDownloadRequest logDownloadRequest) {
            this.logDownloadRequest = logDownloadRequest;
        }
        public void run() {
            LogDownloadService logDownloadService = new LogDownloadService(requestIdToResponseQueueMap, protoService, 1000, "", Clock.systemDefaultZone());
            LogDownloadCallbacksService logDownloadCallbacksService = new LogDownloadCallbacksService(logDownloadService);
            Message message = new TraderButlerResponse("USER", "USER1", "GET");
            TraderButlerRequest request = TraderButlerRequest.builder().ActionType("GET").FileName("FILENAME").MemberId("USER").UserId("USER1").build();
            ((TraderButlerResponse) message).setRequest(request);
            MessageWrapper messageWrapper = new MessageWrapper<>(message);
            messageWrapper.setMessage(message);
            await().atMost(Duration.FIVE_HUNDRED_MILLISECONDS).until(() -> requestIdToResponseQueueMap.containsKey(logDownloadRequest.getRequestKey()));
            logDownloadCallbacksService.onMessage(messageWrapper);
        }
    }

    private class OnMessageOmsiGetResponse extends Thread {

        private LogDownloadRequest logDownloadRequest;

        public OnMessageOmsiGetResponse(LogDownloadCallbacksService logDownloadCallbacksService, LogDownloadRequest logDownloadRequest) {
            this.logDownloadRequest = logDownloadRequest;
        }
        public void run() {
            LogDownloadService logDownloadService = new LogDownloadService(requestIdToResponseQueueMap, protoService, 1000, "", Clock.systemDefaultZone());
            LogDownloadCallbacksService logDownloadCallbacksService = new LogDownloadCallbacksService(logDownloadService);
            Message message = new OMSIButlerResponse("USER", "OMSI", "DIR");
            OMSIButlerRequest request = OMSIButlerRequest.builder().ActionType("DIR").FileName("FILENAME").MemberId("USER").UserId("").ResendFromStart(1).build();
            ((OMSIButlerResponse) message).setRequest(request);
            MessageWrapper messageWrapper = new MessageWrapper<>(message);
            messageWrapper.setMessage(message);
            await().atMost(Duration.FIVE_HUNDRED_MILLISECONDS).until(() -> requestIdToResponseQueueMap.containsKey(logDownloadRequest.getRequestKey()));
            logDownloadCallbacksService.onMessage(messageWrapper);
        }
    }




}