package cz.mxmx.memoryanalyzer.memorywaste;

import cz.mxmx.memoryanalyzer.model.MemoryDump;
import cz.mxmx.memoryanalyzer.model.memorywaste.Waste;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WasteAnalyzerPipeline implements WasteAnalyzer {

	private final List<WasteAnalyzer> analyzers;
	private final boolean multiThreaded;

	public WasteAnalyzerPipeline(List<WasteAnalyzer> analyzers, boolean multiThreaded) {
		this.analyzers = analyzers;
		this.multiThreaded = multiThreaded;
	}

	@Override
	public List<Waste> findMemoryWaste(MemoryDump memoryDump) {
		return this.multiThreaded ? this.findMemoryWasteMultiThreaded(memoryDump) : this.findMemoryWasteSingleThreaded(memoryDump);
	}

	private List<Waste> findMemoryWasteMultiThreaded(MemoryDump memoryDump) {
		List<Waste> wasteList = new ArrayList<>();
		List<Thread> threads = new ArrayList<>();

		for (WasteAnalyzer analyzer : this.analyzers) {
			Thread t = new Thread(() -> {
				List<Waste> memoryWaste = analyzer.findMemoryWaste(memoryDump);
				wasteList.addAll(memoryWaste);
			});

			threads.add(t);
			t.start();
		}

		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return wasteList;
	}

	private List<Waste> findMemoryWasteSingleThreaded(MemoryDump memoryDump) {
		return this.analyzers
				.stream()
				.map(analyzer -> analyzer.findMemoryWaste(memoryDump))
				.reduce(
						new ArrayList<>(),
						(result1, result2) -> Stream.concat(result1.stream(), result2.stream()).collect(Collectors.toList())
				);
	}
}
