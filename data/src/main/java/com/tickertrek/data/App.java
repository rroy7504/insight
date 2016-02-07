package com.tickertrek.data;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
	DataSource source = new DataSource();
	source.getPriceData("GOOG");	
    }
}
