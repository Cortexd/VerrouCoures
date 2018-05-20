package com.coures.renaud.verroucoures;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WebService {

    final String URL = "http://192.168.1.3:18099/";
    final String NAMESPACE = "http://banane.home/";
    final String METHODNAME = "actionRelais";

    SoapObject Request = new SoapObject(NAMESPACE, METHODNAME);


    // Méthode dans laquelle nous appelons le service web et récupérons le résultat renvoyé par ce dernier
    public boolean executionRequete( String action,int relays){

       //Renseignement des valeurs des paramètres
        try
        {
            Request.addProperty("relays", relays.toString());
            Request.addProperty("action", action);
        }
        catch(Exception e)
        {
            Log.e("VerrouActivity", e.getMessage());
        }

        //Création de l'enveloppe
        SoapSerializationEnvelope enveloppe = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        //Ajout de la requête dans l'enveloppe
        enveloppe.setOutputSoapObject(Request);

        //Envoi de la requête et traitement du résultat
        HttpTransportSE http_transport = new HttpTransportSE(url);
        try
        {
            http_transport.call(namespace + methodName, enveloppe);
            SoapPrimitive reponse = (SoapPrimitive)enveloppe.getResponse();
            //resultat_float = Float.parseFloat(reponse.toString());
        }
        catch (Exception e)
        {
            Log.e("Erreur envoi de la requête : ", e.getMessage());
        }

        //Retourner le résultat du calcul
        return true;
    }

}