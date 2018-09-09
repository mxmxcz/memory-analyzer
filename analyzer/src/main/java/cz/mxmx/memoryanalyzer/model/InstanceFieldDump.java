package cz.mxmx.memoryanalyzer.model;

import java.util.ArrayList;
import java.util.List;

public class InstanceFieldDump<T> {
	private final String name;
	private final Class<T> type;
	private final List<T> values = new ArrayList<>();

	public InstanceFieldDump(String name, Class<T> type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public Class<T> getType() {
		return type;
	}

	public List<T> getValues() {
		return values;
	}

	public void addValue(T value) {
		this.values.add(value);
	}
}
