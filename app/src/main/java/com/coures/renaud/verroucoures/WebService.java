package com.coures.renaud.verroucoures;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WebService extends AsyncTask<ParamRelays, Void, Boolean> {

    final String URL = "http://192.168.1.3:18099/";
    final String NAMESPACE = "http://192.168.1.3:18099/";
    final String METHODNAME = "actionRelais";

    SoapObject Request = new SoapObject(NAMESPACE, METHODNAME);

    private Exception exception;


    // Méthode dans laquelle nous appelons le service web et récupérons le résultat renvoyé par ce dernier
    protected Boolean doInBackground(ParamRelays... paramRelays)
    {


            //Renseignement des valeurs des paramètres
            try {
                    Request.addProperty("v1", paramRelays[0].action);
                    Request.addProperty("v2", Integer.toString(paramRelays[0].relays));
                    Request.addProperty("v3", paramRelays[0].action);
                }
                catch (Exception e) {
                Log.e("VerrouActivity", e.getMessage());
            }

            //Création de l'enveloppe
            //SoapSerializationEnvelope enveloppe = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            SoapSerializationEnvelope enveloppe = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            enveloppe.dotNet = true;
            enveloppe.setAddAdornments(false);


            //Ajout de la requête dans l'enveloppe
            enveloppe.setOutputSoapObject(Request);

            //Envoi de la requête et traitement du résultat
            HttpTransportSE http_transport = new HttpTransportSE(URL);
            try {

                http_transport.call(NAMESPACE + METHODNAME, enveloppe);
                SoapPrimitive reponse = (SoapPrimitive) enveloppe.getResponse();

                //resultat_float = Float.parseFloat(reponse.toString());

                return true;
            }
            catch (Exception e)
            {
                this.exception = e;
                return false;
            } finally
            {
                //is.close();
            }
    }


}