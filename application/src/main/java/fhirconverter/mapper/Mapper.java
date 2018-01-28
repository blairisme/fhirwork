package fhirconverter.mapper;

import java.util.Vector;
 
public class Mapper {
	private Vector<MapperEntry> entryList;
	public Mapper() {
		initialise();
	}
	private void initialise() {
		entryList = new Vector<MapperEntry>();
		for(int i = 0; i< MapperEntry.size;i++) {
			MapperEntry entry = new MapperEntry();
			entry.getFromConfig(i);
			entryList.add(entry);
		}
	}
	
	
	public Object convert(Object obj, int type) {
		
		MapperEntry entry = entryList.get(type);
		for(String from : entry.getKeys()) {
			String to = entry.get(from);
			//convert from from to to
		}
		
		return null;
		
	}
	
	
	
}
