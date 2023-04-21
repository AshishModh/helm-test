package logdownload;

import org.apache.hadoop.yarn.webapp.hamlet2.Hamlet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Clock;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

@RunWith(MockitoJUnitRunner.class)
public class LogDownloadCallbacksServiceTest {

    private String requestKey = "USERUSER1DIR";
    private String OMSIRequestKey = "USERDIR";

    @Mock
    IProtoService protoService;
    Clock clock;
    ConcurrentMap<String, BlockingQueue<Message>> requestIdToResponseQueueMap = new ConcurrentHashMap<>();

    @Before
    public void test_init() {
        requestIdToResponseQueueMap.put(requestKey, new LinkedBlockingQueue<>());
        requestIdToResponseQueueMap.put(OMSIRequestKey, new LinkedBlockingQueue<>());
    }


    @Test
    public void TraderButlerResponseTest() {
        LogDownloadService logDownloadService = new LogDownloadService(requestIdToResponseQueueMap, protoService, 4000, "", clock);
        LogDownloadCallbacksService logDownloadCallbacksService = new LogDownloadCallbacksService(logDownloadService);
        Message message = new TraderButlerResponse("USER", "USER1", "DIR");
        TraderButlerRequest request = TraderButlerRequest.builder().ActionType("DIR").FileName("FILENAME").MemberId("USER").UserId("USER1").build();
        ((TraderButlerResponse) message).setRequest(request);
        MessageWrapper messageWrapper = new MessageWrapper<>(message);
        messageWrapper.setMessage(message);
        logDownloadCallbacksService.onMessage(messageWrapper);
        BlockingQueue<Message> messages = logDownloadService.getRequestIdToResponseQueueMap().get(requestKey);
        Assert.assertEquals(1, messages.size());

    }

    @Test
    public void OMSIButlerResponseTest() {
        LogDownloadService logDownloadService = new LogDownloadService(requestIdToResponseQueueMap, protoService, 4000, "", clock);
        LogDownloadCallbacksService logDownloadCallbacksService = new LogDownloadCallbacksService(logDownloadService);
        Message message = new OMSIButlerResponse("USER", "USER1", "DIR");
        OMSIButlerRequest request = OMSIButlerRequest.builder().ActionType("DIR").FileName("FILENAME").MemberId("USER").UserId("").ResendFromStart(1).build();
//        String requestKey = LogDownloadRequest.generateKeyForResponseMap(request.getMemberId(), request.getUserId(), request.getActionType());
        ((OMSIButlerResponse) message).setRequest(request);
//        logDownloadService.getRequestIdToResponseQueueMap().containsKey(requestKey);
//        BlockingQueue<Message> messageQueue = logDownloadService.getRequestIdToResponseQueueMap().get(requestKey);
        MessageWrapper messageWrapper = new MessageWrapper<>(message);
        messageWrapper.setMessage(message);
        logDownloadCallbacksService.onMessage(messageWrapper);
        BlockingQueue<Message> messages = logDownloadService.getRequestIdToResponseQueueMap().get(OMSIRequestKey);
        Assert.assertEquals(1, messages.size());
    }

    @Test
    public void requestKeyWithSpacesReceived() {
        LogDownloadService logDownloadService = new LogDownloadService(requestIdToResponseQueueMap, protoService, 4000, "", clock);
        LogDownloadCallbacksService logDownloadCallbacksService = new LogDownloadCallbacksService(logDownloadService);
        Message message = new TraderButlerResponse("USER", "USER 1", "DIR");
        TraderButlerRequest request = TraderButlerRequest.builder().ActionType("DIR").FileName("FILENAME").MemberId("  USER  ").UserId("USER 1").build();
//        String requestKey = LogDownloadRequest.generateKeyForResponseMap(request.getMemberId(), request.getUserId(), request.getActionType());
        ((TraderButlerResponse) message).setRequest(request);
//        logDownloadService.getRequestIdToResponseQueueMap().containsKey(requestKey);
//        BlockingQueue<Message> messageQueue = logDownloadService.getRequestIdToResponseQueueMap().get(requestKey);
        MessageWrapper messageWrapper = new MessageWrapper<>(message);
        messageWrapper.setMessage(message);
        logDownloadCallbacksService.onMessage(messageWrapper);
        BlockingQueue<Message> messages = logDownloadService.getRequestIdToResponseQueueMap().get(requestKey);
        Assert.assertEquals(1, messages.size());

    }

    @Test
    public void traderCallBackAreInQueue() {
        LogDownloadService logDownloadService = new LogDownloadService(requestIdToResponseQueueMap, protoService, 4000, "", clock);
        LogDownloadCallbacksService logDownloadCallbacksService = new LogDownloadCallbacksService(logDownloadService);
        Message message = new TraderButlerResponse("USER", "USER1", "DIR");
        TraderButlerRequest request = TraderButlerRequest.builder().ActionType("DIR").FileName("FILENAME").MemberId("USER").UserId("USER1").build();
//        String requestKey = LogDownloadRequest.generateKeyForResponseMap(request.getMemberId(), request.getUserId(), request.getActionType());
        ((TraderButlerResponse) message).setRequest(request);
//        logDownloadService.getRequestIdToResponseQueueMap().containsKey(requestKey);
//        BlockingQueue<Message> messageQueue = logDownloadService.getRequestIdToResponseQueueMap().get(requestKey);
        MessageWrapper messageWrapper = new MessageWrapper<>(message);
        messageWrapper.setMessage(message);
        logDownloadCallbacksService.onMessage(messageWrapper);
        BlockingQueue<Message> messages = logDownloadService.getRequestIdToResponseQueueMap().get(requestKey);
        Assert.assertEquals(1, messages.size());
        logDownloadCallbacksService.onMessage(messageWrapper);
        Assert.assertEquals(2, messages.size());

    }

    @Test
    public void traderCallBackWithWrongResponseOrKeyMismatch() {
        LogDownloadService logDownloadService = new LogDownloadService(requestIdToResponseQueueMap, protoService, 4000, "", clock);
        LogDownloadCallbacksService logDownloadCallbacksService = new LogDownloadCallbacksService(logDownloadService);
        Message message = new TraderButlerResponse("USER", "USER1", "DIR");
        TraderButlerRequest request = TraderButlerRequest.builder().ActionType("DIR").FileName("FILENAME").MemberId("USER").UserId("USER2").build();
        ((TraderButlerResponse) message).setRequest(request);
        MessageWrapper messageWrapper = new MessageWrapper<>(message);
        messageWrapper.setMessage(message);
        logDownloadCallbacksService.onMessage(messageWrapper);
        BlockingQueue<Message> messages = requestIdToResponseQueueMap.get(requestKey);
        Assert.assertEquals(0, messages.size());

    }

    @Test
    public void nullValueTest(){
        LogDownloadService logDownloadService = new LogDownloadService(requestIdToResponseQueueMap, protoService,4000,"",clock);
        LogDownloadCallbacksService logDownloadCallbacksService = new LogDownloadCallbacksService(logDownloadService);
        Message message = new TraderButlerResponse("USER", "USER1", "DIR");
        TraderButlerRequest request = TraderButlerRequest.builder().MemberId("USER").build();
        ((TraderButlerResponse) message).setRequest(request);
        MessageWrapper messageWrapper = new MessageWrapper<>(message);
        messageWrapper.setMessage(message);
        logDownloadCallbacksService.onMessage(messageWrapper);
        BlockingQueue<Message> messages = requestIdToResponseQueueMap.get(requestKey);
        Assert.assertEquals(0, messages.size());
    }


}
