package logdownload;

import java.util.concurrent.BlockingQueue;

public class LogDownloadCallbacksService {
    private final LogDownloadService logDownloadService;

    public LogDownloadCallbacksService(final LogDownloadService logDownloadService) {
        this.logDownloadService = logDownloadService;
    }

    public void onMessage(final MessageWrapper<Message> messageWrapper) {
        String requestKey = "";
        if (messageWrapper.getMessage() instanceof TraderButlerResponse) {
            TraderButlerRequest re = ((TraderButlerResponse) messageWrapper.getMessage()).getRequest();
            requestKey = LogDownloadRequest.generateKeyForResponseMap(re.getMemberId(), re.getUserId(), re.getActionType());
            System.out.println(String.format("Ln5 message received against request key: %s", requestKey));

        } else if (messageWrapper.getMessage() instanceof OMSIButlerResponse) {
            OMSIButlerRequest re = ((OMSIButlerResponse) messageWrapper.getMessage()).getRequest();
            requestKey = LogDownloadRequest.generateKeyForResponseMap(re.getMemberId(), re.getUserId(), re.getActionType());
            System.out.println(String.format("OMSI message received against request key: %s", requestKey));

        }
        if (this.logDownloadService.getRequestIdToResponseQueueMap().containsKey(requestKey)) {
            BlockingQueue<Message> messageQueue = this.logDownloadService.getRequestIdToResponseQueueMap().get(requestKey);
            messageQueue.add(messageWrapper.getMessage());
        } else {
            System.out.println(String.format("No request found against response having key %s", requestKey));
        }


    }
}
