package br.unirio.dsw.chamadas;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Gson gson = new Gson();
    	String[] strings = {"opcao 1", "opcao 2", "opcao 3"};
    	String str = gson.toJson(strings);
    	ArrayList<String> listaStr = gson.fromJson(str, new TypeToken<ArrayList<String>>(){}.getType());
    	String str2 = gson.toJson(listaStr); 
    	for(String s: listaStr) System.out.println(s);
    	System.out.println(str);
    	System.out.println(str2);
    }
}
