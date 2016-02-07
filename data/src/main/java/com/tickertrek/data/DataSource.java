package com.tickertrek.data;

import java.util.*;
import java.net.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import org.json.*;
/**
 *
 * @author root
 */
public class DataSource {
    
     Hashtable TickerList;
     Hashtable FailedTickerList;
     private final Object lockObj = new Object();
     
     public DataSource(){
         TickerList = new Hashtable();
         FailedTickerList = new Hashtable();
     };
    
    /*public String getYahooTicker(String ticker, String ex){
        String tickerList = "";
            if (Constants.Exchange.BSE.equals(ex))
                return ticker+".BO";
            else if(Constants.Exchange.NSE.equals(ex))
                return ticker+".NS";
            else
                return ticker;
            
           
    }
    
    public String getYahooTickerList(Hashtable tickerQueue){
        String tickerList = "";
        Enumeration keys = tickerQueue.keys();
        
        while(keys.hasMoreElements())
        {  
            
            String k = (String)keys.nextElement();
            String ex =  k.substring( k.length()-1 ) ;
            String symbol = k.substring( 0, k.length()-1 );
            //OrderBase o = (OrderBase)tickerQueue.get((String)keys.nextElement());;
           
            
            if (ex.equals(Constants.Exchange.BSE))
                tickerList += ("+" + symbol+".BO");
            else if(ex.equals(Constants.Exchange.NSE))
                tickerList += ("+" + symbol+".NS");
            else
                tickerList += ("+" + symbol);
        }
        // get rid of that extra "+" sign from the begining
            if(!tickerList.isEmpty()) tickerList = tickerList.substring(1);
       return tickerList;     

    
    }
    
    public Quote deleteYahooExt(Quote q){
        int dotIndex = q.Symbol.indexOf('.'); //  find the begining of the exchange related suffix
        if(dotIndex >= 0){
            //get Exchange...
            String ex = q.Symbol.substring(dotIndex+1);
            if("BO".equals(ex)) q.Exchange = Constants.Exchange.BSE;
            else if("NS".equals(ex)) q.Exchange = Constants.Exchange.NSE;

            // if the suffix exits get rid of it...
            if(dotIndex != -1 ) q.Symbol = q.Symbol.substring(0, dotIndex);
        }
        return q;
    }*/
        
    public  void  getPriceData(String ticker) {
	 if (!ticker.isEmpty()) {
	   System.out.println("Getting data for: " + ticker); 
                try {    //http://www.gummy-stuff.org/Yahoo-data.htm
                        URL url = new URL("http://finance.yahoo.com/webservice/v1/symbols/" + ticker + "/quote?format=json");
                        // URL url = new URL("http://quote.yahoo.com/d/quotes.json?s=" + ticker + "&f=sl1t1np2vhgc6xd1");
                        URLConnection yc = url.openConnection();

                        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                        String inputLine="";
			String buffer = "";
                        while ((inputLine = in.readLine()) != null) {
                          buffer += inputLine;
			}
                            try{
                                    System.out.println("Called at initialization: " + inputLine);
                                    JSONObject quote = new JSONObject(buffer);
                                    
                                    System.out.println("Received Data: " + quote);
                                    //SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mma");
                                    //java.util.Date date = Constants.yahooQuoteDateFormat.parse(quote.getString(2) + " "+ quote.getString(3) );
                                    // java.util.Date date = this.getESTToGMT(quote.getString(10)+ " "+ quote.getString(2));
                                    //date.setTime(date.getTime() - 12*60*60*1000);
                                    //long gmtTime = date.getTime() + TimeZone.getTimeZone("America/New_York").getOffset(date.getTime());
                                    //date.setTime(gmtTime);
                                    //System.out.println("Date from Datasource:"+Constants.istDateFormat.format(date));
                                    //Quote q = new Quote(quote.getString(0), date, (float)quote.getDouble(1),Constants.Exchange.UNKNOWN);
                                    // this.updateTickerStat(q.Symbol, true);
                                    // q = this.deleteYahooExt(q);
                                   // Constants.OPLogger.info("Parsed quote:"+q);
                                   //  quoteList.add(q);
                                    
                                } catch (Exception e){
					e.printStackTrace();
                                        // if(this.failedQouteRetrivalCount(inputLine) < Constants.FAILED_LOG_THRESHOLD)
                                        //     Constants.OPLogger.log(Level.SEVERE,"Failed to get quote from Yahoo, received data:"+inputLine, e);
                                        // mal formed JSON which means data is not available so ignore it
                                    }
                         // end of while
                        
                     in.close();
                    }catch(Exception e){
			e.printStackTrace();
                       // Constants.OPLogger.log(Level.SEVERE,"Failed to get quote from Yahoo,Ticker List:"+list, e);
                        
                    }
             
        }
     return;
    }
    public Date getESTToGMT(String d) throws Exception{
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mmaa");
        format.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        java.util.Date date = format.parse(d);
        return date;
    }

   /*  public  Quote getPriceData(String ticker){
          try{
                        URL url = new URL("http://finance.yahoo.com/d/quotes.json?s="+ticker+"&f=sl");
                        URLConnection yc = url.openConnection();
                        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                        String inputLine;
                        if ((inputLine = in.readLine()) != null){
                            try{
                                    //System.err.println("Called at initialization: "+inputLine);
                                    JSONArray quote = new JSONArray("["+inputLine+"]");
                                    java.util.Date date = Constants.yahooQuoteDateFormat.parse(quote.getString(2) + " "+ quote.getString(3) );
                                    //long gmtTime = date.getTime() + TimeZone.getTimeZone("America/New_York").getOffset(date.getTime());
                                    //date.setTime(gmtTime);
                                    System.out.println("Date from Datasource:"+Constants.istDateFormat.format(date));
                                    return new Quote(quote.getString(0), date, (float)quote.getDouble(1));
                                    }catch(Exception e){
                                        e.printStackTrace();
                                        // mal formed JSON which means data is not available so ignore it
                                    }
                        } // end of while
                     in.close();
                    }catch(Exception e){ e.printStackTrace();}
         return new Quote("", new Date(), 0);
     }*/
}
