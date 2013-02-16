package verbosegcparser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class FullGCExtractor {
	
	   public static void main( String[] args ) throws Exception
	   {
		    String usage = "FullGCExtractor gcLogDirectory";
		   
	    	if( args.length  != 1  ){
	    		throw new Exception("Usage: " + usage);
	    	}
	    	
	    	String directory = args[0];    	
	    	List<File> files = findGCFiles(directory);

            for( File f: files ){
            	System.out.println(f.getName());
            	parseFile(f);
            }
	    	
	    	
	    	
	    	
	    	
		   
	   }
	   
	   
	   
	   
	   public static List<File> findGCFiles( String gcLogDirectory ){
		   
	    	File file = new File(gcLogDirectory);
	    	String[] directories = file.list();
	    	
	    	List<File> gcFiles = new ArrayList<File>();
	    	
	    	for( String s : directories ){
	    		
	    		File f = new File(gcLogDirectory+ "/" + s);
	    		
	    		if ( f.isFile() ){
	    			if (s.endsWith("verbosegc.log")){
	    				//System.out.println("Found gc file: " + f.getName() );
	    				gcFiles.add(f);
	    			}
	    		}
	    		
	    	}
	    	
	    	return gcFiles;
		   
	   }
	   
	   
	   private static Map<String, String> parseFile( File f ) throws ParserConfigurationException, SAXException, IOException {
		 
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(f);
		    
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			
			NodeList nList = doc.getElementsByTagName("af");
			search(nList); 
		    return null;
	   }
	   
	   
	   public static void search(NodeList nodelist)
	   {
	       for(int i=0; i<nodelist.getLength(); i++){
	         Node childNode = nodelist.item(i);
	         System.out.println(childNode.getNodeName());
	         
	         NodeList children = childNode.getChildNodes();
	         if (children != null)
	         {
	            search(children);
	         }
	       }
	   }
	   
	   

}
