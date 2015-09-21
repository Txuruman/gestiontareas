package es.securitasdirect.tareas.service.model;

/**
 * Para devolver datos del resultado de discardNotificationTask
 */
public class DiscardNotificationTaskResult {

    private boolean wasInMemory = false;
    private boolean ticketWasSaved = false;
    private boolean taskWasFinalized = false;
    private boolean isCallDone=false;


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

	public boolean isCallDone() {
		return isCallDone;
	}

	public void setCallDone(boolean isCallDone) {
		this.isCallDone = isCallDone;
	}

	@Override
	public String toString() {
		return "DiscardNotificationTaskResult [wasInMemory=" + wasInMemory + ", ticketWasSaved=" + ticketWasSaved
				+ ", taskWasFinalized=" + taskWasFinalized + ", isCallDone=" + isCallDone + "]";
	}

   
}
