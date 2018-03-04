package org.ucl.fhirwork.configuration.data;

public class CacheConfig {
    private boolean enable;
    
    public CacheConfig(String enable) {
	if(enable == null)
	    this.enable = false;
	else
	    this.enable = true;
    }
    public boolean getEnable() {
	return enable;
    }

}
