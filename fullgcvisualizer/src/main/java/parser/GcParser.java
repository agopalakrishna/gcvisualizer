package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TimeZone;
import java.util.TreeSet;

/**
 * Data sorter
 *
 */
public class GcParser 
{
    public static void main( String[] args ) throws Exception
    {
    	
    	//2013-01-25T04:34:29, 10a, 0.48
    	//2013-01-25T04:36:18, 10a, 0.50
    	//2013-01-25T04:37:54, 10a, 0.44
    	//2013-01-25T04:38:40, 10a, 0.43

    	
    	//args[0] -- Input file
    	//args[1] -- Output file
    	//args[2] -- Server names
        
    	if( args.length  != 3  ){
    		throw new Exception("Too many arguments");
    	}
    	
    	String inputFile      = args[0];
    	String outputFile     = args[1];
    	String serverNames    = args[2];
    	
    	File file = new File(inputFile);
    	FileReader reader = null;
        reader = new FileReader(file);
    	
    	BufferedReader lineReader = new BufferedReader(reader);
    	
    	String line = null;
		line = lineReader.readLine();
		Map<Date, String> map = new HashMap<Date,String>();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));    	
 
		SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		outputDateFormat.setTimeZone(TimeZone.getTimeZone("PST")); 
		
    	while( line != null  ){
    		
    		String[]columns = line.split(",");
    		Date key = dateFormat.parse(columns[0]); 
    		
    		String outputLine = outputDateFormat.format(key)+ "," + columns[1] + "," + columns[2];
    		map.put(key, outputLine);
    		
    		//System.out.println("Old: " + line + " New: " + outputLine);
    		line = lineReader.readLine();
    		
    		
    	}
    	
    	
    	File of = new File(outputFile);
    	FileWriter writer = new FileWriter(of);
    	
    	String formattedHtmlString = "['Time'\t";
    	String[]  servers = serverNames.split(",");
    
    	for( String server: servers ){
    		
    		formattedHtmlString = formattedHtmlString + ",";
    		formattedHtmlString = formattedHtmlString + "'";
    		formattedHtmlString = formattedHtmlString + server;
    		formattedHtmlString = formattedHtmlString + "'" + "\t";
    		
    	}
    	formattedHtmlString = formattedHtmlString + "]";
    	formattedHtmlString = formattedHtmlString + ",\n";
    	
    	writer.write(formattedHtmlString);
    	
    	SortedSet<Date> keys = new TreeSet<Date>(map.keySet());
    	String finalLine = null;
    	for (Date key : keys) { 
    	   String sortedLine = map.get(key);
    	   
    	   String[]  columns = sortedLine.split(",");
    	   String outputLine = "['" + columns[0] + "'\t";
    	   
    	   for(String server: servers){
    		   
    		   if( columns[1].trim().equalsIgnoreCase(server.trim()) ){
    		       outputLine = outputLine + "," + columns[2].trim() + "\t" ;
    		   }
    		   else{
    			   outputLine = outputLine + ",0\t";
    		   }
    		   
    	   }
    	   outputLine = outputLine + "]";
    	   finalLine = outputLine;
    	   outputLine = outputLine + ",";
    	   outputLine = outputLine + "\n";
    	   writer.write(outputLine);
           System.out.println(outputLine);

    	}
    	
 	    writer.write(finalLine);
    	lineReader.close();
    	writer.close();
    	
    }
    
}
