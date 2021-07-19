package br.com.serratecEcommerce.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormataData{

    public static String formatarDataHoraPadraoBrasil(Date data){
        
        var formatador = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        return formatador.format(data);
    }
    public static String formatarDataPadraoBrasil(Date data){
        
        var formatador = new SimpleDateFormat("dd/MM/yyyy");
        return formatador.format(data);
    }

    public static Date ConverteParaData(String texto) throws Exception{
        return new SimpleDateFormat("dd/MM/yyyy").parse(texto);
    }
}
