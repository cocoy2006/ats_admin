package molab.main.java.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import molab.main.java.util.Path;
import molab.main.java.util.PropertiesUtil;

import org.springframework.stereotype.Service;

@Service
public class CfgService {

	public Map<String, String> findAll() {
		Map<String, String> map = new HashMap<String, String>();
		Properties props = PropertiesUtil.load(Path.cfg());
		for(Object obj : props.keySet()) {
			String key = String.valueOf(obj);
			map.put(key, props.getProperty(key));
		}
		return map;
	}
	
	public void set(String key, String value) {
		PropertiesUtil.set(Path.cfg(), key, value);
	}
	
}
