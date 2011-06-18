package com.blogspot.ficklemind.bitly;


import java.lang.Exception;



/**
 * Class to handle exceptions during accessing bitly api
 */
public class BitlyException extends Exception {
    /** version ID? */
	private static final long serialVersionUID = -8702623393063656645L;
	/** json response associated with this exception */
    private String m_json;


    /**
     * Constructor
     * @param message error message
     * @param json json response associated with this exception
     */
    public BitlyException(String message, String json) {
        super(message);
        m_json = json;
    }

    /**
     * Constructor
     * @param message error message
     */
    public BitlyException(String message) {
        super(message);
        m_json = null;
    }

    /**
     * Converts the exception to string
     * @return desired string
     */
    public String toString() {
        String msg = super.toString();
        if(m_json != null) {
            msg += m_json;
        }
        return msg;
    }
}
