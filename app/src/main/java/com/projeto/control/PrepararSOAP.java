package com.projeto.control;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

/**
 * Created by leo on 16/03/16.
 */
public class PrepararSOAP {

    public static SoapSerializationEnvelope envelopar(SoapObject soapObject) {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        // Adicionamos ao envelope o objeto que queremos enviar
        envelope.setOutputSoapObject(soapObject);
        envelope.implicitTypes = true; // Flag obrigat�rio para funcionar
        return envelope;
    }
}
