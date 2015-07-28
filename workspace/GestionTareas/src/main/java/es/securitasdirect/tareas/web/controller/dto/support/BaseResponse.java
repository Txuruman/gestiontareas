package es.securitasdirect.tareas.web.controller.dto.support;

import es.securitasdirect.tareas.model.InstallationData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Javier Naval on 06/07/2015.
 */
public class BaseResponse {

    private List<Message> messages = new ArrayList<Message>();

    /**
     * Clase para almacenar los mensajes de respuesta a las peticiones al servidor
     */
    public class Message {
        /**
         * Constants for Level
         */
        public static final String PRIMARY = "primary";
        public static final String SUCCSESS = "success";
        public static final String INFO = "info";
        public static final String WARNING = "warning";
        public static final String DANGER = "danger";

        /**
         * Level of the Message, required
         */
        String level = INFO;
        /**
         * Component to associate the message, optional
         */
        String forElement;
        /**
         * Text of the message, required
         */
        String value;

        public Message() {
        }

        public Message(String level, String value, String forElement) {
            this.forElement = forElement;
            this.value = value;
            this.level = level;
        }

        public Message(String level, String value) {
            this.level = level;
            this.value = value;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getForElement() {
            return forElement;
        }

        public void setForElement(String forElement) {
            this.forElement = forElement;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }


        @Override
        public String toString() {
            return "Message{" +
                    "level='" + level + '\'' +
                    ", forElement='" + forElement + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    public BaseResponse info(String value) {
        if(value!=null && !value.isEmpty()) {
            messages.add(new Message(Message.INFO, value));
        }
        return this;
    }

    public BaseResponse warning(String value) {
        if(value!=null && !value.isEmpty()){
            messages.add(new Message(Message.WARNING,value));
        }
        return this;
    }

    public BaseResponse success(String value) {
        if(value!=null && !value.isEmpty()){
            messages.add(new Message(Message.SUCCSESS,value));
        }
        return this;
    }

    public BaseResponse primary(String value) {
        if(value!=null && !value.isEmpty()){
            messages.add(new Message(Message.PRIMARY,value));
        }
        return this;
    }

    public BaseResponse danger(String value) {
        if(value!=null && !value.isEmpty()){
            messages.add(new Message(Message.DANGER,value));
        }
        return this;
    }


    public BaseResponse addMessage(String level, String value) {
        if(value!=null && !value.isEmpty()){
            messages.add(new Message(level, value));
        }
        return this;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void addMessages(List<Message> messagesAdd){
        for (Message messageToAdd : messagesAdd) {
            messages.add(messageToAdd);
        }
    }

    public void addMessage(Message msg){
        messages.add(msg);
    }

    public boolean hasError(){
        Iterator<Message> messageList = this.messages.iterator();
        boolean hasError = false;
        if(messageList.hasNext()){
            if(messageList.next().getLevel().equals(Message.DANGER)){
                hasError = true;
            }
        }
        return hasError;
    }


    @Override
    public String toString() {
        return "BaseResponse{" +
                "messages=" + messages +
                '}';
    }
}
