package cz.mxmx.memoryanalyzer.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.Map;

public class InstanceDump extends GenericDump {
	private final ClassDump classDump;
	private final Map<InstanceFieldDump, Object> instanceFieldValues = new HashMap<>();

	public InstanceDump(Long objectId, ClassDump classDump) {
		super(objectId);
		this.classDump = classDump;
	}

	public ClassDump getClassDump() {
		return classDump;
	}

	public Map<InstanceFieldDump, Object> getInstanceFieldValues() {
		return instanceFieldValues;
	}

	public void addInstanceField(InstanceFieldDump instanceFieldDump, Object value) {
		this.instanceFieldValues.put(instanceFieldDump, value);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("classDump", classDump)
				.append("instanceFieldValues", instanceFieldValues)
				.append("instanceId", instanceId)
				.toString();
	}
}
