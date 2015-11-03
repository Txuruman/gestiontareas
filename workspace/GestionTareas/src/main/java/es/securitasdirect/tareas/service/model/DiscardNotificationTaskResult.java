package es.securitasdirect.tareas.service.model;

/**
 * Para devolver datos del resultado de discardNotificationTask
 */
public class DiscardNotificationTaskResult {

    private boolean wasInMemory = false;
    private boolean ticketWasSaved = false;
    private boolean taskWasFinalized = false;
    private boolean ommitedCallToDiscard = false;
    private boolean fromSearch = false;

    

    public DiscardNotificationTaskResult() {
	}

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

    public void setOmmitedCallToDiscard(boolean ommitedCallToDiscard) {
        this.ommitedCallToDiscard = ommitedCallToDiscard;
    }

    public boolean isOmmitedCallToDiscard() {
        return ommitedCallToDiscard;
    }

    public boolean isFromSearch() {
		return fromSearch;
	}

	public void setFromSearch(boolean fromSearch) {
		this.fromSearch = fromSearch;
	}

	@Override
	public String toString() {
		return "DiscardNotificationTaskResult [wasInMemory=" + wasInMemory + ", ticketWasSaved=" + ticketWasSaved
				+ ", taskWasFinalized=" + taskWasFinalized + ", ommitedCallToDiscard=" + ommitedCallToDiscard
				+ ", fromSearch=" + fromSearch + "]";
	}

	
}
