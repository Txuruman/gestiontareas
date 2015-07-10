package es.securitasdirect.tareas.web.controller.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Utitlity to acces internalization messages
 */
@Named(value = "messageUtil")
@Singleton
public class MessageUtil {

    /** For singleton outside Spring context */
    private static MessageUtil instance;

    /** Resource bundle configured in Spring, mvc-dispatcher-servlet.xml */
    private MessageSource messageSource;


    @Inject
    public MessageUtil(MessageSource ms) {
        messageSource = ms;
        instance = this;
    }

    /**
     * Call it from non spring aware code to obtain a reference to the ResourcesUtil singleton instance.
     */
    public static MessageUtil getInstance() {
        return instance;
    }

    /**
     * Return the property value for the contextual locale.
     * If no value is found, the passed key is returned.
     */
    public String getProperty(String key, Object... args) {
        if (key == null) {
            return "";
        }

        return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    }


    public String getPropertyOrNull(String key, Object... args) {
        if (key == null) {
            return "";
        }

        return messageSource.getMessage(key, args, "", LocaleContextHolder.getLocale());
    }

    /**
     * Same as getProperty() but use the count to choose the proper key.
     * Used when the message varies depending on the context. For example: 'there is no result' vs 'there is one result' vs 'there are n results'
     * @param key the base key
     */
    public String getPluralableProperty(String key, int count) {
        if (key == null) {
            return "";
        }

        switch (count) {
            case 0:
                return getProperty(key + "_0");
            case 1:
                return getProperty(key + "_1");
            default:
                return getProperty(key + "_n", count);
        }
    }


}
