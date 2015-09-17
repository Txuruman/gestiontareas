package es.securitasdirect.tareas.service.model;

/**
 * Para devolver datos del resultado de discardNotificationTask
 */
public class DiscardNotificationTaskResult {

    private boolean wasInMemory = false;
    private boolean ticketWasSaved = false;
    private boolean taskWasFinalized = false;


    public boolean isTaskWasFinalized() {
        return taskWasFinalized;
    }

    public void setTaskWasFinalized(boolean taskWasFinalized) {
        this.taskWasFinalized = taskWasFinalized;
    }

    public boolean isWasInMemory() {
        return wasInMemory;
    }

    public void setWasInMemory(boolean wasInMemory) {
        this.wasInMemory = wasInMemory;
    }


    public boolean isTicketWasSaved() {
        return ticketWasSaved;
    }

    public void setTicketWasSaved(boolean ticketWasSaved) {
        this.ticketWasSaved = ticketWasSaved;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DiscardNotificationTaskResult{");
        sb.append("wasInMemory=").append(wasInMemory);
        sb.append(", ticketWasSaved=").append(ticketWasSaved);
        sb.append(", taskWasFinalized=").append(taskWasFinalized);
        sb.append('}');
        return sb.toString();
    }
}
